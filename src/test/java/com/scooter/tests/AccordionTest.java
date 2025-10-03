package com.scooter.tests;

import com.scooter.pageobjects.MainPage;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class AccordionTest extends com.scooter.BaseTest {


      //Метод предоставляет данные для параметризованного теста
    static Stream<Arguments> accordionTestData() {
        return Stream.of(
                Arguments.of(0, "Сутки — 400 рублей. Оплата курьеру — наличными или картой."),
                Arguments.of(1, "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другой."),
                Arguments.of(2, "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."),
                Arguments.of(3, "Только начиная с завтрашнего дня. Но скоро станем расторопнее."),
                Arguments.of(4, "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."),
                Arguments.of(5, "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."),
                Arguments.of(6, "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."),
                Arguments.of(7, "Да, обязательно. Всем самокатов! И Москве, и Московской области.")
        );
    }


     // Параметризованный тест для проверки всех вопросов аккордеона

    @ParameterizedTest
    @MethodSource("accordionTestData")
    public void testAccordionItem(int questionIndex, String expectedAnswer) {
        MainPage mainPage = new MainPage(driver);
        mainPage.acceptCookies();

        // Кликаем на вопрос с заданным индексом
        mainPage.clickAccordionItem(questionIndex);

        // Проверяем что ответ стал видимым
        assertTrue(mainPage.isAccordionVisible(questionIndex),
                "Ответ на вопрос с индексом " + questionIndex + " должен быть видим после клика");

        // Проверяем текст ответа
        String actualAnswer = mainPage.getAccordionText(questionIndex);
        assertEquals(expectedAnswer, actualAnswer,
                "Текст ответа на вопрос с индексом " + questionIndex + " не соответствует ожидаемому");
    }
}