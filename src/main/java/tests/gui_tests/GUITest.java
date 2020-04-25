package tests.gui_tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class GUITest extends GUITestsHelper {

    /**
     * 2. UI тестирование
     * Задача:
     * · запустить Chrome
     * · открыть https://www.google.com/
     * · написать в строке поиска «Открытие»
     * · нажать Поиск
     * · проверить, что в результатах поиска есть https://www.open.ru
     * · перейти на сайт https://www.open.ru
     * · проверить в блоке «Курс обмена в интернет-банке», что курс продажи больше курса покупки, для USD и для EUR.
     */
    @Test(description = "UI тестирование")
    public void test001() {
        final String link = "https://www.google.com/";
        final String XPath_Google_Search_Field = "//input[@type='text']";
        final String searchText = "Открытие";
        final String XPath_Google_Search_Button = "//input[@name='btnK']";
        final String XPath_Result = "//a[@href='https://www.open.ru/']";
        final String XPath_Block_Exchange_Rates_Title = "//h2[.='Курс обмена в интернет-банке']";

        log("Открываем ссылку: " + link);
        openLink(link);
        log("Вводим в поисковую строку текст: " + searchText);
        inputText(XPath_Google_Search_Field, searchText);
        log("Нажимаем на кнопку поиска");
        click(XPath_Google_Search_Button);
        log("Проверяем, что в результатах поиска есть https://www.open.ru");
        Assert.assertTrue(isElementDisplayed(XPath_Result), "На странице отсутствуют ссылки на https://www.open.ru");
        log("Открываем https://www.open.ru");
        click(XPath_Result);
        log("Прокручиваем к блоку курсов валют");
        scrollToElement(XPath_Block_Exchange_Rates_Title);
        double usdBuy = getExchangeRate("USD", true);
        log("Покупка долларов: " + usdBuy);
        double usdSale = getExchangeRate("USD", false);
        log("Продажа долларов: " + usdSale);
        log("Проверяем, что " + usdSale + " > " + usdBuy);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(usdSale > usdBuy, "Курс продажи долларов не больше курса покупки");
        double eurBuy = getExchangeRate("EUR", true);
        log("Покупка евро: " + eurBuy);
        double eurSale = getExchangeRate("EUR", false);
        log("Продажа евро: " + eurSale);
        log("Проверяем, что " + eurSale + " > " + eurBuy);
        softAssert.assertTrue(eurSale > eurBuy, "Курс продажи евро не больше курса покупки");
        softAssert.assertAll();
    }

    /**
     * Получить цену покупки или продажи валюты
     *
     * @param currency - валюта (USD, EUR)
     * @param isBuy - покупка (true) или продажа (false)
     * @return стоимость покупки или продажи
     */
    private double getExchangeRate(String currency, boolean isBuy) {
        return Double.parseDouble(getText("//span[contains(@class, 'currency-name') and .='" + currency +
                "']//ancestor::td/following-sibling::td[" + (isBuy ? 1 : 3) + "]//span").replace(",", "."));
    }
}