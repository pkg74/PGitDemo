package stepDefinations;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

public class StepDefination extends Utils {
	RequestSpecification res;
	ResponseSpecification resspec;
	Response response;
	static String place_id;

	TestDataBuild data = new TestDataBuild();

	@Given("Add Place Payload with {string} {string} {string}")
	public void add_place_payload_with(String name, String language, String address) throws IOException {

		// resspec = new
		// ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		res = given().log().all().spec(requestSpecification()).body(data.addPlacePayLoad(name, language, address));
	}

	@When("user calls {string} with {string} http method")
	public void user_calls_with_http_method(String resource, String method) {
		// constructor will be called with value of resource which you pass
		APIResources resourceAPI = APIResources.valueOf(resource);
		System.out.println(resourceAPI.getResource());
		resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		if (method.equalsIgnoreCase("POST")) {
			response = res.when().post(resourceAPI.getResource());
		} else if (method.equalsIgnoreCase("GET")) {
			response = res.when().get(resourceAPI.getResource());
		}
		// response =
		// res.when().post(resourceAPI.getResource()).then().spec(resspec).extract().response();

	}

	@Then("the API call is sucess with status code {int}")
	public void the_api_call_is_sucess_with_status_code(Object statuscode) {
		String responseString = response.asString();
		System.out.println(responseString);
		assertEquals(response.getStatusCode(), statuscode);

	}

	@Then("{string} in response body is {string}")
	public void in_response_body_is(String keyValue, String ExpectedValue) {

		assertEquals(getJsonPath(response, keyValue), ExpectedValue);
	}

	@Then("verify place_Id created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String ExpectName, String resource) throws IOException {
		place_id = getJsonPath(response, "place_id");
		res = given().spec(requestSpecification()).queryParam("place_id", place_id);
		user_calls_with_http_method(resource, "GET");
		String ActualName = getJsonPath(response, "name");
		System.out.println("Get name: " + ActualName);
		assertEquals(ExpectName, ActualName);
	}

	@Given("Delete Paylod")
	public void delete_paylod() throws IOException {
		res=given().spec(requestSpecification()).body(data.deletePlcePayload(place_id));
	}

}
