

package com.alphatrader.javagui.data;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.alphatrader.javagui.AppState;
import com.alphatrader.javagui.data.Bond;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * @author frangelo
 *
 */
public class Notification  {
	  /**
	   * Fetches all unread events of the user and marks them as read
	   * @return all unread events
	   */
	  public static List<Notification> getAllEvents() {
	    List<Notification> myReturn = new ArrayList<>();

	    try {
	      HttpResponse<JsonNode> response = Unirest.get(AppState.getInstance().getApiUrl() + "/api/notifiactions/unread/")
	          .header("accept", "*/*").header("Authorization", "Bearer " + AppState.getInstance().getUser().getToken())
	          .header("X-Authorization", "e1d149fb-0b2a-4cf5-9ef7-17749bf9d144").asJson();
	      

	      JSONArray notifications = response.getBody().getArray();

	      for (int i = 0; i < notifications.length(); i++) {
	        //System.out.println(notifactions.getJSONObject(i).toString(2));
	        myReturn.add(notifications.createFromJson(notifications.getJSONObject(i)));
	      }

	    } catch (UnirestException e) {
	      System.err.println("Error fetching notifiactions: " + e.getMessage());
	    }
	    

	    return myReturn;
	  }
	 /**
	  *	Creates a Notification from the api json answers and marks it as read
	 */
	  public static Notification createFromJson(JSONObject json) {
		  Notification myReturn = new Notification(
				   LocalDateTime.ofInstant(Instant.ofEpochMilli(json.getLong("date")), ZoneId.systemDefault()),
				  json.getJSONObject("content").getString("message")
   );

	  
}
/**
 *  Creates a unread Notification with the given parameters 
 * @param date
 * @param message
 */
public Notification(LocalDateTime date, String message ) {
    this.date = date;
    this.message = message;
  }
@Override
public String toString() {
  String date;
String message;
return "Notifiation{" +
      "message='" + message + '\'' +
      ", date=" + date +
      '}';
}

/** 
 * Markiert ale Notifications als gelesen
 */
public void MarkAllRead(){
	
		 HttpResponse<JsonNode> response = Unirest.put(AppState.getInstance().getApiUrl() + "/api/notifiactions/read/")
		          .header("accept", "*/*").header("Authorization", "Bearer " + AppState.getInstance().getUser().getToken())
		          .header("X-Authorization", "e1d149fb-0b2a-4cf5-9ef7-17749bf9d144").asJson();
	}
/**
 * 
 * @return the Message
 */
public String getMessage()
{
	return message;
}

}


