import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * @author christopher.guckes@torq-dev.de
 */
public class BotCentral {

    /**
     * @var string
     */
    private String apiUrl = "http://stable.alpha-trader.com/api/";

    /**
     * @param args
     */
    public static void main(String[] args) {
        token = this.getToken();
        this.getCompanies(token);
        this.getBonds(token);
    }

    private String getToken() {
        String token = "";
        try {
            HttpResponse<JsonNode> response = Unirest.post(this.apiUrl + "user/token/")
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

        return token;
    }

    private void getCompanies(String token) {
        try {
            HttpResponse<JsonNode> response = Unirest.get(this.apiUrl + "companies/")
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

    private void getBonds(String token) {
        try {
            HttpResponse<JsonNode> response = Unirest.get(this.apiUrl + "bonds/")
                    .header("accept", "*/*").header("Authorization", "Bearer " + token)
                    .header("X-Authorization", "e1d149fb-0b2a-4cf5-9ef7-17749bf9d144").asJson();

            JSONArray companyBonds = response.getBody().getArray();

            for (int i = 0; i < companyBonds.length(); i++) {
                System.out.println(companyBonds.getJSONObject(i).toString(2));
            }
        } catch (UnirestException e) {
            System.err.println("Error fetching bonds : " + e.getMessage());
        }
    }
}
