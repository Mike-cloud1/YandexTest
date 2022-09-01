package uiTests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {
    public static void setUp(){
        Configuration.baseUrl = "https://market.yandex.ru/";
    }

    @BeforeAll
    public static void init(){
        setUp();
    }

    @AfterAll
    public static void close(){
        Selenide.closeWebDriver();
    }
}