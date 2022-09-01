package uiTests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pages.YandexPage;

class MainTest extends BaseTest {

    @ParameterizedTest
    @DisplayName("Тестовой задание")
    @CsvSource(value = {
            "Lenovo, 25000, 30000"
    })
    void goingToGoal(String notebook, String minPrice, String maxPrice) {
        YandexPage yandexPage = new YandexPage();
        yandexPage
                .openPage()
                .goToCatalog()
                .goToCategory("Компьютеры")
                .goToSubItems("Ноутбуки").selectNotebook(notebook)
                .setPrice(minPrice,maxPrice)
                .compareTitle(notebook)
                .comparePrice(minPrice,maxPrice);
    }
}