/**
 * Created on 25 Apr, 2020
 */
package tests.gui;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import tests.TestHelper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

/**
 * Класс, общий для всех GUI-тестов
 *
 * @author vmohnachev
 */
public class GUITestsHelper extends TestHelper {

    // объект для записи логов в файл
    private FileWriter writer;

    /**
     * Создать файл лога
     */
    void createLogFile() {
        File logsDir = new File(WebDriverRunner.driver().config().reportsFolder() + "\\" + getLogsPath());
        logsDir.mkdirs();
        try {
            writer = new FileWriter(new File(WebDriverRunner.driver().config().reportsFolder()
                    + "\\" + getLogsPath() + this.getClass().getName() + "_"
                    + new SimpleDateFormat("HH.mm.ss").format(new Date()) + ".txt"), true);
        }
        catch (IOException e) {
            logDebug("Не удалось создать файл лога");
        }
    }

    @Override
    protected void log(String logRecord) {
        logDebug(logRecord);
        try {
            writer.write(logRecord + "\n");
            writer.flush();
        } catch (IOException e) {
            logDebug("Не удалось записать строку в файл");
        }
    }

    @BeforeClass
    public void openBrowser(){
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
        Selenide.open();
        WebDriverRunner.getWebDriver().manage().window().maximize();
        createLogFile();
    }

    @AfterClass
    public void closeBrowser(){
        makeScreenshot();
        Selenide.closeWebDriver();
    }

    /**
     * Сделать скриншот и сохранить в папку с логами
     */
    void makeScreenshot() {
        Selenide.screenshot(getLogsPath() + "screenshot_" +
                new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss").format(new Date()));
    }

    /**
     * Получить путь к папке с логами
     * @return
     */
    protected final String getLogsPath() {
        return "gui/" + getDateFolderName();
    }

    /**
     * Открыть ссылку
     * @param link - адрес ссылки
     */
    void openLink(String link) {
        Selenide.open(link);
    }

    /**
     * Ввести текст в некоторое поле
     * @param xpath - путь к полю
     * @param text - текст для ввода
     */
    void inputText(String xpath, String text) {
        $x(xpath).sendKeys(text);
    }

    /**
     * Кликнуть на некоторый элемент на странице.
     * Перед нажатием осуществляется ожидание появления элемента
     * @param xpath - путь к элементу
     */
    void click(String xpath) {
        waitForElementToBeDisplayed(xpath);
        $x(xpath).click();
    }

    /**
     * Ожидание появления некоторого элемента на странице
     * @param xpath - путь к элементу
     */
    void waitForElementToBeDisplayed(String xpath) {
        int attempts = 0;
        while ($$x(xpath).isEmpty()) {
            if (attempts < TIMEOUT) {
                Selenide.sleep(10);
                attempts++;
            }
            else throw new RuntimeException("Элемент " + xpath + " не появился");
        }
        WebElement element = $$x(xpath).get(0);
        while (!element.isDisplayed()) {
            if (attempts < TIMEOUT) {
                Selenide.sleep(10);
                attempts++;
            }
            else throw new RuntimeException("Элемент " + xpath + " не виден");
        }
    }

    /**
     * Проверить, что элемент отображается на странице
     * @param xpath - путь к элементу
     * @return true, если элемент отображается, иначе - false
     */
    boolean isElementDisplayed(String xpath) {
        return $x(xpath).isDisplayed();
    }

    /**
     * Прокрутить к нужному элементу на странице
     * @param xpath - путь к элементу
     */
    void scrollToElement(String xpath) {
        $x(xpath).scrollIntoView(true);
    }

    /**
     * Получить текст, содержащийся в элементе
     * @param xpath - путь к элементу
     * @return {@link String}
     */
    String getText(String xpath) {
        return $x(xpath).getText();
    }
}