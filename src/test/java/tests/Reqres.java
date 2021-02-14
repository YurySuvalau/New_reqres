package tests;

import adaptesrs.ReqresAdapter;
import com.google.gson.Gson;
import lombok.Data;
import objects.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Data
public class Reqres {
    public static final String URL = "https://reqres.in";
    public static final String LIST_USERS = "/api/users?page=2";
    public static final String SINGLE_USER = "/api/users/2";
    public static final String SINGLE_USER_NOT_FOUND = "/api/users/23";
    public static final String LIST_RESOURCE = "/api/unknown";
    public static final String SINGLE_RESOURCE = "/api/unknown/2";
    public static final String SINGLE_RESOURCE_404 = "/api/unknown/23";
    public static final String CREATE = "/api/users";
    public static final String PUT = "/api/users/2";
    public static final String REGISTER_SUCCESSFUL = "/api/register";
    public static final String LOGIN = "/api/login";
    public static final String DELAYED = "/api/users?delay=3";
    public String name = "morpheus";
    public String job = "leader";
    public String jobUpdate = "zion resident";

    @Test
    public void listUserIdTest() {
        String body = given()
                .when()
                .get(URL + LIST_USERS)
                .then()
                .extract().body().asString();
        UsersList listUser = new Gson().fromJson(body, UsersList.class);
        int id = listUser.getData().get(1).getId();
        Assert.assertEquals(id, 8);
    }

    @Test
    public void singleUserResponseTest() {
        given()
                .when()
                .get(URL + SINGLE_USER)
                .then()
                .statusCode(200);
    }

    @Test
    public void singleUserNotFoundError() {
        given()
                .when()
                .get(URL + SINGLE_USER_NOT_FOUND)
                .then()
                .statusCode(404);
    }

    @Test
    public void listResourceTest() {
        String body = given()
                .when()
                .get(URL + LIST_RESOURCE)
                .then()
                .extract().body().asString();
        ListResource res = new Gson().fromJson(body, ListResource.class);
        String pantoneValue = res.getData().get(4).getPantoneValue();
        Assert.assertEquals(pantoneValue, "17-1456");
    }

    @Test
    public void singleResourceTest() {
        String body = given()
                .when()
                .get(URL + SINGLE_RESOURCE)
                .then()
                .extract().body().asString();
        SingleResource name = new Gson().fromJson(body, SingleResource.class);
        String singleName = name.getData().getName();
        Assert.assertEquals(singleName, "fuchsia rose");
    }

    @Test
    public void singleResourceNotFoundTest() {
        new ReqresAdapter().get(SINGLE_RESOURCE_404, 404);
    }

    @Test
    public void createPostTest() {
        Suite suite = Suite.builder()
                .name(name)
                .job(job)
                .build();
        String actualResult = new ReqresAdapter().post(CREATE, "{\n" +
                "    \"name\": \"" + name + "\",\n" +
                "    \"job\": \"" + job + "\"\n" +
                "}", 201).getBody().jsonPath().getJsonObject("name");
        Assert.assertEquals(actualResult, name);
    }

    @Test
    public void putTest() {
        Suite suite = Suite.builder()
                .name(name)
                .job(jobUpdate)
                .build();
        String actualResult = new ReqresAdapter().put(PUT, "{\n" +
                "    \"name\": \"" + name + "\",\n" +
                "    \"job\": \"" + jobUpdate + "\"\n" +
                "}", 200).getBody().jsonPath().getJsonObject("name");
        Assert.assertEquals(actualResult, name);
    }

    @Test
    public void deleteTest() {
        Suite suite = Suite.builder()
                .name(name)
                .job(job)
                .build();
        new ReqresAdapter().post(CREATE, "{\n" +
                "    \"name\": \"" + name + "\",\n" +
                "    \"job\": \"" + job + "\"\n" +
                "}", 201);
        new ReqresAdapter().delete(PUT, 204);
    }

    @Test
    public void registerSuccessfulTest() {
        Suite suite = Suite.builder()
                .email("eve.holt@reqres.in")
                .password("pistol")
                .build();
        int actualResult = new ReqresAdapter().post(REGISTER_SUCCESSFUL, "{\n" +
                "    \"email\": \"eve.holt@reqres.in\",\n" +
                "    \"password\": \"pistol\"\n" +
                "}", 200).getBody().jsonPath().getJsonObject("id");
        Assert.assertEquals(actualResult, 4);
    }

    @Test
    public void registerUnsuccessfulTest() {
        Suite suite = Suite.builder()
                .email("sydney@fife")
                .build();
        String actualResult = new ReqresAdapter().post(REGISTER_SUCCESSFUL, "{\n" +
                "    \"email\": \"sydney@fife\"\n" +
                "}", 400).getBody().jsonPath().getJsonObject("error");
        Assert.assertEquals(actualResult, "Missing password");
    }

    @Test
    public void loginTest() {
        Suite suite = Suite.builder()
                .email("eve.holt@reqres.in")
                .password("cityslicka")
                .build();
        String actualResult = new ReqresAdapter().post(LOGIN, "{\n" +
                "    \"email\": \"eve.holt@reqres.in\",\n" +
                "    \"password\": \"cityslicka\"\n" +
                "}", 200).getBody().jsonPath().getJsonObject("token");
        Assert.assertEquals(actualResult, "QpwL5tke4Pnpja7X4");
    }

    @Test
    public void loginUnsuccessfulTest() {
        Suite suite = Suite.builder()
                .email("peter@klaven")
                .build();
        String actualResult = new ReqresAdapter().post(LOGIN, "{\n" +
                "    \"email\": \"peter@klaven\"\n" +
                "}", 400).getBody().jsonPath().getJsonObject("error");
        Assert.assertEquals(actualResult, "Missing password");
    }

    @Test
    public void delayedTest() {
        String body = new ReqresAdapter().get(DELAYED, 200);
        UsersList list = new Gson().fromJson(body, UsersList.class);
        int actualResult = list.getData().get(0).getId();
        Assert.assertEquals(actualResult, 1);
    }
}
