package adaptesrs;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ReqresAdapter {
    public static final String URL = "https://reqres.in";

    public String get(String url, int statusCode) {
        return given()
                .header("Content-Type", "application/json")
                .when()
                .get(URL + url)
                .then()
                .statusCode(statusCode)
                .log().all()
                .extract().body().asString();
    }

    public Response post(String url, String body, int statusCode) {
        return  given()
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post(URL + url)
                .then()
                .statusCode(statusCode)
                .log().all()
                .extract().response();
    }

    public void delete(String url, int statusCode) {
        given()
                .header("Content-Type", "application/json")
                .when()
                .delete(URL + url)
                .then()
                .statusCode(statusCode)
                .log().all();
    }

    public Response put(String url, String body, int statusCode) {
        return given()
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .put(URL + url)
                .then()
                .statusCode(statusCode)
                .log().all()
                .extract().response();
    }

    public Response patch(String url,String body, int statusCode) {
        return given()
                .header("Content-Type", "application/json")
                .when()
                .patch(URL + url)
                .then()
                .log().all()
                .extract().response();
    }
}
