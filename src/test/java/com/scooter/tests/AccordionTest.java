package com.scooter.tests;

import com.scooter.pageobjects.MainPage;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AccordionTest extends com.scooter.BaseTest {

    @Test
    public void testAccordionItems() {
        MainPage mainPage = new MainPage(driver);
        mainPage.acceptCookies();

        // Тестируем несколько элементов аккордеона
        String[] expectedTexts = {
                "Сутки — 400 рублей. Оплата курьеру — наличными или картой.",
                "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим.",
                "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."
        };

        for (int i = 0; i < 3; i++) {
            mainPage.clickAccordionItem(i);
            assertTrue(mainPage.isAccordionVisible(i), "Аккордеон должен быть видим после клика"); // Проверяем что панель с ответом стала видимой
            assertEquals(expectedTexts[i], mainPage.getAccordionText(i),
                    "Текст аккордеона не соответствует ожидаемому");  // Проверяем что текст ответа соответствует ожидаемому
        }
    }
}