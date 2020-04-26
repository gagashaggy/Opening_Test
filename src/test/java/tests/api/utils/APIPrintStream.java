/**
 * Created on 26 Apr, 2020
 */
package tests.api.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * Класс, обеспечивающий логирование сообщений RestAssured в консоль и в текстовый файл при выполнении API-тестов
 *
 * @author vmohnachev
 */
public class APIPrintStream extends PrintStream {

    public APIPrintStream(File file) throws FileNotFoundException {
        super(file);
    }

    @Override
    public void println(String s) {
        super.println(s);
        flush();
        System.out.println(s);
    }
}