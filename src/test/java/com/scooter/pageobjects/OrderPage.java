package com.scooter.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderPage {
    private final WebDriver driver;

    // Локаторы для формы заказа
    private final By nameInput = By.xpath(".//input[@placeholder='* Имя']");// Поле ввода имени
    private final By surnameInput = By.xpath(".//input[@placeholder='* Фамилия']");// Поле ввода фамилии
    private final By addressInput = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']");// Поле ввода адреса
    private final By metroInput = By.xpath(".//input[@placeholder='* Станция метро']");// Поле ввода станции метро
    private final By phoneInput = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']");// Поле ввода телефона
    private final By nextButton = By.xpath(".//button[text()='Далее']");// Кнопка "Далее" для перехода ко второй части формы

    // Локаторы для второй части формы
    private final By dateInput = By.xpath(".//input[@placeholder='* Когда привезти самокат']"); // Поле ввода даты доставки
    private final By rentalPeriod = By.className("Dropdown-placeholder");// Выпадающий список с периодом аренды
    private final By periodOption = By.xpath(".//div[@class='Dropdown-option']");// Опции в выпадающем списке
    private final By colorBlack = By.id("black"); // Чекбокс черного цвета
    private final By colorGrey = By.id("grey");// Чекбокс серого цвета
    private final By commentInput = By.xpath(".//input[@placeholder='Комментарий для курьера']");// Поле для комментария курьеру
    private final By orderButton = By.xpath(".//button[contains(text(), 'Заказать')]");// Кнопка "Заказать" для оформления
    private final By confirmButton = By.xpath(".//button[text()='Да']"); // Кнопка подтверждения заказа "Да"
    private final By successMessage = By.xpath(".//div[contains(text(), 'Заказ оформлен')]");// Сообщение об успешном оформлении заказа

    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    // Заполнение первой части формы
    public void fillFirstPart(String name, String surname, String address, String metro, String phone) {
        driver.findElement(nameInput).sendKeys(name); // Заполняем поле имени
        driver.findElement(surnameInput).sendKeys(surname); // Заполняем поле фамилии
        driver.findElement(addressInput).sendKeys(address); // Заполняем поле адреса
        driver.findElement(metroInput).sendKeys(metro); // Заполняем поле метро и выбираем станцию
        driver.findElements(By.className("select-search__row")).get(0).click(); // Выбор станции метро
        driver.findElement(phoneInput).sendKeys(phone); // Заполняем поле телефона
        driver.findElement(nextButton).click();  // Нажимаем кнопку "Далее" для перехода ко второй части
    }

    // Заполнение второй части формы
    public void fillSecondPart(String date, String period, String color, String comment) {
        // Установка даты
        WebElement dateField = driver.findElement(dateInput);  // Находим поле ввода даты
        dateField.sendKeys(Keys.CONTROL + "a");   // Очищаем поле: Ctrl+A
        dateField.sendKeys(Keys.DELETE); //и Delete
        dateField.sendKeys(date);  // Вводим новую дату
        dateField.sendKeys(Keys.ENTER); // Нажимаем Enter для подтверждения

        // Выбор периода аренды
        driver.findElement(rentalPeriod).click();  // Открываем выпадающий список
        driver.findElements(periodOption).stream()    // Преобразуем список в поток
                .filter(element -> element.getText().equals(period)) // Фильтруем по тексту
                .findFirst()  // Берем первый подходящий элемент
                .ifPresent(WebElement::click);     // Если нашли - кликаем

        // Выбор цвета
        if ("black".equals(color)) {
            driver.findElement(colorBlack).click();
        } else if ("grey".equals(color)) {
            driver.findElement(colorGrey).click();
        }

        // Комментарий
        driver.findElement(commentInput).sendKeys(comment);

        // Нажатие кнопки заказа
        driver.findElement(orderButton).click();
    }

    // Подтверждение заказа
    public void confirmOrder() {
        driver.findElement(confirmButton).click();
    }

    // Проверка успешного оформления
    public boolean isOrderSuccess() {
        // Используем явное ожидание - ждем до 5 секунд пока элемент появится
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(successMessage));
        // Проверяем, что элемент отображается
        return driver.findElement(successMessage).isDisplayed();
    }
}