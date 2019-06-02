import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
    private static String CommentsFilePath = "comments.txt";

    private String fullName;
    private String firstName;
    private String emailAddress;
    private String comment;

    FormHelper(int popSize, boolean generateComments){
        this.popSize = popSize;
        this.getRandom = new Random();
        this.namer = new NameGenerator(namesFilePath);
        this.domains = fileParser(domainsFilePath, ",");

        populateNames();
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

    private void createNewPerson(){
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

    private void populateNames() {
        ArrayList<String> fullNames = new ArrayList<String>();
        for (int i = 0; i < this.popSize; i++) {
            this.names.add(getFullName());
        }
    }

    private void populateComments() {
        // THIS LINE WILL NEED TO BE CHANGED
        this.comments = fileParser(CommentsFilePath, ",");
        //
        ArrayList<String> auditList = fileParser("auditList.txt", ",");
        filterComments(auditList);
    }

    private void filterComments(ArrayList <String> auditList) {
        for( String badWord : auditList) {
            for (String thisComment : this.comments) {
                if(thisComment.contains(badWord)) {
                    int index = this.comments.indexOf(thisComment);
                    this.comments.remove(index);
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
}
