package edu.northwestern.socialmedia.hestondissertationtest;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Future;

/**
 * Created by matthewheston on 2/9/18.
 */

public class WebPoster {

    public static void PostMessage(Message message) {
        try {

            JSONObject jsonObj = new JSONObject();
            jsonObj.put("message_from", message.getMessageFrom());
            jsonObj.put("handled", message.isHandled() ? 1 : 0);
            jsonObj.put("received_at", message.getReceivedAt().getTime());
            jsonObj.put("responded_at", message.getRespondedAt().getTime());
            jsonObj.put("message_text", message.getMessageText());

            try {
                Future<HttpResponse<String>> future = Unirest.post("http://10.0.2.2:5000/message/")
                        .header("accept", "application/json")
                        .body(jsonObj.toString())
                        .asStringAsync();
            }
            catch (Exception ex) {

            }

        }
        catch(JSONException ex) {
            // still a bad developer tbh
        }
    }
}

