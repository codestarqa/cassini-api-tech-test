package in.reqres.testsuites;

import in.reqres.EndPoints;
import in.reqres.LoginPojo;
import in.reqres.PropertyReader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
public class ReqRestTest {
    @BeforeClass
    public static void inIt() {
        RestAssured.baseURI = PropertyReader.getInstance().getProperty("baseUri");
    }

    //To get registered with payload in JSON format
    @Test
    public void getARegisterUser() {
        given().auth().none()
                .contentType(ContentType.JSON)
                .body(new File("./UserPayload.json"))
                .post(EndPoints.REGISTER)
                .then().statusCode(200).log().all()
                .body("id", equalTo(4), "token", equalTo("QpwL5tke4Pnpja7X4"));
    }
    //To logged in with email and password in body request
    @Test
    public void getBLoginUSer() {

        given().auth().none()
                .contentType(ContentType.JSON)
                .body(LoginPojo.getLoginBody("eve.holt@reqres.in","pistol"))
                .post(EndPoints.LOGIN)
                .then().statusCode(200).log().all()
                .body("token", equalTo("QpwL5tke4Pnpja7X4"));

    }
    //To get all records from the source and assert an id and other details
    @Test
    public void getCResourceList() {
        given().auth().none()
                .contentType(ContentType.JSON)
                .when()
                .get(EndPoints.LIST)
                .then().statusCode(200).log().all()
                .body("page", equalTo(1), "per_page", equalTo(6),
                        "total", equalTo(12), "total_pages", equalTo(2))
                .body("data[4].id", equalTo(5), "data[4].name", equalTo("tigerlily"),
                        "data[4].year", equalTo(2004), "data[4].color", equalTo("#E2583E"),
                        "data[4].pantone_value",equalTo("17-1456"));
    }

}
