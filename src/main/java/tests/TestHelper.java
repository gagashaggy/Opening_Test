package tests;

import com.codeborne.selenide.WebDriverRunner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class TestHelper {
    // таймаут для ожидания
    protected final int TIMEOUT = 100;
    // объект для записи логов в файл
    protected FileWriter writer;

    protected abstract String getLogsPath();

    /**
     * Получить название папки для логов за конкретный день
     * @return
     */
    protected String getDateFolderName() {
        return new SimpleDateFormat("yyyy.MM.dd").format(new Date()) + "/";
    }

    /**
     * Создать файл лога
     */
    protected void createLogFile() {
        File logsDir = new File(WebDriverRunner.driver().config().reportsFolder() + "\\" + getLogsPath());
        logsDir.mkdirs();
        try {
            writer = new FileWriter(new File(WebDriverRunner.driver().config().reportsFolder() + "\\"
                    + getLogsPath() + "log " + new SimpleDateFormat("HH.mm.ss").format(new Date()) + ".txt"), true);
        }
        catch (IOException e) {
            logDebug("Не удалось создать файл лога");
        }
    }

    /**
     * Запись в лог (в файл и в консоль)
     * @param logRecord - строка для записи в лог
     */
    protected void log(String logRecord) {
        logDebug(logRecord);
        try {
            writer.write(logRecord + "\n");
            writer.flush();
        } catch (IOException e) {
            logDebug("Не удалось записать строку в файл");
        }
    }

    /**
     * Запись в лог (только в консоль)
     * @param logRecord - строка для записи в лог
     */
    protected void logDebug(String logRecord) {
        System.out.println(logRecord);
    }
}