import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

//import org.openqa.selenium.WebDriver;

public class Interface {

    public static void main(String args[]) {
        try {
            //testFormFill(100);
            runAutomation(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testFormFill(int popSize) throws Exception {
        FormHelper filler = new FormHelper(popSize, true);
        for(int i = 0; i < popSize; i++){
            filler.createNewPerson();
            System.out.println("======= FORM #"+i+" ===================");
            System.out.println("First Name:\n"+filler.getFirstName());
            System.out.println("\nLast Name:\n"+filler.getLastName());
            System.out.println("\nFull Name:\n"+filler.getFullName());
            System.out.println("\nEmail:\n"+filler.getEmail());
            System.out.println("\nComment:\n"+filler.getComment()+"\n\n");
        }
    }

    private static void runAutomation(int batchSize) throws Exception {
        String baseURL = "https://desertfarms.com/blogs/news";
        System.setProperty("webdriver.chrome.driver", "C://chromedriver/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.get(baseURL);
        List<WebElement> pages = driver.findElements(By.xpath("//*[contains(text(), 'Comments')]"));

        FormHelper filler = new FormHelper(batchSize * pages.size(), true);
        ArrayList<String> links = new ArrayList<String>();

        for(WebElement e : pages) {
            links.add(e.getAttribute("href"));
        }
        for (String link : links) {
            for (int i = 0; i < batchSize; i++) {
                try {
                    driver.get(link);
                    filler.createNewPerson();

                    WebElement nameField = driver.findElement(By.id("comment_author"));
                    WebElement emailField = driver.findElement(By.id("comment_email"));
                    WebElement commentField = driver.findElement(By.id("comment_body"));
                    WebElement submit = driver.findElement(By.id("comment-submit"));

                    nameField.sendKeys(filler.getFullName());
                    emailField.sendKeys(filler.getEmail());
                    commentField.sendKeys(filler.getComment());
                    submit.click();

                }
                catch (org.openqa.selenium.WebDriverException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    private static void runFileCleaner(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String names = reader.readLine();
            reader.close();

            names = names.replace(" ", ""); //remove whitespaces from file

            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(names);
            writer.close();
        }
        catch (FileNotFoundException noFile) {
            System.err.println("there is no file at location: "+filePath);
            noFile.printStackTrace();
        }
        catch (IOException io) {
            System.err.println("read error");
            io.getMessage();
            io.printStackTrace();
        }
    }

    private static ArrayList<String> readContentFile(String filePath) {
        String[] nameArray = null;
        try{
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String names = br.readLine();
            nameArray = names.split(",");
        }
        catch (FileNotFoundException noFile) {
            System.out.println("name source file not found");
            noFile.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<String>(Arrays.asList(nameArray));
    }
}
