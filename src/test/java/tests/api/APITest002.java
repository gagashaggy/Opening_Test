/**
 * Created on 26 Apr, 2020
 */
package tests.api;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.api.data.User;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Пример API-теста
 *
 * @author vmohnachev
 */
public class APITest002 extends APITestsHelper {
    /**
     * Создать пользователя (POST https://reqres.in/api/users) с данными из примера на сайте: подготовить тело запроса,
     * отправить запрос с телом, замапить ответ на объект и проверить, что в ответе те же самые значения из запроса.
     */
    @Test(description = "API-тест 002")
    public void test002() {
        Map<String, String> data = new HashMap<>();
        data.put("name", "morpheus");
        data.put("job", "leader");
        Response response = given()
                .body(data)
                .log().all()
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().all()
                .statusCode(201)
                .body("name", notNullValue())
                .body("job", notNullValue())
                .body("id", notNullValue())
                .body("createdAt", notNullValue())
                .extract()
                .response();

        JSONObject object = new JSONObject(response.asString());
        User user = new User(
                object.getString("name"),
                object.getString("job"),
                object.getString("id"),
                object.getString("createdAt"));

        log("Проверяем, что поле name равно значению из запроса: \t name = " + user.getName());
        Assert.assertEquals(user.getName(), data.get("name"));
        log("Проверяем, что поле job равно значению из запроса: \t job = " + user.getJob());
        Assert.assertEquals(user.getJob(), data.get("job"));
    }
}