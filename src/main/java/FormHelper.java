import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class FormHelper {

    private int popSize;
    private Random getRandom;
    private NameGenerator namer;

    private ArrayList<String> names;
    private ArrayList<String> domains;
    private ArrayList<String> comments;

    private static String namesFilePath = "names.txt";
    private static String domainsFilePath = "domains.txt";
    private static String commentsFilePath = "comments_tz.csv";
    private static String auditWordsFilePath = "auditWords.txt";

    private String fullName;
    private String firstName;
    private String emailAddress;
    private String comment;

    FormHelper(int popSize, boolean generateComments) throws Exception {
        this.popSize = popSize;
        this.getRandom = new Random();
        this.comments = new ArrayList<String>();
        this.namer = new NameGenerator(namesFilePath);
        this.domains = fileParser(domainsFilePath, ",");


        //populateNames();
        if(generateComments) populateComments();
    }

    public String getEmail() {
        return this.emailAddress;
    }

    public String getFullName() {
        return this.fullName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.fullName.replace(this.firstName+" ", "");
    }

    public String getComment(){
        return this.comment;
    }

    public void createNewPerson(){
        createName();
        createEmail();
        createComment();
    }

    private void createName() {
        this.firstName = namer.getRandomFirstName();
        this.fullName = namer.getFullName(this.firstName);
    }

    private void createEmail() {
        int randInt = getRandom.nextInt((99 - 10) + 1) + 10;
        int index = getRandom.nextInt(domains.size());
        String emailSuffix = "@" + domains.get(index) + ".com";
        this.emailAddress = this.firstName + randInt + emailSuffix;
    }

    private void createComment() {
        int index = getRandom.nextInt(comments.size());
        this.comment = comments.get(index);
    }

    private void populateComments() throws Exception {
        Reader in = new FileReader("comments_tz.csv");
        Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
        ArrayList<String> commentList = new ArrayList<String>();
        for (CSVRecord record : records) {
            commentList.add(record.get(4));
        }
        filterComments(commentList);
    }

    private void filterComments(ArrayList <String> dirtyComments) {
        ArrayList<String> auditWords = readContentFile(auditWordsFilePath);
        for (String comment : dirtyComments){
            if(comment.length() >= 1) {
                boolean clean = true;
                for (String badWord : auditWords) {
                    if (comment.toLowerCase().contains(badWord)){
                        clean = false;
                    }
                }
                if(clean) {
                    System.out.println(comment);
                    this.comments.add(comment);
                }
            }
        }
    }

    private static ArrayList<String> fileParser(String filePath, String delimiter) {
        String[] stringArray = null;
        try{
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String strings = br.readLine();
            stringArray = strings.split(delimiter);
        } catch (FileNotFoundException noFile) {
            System.out.println("file not found!");
            noFile.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<String>(Arrays.asList(stringArray));
    }

    private static ArrayList<String> readContentFile(String filePath) {
        String[] nameArray = null;
        try{
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String names = br.readLine();
            nameArray = names.split(",");
        } catch (FileNotFoundException noFile) {
            System.out.println("name source file not found");
            noFile.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<String>(Arrays.asList(nameArray));
    }
}
