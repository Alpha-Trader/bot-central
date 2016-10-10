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
		                                           .header("accept", "*/*")
		                                           .queryString("username", "<Benutzername>")
		                                           .field("password", "<Passwort>")
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
	}

}
