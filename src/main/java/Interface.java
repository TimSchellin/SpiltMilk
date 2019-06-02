import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.*;
import java.util.concurrent.TimeUnit;

//import org.openqa.selenium.WebDriver;

public class Interface {
    public static String nameFile = "C:\\Users\\Tim-Laptop\\IdeaProjects\\CamelKiller\\names.txt";
    public static String domainFile = ""; //do I need this?

    public static void main(String args[]) {
        try {

            System.out.println("main");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void runAutomation(){
        System.setProperty("webdriver.chrome.driver", "C://chromedriver/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        

    }

    private static void runFileCleaner(String filePath){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String names = reader.readLine();
            reader.close();

            names = names.replace(" ", ""); //remove whitespaces from file

            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(names);
            writer.close();
        } catch (FileNotFoundException noFile) {
            System.out.println("there is no file at location: "+filePath);
            noFile.printStackTrace();
        } catch (IOException io) {
            System.out.println("read error");
            io.getMessage();
            io.printStackTrace();
        }
    }
}
