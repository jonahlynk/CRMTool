package com.lynk.admin.crmtool;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class ExistingProspect extends Activity implements Lynk_listener,AppConstants.urlConstants
{

    Context ctx = null;
    String[] custReasonarr = null;

    HashMap<String,String> custReasonhMap = null;
    String createdBy = null;
    String customercode = "";
    String reason = null;
    String remarks_revi = null;
    Spinner reasonspin = null;
    String CustLatlng = "";
    LocationManager locationManager ;
    Bundle extras = null;
    Lynk_listener lynk_listener = null;

    ProgressDialog pdialog  = null;

    Handler lochandler = null;
    Runnable runnable = null;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        ctx = this;
        setContentView(R.layout.exist_prospect_nonedit);

        try
        {


        lynk_listener = this;
        lochandler =  new Handler();



        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        Intent i = getIntent();
        extras = i.getExtras();

        if (custReasonarr == null)
        {
            pdialog = ProgressDialog.show(ctx,"","Loading details...",true);
            new Network().getCustomerReason(ctx, lynk_listener);
        }

        createdBy  = SharedPref.getInstance().getStringValue(ctx, "loginuser");
        customercode  = extras.getString("CustomerCode");


        ((TextView)findViewById(R.id.custnameval)).setText(extras.getString("BusinessName"));
        ((TextView)findViewById(R.id.custCodeVal)).setText(customercode);
        ((TextView)findViewById(R.id.contPersionValue)).setText(extras.getString("ContactPerson"));
        ((TextView)findViewById(R.id.phone1value)).setText(extras.getString("Phone1"));
        ((TextView)findViewById(R.id.phone2value)).setText(extras.getString("Phone2"));
        ((TextView)findViewById(R.id.customerTypevalue)).setText(extras.getString("CustomerType"));
        ((TextView)findViewById(R.id.remarksvalue)).setText(extras.getString("Remarks"));

        reasonspin = (Spinner) findViewById(R.id.reason);
            /*
        reasonspin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if (event.getAction() == MotionEvent.ACTION_UP)
                {
                    //send request and fill
                    if (custReasonarr == null)
                    {
                        new Network().getCustomerReason(ctx, lynk_listener);
                    }
                }
                return false;
            }

        });
        */




        custReasonhMap = new HashMap<String,String>();

        if(extras.getString("CustomerType").equalsIgnoreCase("customer"))
            ((Button)findViewById(R.id.editbtn)).setVisibility(View.GONE);

        ((Button)findViewById(R.id.editbtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                pdialog = ProgressDialog.show(ctx,"","Loading details...",true);
                new Network().getCustomerData(customercode,ctx,lynk_listener);
            }
        });


        ((Button)findViewById(R.id.geo)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (locationManager == null) return;

                if(CustLatlng.length() >0)
                    return;

                boolean gps_enabled = false;
                boolean network_enabled = false;


                pdialog = ProgressDialog.show(ctx, "", "Getting Location...", true);
                pdialog.setCancelable(true);


                try
                {

                    gps_enabled=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                    if(!gps_enabled)
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                        builder.setMessage("Enable GPS!")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id)
                                    {
                                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                        startActivity(intent);
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //  Action for 'NO' Button
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.setTitle("LYNK");
                        alert.show();
                    }
                }catch(Exception ex)
                {

                }
                try
                {
                    network_enabled=locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                }
                catch(Exception ex)
                {

                }

                //don't start listeners if no provider is enabled
                if(!gps_enabled && !network_enabled)
                    return ;


                if(gps_enabled)
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGps);
                if(network_enabled)
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListenerNetwork);

                runnable = new Runnable()
                {
                    public void run()
                    {

                        //

                        Log.e("Timeout Timer ", "" + 10 + "sec elapsed!");

                        locationManager.removeUpdates(locationListenerGps);
                        locationManager.removeUpdates(locationListenerNetwork);




                        pdialog.dismiss();

                        ((ImageView)findViewById(R.id.tickid)).setVisibility(View.GONE);
                        ((ImageView)findViewById(R.id.crossid)).setVisibility(View.VISIBLE);

                        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                        builder.setMessage("Failed to get location coordinates! Try again in the open space!")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {


                                        dialog.dismiss();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.setTitle("LYNK");
                        alert.show();


                    }
                };


                if(lochandler != null)
                    lochandler.postDelayed(runnable,10000);



            }
        });


        ((Button)findViewById(R.id.submitbtn)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {

                    if (locationManager == null) return;

                    if( CustLatlng == null || CustLatlng.length() <= 0)
                    {
                        Toast.makeText(ctx, "Location not yet updated!", Toast.LENGTH_LONG).show();
                        return;
                    }

                    if(((EditText)findViewById(R.id.remarks_revisit)).getText().toString().length() <= 0)
                    {
                        Toast.makeText(ctx,"Revisit remarks should not be empty!",Toast.LENGTH_LONG).show();
                        return;
                    }

                    if(reasonspin.getSelectedItem().toString().length() <=0)
                    {
                        Toast.makeText(ctx, "Reason should not be empty!", Toast.LENGTH_LONG).show();
                        return;
                    }

                    //Hiding keyboard..
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(((EditText)findViewById(R.id.remarks_revisit)).getWindowToken(), 0);


                    JSONObject jsonObj = new JSONObject();
                    jsonObj.put("BusinessName", extras.getString("BusinessName"));
                    jsonObj.put("ContactPerson", extras.getString("ContactPerson"));
                    jsonObj.put("CreatedBy", createdBy);
                    jsonObj.put("CustLatlng", CustLatlng);
                    jsonObj.put("Phone1", extras.getString("Phone1"));
                    jsonObj.put("Phone2", extras.getString("Phone2"));
                    jsonObj.put("ProspectCode", customercode);
                    jsonObj.put("Remarks", ((EditText) findViewById(R.id.remarks_revisit)).getText().toString());
                    jsonObj.put("RevisitReason", custReasonhMap.get(reasonspin.getSelectedItem().toString()));

                    Log.e("Curr Geo Pts ", "" + CustLatlng);

                    Log.e("Revisit Json Req ", "" + jsonObj.toString());

                    pdialog = ProgressDialog.show(ctx,"","Submitting details...",true);
                    new Network().submit_ExistingProspect(jsonObj, ctx, lynk_listener);

                }
                catch (Exception e)
                {
                    Log.e("ErrorLog", "Exception in prepareNewProspectJSON" + e.getMessage());
                }

            }
        });



        ((ImageView)findViewById(R.id.backbtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ctx, CustomerSearch.class);
                startActivity(i);
                finish();
            }
        });


        }
        catch (Exception e)
        {
                Log.e("ExistProsp onCreate ",""+e.getMessage());
        }
    }

    LocationListener locationListenerGps = new LocationListener() {
        public void onLocationChanged(Location location)
        {
            //timer1.cancel();
            CustLatlng = location.getLatitude() +", "+ location.getLongitude();

            if(pdialog != null) pdialog.dismiss();

            if(lochandler != null)
                lochandler.removeCallbacks(runnable);


            ((ImageView)findViewById(R.id.crossid)).setVisibility(View.GONE);
            ((ImageView)findViewById(R.id.tickid)).setVisibility(View.VISIBLE);

            Log.e("Location Geo GPS", "" + CustLatlng);
            //locationResult.gotLocation(location);
            locationManager.removeUpdates(this);
            locationManager.removeUpdates(locationListenerNetwork);
        }
        public void onProviderDisabled(String provider) {}
        public void onProviderEnabled(String provider) {}
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    };

    LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location)
        {
            //timer1.cancel();
            //locationResult.gotLocation(location);
            if(pdialog != null) pdialog.dismiss();

            if(lochandler != null)
                lochandler.removeCallbacks(runnable);

            CustLatlng = location.getLatitude() +", "+ location.getLongitude();

            ((ImageView)findViewById(R.id.crossid)).setVisibility(View.GONE);
            ((ImageView)findViewById(R.id.tickid)).setVisibility(View.VISIBLE);

            Log.e("Location Geo NW", "" + CustLatlng);

            locationManager.removeUpdates(this);
            locationManager.removeUpdates(locationListenerGps);
        }
        public void onProviderDisabled(String provider) {}
        public void onProviderEnabled(String provider) {}
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    };



    @Override
    public void onSuccessResponse(int Type, JSONObject response)
    {

        try
        {



        if(pdialog != null)
            pdialog.dismiss();

        switch (Type)
        {
            case 1: // GetCustomerReason
               //
                break;
            case 2: // Lead Collection Revisit
                if(response != null)
                {
                    if (response.optString("text").equalsIgnoreCase("Successfully Created."))
                    {
                        //Need to decide which page to show
                        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                        builder.setMessage("Successfully submitted!")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        dialog.dismiss();

                                        /*
                                        ((TextView)findViewById(R.id.reasonne)).setText(reasonspin.getSelectedItem().toString());
                                        ((Spinner)findViewById(R.id.reason)).setVisibility(View.GONE);
                                        ((TextView)findViewById(R.id.reasonne)).setVisibility(View.VISIBLE);

                                        ((TextView)findViewById(R.id.remarksne)).setText(((EditText)findViewById(R.id.remarks_revisit)).getText().toString());
                                        ((EditText)findViewById(R.id.remarks_revisit)).setVisibility(View.GONE);
                                        ((TextView)findViewById(R.id.remarksne)).setVisibility(View.VISIBLE);
                                        */

                                        Intent i = new Intent(ctx, Prospects.class);
                                        startActivity(i);
                                        finish();


                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.setTitle("LYNK");
                        alert.show();
                    }
                    else
                        Toast.makeText(ctx,response.optString("text"),Toast.LENGTH_LONG).show();
                }
                break;

            case 3: // Get Customer Data
                if(response != null)
                {
                    //parse json response and fill form with editable fields
                    try
                    {
                        JSONObject jsonObject = (JSONObject)response.getJSONObject("Info");
                        Intent i = new Intent(ctx, NewProspsect.class);

                        Bundle bundle = new Bundle();


                        bundle.putString("CustomerCode",customercode);

                        bundle.putString("BusinessName",jsonObject.optString("BusinessName"));
                        bundle.putString("BusinessType",jsonObject.optString("BusinessType"));

                        bundle.putString("ContactPerson",jsonObject.optString("ContactPerson"));
                        bundle.putString("CustLatlng",jsonObject.optString("CustLatlng"));
                        bundle.putString("CustomerType",jsonObject.optString("CustomerType"));
                        bundle.putString("HaveOwnTruck",jsonObject.optString("HaveOwnTruck"));
                        bundle.putString("IsDiffLoadingPoint",jsonObject.optString("IsDiffLoadingPoint"));
                        bundle.putString("IsLabourRequired",jsonObject.optString("IsLabourRequired"));
                        bundle.putString("IsTheyUseTruck",jsonObject.optString("IsTheyUseTruck"));
                        bundle.putString("Landmark",jsonObject.optString("Landmark"));
                        bundle.putString("LoadingAddress",jsonObject.optString("LoadingAddress"));
                        bundle.putString("Phone1",jsonObject.optString("Phone1"));
                        bundle.putString("Phone2",jsonObject.optString("Phone2"));
                        bundle.putString("Remarks",jsonObject.optString("Remarks"));
                        bundle.putString("TrucksBookPerWeek",jsonObject.optString("TrucksBookPerWeek"));
                        bundle.putString("VehicleType",jsonObject.optString("VehicleType"));
                        i.putExtras(bundle);


                        startActivity(i);
                        finish();
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }


                }
                break;

            case 4: // Get Existing Customer Lat long
                if(response != null)
                {
                    //parse json response and show toast.
                }
                break;
        }
        }
        catch (Exception e)
        {

        }

    }

    @Override
    public void onSuccessResponse(int Type, JSONArray response)
    {
            //GetCustomer Reason
        try
        {

            if(pdialog != null)
                pdialog.dismiss();
            pdialog = null;

        if(response != null)
        {
            //parse json response and fill reason spinner

            custReasonarr = new String[response.length() + 1];

            if(custReasonhMap != null)
            {
                custReasonhMap.put("Select Reason", "");
                custReasonarr[0] =  "Select Reason";
            }

            for (int i = 0; i< custReasonarr.length -1; i++)
            {
                JSONObject jsonObject = (JSONObject)response.getJSONObject(i);
                custReasonarr[i+1] = jsonObject.optString("text");
                custReasonhMap.put(custReasonarr[i+1],jsonObject.optString("value"));
            }

            ArrayAdapter<String> spinnerArrayAdapter  = new ArrayAdapter<String>(ctx, android.R.layout.simple_spinner_item, custReasonarr); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            reasonspin.setAdapter(spinnerArrayAdapter);


        }
        }
        catch (Exception e )
        {
            Log.e("Exception Cust Reason",""+e.getMessage());
        }
    }

    @Override
    public void onFailureResponse(int Type)
    {
        try
        {
            if (pdialog != null)
                pdialog.dismiss();

            switch (Type)
            {
                case Network.TRY_AGAIN:
                    Globals.showPrompt("Bad Network. Try Again!", ctx);
                    break;
                case Network.CHOOSE_PROSPECT:
                    Toast.makeText(ctx,"Bad Network. Try Again!",Toast.LENGTH_LONG ).show();
                    Intent i  =  new Intent(ctx, Prospects.class);
                    startActivity(i);
                    finish();

                    break;
                case Network.CUST_SEARCH:
                    Toast.makeText(ctx,"Bad Network. Try Again!",Toast.LENGTH_LONG ).show();
                    Intent ob  =  new Intent(ctx, CustomerSearch.class);
                    startActivity(ob);
                    finish();
                    break;
            }
        }
        catch (Exception e) {

        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (event.getKeyCode()==KeyEvent.KEYCODE_BACK)
        {
            return true;
        }
        return  false;
    }



}
