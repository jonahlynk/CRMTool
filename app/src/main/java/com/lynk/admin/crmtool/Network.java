package com.lynk.admin.crmtool;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 12/23/2015.
 */
public class Network
{

    public static final int TRY_AGAIN = 1;
    public static final int CUST_SEARCH = 2;
    public static final int CHOOSE_PROSPECT = 3;



    public void doLogin(JSONObject loginJson, final Context ctx, final Lynk_listener listener)
    {

        try
        {

           String url =  RequestHandler.getInstance().generateUrl(ctx, Globals.LOGIN_URL);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, loginJson, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response)
                {

                    if(listener != null)
                        listener.onSuccessResponse(1, response);
                }

            }
                    , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    if(error instanceof TimeoutError)
                    {
                        Log.e("Login Timeout Error ", "" + error.getMessage());
                        if(listener != null)
                            listener.onFailureResponse(TRY_AGAIN);
                    }
                    else
                        Log.e("Login Err Resp", error.toString());
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(ctx);

            /*
            int socketTimeout = 5000;
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsonObjectRequest.setRetryPolicy(policy);
            */

            requestQueue.add(jsonObjectRequest);
        }
        catch (Exception e)
        {
            Toast.makeText(ctx, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }


    public void submit_NewProspect(JSONObject jsonObject, final Context ctx, final Lynk_listener listener)
    {

        try
        {

            String url =  RequestHandler.getInstance().generateUrl(ctx, Globals.LEADCOLLECTION_URL);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response)
                {
                    Log.e("NewSubmit Success Resp", response.toString());
                    if(listener != null)
                        listener.onSuccessResponse(1,response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    if(error instanceof TimeoutError)
                    {

                        Log.e("New Sub Timeout Error ", "" + error.getMessage());
                        if(listener != null)
                            listener.onFailureResponse(TRY_AGAIN);
                    }
                    else
                        Log.e("New Submit Err Resp", error.getMessage());
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(ctx);

            /*
            int socketTimeout = 5000;
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsonObjectRequest.setRetryPolicy(policy);
            */

            requestQueue.add(jsonObjectRequest);
            //VolleySingleton.getInstance(ctx).addToRequestQueue(jsonObjectRequest);

        }
        catch (Exception e)
        {
            Toast.makeText(ctx, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    public void submit_ExistingProspect(JSONObject jsonObject, final Context ctx, final Lynk_listener listener)
    {

        try
        {
            String url =  RequestHandler.getInstance().generateUrl(ctx, Globals.LEADCOLLECTION_REVISIT_URL);


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response)
                {
                    Log.e("Exist submit ",""+response.toString());
                    if(listener != null)
                        listener.onSuccessResponse(2,response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    if(error instanceof TimeoutError)
                    {

                        Log.e("Ext Sub Timeout Error ", "" + error.getMessage());
                        if(listener != null)
                            listener.onFailureResponse(TRY_AGAIN);
                    }
                    else
                        Log.e("Ext Submit Err Resp", error.getMessage());
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(ctx);

            /*
            int socketTimeout = 5000;
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsonObjectRequest.setRetryPolicy(policy);
            */

            requestQueue.add(jsonObjectRequest);

            //VolleySingleton.getInstance(ctx).addToRequestQueue(jsonObjectRequest);

        }
        catch (Exception e)
        {
            Toast.makeText(ctx, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    public void getBusinessType(final Context ctx, final Lynk_listener listener)
    {

        try
        {
            String url =  RequestHandler.getInstance().generateUrl(ctx, Globals.BUSINESSTYPE_URL);


            JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response)
                {
                    Log.e("BusinessType ", "" + response.toString());
                    if(listener != null)
                        listener.onSuccessResponse(2,response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    if(error instanceof TimeoutError)
                    {

                        Log.e("BType Timeout Error ", "" + error.getMessage());
                        if(listener != null)
                            listener.onFailureResponse(CHOOSE_PROSPECT);
                    }
                    else
                        Log.e("BType Err Resp", error.getMessage());
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(ctx);

            /*
            int socketTimeout = 10000;
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            request.setRetryPolicy(policy);
            */

            requestQueue.add(request);

            //Time out setting...Check this out..
            //request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


            //VolleySingleton.getInstance(ctx).addToRequestQueue(jsonObjectRequest);

        }
        catch (Exception e)
        {
            Toast.makeText(ctx, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    public void getCustomerType(final Context ctx, final Lynk_listener listener)
    {

        try
        {
            String url =  RequestHandler.getInstance().generateUrl(ctx, Globals.CUSTOMERTYPE_URL);


            JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response)
                {
                    Log.e("Customer Type ", "" + response.toString());
                    if(listener != null)
                        listener.onSuccessResponse(3,response);
                }
            }, new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error)
                {

                    if(error instanceof TimeoutError)
                    {

                        Log.e("CType Timeout Error ", "" + error.getMessage());
                        if(listener != null)
                            listener.onFailureResponse(CHOOSE_PROSPECT);
                    }
                    else
                        Log.e("CType Err Resp", error.getMessage());
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(ctx);

            /*
            int socketTimeout = 10000;
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            request.setRetryPolicy(policy);
            */

            requestQueue.add(request);

            //Time out setting...Check this out..
            //request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


            //VolleySingleton.getInstance(ctx).addToRequestQueue(jsonObjectRequest);

        }
        catch (Exception e)
        {
            Toast.makeText(ctx, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void getCustomerReason(final Context ctx, final Lynk_listener listener)
    {

        try
        {

            String url =  RequestHandler.getInstance().generateUrl(ctx, Globals.CUSTOMER_REASON_URL);


            JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response)
                {
                    Log.e("Customer Reason  ", "" + response.toString());
                    if(listener != null)
                        listener.onSuccessResponse(1,response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    if(error instanceof TimeoutError)
                    {

                        Log.e("BType Timeout Error ", "" + error.getMessage());
                        if(listener != null)
                            listener.onFailureResponse(CUST_SEARCH);
                    }
                    else
                        Log.e("BType Err Resp", error.getMessage());
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(ctx);

            /*
            int socketTimeout = 10000;
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            request.setRetryPolicy(policy);
            */

            requestQueue.add(request);

            //Time out setting...Check this out..
            //request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


            //VolleySingleton.getInstance(ctx).addToRequestQueue(jsonObjectRequest);

        }
        catch (Exception e)
        {
            Toast.makeText(ctx, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void getCustomerData(String custCode, final Context ctx, final Lynk_listener listener)
    {

        try
        {

            String url =  RequestHandler.getInstance().generateUrl(ctx, Globals.GETCUSTOMER_DATA_URL+"/"+custCode);


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response)
                {
                    Log.e("Edit Resp ", "" + response.toString());
                    if(listener != null)
                        listener.onSuccessResponse(3,response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    if(error instanceof TimeoutError)
                    {
                        Log.e("Edit Timeout Error ", "" + error.getMessage());
                        if(listener != null)
                            listener.onFailureResponse(CUST_SEARCH);//show search page
                    }
                    else
                        Toast.makeText(ctx, "Edit REsp Err "+error.getMessage(), Toast.LENGTH_LONG).show();

                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(ctx);

            /*
            int socketTimeout = 20000;
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsonObjectRequest.setRetryPolicy(policy);
            */


            requestQueue.add(jsonObjectRequest);

            //Time out setting...Check this out..
            //request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


            //VolleySingleton.getInstance(ctx).addToRequestQueue(jsonObjectRequest);

        }
        catch (Exception e)
        {
            Toast.makeText(ctx, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void searchCustomer(String custName, final Context ctx, final Lynk_listener listener)
    {

        try
        {
            String url =  RequestHandler.getInstance().generateUrl(ctx, Globals.CUSTOMER_SEARCH_URL+"/"+custName);


            JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response)
                {
                    Log.e("Customer search  ", "" + response.toString());
                    if(listener != null)
                        listener.onSuccessResponse(1,response);
                }
            }, new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    if(error instanceof TimeoutError)
                    {
                        Log.e("Search Timeout Error ", "" + error.getMessage());
                        if(listener != null)
                            listener.onFailureResponse(TRY_AGAIN);
                    }
                    else
                        Log.e("Search Err Resp", error.toString());
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(ctx);

            /*
            int socketTimeout = 20000;
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            request.setRetryPolicy(policy);
            */

            requestQueue.add(request);

            //Time out setting...Check this out..
            //request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


            //VolleySingleton.getInstance(ctx).addToRequestQueue(jsonObjectRequest);

        }
        catch (Exception e)
        {
            Toast.makeText(ctx, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }




    public void getExistCustLatLng(JSONObject jsonObject, final Context ctx, final Lynk_listener listener)
    {

        try
        {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Globals.EXIST_CUST_LATLNG_URL, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response)
                {
                    Toast.makeText(ctx, "Login "+response.toString(), Toast.LENGTH_LONG).show();
                    if(listener != null)
                        listener.onSuccessResponse(4,response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ctx, "Login Error "+error.toString(), Toast.LENGTH_LONG).show();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(ctx);
            requestQueue.add(jsonObjectRequest);

            //Time out setting...Check this out..
            //request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


            //VolleySingleton.getInstance(ctx).addToRequestQueue(jsonObjectRequest);

        }
        catch (Exception e)
        {
            Toast.makeText(ctx, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}
