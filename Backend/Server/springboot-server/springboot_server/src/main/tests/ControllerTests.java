
package onetomany;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;


@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.RANDOM_PORT)

@RunWith(SpringRunner.class)
public class ControllerTests {

  @LocalServerPort
	int port;

	@Before
	public void setUp() {
		RestAssured.port = port;
		RestAssured.baseURI = "http://localhost";
	}

    @Test
	public void getItem() {
		// Send request and receive response
		Response response = RestAssured.given().
				header("Content-Type", "text/plain").
				header("charset","utf-8").
				pathParam("id", 1).
				when().
				get("/getitem/");


		// Check status code
		int statusCode = response.getStatusCode();
		assertEquals(200, statusCode);

		// Check response body for correct response
		String returnString = response.getBody().asString();
		try {
			JSONArray returnArr = new JSONArray(returnString);
			JSONObject returnObj = returnArr.getJSONObject(returnArr.length()-1);
			assertEquals(1, returnObj.get("id"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void addItem() {
		// Send request and receive response
		Response response = RestAssured.given().
				header("Content-Type", "text/plain").
				header("charset","utf-8").
				param("name", "TestItem").
				param("description", "TestItem").
				param("type", "TestItem").
				when().
				post("/additem2/");


		// Check status code
		int statusCode = response.getStatusCode();
		assertEquals(200, statusCode);

		// Check response body for correct response
		String returnString = response.getBody().asString();
		assertEquals("item added", returnString);
	}

	@Test
	public void addFriend() {
		// Send request and receive response
		Response response = RestAssured.given().
				header("Content-Type", "text/plain").
				header("charset","utf-8").
				param("u1", "TestTest").
				param("u2", "Testing").
				when().
				post("/addFriend");


		// Check status code
		int statusCode = response.getStatusCode();
		assertEquals(200, statusCode);

		// Check response body for correct response
		String returnString = response.getBody().asString();
		assertEquals("Success", returnString);
	}

	@Test
	public void addFriendFail() {
		// Send request and receive response
		Response response = RestAssured.given().
				header("Content-Type", "text/plain").
				header("charset","utf-8").
				param("u1", "Userthatlikelydoesntexist").
				param("u2", "Testing").
				when().
				post("/addFriend");


		// Check status code
		int statusCode = response.getStatusCode();
		assertEquals(200, statusCode);

		// Check response body for correct response
		String returnString = response.getBody().asString();
		assertEquals("User not found", returnString);
	}

	@Test
	public void getUserWorldList() {
		// Send request and receive response
		Response response = RestAssured.given().
				header("Content-Type", "text/plain").
				header("charset","utf-8").
				param("u1", "Userthatlikelydoesntexist").
				param("u2", "Testing").
				when().
				post("/addFriend");


		// Check status code
		int statusCode = response.getStatusCode();
		assertEquals(200, statusCode);

		// Check response body for correct response
		List<String> returnString = response.getBody();
		assertEquals("Jopm's Abominable Realm of Absolute Destruction", returnString.get(0));
		
	}

	@Test
	public void deleteCharacter() {
		// Send request and receive response
		Response response = RestAssured.given().
				header("Content-Type", "text/plain").
				header("charset","utf-8").
				param("characterName", "Jopm").
				param("username", "JopmV3").
				when().
				post("/deleteCharacter/");


		// Check status code
		int statusCode = response.getStatusCode();
		assertEquals(200, statusCode);

		// Check response body for correct response
		String returnString = response.getBody().asString();
		assertEquals("Success", Success);
	}

	@Test
	public void addInventory() {
		// Send request and receive response
		Response response = RestAssured.given().
				header("Content-Type", "text/plain").
				header("charset","utf-8").
				param("characterName", "Jopm").
				param("username", "JopmV3").
				when().
				post("/addInventory/");


		// Check status code
		int statusCode = response.getStatusCode();
		assertEquals(200, statusCode);

		// Check response body for correct response
		String returnString = response.getBody().asString();
		assertEquals("It worked", Success);
	}

	@Test
	public void delInventory() {
		// Send request and receive response
		Response response = RestAssured.given().
				header("Content-Type", "text/plain").
				header("charset","utf-8").
				param("characterName", "Test1").
				param("username", "username").
				when().
				post("/delInventory/");


		// Check status code
		int statusCode = response.getStatusCode();
		assertEquals(200, statusCode);

		// Check response body for correct response
		String returnString = response.getBody().asString();
		assertEquals("It worked", Success);
	}
	
}
