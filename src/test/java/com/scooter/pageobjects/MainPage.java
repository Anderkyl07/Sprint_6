package com.scooter.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MainPage {
    private final WebDriver driver;

    // Локаторы для выпадающего списка "Вопросы о важном"
    private final By cookieButton = By.id("rcc-confirm-button");    // Кнопка принятия куки (нашли по id из HTML)
    private final By accordionHeading = By.className("accordion__button"); // Заголовки аккордеона (вопросы) - ищем по классу
    private final By accordionPanel = By.className("accordion__panel");  // Панели аккордеона (ответы) - ищем по классу

    // Локаторы для кнопок заказа
    private final By topOrderButton = By.className("Button_Button__ra12g"); // Верхняя кнопка в хедере
    private final By bottomOrderButton = By.xpath(".//button[contains(text(), 'Заказать') and contains(@class, 'Button_UltraBig')]"); // Нижняя кнопка "Заказать" - используем XPath для более точного поиска
    /**
     * Конструктор - инициализирует Page Object с драйвером
     */
    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    // Методы для работы с куки
    public void acceptCookies() {
        driver.findElement(cookieButton).click();  // Находим элемент кнопки по локатору и кликаем по нему
    }

    // Методы для аккордеона
    public void clickAccordionItem(int index) {
        WebElement accordionItem = driver.findElements(accordionHeading).get(index);  // Находим все элементы-вопросы и берем нужный по индексу
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", accordionItem); // Прокручиваем страницу до элемента
        accordionItem.click();  // Кликаем на элемент
    }

    public String getAccordionText(int index) {
        WebElement panel = driver.findElements(accordionPanel).get(index);  // Находим панель с ответом по индексу
        return panel.getText();
    }

    public boolean isAccordionVisible(int index) {
        WebElement panel = driver.findElements(accordionPanel).get(index);    // Находим панель с ответом по индексу
        return panel.isDisplayed();  // Возвращаем текст из элемента
    }

    // Методы для кнопок заказа
    public void clickTopOrderButton() {
        driver.findElement(topOrderButton).click();    // Находим элемент кнопки
    }

    public void clickBottomOrderButton() {
        WebElement button = driver.findElement(bottomOrderButton);       // Находим элемент кнопки
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", button);   // Прокручиваем до кнопки (она внизу страницы)
        button.click();  // Кликаем на кнопку
    }
}