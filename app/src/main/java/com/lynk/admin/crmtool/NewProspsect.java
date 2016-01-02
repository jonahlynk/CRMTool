package com.lynk.admin.crmtool;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class NewProspsect extends Activity implements Lynk_listener,AppConstants.urlConstants
{

    String businessName;
    String businessType ="";
    String ContactPerson;
    String CreatedBy;
    String CustLatlng = "";
    String CustomerType = "";
    String HaveOwnTruck="N";
    String IsDiffLoadingPoint="N";
    String IsLabourRequired="N";
    String IsTheyUseTruck = "N";
    String landmark;
    String LoadingAddress;
    String Phone1;
    String Phone2;
    String ProspectCode;
    String Remarks;
    String TrucksBookPerWeek;
    String VehicleType = "";

    String[] busiTypearr = null;
    String[] custTypearr = null;

    HashMap<String,String> busiTypehMap = null;
    HashMap<String,String> custTypehMap = null;

    Spinner bType = null;
    Spinner custType = null;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    LocationManager locationManager ;

    Context ctx = null;

    String CustomerCode = "";

    boolean IsSmall = false;
    boolean IsMedium = false;
    boolean IsLarge = false;

    ProgressDialog pdialog  = null;

   Handler lochandler = null;
    Runnable runnable = null;

    Lynk_listener lynk_listener = null;
    Bundle extras = null;
    JSONObject jsonObj = null;
    int thTimer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        try
        {


        ctx = this;
        lynk_listener = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_prospect);
        lochandler =  new Handler();

        pdialog = ProgressDialog.show(ctx,"","Loading details...",true);
        LoadSpinners();

            //
            Intent i = getIntent();
            extras = i.getExtras();
            if(extras != null ) //coming by editing
            {


                CustomerCode = extras.getString("CustomerCode");

                ((TextView) findViewById(R.id.businamene)).setText(extras.getString("BusinessName"));
                ((EditText) findViewById(R.id.custname)).setVisibility(View.GONE);
                ((TextView) findViewById(R.id.businamene)).setVisibility(View.VISIBLE);

                businessType = extras.getString("BusinessType");
                Log.e("Business Type",""+businessType);

                CustomerType = extras.getString("CustomerType");


                Log.e("Cust Latlng",""+extras.getString("CustLatlng"));

                if(extras.getString("CustLatlng").length() <=0)
                {
                    ((ImageView) findViewById(R.id.crossid)).setVisibility(View.GONE);
                    ((ImageView) findViewById(R.id.tickid)).setVisibility(View.GONE);
                }
                else
                {
                     CustLatlng = extras.getString("CustLatlng");
                    ((ImageView) findViewById(R.id.crossid)).setVisibility(View.GONE);
                    ((ImageView) findViewById(R.id.tickid)).setVisibility(View.VISIBLE);

                }

                ((EditText) findViewById(R.id.busiaddr)).setText(extras.getString("Landmark"));

                ((EditText) findViewById(R.id.contPerson)).setText(extras.getString("ContactPerson"));


                ((TextView) findViewById(R.id.phone1ne)).setText(extras.getString("Phone1"));
                ((EditText) findViewById(R.id.phone1)).setVisibility(View.GONE);
                ((TextView) findViewById(R.id.phone1ne)).setVisibility(View.VISIBLE);

                ((EditText) findViewById(R.id.phone2)).setText(extras.getString("Phone2"));


                Log.e("Use Truck value ", "" + extras.getString("IsDiffLoadingPoint"));


                if(extras.getString("IsDiffLoadingPoint").length() >0 && extras.getString("IsDiffLoadingPoint").equalsIgnoreCase("Y"))
                {
                    ((Switch) findViewById(R.id.loadPt)).setChecked(true);
                    ((LinearLayout) findViewById(R.id.loadaddrlayout)).setVisibility(View.VISIBLE);
                    ((EditText) findViewById(R.id.loadaddr)).setText(extras.getString("LoadingAddress"));
                    IsDiffLoadingPoint = "Y";
                }
                else
                {
                    ((Switch) findViewById(R.id.loadPt)).setChecked(false);
                    ((LinearLayout) findViewById(R.id.loadaddrlayout)).setVisibility(View.GONE);
                }




                if(extras.getString("IsTheyUseTruck").length() > 0 && extras.getString("IsTheyUseTruck").equalsIgnoreCase("Y"))
                {
                    ((Switch) findViewById(R.id.useTruck)).setChecked(true);
                    IsTheyUseTruck = "Y";
                }
                else
                    ((Switch) findViewById(R.id.useTruck)).setChecked(false);


                if(extras.getString("HaveOwnTruck").length() > 0 && extras.getString("HaveOwnTruck").equalsIgnoreCase("Y"))
                {
                    ((Switch) findViewById(R.id.ownTruck)).setChecked(true);
                    HaveOwnTruck = "Y";
                }
                else
                    ((Switch) findViewById(R.id.ownTruck)).setChecked(false);


                if(extras.getString("IsLabourRequired").length() > 0 && extras.getString("IsLabourRequired").equalsIgnoreCase("Y"))
                {
                    ((Switch) findViewById(R.id.labReq)).setChecked(true);
                    IsLabourRequired = "Y";
                }
                else
                    ((Switch) findViewById(R.id.labReq)).setChecked(false);

                //((TextView) findViewById(R.id.customerTypevalue)).setText(extras.getString("CustomerType"));
                //((TextView) findViewById(R.id.vehicleTypeval)).setText(extras.getString("VehicleType"));
                ((EditText) findViewById(R.id.TrkperWk)).setText(extras.getString("TrucksBookPerWeek"));

                VehicleType = extras.getString("VehicleType");

                ((TextView) findViewById(R.id.remarksne)).setText(extras.getString("Remarks"));
                ((EditText) findViewById(R.id.remarks)).setVisibility(View.GONE);
                ((TextView) findViewById(R.id.remarksne)).setVisibility(View.VISIBLE);
            }
            //


        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        busiTypehMap = new HashMap<String,String>();
        custTypehMap = new HashMap<String,String>();

        ((ImageView)findViewById(R.id.backbtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = null;
                if(extras == null)
                    i =  new Intent(ctx, Prospects.class);
                else
                    i =  new Intent(ctx, CustomerSearch.class);
                startActivity(i);
                finish();
            }
        });

        bType = (Spinner) findViewById(R.id.businessType);

            /*
            bType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                {
                    Toast.makeText(ctx,position+"",Toast.LENGTH_LONG ).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent)
                {
                    Toast.makeText(ctx,"Nothing",Toast.LENGTH_LONG ).show();
                }
            });
            */

       /*
        bType.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    //send request and fill
                    if (busiTypearr == null)
                        new Network().getBusinessType(ctx, lynk_listener);
                }
                return false;
            }
        });
        */



            /*
        bType.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                //send req and get business type list and fill in array.


                ArrayAdapter<String> spinnerArrayAdapter  = new ArrayAdapter<String>(ctx, android.R.layout.simple_spinner_item, busiTypearr); //selected item will look like a spinner set from XML
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                bType.setAdapter(spinnerArrayAdapter);
            }
        });
        */

            custType = (Spinner) findViewById(R.id.customerType);
            /*
            custType.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {

                        if (custTypearr == null)
                            new Network().getCustomerType(ctx, lynk_listener);
                    }
                    return false;
                }
            });
            */

            ((Switch) findViewById(R.id.loadPt)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    IsDiffLoadingPoint = (isChecked ? "Y" : "N");

                    if (isChecked)
                        ((LinearLayout) findViewById(R.id.loadaddrlayout)).setVisibility(View.VISIBLE);
                    else
                        ((LinearLayout) findViewById(R.id.loadaddrlayout)).setVisibility(View.GONE);
                }
            });


            ((Switch) findViewById(R.id.useTruck)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    IsTheyUseTruck = (isChecked ? "Y" : "N");
                }
            });

            ((Switch) findViewById(R.id.ownTruck)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    HaveOwnTruck = (isChecked ? "Y" : "N");
                }
            });

            ((Switch) findViewById(R.id.labReq)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    IsLabourRequired = (isChecked ? "Y" : "N");
                }
            });


            ((ToggleButton) findViewById(R.id.smalltb)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                        IsSmall = true;
                    else
                        IsSmall = false;
                }
            });

            ((ToggleButton) findViewById(R.id.mediumtb)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                        IsMedium = true;
                    else
                        IsMedium = false;
                }
            });

            ((ToggleButton) findViewById(R.id.largetb)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                        IsLarge = true;
                    else
                        IsLarge = false;
                }
            });


            if (VehicleType.equalsIgnoreCase("ALL"))
            {
                ((ToggleButton) findViewById(R.id.smalltb)).setChecked(true);
                ((ToggleButton) findViewById(R.id.mediumtb)).setChecked(true);
                ((ToggleButton) findViewById(R.id.largetb)).setChecked(true);
            }
            else if (VehicleType.equalsIgnoreCase("LYNKSM"))
            {
                ((ToggleButton) findViewById(R.id.smalltb)).setChecked(true);
                ((ToggleButton) findViewById(R.id.mediumtb)).setChecked(true);
            }
            else if (VehicleType.equalsIgnoreCase("LYNKSL"))
            {
                ((ToggleButton) findViewById(R.id.smalltb)).setChecked(true);
                ((ToggleButton) findViewById(R.id.largetb)).setChecked(true);
            }
            else if (VehicleType.equalsIgnoreCase("LYNKML"))
            {
                ((ToggleButton) findViewById(R.id.mediumtb)).setChecked(true);
                ((ToggleButton) findViewById(R.id.largetb)).setChecked(true);
            }
            else if (VehicleType.equalsIgnoreCase("LYNKS"))
            {
                ((ToggleButton) findViewById(R.id.smalltb)).setChecked(true);
            }
            else if (VehicleType.equalsIgnoreCase("LYNKM"))
            {
                ((ToggleButton) findViewById(R.id.mediumtb)).setChecked(true);
            }
            else if (VehicleType.equalsIgnoreCase("LYNKL"))
            {
                ((ToggleButton) findViewById(R.id.largetb)).setChecked(true);
            }

            ((Button) findViewById(R.id.submitbtn)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (((EditText) findViewById(R.id.custname)).getVisibility() == View.VISIBLE)
                        businessName = ((EditText) findViewById(R.id.custname)).getText().toString();
                    else if (((TextView) findViewById(R.id.businamene)).getVisibility() == View.VISIBLE)
                        businessName = ((TextView) findViewById(R.id.businamene)).getText().toString();


                    landmark = ((EditText) findViewById(R.id.busiaddr)).getText().toString();
                    ContactPerson = ((EditText) findViewById(R.id.contPerson)).getText().toString();

                    //Phone1 = ((EditText) findViewById(R.id.phone1)).getText().toString();

                    if (((EditText) findViewById(R.id.phone1)).getVisibility() == View.VISIBLE)
                        Phone1 = ((EditText) findViewById(R.id.phone1)).getText().toString();
                    else if (((TextView) findViewById(R.id.phone1ne)).getVisibility() == View.VISIBLE)
                        Phone1 = ((TextView) findViewById(R.id.phone1ne)).getText().toString();


                    Phone2 = ((EditText) findViewById(R.id.phone2)).getText().toString();
                    TrucksBookPerWeek = ((EditText) findViewById(R.id.TrkperWk)).getText().toString();
                    //Remarks = ((EditText) findViewById(R.id.remarks)).getText().toString();

                    if (((EditText) findViewById(R.id.remarks)).getVisibility() == View.VISIBLE)
                        Remarks = ((EditText) findViewById(R.id.remarks)).getText().toString();
                    else if (((TextView) findViewById(R.id.remarksne)).getVisibility() == View.VISIBLE)
                        Remarks = ((TextView) findViewById(R.id.remarksne)).getText().toString();


                    LoadingAddress = ((EditText) findViewById(R.id.loadaddr)).getText().toString();


                    if (IsSmall && IsMedium && IsLarge)
                        VehicleType = "ALL";
                    else if (IsSmall && IsMedium)
                        VehicleType = "LYNKSM";
                    else if (IsSmall && IsLarge)
                        VehicleType = "LYNKSL";
                    else if (IsMedium && IsLarge)
                        VehicleType = "LYNKML";
                    else if (IsSmall)
                        VehicleType = "LYNKS";
                    else if (IsMedium)
                        VehicleType = "LYNKM";
                    else if (IsLarge)
                        VehicleType = "LYNKL";

                    if (!VehicleType.equalsIgnoreCase("LYNKS") &&
                            !VehicleType.equalsIgnoreCase("LYNKM") &&
                            !VehicleType.equalsIgnoreCase("LYNKL") &&
                            !VehicleType.equalsIgnoreCase("LYNKSM") &&
                            !VehicleType.equalsIgnoreCase("LYNKSL") &&
                            !VehicleType.equalsIgnoreCase("LYNKML") &&
                            !VehicleType.equalsIgnoreCase("ALL")
                            ) {
                        Toast.makeText(ctx, "Vehicle Type must be selected!", Toast.LENGTH_LONG).show();
                        return;
                    }


                    Spinner bType = (Spinner) findViewById(R.id.businessType);
                    if (busiTypehMap != null && busiTypehMap.size() > 0 && bType.getSelectedItemPosition() > 0)
                        businessType = busiTypehMap.get(bType.getSelectedItem().toString());
                    else {
                        Toast.makeText(ctx, "Business Type must be selected!", Toast.LENGTH_LONG).show();
                        return;
                    }

                /*
                if(extras != null && businessType.length() <=0)
                {
                    businessType = extras.getString("BusinessType");
                    if (busiTypearr != null)
                        businessType = busiTypehMap.get(businessType).toString();
                }
                */


                    Spinner cType = (Spinner) findViewById(R.id.customerType);
                    if (custTypehMap != null && custTypehMap.size() > 0 && cType.getSelectedItemPosition() > 0) {
                        CustomerType = custTypehMap.get(cType.getSelectedItem().toString());
                    /*
                    if(CustomerType.equalsIgnoreCase("HIGH"))
                        CustomerType = "HIG";
                    else if(CustomerType.equalsIgnoreCase("MEDIUM"))
                        CustomerType = "MED";
                    else if(CustomerType.equalsIgnoreCase("LOW"))
                        CustomerType = "LOW";
                    else if(CustomerType.equalsIgnoreCase("Customer"))
                        CustomerType = "CU";
                       */
                    } else {
                        Toast.makeText(ctx, "CustomerType must be selected!", Toast.LENGTH_LONG).show();
                        return;
                    }

                    if (CustLatlng == null || CustLatlng.length() <= 0) {
                        Toast.makeText(ctx, "CustLatlng should not be left empty!", Toast.LENGTH_LONG).show();
                        return;
                    }

                /*
                if(extras != null && CustomerType.length() <=0)
                {
                    CustomerType = extras.getString("CustomerType");
                    if (custTypearr != null)
                        CustomerType = custTypehMap.get(CustomerType).toString();
                }
                */


                    if (extras == null) {

                        if (businessName == null || businessName.length() <= 0) {
                            Toast.makeText(ctx, "Customer Name should not be left empty!", Toast.LENGTH_LONG).show();
                            return;
                        } else if (businessType == null || businessType.length() <= 0) {
                            Toast.makeText(ctx, "BusinessType should not be left empty!", Toast.LENGTH_LONG).show();
                            return;
                        } else if (ContactPerson == null || ContactPerson.length() <= 0) {
                            Toast.makeText(ctx, "ContactPerson should not be left empty!", Toast.LENGTH_LONG).show();
                            return;
                        } else if (CustLatlng == null || CustLatlng.length() <= 0) {
                            Toast.makeText(ctx, "Location should not be left empty!", Toast.LENGTH_LONG).show();
                            return;
                        } else if (CustomerType == null || CustomerType.length() <= 0) {
                            Toast.makeText(ctx, "CustomerType should not be left empty!", Toast.LENGTH_LONG).show();
                            return;
                        } else if (HaveOwnTruck == null || HaveOwnTruck.length() <= 0) {
                            Toast.makeText(ctx, "HaveOwnTruck should not be left empty!", Toast.LENGTH_LONG).show();
                            return;
                        } else if (IsLabourRequired == null || IsLabourRequired.length() <= 0) {
                            Toast.makeText(ctx, "IsLabourRequired should not be left empty!", Toast.LENGTH_LONG).show();
                            return;
                        } else if (IsTheyUseTruck == null || IsTheyUseTruck.length() <= 0) {
                            Toast.makeText(ctx, "IsTheyUseTruck should not be left empty!", Toast.LENGTH_LONG).show();
                            return;
                        } else if (landmark == null || landmark.length() <= 0) {
                            Toast.makeText(ctx, "landmark should not be left empty!", Toast.LENGTH_LONG).show();
                            return;
                        } else if (Phone1 == null || Phone1.length() <= 0) {
                            Toast.makeText(ctx, "Phone1 should not be left empty!", Toast.LENGTH_LONG).show();
                            return;
                        } else if (TrucksBookPerWeek == null || TrucksBookPerWeek.length() <= 0) {
                            Toast.makeText(ctx, "TrucksBookPerWeek should not be left empty!", Toast.LENGTH_LONG).show();
                            return;
                        } else if (VehicleType == null || VehicleType.length() <= 0) {
                            Toast.makeText(ctx, "VehicleType should not be left empty!", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }


                    if (IsDiffLoadingPoint != null && IsDiffLoadingPoint.length() > 0 && IsDiffLoadingPoint.equalsIgnoreCase("Y")) {
                        if (LoadingAddress == null || LoadingAddress.length() <= 0)
                            Toast.makeText(ctx, "Loading address should not be left empty!", Toast.LENGTH_LONG).show();
                    }


                    //Log.e("Login User ", SharedPref.getInstance().getStringValue(ctx, "loginuser"));

                    Beans.NewProspectData prosData = new Beans().new NewProspectData(businessName, businessType, ContactPerson, SharedPref.getInstance().getStringValue(ctx, "loginuser"), CustLatlng, CustomerType, HaveOwnTruck, IsDiffLoadingPoint, IsLabourRequired, IsTheyUseTruck, landmark, LoadingAddress,
                            Phone1, Phone2, CustomerCode, Remarks, TrucksBookPerWeek, VehicleType);

                    JSONObject jsonObj = Globals.prepareNewProspectJSON(prosData);

                    Log.e("New Submit Requ JSON", jsonObj.toString());

                    //check and send submit request for new and exist prospect by customer code...
                    //send submit request
                /*
                if (CustomerCode != null && CustomerCode.length() > 0) //Exist prospect
                    new Network().submit_ExistingProspect(jsonObj, ctx, lynk_listener);
                else //New prospect submit
                */
                //Calling Lead Collection with Customercode --> when exist prospect edited and then submitting Else without Customer code --> when new prospect is submitted.

                    //new TimerThread().execute();

                    pdialog = ProgressDialog.show(ctx,"","Submitting details...",true);

                    new Network().submit_NewProspect(jsonObj, ctx, lynk_listener);
                //

            }
        });




        ((Button)findViewById(R.id.geo)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                boolean gps_enabled = false;
                boolean network_enabled = false;

                try
                {

                    if (locationManager == null) return;

                    if(CustLatlng != null && CustLatlng.length() >0)
                        return;





                    pdialog = ProgressDialog.show(ctx,"","Getting Location...",true);



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
                    @SuppressWarnings("deprecation")
                    public void run()
                    {

                        //
                        Log.e("Timeout Timer ", "" + 5 + "sec elapsed!");

                        if(locationManager == null)
                            return;

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





        }
        catch (Exception e)
        {
            Log.e("Exception subnmit" ,""+ e.getMessage());

        }


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void LoadSpinners()
    {
        try
        {
            if (busiTypearr == null)
                new Network().getBusinessType(ctx, lynk_listener);

            if (custTypearr == null)
                new Network().getCustomerType(ctx, lynk_listener);

        }
        catch (Exception e)
        {

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
    public void onStart()
    {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "NewProspsect Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.lynk.admin.crmtool/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "NewProspsect Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.lynk.admin.crmtool/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


    @Override
    public void onSuccessResponse(int Type, JSONObject response)
    {

        if(response == null)
            return;

        if(pdialog != null)
            pdialog.dismiss();


        switch (Type)
        {
            case 1:
                if(response != null)
                {
                    if (response.optString("text").equalsIgnoreCase("Successfully Created."))
                    {
                        //Prompt showing added successfully.
                        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                        builder.setMessage("Prospect successfully added!")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id)
                                    {

                                        businessName = "";
                                        businessType="";
                                        ContactPerson="";
                                        CustLatlng = "";
                                        CustomerType = "";
                                        HaveOwnTruck="no";
                                        IsDiffLoadingPoint="no";
                                        IsLabourRequired="no";
                                        IsTheyUseTruck = "no";
                                        landmark="";
                                        LoadingAddress="";
                                        Phone1="";
                                        Phone2="";
                                        ProspectCode="";
                                        Remarks="";
                                        TrucksBookPerWeek="";
                                        VehicleType = "";

                                        ((ImageView)findViewById(R.id.tickid)).setVisibility(View.INVISIBLE);

                                        //((NewProspsect)ctx).setContentView(R.layout.new_prospect);

                                        Intent i = new Intent(ctx, NewProspsect.class);
                                        startActivity(i);
                                        finish();

                                        dialog.dismiss();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.setTitle("LYNK");
                        alert.show();


                        /*
                        Intent i = new Intent(ctx, Prospects.class);
                        startActivity(i);
                        finish();
                        */
                    }
                    else if(response.optString("text").equalsIgnoreCase("Successfully Updated."))
                    {
                        Toast.makeText(ctx, "" + response.optString("text"), Toast.LENGTH_LONG).show();
                        Intent i = new Intent(ctx, Prospects.class);
                        startActivity(i);
                        finish();
                    }
                    else
                        Toast.makeText(ctx, "" + response.optString("text"), Toast.LENGTH_LONG).show();


                }
                break;

            case 3: //CUSTOMERTYPE
                //parse response json and fill customer type spinner.

                /*
                custTypearr = new String[response.length()];
                keys=response.keys();
                i = 0;
                while(keys.hasNext())
                {
                    String key=keys.next();
                    custTypearr[i] = key;
                    custTypehMap.put(key,response.optString(key));
                    i++;
                }
                */

                break;


        }
    }

    @Override
    public void onSuccessResponse(int Type, JSONArray response)
    {

        try
        {


        if(Type == 2)
        {

            if(pdialog != null)
                pdialog.dismiss();

            pdialog = null;

            //parse response json and fill business type spinner.

            busiTypearr = new String[response.length() + 1];

            if(busiTypehMap != null)
            {
                busiTypehMap.put("Select", "");
                busiTypearr[0] = "Select Type";
            }

            for (int i = 0; i< busiTypearr.length-1; i++)
            {
                JSONObject jsonObject = (JSONObject)response.getJSONObject(i);
                busiTypearr[i+1] = jsonObject.optString("text");
                busiTypehMap.put(jsonObject.optString("text"),jsonObject.optString("value"));
            }

            int pos = 0;
            if(extras != null) // Show existing selected item in the edit page
            {
                if (businessType.length()>0)
                {

                    for (Map.Entry<String, String> entry : busiTypehMap.entrySet())
                    {
                        if (entry.getValue().trim().equalsIgnoreCase(businessType.trim()))
                        {
                            //if(Arrays.asList(busiTypearr).contains(entry.getKey()))
                            pos = Arrays.asList(busiTypearr).indexOf(entry.getKey());
                            break;
                        }
                    }
                }

            }

            ArrayAdapter<String> spinnerArrayAdapter  = new ArrayAdapter<String>(ctx, android.R.layout.simple_spinner_item, busiTypearr); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            bType.setAdapter(spinnerArrayAdapter);
            bType.setSelection(pos);

        }
        else if(Type == 3)
        {

            if(pdialog != null)
                pdialog.dismiss();

            custTypearr = new String[response.length() + 1];

            if(custTypehMap != null)
            {
                custTypehMap.put("Select", "");
                custTypearr[0] =  "Select Type";
            }

            for (int i = 0; i< custTypearr.length - 1; i++)
            {
                JSONObject jsonObject = (JSONObject)response.getJSONObject(i);
                custTypearr[i+1] = jsonObject.optString("text");
                custTypehMap.put(custTypearr[i+1],jsonObject.optString("value"));
            }


            int pos = 0;
            if(extras != null) // Show existing selected item in the edit page
            {

                if (CustomerType.length()>0)
                {

                    for (Map.Entry<String, String> entry : custTypehMap.entrySet())
                    {
                        if (entry.getValue().trim().equalsIgnoreCase(CustomerType.trim()))
                        {
                            //if(Arrays.asList(busiTypearr).contains(entry.getKey()))
                            pos = Arrays.asList(custTypearr).indexOf(entry.getKey());
                            break;
                        }
                    }
                }

            }



            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(ctx, android.R.layout.simple_spinner_item, custTypearr); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            custType.setAdapter(spinnerArrayAdapter);
            custType.setSelection(pos);
        }


        }
        catch (Exception e)
        {
            Log.e("Dropdown Exception ",""+e.getMessage());
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


    class TimerThread extends AsyncTask<String,String,String>
    {

        @Override
        protected void onPreExecute()
        {
            try
            {
                if(thTimer == 0)
                    pdialog = ProgressDialog.show(ctx, "", "Submitting details...", true);
            }
            catch (Exception e)
            {

            }

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params)
        {
            try
            {
                if(thTimer == 0)
                    new Network().submit_NewProspect(jsonObj, ctx, lynk_listener);

                Thread.sleep(1000);
                thTimer ++;
                return "";
            }
            catch (Exception e)
            {

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s)
        {
            try
            {
                if(thTimer == 30)
                {
                    Log.e("TimerTh 10sec elapsed!","");

                    thTimer = 0;

                    if(pdialog != null)
                        pdialog.dismiss();

                    AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                    builder.setMessage("Check network and Try again!")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    dialog.dismiss();
                                    ///new TimerThread().execute();
                                    //new Network().submit_NewProspect(jsonObj, ctx, lynk_listener);
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.setTitle("LYNK");
                    alert.show();


                }
                else
                    new TimerThread().execute();

            }
            catch (Exception e)
            {

            }
            super.onPostExecute(s);
        }
    }

}
