/**
 * Created on 25 Apr, 2020
 */
package tests;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Класс, общий для всех тестов
 *
 * @author vmohnachev
 */
public abstract class TestHelper {
    // таймаут для ожидания
    protected final int TIMEOUT = 100;

    /**
     * Получить путь к папке с логами
     * @return
     */
    protected abstract String getLogsPath();

    /**
     * Получить название папки для логов за конкретный день
     * @return
     */
    protected String getDateFolderName() {
        return new SimpleDateFormat("yyyy.MM.dd").format(new Date()) + "/";
    }

    /**
     * Запись в лог (в файл и в консоль)
     * @param logRecord - строка для записи в лог
     */
    protected abstract void log(String logRecord);

    /**
     * Запись в лог (только в консоль)
     * @param logRecord - строка для записи в лог
     */
    protected void logDebug(String logRecord) {
        System.out.println(logRecord);
    }
}