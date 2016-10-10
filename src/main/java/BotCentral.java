import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/**
 * @author christopher.guckes@torq-dev.de
 */
public class BotCentral {

    /**
     * @var string
     */
    private static String apiUrl = "http://stable.alpha-trader.com/api/";

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(apiUrl);
        String token = getToken(apiUrl);
        System.out.println(token);
        getCompanies(token,apiUrl);

        getBonds(token,apiUrl);
    }

    private static String getToken(String apiUrl)
    {
        String token = "";

        try {
            System.out.println(apiUrl+ "user/token/");

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

        return token;
    }

    private static void getCompanies(String token, String apiUrl) {
        try {
            HttpResponse<JsonNode> response = Unirest.get(apiUrl + "companies/")
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

    private static void getBonds(String token,String apiUrl) {
        try {
            HttpResponse<JsonNode> response = Unirest.get( apiUrl+ "bonds/")
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
