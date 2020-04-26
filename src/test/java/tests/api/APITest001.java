/**
 * Created on 26 Apr, 2020
 */
package tests.api;

import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.api.data.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Пример API-теста
 *
 * @author vmohnachev
 */
public class APITest001 extends APITestsHelper {

    /**
     * Получить список пользователей (GET  https://reqres.in/api/users?page=2),
     * замапить на объект и проверить, что все поля пришли (достаточно на notNull).
     */
    @Test(description = "API-тест 001")
    public void test001() {

        Response response = given()
                .log().all()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().all()
                .statusCode(200)
                .body("page", notNullValue())
                .body("per_page", notNullValue())
                .body("total", notNullValue())
                .body("total_pages", notNullValue())
                .extract()
                .response();

        List<User> users = new ArrayList<>();
        JSONObject object = new JSONObject(response.asString());
        List<Object> data = ((JSONArray)object.get("data")).toList();
        for (Object obj : data) {
            Map<String, Object> map =(HashMap<String, Object>) obj;
            Map<String, String> userMap = new HashMap<>();
            userMap.put("id", ((Integer) map.get("id")).toString());
            userMap.put("email", (String) map.get("email"));
            userMap.put("first_name", (String) map.get("first_name"));
            userMap.put("last_name", (String) map.get("last_name"));
            userMap.put("avatar", (String) map.get("avatar"));
            users.add(new User(
                    userMap.get("id"),
                    userMap.get("email"),
                    userMap.get("first_name"),
                    userMap.get("last_name"),
                    userMap.get("avatar")));
            for (User user : users) {
                log("Проверяем, что поле id != null: \t id = " + user.getId());
                Assert.assertNotNull(user.getId());
                log("Проверяем, что поле email != null: \t email = " + user.getEmail());
                Assert.assertNotNull(user.getEmail());
                log("Проверяем, что поле first_name != null: \t first_name = " + user.getFirstName());
                Assert.assertNotNull(user.getFirstName());
                log("Проверяем, что поле last_name != null: \t last_name = " + user.getLastName());
                Assert.assertNotNull(user.getLastName());
                log("Проверяем, что поле avatar != null: \t avatar = " + user.getAvatar());
                Assert.assertNotNull(user.getAvatar());
            }
        }
    }
}