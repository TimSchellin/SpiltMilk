import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class FormHelper {

    private Random randGenerator;
    private NameGenerator namer;

    private ArrayList<String> domains;
    private ArrayList<String> comments;

    private static String namesFilePath = "C:\\Users\\Tim-Laptop\\IdeaProjects\\CamelKiller\\names.txt";
    private static String domainsFilePath = "C:\\Users\\Tim-Laptop\\IdeaProjects\\CamelKiller\\domains.txt";
    private static String CommentsFilePath = "";

    private int popSize;

    private String fullName;
    private String firstName;
    private String emailAddress;
    private String comment;

    FormHelper(boolean generateComments, int popSize){
        this.randGenerator = new Random();
        this.namer = new NameGenerator(namesFilePath);
        this.domains = fileHandler(domainsFilePath, ",");
        this.popSize = popSize;

        generateNames();
        generateEmails();

        if(generateComments){
            this.comments =
        }
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
        createEmailAddress();
        createComment();
    }

    private void createName() {
        this.firstName = namer.getRandomFirstName();
        this.fullName = namer.getFullName(this.firstName);
    }

    private void createEmailAddress() {
        int randInt = randGenerator.nextInt((99 - 10) + 1) + 10;
        this.emailAddress = this.firstName + randInt + createEmailSuffix();
    }

    private String createEmailSuffix() {
        int index = randGenerator.nextInt(domains.size());
        String email = "@" + domains.get(index) + ".com";
        return null;
    }

    private void createComment(){
        int index = randGenerator.nextInt(comments.size());
        this.comment = comments.get(index);
    }

    private void populateComments(){
        // not created yet
    }

    private static ArrayList<String> fileHandler(String filePath, String delimiter) {
        String[] nameArray = null;
        try{
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String names = br.readLine();
            nameArray = names.split(delimiter);
        } catch (FileNotFoundException noFile) {
            System.out.println("file not found!");
            noFile.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<String>(Arrays.asList(nameArray));
    }
}
