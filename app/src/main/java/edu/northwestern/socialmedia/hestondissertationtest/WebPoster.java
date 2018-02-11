package edu.northwestern.socialmedia.hestondissertationtest;

import android.content.Context;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.scottyab.aescrypt.AESCrypt;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.Future;

/**
 * Created by matthewheston on 2/9/18.
 */

public class WebPoster {

    static String enc = "enc";

    public static void PostMessage(Context context, Message message) {
        try {
            String FILENAME = "id_file";
            String participantId = "";
            FileInputStream fis;
            try {
                fis = context.openFileInput(FILENAME);
                byte[] input = new byte[fis.available()];
                while (fis.read(input) != -1) {}
                participantId += new String(input);
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
            }

            JSONObject jsonObj = new JSONObject();
            jsonObj.put("message_from", AESCrypt.encrypt(enc,message.getMessageFrom()));
            jsonObj.put("message_from_name", AESCrypt.encrypt(enc,message.getMessageFromName()));
            jsonObj.put("in_response_to", AESCrypt.encrypt(enc,message.getInResponseTo()));
            jsonObj.put("handled", message.isHandled() ? 1 : 0);
            jsonObj.put("received_at", message.getReceivedAt().getTime());
            jsonObj.put("responded_at", message.getRespondedAt().getTime());
            jsonObj.put("message_text", AESCrypt.encrypt(enc,message.getMessageText()));
            jsonObj.put("puid", message.getUid());
            jsonObj.put("participant_id", participantId);

            try {
                Future<HttpResponse<String>> future = Unirest.post("http://10.0.2.2:5000/message/")
                        .header("Content-Type", "application/json")
                        .body(jsonObj.toString())
                        .asStringAsync();
            }

            catch (Exception ex) {

            }

        }
        catch(JSONException ex) {
            // still a bad developer tbh
        }
        catch (Exception ex) {

        }

    }

    public static void PostSurveyResult(Context context, SurveyResult result) {
        try {
            String FILENAME = "id_file";
            String participantId = "";
            FileInputStream fis;
            try {
                fis = context.openFileInput(FILENAME);
                byte[] input = new byte[fis.available()];
                while (fis.read(input) != -1) {
                }
                participantId += new String(input);
            } catch (FileNotFoundException e) {
            } catch (IOException e) {

            }

            JSONObject jsonObj = new JSONObject();
            jsonObj.put("availability", result.getAvailability());
            jsonObj.put("urgency", result.getUrgency());
            jsonObj.put("message_id", result.getMessageId());
            jsonObj.put("participant_id", participantId);
            jsonObj.put("puid", result.getUid());

            try {
                Future<HttpResponse<String>> future = Unirest.post("http://10.0.2.2:5000/surveyresult/")
                        .header("Content-Type", "application/json")
                        .body(jsonObj.toString())
                        .asStringAsync();
            } catch (Exception e) {
            }

        } catch (Exception e) {

        }
    }

    public static void PostAllMessage(Context context, AllMessage message) {
        try {
            String FILENAME = "id_file";
            String participantId = "";
            FileInputStream fis;
            try {
                fis = context.openFileInput(FILENAME);
                byte[] input = new byte[fis.available()];
                while (fis.read(input) != -1) {
                }
                participantId += new String(input);
            } catch (FileNotFoundException e) {
            } catch (IOException e) {

            }

            JSONObject jsonObj = new JSONObject();
            jsonObj.put("body", AESCrypt.encrypt(enc,message.getBody()));
            jsonObj.put("thread_id", message.getThreadId());
            jsonObj.put("type", message.getType());
            jsonObj.put("received_at", message.getDate().getTime());
            jsonObj.put("participant_id", participantId);


            try {
                Future<HttpResponse<String>> future = Unirest.post("http://10.0.2.2:5000/allmessage/")
                        .header("Content-Type", "application/json")
                        .body(jsonObj.toString())
                        .asStringAsync();
            } catch (Exception e) {
            }

        } catch (Exception e) {

        }
    }

    public static void PostThread(Context context, Thread thread) {
        try {
            String FILENAME = "id_file";
            String participantId = "";
            FileInputStream fis;
            try {
                fis = context.openFileInput(FILENAME);
                byte[] input = new byte[fis.available()];
                while (fis.read(input) != -1) {
                }
                participantId += new String(input);
            } catch (FileNotFoundException e) {
            } catch (IOException e) {

            }

            JSONObject jsonObj = new JSONObject();
            jsonObj.put("uid", thread.getUid());
            jsonObj.put("contact_name", AESCrypt.encrypt(enc,thread.getContactName()));
            jsonObj.put("address", AESCrypt.encrypt(enc,thread.getAddress()));
            jsonObj.put("participant_id", participantId);


            try {
                Future<HttpResponse<String>> future = Unirest.post("http://10.0.2.2:5000/thread/")
                        .header("Content-Type", "application/json")
                        .body(jsonObj.toString())
                        .asStringAsync();
            } catch (Exception e) {
            }

        } catch (Exception e) {

        }
    }

}

