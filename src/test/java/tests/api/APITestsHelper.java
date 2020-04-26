/**
 * Created on 26 Apr, 2020
 */
package tests.api;

import com.codeborne.selenide.WebDriverRunner;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import org.testng.annotations.BeforeClass;
import tests.TestHelper;
import tests.api.utils.APIPrintStream;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static io.restassured.config.RestAssuredConfig.config;

/**
 * Класс, общий для всех API-тестов
 *
 * @author vmohnachev
 */
public class APITestsHelper extends TestHelper {

    // объект для записи логов в файл
    private APIPrintStream printStream;

    @Override
    protected String getLogsPath() {
        return "api/" + getDateFolderName();
    }

    @Override
    protected void log(String logRecord) {
        printStream.println(logRecord);
        printStream.flush();
    }

    @BeforeClass
    public void createLog() {
        try {
            // объект для записи логов в файл
            printStream = new APIPrintStream(new File(WebDriverRunner.driver().config().reportsFolder()
                    + "\\" + getLogsPath() + this.getClass().getName()
                    + "_" + new SimpleDateFormat("HH.mm.ss").format(new Date()) + ".txt"));
            RestAssured.config = config().logConfig(new LogConfig().defaultStream(printStream));
        } catch (IOException e) {
            logDebug("Не удалось создать файл лога");
        }
    }
}