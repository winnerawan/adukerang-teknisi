package co.ipb.adukerang.gcm;

/**
 * Created by winnerawan on 3/29/16.
 */
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.util.Log;

import co.ipb.adukerang.controller.AppConfig;

public class ShareExternalServer {

    public String shareRegIdWithAppServer(final String regId,
                                          final String userName) {
        String result = "";
        Map paramsMap = new HashMap();
        paramsMap.put("action", "shareRegId");
        paramsMap.put("regId", regId);
        paramsMap.put("name", userName);
        result = request(paramsMap);
        if ("success".equalsIgnoreCase(result)) {
            result = "RegId shared with GCM application server successfully. Regid: "
                    + regId + ". Username: " + userName;
        }
        Log.d("ShareExternalServer", "Result: " + result);
        return result;
    }

    public String sendMessage(final String fromUserName,
                              final String toUserName, final String messageToSend) {

        String result = "";
        Map paramsMap = new HashMap();
        paramsMap.put("action", "sendMessage");
        paramsMap.put("name", fromUserName);
        paramsMap.put("toName", toUserName);
        paramsMap.put("message", messageToSend);
        result = request(paramsMap);
        if ("success".equalsIgnoreCase(result)) {
            result = "Message " + messageToSend + " sent to user " + toUserName
                    + " successfully.";
        }
        Log.d("ShareExternalServer", "Result: " + result);
        return result;
    }


    public String request(Map paramsMap) {
        String result = "";
        URL serverUrl = null;
        OutputStream out = null;
        HttpURLConnection httpCon = null;
        try {
            serverUrl = new URL(AppConfig.URL_GCM);
            StringBuilder postBody = new StringBuilder();
            Iterator<Entry<String, String>> iterator = paramsMap.entrySet()
                    .iterator();
            while (iterator.hasNext()) {
                Entry param = iterator.next();
                postBody.append(param.getKey()).append('=')
                        .append(param.getValue());
                if (iterator.hasNext()) {
                    postBody.append('&');
                }
            }
            String body = postBody.toString();
            byte[] bytes = body.getBytes();
            httpCon = (HttpURLConnection) serverUrl.openConnection();
            httpCon.setDoOutput(true);
            httpCon.setUseCaches(false);
            httpCon.setFixedLengthStreamingMode(bytes.length);
            httpCon.setRequestMethod("POST");
            httpCon.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded;charset=UTF-8");
            Log.d("ShareExternalServer", "Just before getting output stream.");
            out = httpCon.getOutputStream();
            out.write(bytes);
            int status = httpCon.getResponseCode();
            Log.d("ShareExternalServer", "HTTP Connection Status: " + status);
            if (status == 200) {
                result = "success";
            } else {
                result = "Post Failure." + " Status: " + status;
            }

        } catch (MalformedURLException e) {
            Log.e("ShareExternalServer", "Unable to Connect. Invalid URL: "
                    + AppConfig.URL_GCM, e);
            result = "Invalid URL: " + AppConfig.URL_GCM;
        } catch (IOException e) {
            Log.e("ShareExternalServer",
                    "Unable to Connect. Communication Error: " + e);
            result = "Unable to Connect GCM App Server.";
        } finally {
            if (httpCon != null) {
                httpCon.disconnect();
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // do nothing
                }
            }
        }
        return result;
    }

}