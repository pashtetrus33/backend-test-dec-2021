package com.geekbrains.backend.test.imgur;

import java.util.Properties;

import com.geekbrains.backend.test.FunctionalTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;


public class ImgurApiFunctionalTest extends FunctionalTest {


    private static Properties properties;
    private static String TOKEN;
    private static String imageHash;
    private static String imageHashForFavorite;


    @BeforeAll
    static void beforeAll() throws Exception {
        properties = readProperties();
        RestAssured.baseURI = properties.getProperty("imgur-api-url");
        TOKEN = properties.getProperty("imgur-api-token");
        imageHashForFavorite = properties.getProperty("imageHashForFavorite");

    }

    @Test
    @DisplayName("Тест запроса информации об аккаунте")
    void getAccountBaseTest() {
        String userName = "pashtetrus";
        given()
                .auth()
                .oauth2(TOKEN)
                .log()
                .all()
                .expect()
                .body("data.id", is(157777337))
                .log()
                .all()
                .when()
                .get("account/" + userName);
    }

    @Test
    @DisplayName("Тест загрузки изображения")
    void postImageTest() {
        Response response = RestAssured.given()
                .auth()
                .oauth2(TOKEN)
                .multiPart("image", getFileResource("img.jpg"))
                .formParam("name", "MyCoolPicture")
                .formParam("title", "Very nice picture!")
                .log()
                .all()
                .expect()
                .body("data.size", is(46314))
                .body("data.type", is("image/jpeg"))
                .body("data.name", is("MyCoolPicture"))
                .body("data.title", is("Very nice picture!"))
                .log()
                .all()
                .when()
                .post("upload");

        //Вытаскиваем из ответа id и записываем в строковую переменную imageHash
        String resString = response.asString();
        String[] resultStr = resString.split("\"");
        imageHash = resultStr[9].trim();

    }

    // TODO: 08.12.2021 Домашка протестировать через RA минимум 5 различных end point-ов
    @Test
    @DisplayName("Тест добавления изображения в Favourites (избранное)")
    void favoriteAnImageTest() {
        given()
                .auth()
                .oauth2(TOKEN)
                .log()
                .all()
                .expect()
                .body("success", is(true))
                .body("status", is(200))
                .log()
                .all()
                .when()
                .post("image/" + imageHashForFavorite + "/favorite");
    }

    @Test
    @DisplayName("Тест изменения title и description изображения")
    void updateImageInformationTest() {
        given()
                .auth()
                .oauth2(TOKEN)
                .formParam("title", "New Title")
                .formParam("description", "New description!")
                .log()
                .all()
                .expect()
                .header("Content-Type", "application/json")
                .header("server", "nginx")
                .body("data", is(true))
                .body("success", is(true))
                .body("status", is(200))
                .log()
                .all()
                .when()
                .post("image/" + imageHash);
    }

    @Test
    @DisplayName("Тест удаления изображения")
    void imageDeletionTest() {
        given()
                .auth()
                .oauth2(TOKEN)
                .log()
                .all()
                .expect()
                .header("Content-Type", "application/json")
                .header("server", "cat factory 1.0")
                .body("data", is(true))
                .body("success", is(true))
                .body("status", is(200))
                .log()
                .all()
                .when()
                .delete("image/" + imageHash);
    }

    @Test
    @DisplayName("Тест создания комментария")
    void commentCreationTest() {
        given()
                .auth()
                .oauth2(TOKEN)
                .formParam("image_id", imageHashForFavorite)
                .formParam("comment", "This is my first comment :)")
                .log()
                .all()
                .expect()
                .header("server", "cat factory 1.0")
                .body("data.error", is("Error saving comment."))
                .body("data.request", is("/3/comment"))
                .body("data.method", is("POST"))
                .log()
                .all()
                .when()
                .post("comment");
    }

    @Test
    @DisplayName("Тест запроса всех постов из аккаунта")
    void getAccountImagesTest() {
        String userName = "pashtetrus";
        given()
                .auth()
                .oauth2(TOKEN)
                .log()
                .all()
                .expect()
                .header("Content-Type", "application/json")
                .body("data[0].type", is("image/jpeg"))
                .body("data[1].has_sound", is(false))
                .log()
                .all()
                .when()
                .get("account/" + userName + "/images");
    }
    @Test
    @DisplayName("Тест проверки электронного ящика аккаунта")
    void verifyUsersEmailTest() {
        String userName = "pashtetrus";
        given()
                .auth()
                .oauth2(TOKEN)
                .log()
                .all()
                .expect()
                .header("Content-Type", "application/json")
                .body("data", is(false))
                .body("success", is(true))
                .body("status", is(200))
                .log()
                .all()
                .when()
                .get("account/" + userName + "/verifyemail");
    }
}