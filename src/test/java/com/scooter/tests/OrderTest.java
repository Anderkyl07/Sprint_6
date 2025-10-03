package com.scooter.tests;

import com.scooter.pageobjects.MainPage;
import com.scooter.pageobjects.OrderPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class OrderTest extends com.scooter.BaseTest {

    // Параметризованные данные для тестов
    static Stream<Object[]> orderData() {
        return Stream.of(
                new Object[]{
                        "Иван", "Иванов", "ул. Пушкина, д. 10", "Бульвар Рокоссовского", "89991234567",
                        "15.12.2024", "трое суток", "black", "Позвонить за час"
                },
                new Object[]{
                        "Мария", "Петрова", "пр. Ленина, д. 25", "Черкизовская", "89997654321",
                        "20.12.2024", "сутки", "grey", "Оставить у двери"
                }
        );
    }

    @ParameterizedTest
    @MethodSource("orderData")
    public void testOrderFromTopButton(String name, String surname, String address,
                                       String metro, String phone, String date,
                                       String period, String color, String comment) {
        MainPage mainPage = new MainPage(driver);
        mainPage.acceptCookies();
        mainPage.clickTopOrderButton();

        completeOrder(name, surname, address, metro, phone, date, period, color, comment);
    }

    @ParameterizedTest
    @MethodSource("orderData")
    public void testOrderFromBottomButton(String name, String surname, String address,
                                          String metro, String phone, String date,
                                          String period, String color, String comment) {
        MainPage mainPage = new MainPage(driver);
        mainPage.acceptCookies();
        mainPage.clickBottomOrderButton();

        completeOrder(name, surname, address, metro, phone, date, period, color, comment);
    }

    private void completeOrder(String name, String surname, String address,
                               String metro, String phone, String date,
                               String period, String color, String comment) {
        OrderPage orderPage = new OrderPage(driver);

        // Заполнение первой части формы
        orderPage.fillFirstPart(name, surname, address, metro, phone);

        // Заполнение второй части формы
        orderPage.fillSecondPart(date, period, color, comment);

        // Подтверждение заказа
        orderPage.confirmOrder();
    }
}