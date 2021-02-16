package tests;

import adaptesrs.ReqresAdapter;
import com.google.gson.Gson;

import io.restassured.response.Response;
import lombok.Data;
import objects.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;

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
    public String regEmail = "eve.holt@reqres.in";
    public String regPassword = "pistol";
    public String regUnsuccessfulEmail = "sydney@fife";
    public String loginEmail = "eve.holt@reqres.in";
    public String loginPassword = "cityslicka";
    public String loginUnsuccessfulEmail = "peter@klaven";

    Gson converter = new Gson();

    @Test(description = "Test of users list")
    public void listUserIdTest() throws FileNotFoundException {
        ListData expectedList = converter.fromJson(new FileReader("src/test/java/resources/usersList.json"), ListData.class);
        String suiteFromResponse = new ReqresAdapter().get(LIST_USERS, 200);
        ListData actualResult = converter.fromJson(suiteFromResponse, ListData.class);
        Assert.assertEquals(actualResult, expectedList);
    }

    @Test(description = "Test of single user")
    public void singleUserResponseTest() throws FileNotFoundException {
        UsersData expectedList = converter.fromJson(new FileReader("src/test/java/resources/singleUser.json"), UsersData.class);
        String suiteFromResponse = new ReqresAdapter().get(SINGLE_USER, 200);
        UsersData actualResult = converter.fromJson(suiteFromResponse, UsersData.class);
        Assert.assertEquals(actualResult, expectedList);
    }

    @Test(description = "Single user is not found test")
    public void singleUserNotFoundError() {
        new ReqresAdapter().get(SINGLE_USER_NOT_FOUND, 404);
    }

    @Test(description = "Resource list test")
    public void listResourceTest() throws FileNotFoundException {
        ListResource expectedList = converter.fromJson(new FileReader("src/test/java/resources/resourceList.json"), ListResource.class);
        String suiteFromResponse = new ReqresAdapter().get(LIST_RESOURCE, 200);
        ListResource actualResult = converter.fromJson(suiteFromResponse, ListResource.class);
        Assert.assertEquals(actualResult, expectedList);
    }

    @Test(description = "Single resource test")
    public void singleResourceTest() throws FileNotFoundException {
        Resource expectedList = converter.fromJson(new FileReader("src/test/java/resources/singleResource.json"), Resource.class);
        String suiteFromResponse = new ReqresAdapter().get(SINGLE_RESOURCE, 200);
        Resource actualResult = converter.fromJson(suiteFromResponse, Resource.class);
        Assert.assertEquals(actualResult, expectedList);
    }

    @Test(description = "Resource not found test")
    public void singleResourceNotFoundTest() {
        new ReqresAdapter().get(SINGLE_RESOURCE_404, 404);
    }

    @Test(description = "Create test")
    public void createPostTest() {
        Suite expectedResult = converter.fromJson("{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"leader\"\n" +
                "}", Suite.class);
        Suite suite = Suite.builder()
                .name(name)
                .job(job)
                .build();
        Response suiteFromResponse = new ReqresAdapter().post(CREATE, converter.toJson(suite), 201);
        Suite actualResult = converter.fromJson(suiteFromResponse.asString(), Suite.class);
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test(description = "Put test")
    public void putTest() {
        Suite suite = Suite.builder()
                .name(name)
                .job(jobUpdate)
                .build();
        Response suiteFromResponse = new ReqresAdapter().put(PUT, converter.toJson(suite), 200);
        Suite actualResult = converter.fromJson(suiteFromResponse.asString(), Suite.class);
        Assert.assertEquals(actualResult, suite);
    }

    @Test(description = "Delete test")
    public void deleteTest() {
        new ReqresAdapter().delete(PUT, 204);
    }

    @Test(description = "Registration successful test")
    public void registerSuccessfulTest() {
        RegistrationAnswer expectedAnswer = converter.fromJson("{\n" +
                "    \"id\": 4,\n" +
                "    \"token\": \"QpwL5tke4Pnpja7X4\"\n" +
                "}", RegistrationAnswer.class);
        Suite suite = Suite.builder()
                .email(regEmail)
                .password(regPassword)
                .build();
        Response suiteFromResponse = new ReqresAdapter().post(REGISTER_SUCCESSFUL, converter.toJson(suite), 200);
        RegistrationAnswer actualResult = converter.fromJson(suiteFromResponse.asString(), RegistrationAnswer.class);
        Assert.assertEquals(actualResult, expectedAnswer);
    }

    @Test(description = "Registration unsuccessful test")
    public void registerUnsuccessfulTest() {
        RegistrationAnswer expectedAnswer = converter.fromJson("{\n" +
                "    \"error\": \"Missing password\"\n" +
                "}", RegistrationAnswer.class);
        Suite suite = Suite.builder()
                .email(regUnsuccessfulEmail)
                .build();
        Response suiteFromResponse = new ReqresAdapter().post(REGISTER_SUCCESSFUL, converter.toJson(suite), 400);
        RegistrationAnswer actualResult = converter.fromJson(suiteFromResponse.asString(), RegistrationAnswer.class);
        Assert.assertEquals(actualResult, expectedAnswer);
    }

    @Test(description = "Login successful test")
    public void loginTest() {
        RegistrationAnswer expectedAnswer = converter.fromJson("{\n" +
                "    \"token\": \"QpwL5tke4Pnpja7X4\"\n" +
                "}", RegistrationAnswer.class);
        Suite suite = Suite.builder()
                .email(regEmail)
                .password(regPassword)
                .build();
        Response suiteFromResponse = new ReqresAdapter().post(LOGIN, converter.toJson(suite), 200);
        RegistrationAnswer actualResult = converter.fromJson(suiteFromResponse.asString(), RegistrationAnswer.class);
        Assert.assertEquals(actualResult, expectedAnswer);
    }

    @Test(description = "Login unsuccessful test")
    public void loginUnsuccessfulTest() {
        RegistrationAnswer expectedAnswer = converter.fromJson("{\n" +
                "    \"error\": \"Missing password\"\n" +
                "}", RegistrationAnswer.class);
        Suite suite = Suite.builder()
                .email(loginUnsuccessfulEmail)
                .build();
        Response suiteFromResponse = new ReqresAdapter().post(LOGIN, converter.toJson(suite), 400);
        RegistrationAnswer actualResult = converter.fromJson(suiteFromResponse.asString(), RegistrationAnswer.class);
        Assert.assertEquals(actualResult, expectedAnswer);
    }

    @Test(description = "Delayed test")
    public void delayedTest() throws FileNotFoundException {
        UserData expectedList = converter.fromJson(new FileReader("src/test/java/resources/delayExpectedList.json"), UserData.class);
        String suiteFromResponse = new ReqresAdapter().get(DELAYED, 200);
        UserData actualResult = converter.fromJson(suiteFromResponse, UserData.class);
        Assert.assertEquals(actualResult, expectedList);
    }
}
