package com.lynk.admin.crmtool;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;


import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


import Decoder.BASE64Encoder;

/**
 * This class holds the server request methods,
 * .ie., GET, POST method server request.
 *
 * @author Jeevanadhan
 */
public class RequestHandler implements AppConstants.SharedConstants {


    //Single ton object...
    private static RequestHandler requestHandler = null;

    private static final String method = "GET";
    private static final String HMAC_SHA1_ALGORITHM = "HMACSHA1";

    //Single ton method...
    public static RequestHandler getInstance() {
        if (requestHandler != null) {
            return requestHandler;
        } else {
            requestHandler = new RequestHandler();
            return requestHandler;
        }
    }

    /**
     * Returns the HMACSHA1 signature.
     *
     * @param data
     * @param key
     * @return String
     * @throws SignatureException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */

    private String getSignature(String data, String key)
            throws SignatureException, NoSuchAlgorithmException, InvalidKeyException {
        key = key + "&";
        SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), HMAC_SHA1_ALGORITHM);
        Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
        mac.init(signingKey);
        byte[] hmacData = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return (new BASE64Encoder().encode(hmacData));

    }


    /**
     * Returns the url with the Oauth key.
     *
     * @param url
     * @return String
     */

    public String generateUrl(Context context, String url) {
        try {
            String ServiceUrl = URLEncoder.encode(url, "UTF-8");


            String temp = SharedPref.getInstance().getStringValue(context, consumerKey);
            String temp1 = SharedPref.getInstance().getStringValue(context,consumerSecret);


            String parameters = URLEncoder.encode("oauth_consumer_key=" + SharedPref.getInstance().getStringValue
                    (context, consumerKey) + "&oauth_nonce=&oauth_signature_method=HMAC-SHA1&" +
                    "oauth_timestamp" +
                    "=&oauth_version=1.0");
            String BaseString = method + "&" + ServiceUrl + "&" + parameters;

            String signKey = getSignature(BaseString, SharedPref.getInstance().getStringValue
                    (context, consumerSecret));
            return URLDecoder.decode(ServiceUrl)
                    + "?" + URLDecoder.decode(parameters) + "&oauth_signature=" +
                    URLEncoder.encode(signKey, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}
