import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;

@SuppressWarnings("BusyWait")
public class MainClass {

    public static void main(String[] args) {

        ArrayList<String> phrase = new ArrayList<>();
        System.setProperty("webdriver.chrome.driver", "/bin/chromedriver");
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.manage().window().maximize();

        try {
            driver.get("https://klavogonki.ru/");
            wait.until(ExpectedConditions.textToBe(By.linkText("Быстрый старт"), "Быстрый старт"));
            driver.findElement(By.linkText("Быстрый старт")).click();
            driver.findElement(By.xpath("//input[@onclick=\"$('howtoplay').hide();\"]")).click();
            //wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("hiddentext")));

            while (!driver.findElement(By.xpath("//*[@id=\"hiddentext\"]")).getAttribute("style").equals("display: none;")) {
                Thread.sleep(500);
            }
            String typefocus = driver.findElement(By.xpath("//*[@id='typefocus']")).getText();
            phrase.add(typefocus);
            phrase.add(" ");
            String afterfocus = driver.findElement(By.xpath("//*[@id='afterfocus']")).getText();
            phrase.add(afterfocus);
            phrase.replaceAll(a ->
                    a.replace("a", "а").replace("o", "о").replace("c", "с").replace("e", "е").replace("p", "р"));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//input[@value='Набирайте здесь текст после начала игры' and @disabled='']")));
            WebElement word = driver.findElement(By.xpath("//input[@value='Набирайте здесь текст после начала игры']"));

            for (String piece : phrase) {
                word.sendKeys(piece);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}