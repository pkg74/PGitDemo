package stepDefinations;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {

	@Before("@DeletePlace")
	public void beforeScenario() throws IOException {
		// write a code that will give you place id
		// execute this code only when place id is null

		StepDefination m = new StepDefination();
		if (StepDefination.place_id==null) {
			m.add_place_payload_with("shetty", "French", "Asia");
			m.user_calls_with_http_method("AddPlaceAPI", "POST");
			m.verify_place_id_created_maps_to_using("shetty", "getPlaceAPI");
			//git demo continue
			System.out.println("postjira");
			System.out.println("postjira2");
			System.out.println("postjira3");
		}
	}

}
