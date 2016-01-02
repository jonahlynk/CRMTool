package com.lynk.admin.crmtool;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class VolleySingleton {

	// Singleton object...
	private static VolleySingleton instance;

	final private RequestQueue requestQueue;

	//Constructor...
	private VolleySingleton(Context context) {
		
		requestQueue = Volley.newRequestQueue(context);

	}

	// Singleton method...
	public static VolleySingleton getInstance(Context context) {
		if (instance == null) {
			instance = new VolleySingleton(context);
		}
		return instance;
	}

	public RequestQueue getRequestQueue(Context context) {
		if (requestQueue != null) {
		return requestQueue;
		} else {
			getInstance(context);
			return requestQueue;
		}
	}

	private  RequestQueue getRequestQueue() {
		return requestQueue;
	}



	public <T> void addToRequestQueue(Request<T> req) {
		req.setTag("App");
		getRequestQueue().add(req);
	}


	
}
