package com.geekbrains.backend.test.imgur;
import java.util.Properties;
import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ImgurApiFunctionalTest extends ImgurApiAbstractTest  {

    private static String CURRENT_IMAGE_ID;

    @Test
    @Order(1)
    @DisplayName("Тест запроса информации об аккаунте")
    void getAccountBaseTest() {
        String userName = "pashtetrus";
        given()
                .spec(requestSpecification)
                .log()
                .all()
                .expect()
                .spec(responseSpecification)
                .body("data.id", is(157777337))
                .log()
                .all()
                .when()
                .get("account/" + userName);
    }

    @Test
    @Order(2)
    @DisplayName("Тест загрузки изображения")
    void postImageTest() {
        CURRENT_IMAGE_ID = given()
                .spec(requestSpecification)
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
                .post("upload")
                .body()
                .jsonPath()
                .getString("data.id");

    }

    @Test
    @Order(3)
    @DisplayName("Тест добавления изображения в Favourites (избранное)")
    void favoriteAnImageTest() {
        given()
                .spec(requestSpecification)
                .log()
                .all()
                .expect()
                .spec(responseSpecification)
                .log()
                .all()
                .when()
                .post("image/" + CURRENT_IMAGE_ID + "/favorite");
    }

    @Test
    @Order(4)
    @DisplayName("Тест изменения title и description изображения")
    void updateImageInformationTest() {
        given()
                .spec(requestSpecification)
                .formParam("title", "New Title")
                .formParam("description", "New description!")
                .log()
                .all()
                .expect()
                .spec(responseSpecification)
                .header("server", "nginx")
                .body("data", is(true))
                .log()
                .all()
                .when()
                .post("image/" + CURRENT_IMAGE_ID);
    }

    @Test
    @Order(5)
    @DisplayName("Тест удаления изображения")
    void imageDeletionTest() {
        given()
                .spec(requestSpecification)
                .log()
                .all()
                .expect()
                .spec(responseSpecification)
                .header("server", "cat factory 1.0")
                .body("data", is(true))
                .log()
                .all()
                .when()
                .delete("image/" + CURRENT_IMAGE_ID);
    }

    @Test
    @DisplayName("Тест создания комментария")
    void commentCreationTest() {
        given()
                .spec(requestSpecification)
                .formParam("image_id", "8xGCvWR")
                .formParam("comment", "This is my first comment :)")
                .log()
                .all()
                .expect()
                .spec(responseSpecification)
                .header("server", "cat factory 1.0")
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
                .spec(requestSpecification)
                .log()
                .all()
                .expect()
                .spec(responseSpecification)
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
                .spec(requestSpecification)
                .log()
                .all()
                .expect()
                .spec(responseSpecification)
                .body("data", is(false))
                .log()
                .all()
                .when()
                .get("account/" + userName + "/verifyemail");
    }

}