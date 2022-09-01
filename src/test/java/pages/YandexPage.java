package pages;

import com.codeborne.selenide.*;
import org.junit.jupiter.api.Assertions;
import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Selenide.*;

public class YandexPage {

    public YandexPage() {
    }

    public final SelenideElement
            catalog = $("#catalogPopupButton"),
            minPrice = $x("//div[@data-auto='filter-range-glprice']//span[@data-auto='filter-range-min']//input"),
            maxPrice = $x("//div[@data-auto='filter-range-glprice']//span[@data-auto='filter-range-max']//input"),
            pagesMore = $x("//button[@data-auto='pager-more']"),
            loader = $x("//span[@aria-label='Загрузка...']"),
            mark = $x("//label[text()='Найти производителя']/following-sibling::input");

    ElementsCollection categories = $$x("//li[@data-zone-name='category-link']"),
            subItems = $$x("//ul[@data-autotest-id=\"subItems\"]/li"),
            searchFilters = $$x("//div[@data-grabber='SearchFilters']//label"),
            title = $$x("//h3[@data-zone-name=\"title\"]"),
            price = $$x("//div[@data-zone-name=\"price\"]//span[@data-auto=\"mainPrice\"]"),
            showAll = $$x("//span[@role='button']");

    /**
     * go to catalog
     *
     * @return YandexPage
     */
    public YandexPage goToCatalog() {
        catalog.click();
        return this;
    }

    /**
     * @param category
     *
     * @return YandexPage
     */
    public YandexPage goToCategory(String category) {
        categories.find(Condition.text(category)).click();
        return this;
    }

    /**
     * select notebook
     *
     * @param notebook
     * @return YandexPage
     */
    public YandexPage selectNotebook(String notebook) {
        showAll.find(Condition.text("Показать всё")).click();
        mark.click();
        mark.setValue(notebook);
        SelenideElement element = searchFilters.find((Condition.text(notebook)));
        if (element.getAttribute("aria-checked").equals("false"))
            element.click();
        return this;
    }

    public YandexPage goToSubItems(String subItem) {
        loader.shouldBe(Condition.not(Condition.visible));
        subItems.find(Condition.text(subItem)).click();
        return this;
    }

    /**
     * set min and max price
     *
     * @param min/max
     * @return YandexPage
     */
    public YandexPage setPrice(String min, String max) {
        minPrice.click();
        minPrice.setValue(min);
        maxPrice.click();
        maxPrice.setValue(max);
        return this;
    }

    /**
     * check that chosen notebook is correct
     *
     * @param notebook
     * @return YandexPage
     */
    public YandexPage compareTitle(String notebook) {
        loader.shouldBe(Condition.not(Condition.visible));
        pagesMore.scrollIntoView(false);
        for (SelenideElement element : title) {
            String name = element.getText().toLowerCase();
            Assertions.assertTrue(name.contains(notebook.toLowerCase()), name + " Не содержит " + notebook);

        }
        return this;
    }

    /**
     * check that price is correct
     *
     * @param priceMin/priceMax
     * @return YandexPage
     */
    public YandexPage comparePrice(String priceMin, String priceMax) {
        loader.shouldBe(Condition.not(Condition.visible));
        pagesMore.scrollIntoView(false);
        for (SelenideElement element : price) {
            int realPrice = Integer.parseInt(element.getText().replaceAll("\\D", ""));
            Assertions.assertTrue(Integer.parseInt(priceMin) < realPrice && realPrice < Integer.parseInt(priceMax),
                    realPrice + " не входит в диапазон");
        }
        return this;
    }

    /**
     * Setting the browser window in maximum size
     *
     * @return YandexPage
     */
    public YandexPage openPage() {
        open("/");
        WebDriverRunner.getWebDriver().manage().window().maximize();
        WebDriverRunner.getWebDriver().manage().timeouts().implicitlyWait(2000, TimeUnit.MILLISECONDS);
        return this;
    }
}