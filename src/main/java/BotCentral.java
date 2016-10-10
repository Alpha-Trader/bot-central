import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * @author christopher.guckes@torq-dev.de
 *
 */
public class BotCentral {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String token = "";
		try {
			HttpResponse<JsonNode> response = Unirest.post("http://stable.alpha-trader.com/user/token/")
					.header("accept", "*/*").queryString("username", "Steerforth").field("password", "lollies")
					.asJson();
			JSONObject body = response.getBody().getObject();

			if (body.optInt("code", -1) == 200) {
				token = body.getString("message");
			} else {
				System.err.println("Login failed.");
			}
		} catch (UnirestException e) {
			System.err.println("Login error: " + e.getMessage());
		}

		System.out.println(token);

		try {
			HttpResponse<JsonNode> response = Unirest.get("http://stable.alpha-trader.com/api/companies/")
					.header("accept", "*/*").header("Authorization", "Bearer " + token)
					.header("X-Authorization", "e1d149fb-0b2a-4cf5-9ef7-17749bf9d144").asJson();

			JSONArray companyNodes = response.getBody().getArray();

			for (int i = 0; i < companyNodes.length(); i++) {
				System.out.println(companyNodes.getJSONObject(i).toString(2));
			}
		} catch (UnirestException e) {
			System.err.println("Error fetching companies: " + e.getMessage());
		}
	}

}
