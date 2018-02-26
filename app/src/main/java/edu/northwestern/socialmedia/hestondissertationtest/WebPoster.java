package edu.northwestern.socialmedia.hestondissertationtest;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

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
            jsonObj.put("message_from_name", AnonymizeName(message.getMessageFromName()));
            jsonObj.put("in_response_to", AESCrypt.encrypt(enc,message.getInResponseTo()));
            jsonObj.put("handled", message.isHandled() ? 1 : 0);
            jsonObj.put("received_at", message.getReceivedAt().getTime());
            jsonObj.put("responded_at", message.getRespondedAt().getTime());
            jsonObj.put("message_text", AESCrypt.encrypt(enc,message.getMessageText()));
            jsonObj.put("puid", message.getUid());
            jsonObj.put("participant_id", participantId);

            try {
                HttpResponse<String> future = Unirest.post("http://smsstudy.soc.northwestern.edu/message/")
                        .header("Content-Type", "application/json")
                        .body(jsonObj.toString())
                        .asString();
            }

            catch (Exception ex) {
                Log.e(ex.toString(), ex.toString());
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
            jsonObj.put("friend_urgency", result.getFriendUrgency());
            jsonObj.put("unavailability", result.getUnavailable());
            jsonObj.put("message_id", result.getMessageId());
            jsonObj.put("participant_id", participantId);
            jsonObj.put("puid", result.getUid());

            try {
                HttpResponse<String> future = Unirest.post("http://smsstudy.soc.northwestern.edu/surveyresult/")
                        .header("Content-Type", "application/json")
                        .body(jsonObj.toString())
                        .asString();
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
                HttpResponse<String> future = Unirest.post("http://smsstudy.soc.northwestern.edu/allmessage/")
                        .header("Content-Type", "application/json")
                        .body(jsonObj.toString())
                        .asString();
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
            jsonObj.put("contact_name", AnonymizeName(thread.getContactName()));
            jsonObj.put("address", AESCrypt.encrypt(enc,thread.getAddress()));
            jsonObj.put("participant_id", participantId);


            try {
                HttpResponse<String> future = Unirest.post("http://smsstudy.soc.northwestern.edu/thread/")
                        .header("Content-Type", "application/json")
                        .body(jsonObj.toString())
                        .asString();
            } catch (Exception e) {
            }

        } catch (Exception e) {

        }
    }

    public static String AnonymizeName(String name) {
        String anonymized = "";
        if (name.split(" ").length == 1) {
            anonymized = name;
        } else {
            String splitName[] = name.split(" ");
            anonymized += splitName[0];
            anonymized += " ";
            anonymized += splitName[1].charAt(0);
        }
        return anonymized;
    }


}

