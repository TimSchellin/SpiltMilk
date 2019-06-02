import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class NameGenerator {

    private static Random generator = new Random();

    private ArrayList<String> allNames;
    private ArrayList<String> firstNames;
    private ArrayList<String> lastNames;

    NameGenerator(String filePath) {
        this.allNames = readNameFile(filePath);
        this.firstNames = getNamesOfType("first");
        this.lastNames = getNamesOfType("last");
    }

    public String getRandomFirstName() {
        int index = generator.nextInt(this.firstNames.size());
        return this.firstNames.get(index);
    }

    public String getRandomLastName() {
        int index = generator.nextInt(this.lastNames.size());
        return this.lastNames.get(index);
    }

    public String getFullName() {
       return getRandomFirstName() + " " + getRandomLastName();
    }

    public String getFullName(String firstName) {
        return firstName + " " + getRandomLastName();
    }

    public ArrayList<String> getFullNameList(int howMany) {
        ArrayList<String> fullNames = new ArrayList<String>();
        for(int i = 0; i < howMany; i++) {
            fullNames.add(getFullName());
        }
        return fullNames;
    }

    private ArrayList<String> getNamesOfType(String nameType) {
        boolean isSurname = nameType.equals("last");
        ArrayList<String> namesList = new ArrayList<String>();
        for(String currentName : this.allNames) {
            char endsWith = getLastChar(currentName);
            boolean surnameCondition = (endsWith == 's' || endsWith == 'r');
            if(isSurname && surnameCondition) {
                namesList.add(currentName);
            }
            else if(!isSurname && !surnameCondition) {
                namesList.add(currentName);
            }
        }
        return namesList;
    }

    private ArrayList<String> readNameFile(String filePath) {
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

    private static char getLastChar(String word) {
        return word.charAt(word.length() - 1);
    }
}
