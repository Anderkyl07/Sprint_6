package com.scooter.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class OrderPage {
    private final WebDriver driver;

    // Локаторы для первой части формы
    private final By nameInput = By.xpath(".//input[@placeholder='* Имя']");
    private final By surnameInput = By.xpath(".//input[@placeholder='* Фамилия']");
    private final By addressInput = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']");
    private final By metroInput = By.xpath(".//input[@placeholder='* Станция метро']");
    private final By phoneInput = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']");
    private final By nextButton = By.xpath(".//button[text()='Далее']");

    // Локаторы для выпадающего списка метро
    private final By metroDropdown = By.className("select-search__select");
    private final By metroOption = By.xpath(".//li[@class='select-search__row']//button");

    // Локаторы для второй части формы
    private final By dateInput = By.xpath(".//input[@placeholder='* Когда привезти самокат']");
    private final By rentalPeriod = By.className("Dropdown-root");
    private final By periodOption = By.xpath(".//div[@class='Dropdown-option']");
    private final By colorBlack = By.id("black");
    private final By colorGrey = By.id("grey");
    private final By commentInput = By.xpath(".//input[@placeholder='Комментарий для курьера']");
    private final By orderButton = By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/button[2]");
    private final By confirmButton = By.xpath(".//button[text()='Да']");
    private final By successMessage = By.xpath(".//div[contains(text(), 'Заказ оформлен')]");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    public void fillFirstPart(String name, String surname, String address, String metroStation, String phone) {
        // Заполняем основные поля
        driver.findElement(nameInput).sendKeys(name);
        driver.findElement(surnameInput).sendKeys(surname);
        driver.findElement(addressInput).sendKeys(address);
        driver.findElement(phoneInput).sendKeys(phone);

        // Заполняем поле метро
        fillMetroStation(metroStation);

        // Нажимаем "Далее"
        driver.findElement(nextButton).click();
    }
    //Метод для первой страницы
    private void fillMetroStation(String metroStation) {
        // Кликаем на поле ввода метро
        WebElement metroField = driver.findElement(metroInput);
        metroField.click();
        metroField.sendKeys(metroStation);

        // Ждем появления выпадающего списка
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(metroDropdown));

        // Ищем нужную станцию метро в списке
        List<WebElement> metroOptions = driver.findElements(metroOption);
        for (WebElement option : metroOptions) {
            if (option.getText().contains(metroStation)) {
                option.click();
                return;
            }
        }

        // Если не нашли точное совпадение, кликаем на первую опцию
        if (!metroOptions.isEmpty()) {
            metroOptions.get(0).click();
        }
    }
//метод для второй страницы
    public void fillSecondPart(String date, String period, String color, String comment) {
        // Заполняем дату
        fillDate(date);

        // Выбираем период аренды
        selectRentalPeriod(period);

        // Выбираем цвет
        selectColor(color);

        // Заполняем комментарий
        driver.findElement(commentInput).sendKeys(comment);

        // Нажимаем кнопку заказа
        driver.findElement(orderButton).click();
    }

    private void fillDate(String date) {
        WebElement dateField = driver.findElement(dateInput);

        //  вводим дату
        dateField.sendKeys(date);
        dateField.sendKeys(Keys.ENTER);

        // Кликаем вне поля чтобы закрыть календарь
        driver.findElement(By.tagName("body")).click();
    }

    private void selectRentalPeriod(String period) {
        // Открываем выпадающий список
        driver.findElement(rentalPeriod).click();

        // Ждем появления опций
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(periodOption));

        // Ищем нужный период
        List<WebElement> periods = driver.findElements(periodOption);
        for (WebElement periodOption : periods) {
            if (periodOption.getText().equals(period)) {
                periodOption.click();
                return;
            }
        }

        // Если не нашли, выбираем первую опцию
        if (!periods.isEmpty()) {
            periods.get(0).click();
        }
    }

    private void selectColor(String color) {
        if ("black".equalsIgnoreCase(color)) {
            driver.findElement(colorBlack).click();
        } else if ("grey".equalsIgnoreCase(color)) {
            driver.findElement(colorGrey).click();
        }
    }

    public void confirmOrder() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(confirmButton));
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