package com.lynk.admin.crmtool;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Locale;

public class CustomerSearch extends Activity implements Lynk_listener,AppConstants.urlConstants
{

    String[] custNamearr = null;
    String[] customerarr = null;
    Context ctx = null;
    ListView lview = null;

    String businessName;
    String customerCode;
    String businessType;
    String ContactPerson;
    String CreatedBy;
    String CustLatlng;
    String CustomerType;
    String HaveOwnTruck;
    String IsDiffLoadingPoint;
    String IsLabourRequired;
    String IsTheyUseTruck;
    String landmark;
    String LoadingAddress;
    String Phone1;
    String Phone2;
    String ProspectCode;
    String Remarks;
    String TrucksBookPerWeek;
    String VehicleType;

    ArrayAdapter<String> adapter = null;
    ProgressDialog pdialog  = null;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        try
        {
            setContentView(R.layout.exist_prosp_list);

            ctx = this;
            final Lynk_listener lynk_listener = this;


            final EditText searchbox = (EditText) findViewById(R.id.custsearch);

           TextWatcher edtTxtWatcher = null;
            edtTxtWatcher = new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {
                }

                @Override
                public void afterTextChanged(Editable s)
                {
                    if(s.length() >= 3)
                    {
                        String text = searchbox.getText().toString().toLowerCase(Locale.getDefault());
                        Log.e("Cust Search ",text+" Length "+text.length());

                        //pdialog = ProgressDialog.show(ctx,"","Searching...",true);

                        new Network().searchCustomer(text,ctx,lynk_listener);
                    }
                    //adapter.filter(text);
                }
            };
            searchbox.removeTextChangedListener(edtTxtWatcher);
            searchbox.setText("");// Clearing search box.
            searchbox.addTextChangedListener(edtTxtWatcher);



            ((ImageView)findViewById(R.id.backbtn)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(ctx, Prospects.class);
                    startActivity(i);
                    finish();
                }
            });


            lview = (ListView)findViewById(R.id.existproslistview);


            /*
            String[] arr = new String[100];
            for(int i = 0; i< 100; i++)
            {
                arr[i] = "Value "+i;
            }


            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1, arr);
            adapter.notifyDataSetChanged();
            lview.setAdapter(adapter);
            */

            lview.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    //                    String  itemValue    = (String) listView.getItemAtPosition(position);

                    //Get cust code from the list and show exist paget with coresponding values.


                    if(customerarr == null)
                        return;




                    Intent i = new Intent(ctx, ExistingProspect.class);
                    Bundle bundle = new Bundle();


                    bundle.putString("BusinessName",customerarr[position].split("~")[0]);
                    bundle.putString("ContactPerson",customerarr[position].split("~")[1]);
                    bundle.putString("Phone1",customerarr[position].split("~")[2]);
                    bundle.putString("Phone2",customerarr[position].split("~")[3]);
                    bundle.putString("CustomerType",customerarr[position].split("~")[4]);
                    bundle.putString("Remarks",customerarr[position].split("~")[5]);
                    //business zone and business area are not taken here. Will be taken in future..
                    bundle.putString("CustomerCode",customerarr[position].split("~")[8]);


                    //testing to be deleted
                    /*
                    Log.e("BusinessName",customerarr[position].split("~")[0]);
                    Log.e("ContactPerson",customerarr[position].split("~")[1]);
                    Log.e("Phone1",customerarr[position].split("~")[2]);
                    Log.e("Phone2",customerarr[position].split("~")[3]);
                    Log.e("CustomerType",customerarr[position].split("~")[4]);
                    Log.e("Remarks",customerarr[position].split("~")[5]);
                    Log.e("bzone",customerarr[position].split("~")[6]);
                    Log.e("barea",customerarr[position].split("~")[7]);
                    Log.e("CustomerCode",customerarr[position].split("~")[8]);
                    */
                    //


                    /*
                    bundle.putString("BusinessType",businessType);

                    bundle.putString("CustLatlng",CustLatlng);

                    bundle.putString("HaveOwnTruck",HaveOwnTruck);
                    bundle.putString("IsDiffLoadingPoint",IsDiffLoadingPoint);
                    bundle.putString("IsLabourRequired",IsLabourRequired);
                    bundle.putString("IsTheyUseTruck",IsTheyUseTruck);
                    bundle.putString("Landmark",landmark);
                    bundle.putString("LoadingAddress",LoadingAddress);

                    bundle.putString("TrucksBookPerWeek",TrucksBookPerWeek);
                    bundle.putString("VehicleType",VehicleType);
                    */

                    i.putExtras(bundle);


                    startActivity(i);
                    finish();

                }
            });

            ((ImageView)findViewById(R.id.backbtn)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(ctx, Prospects.class);
                    startActivity(i);
                    finish();
                }
            });

        }
        catch (Exception e)
        {
            Log.e("Search ","Exception "+e.getMessage());
        }
    }

    @Override
    public void onSuccessResponse(int Type, JSONObject response)
    {
        if(response != null)
        {
            // parse customer info and list it in listview.
        }
    }

    @Override
    public void onSuccessResponse(int Type, JSONArray response)
    {
            try
            {
                /*
                if(pdialog != null)
                    pdialog.dismiss();
                 */

                if(response != null)
                {
                    custNamearr = null;

                    custNamearr = new String[response.length()];
                    customerarr = new String[response.length()];

                    for (int i = 0; i< response.length(); i++)
                    {
                        JSONObject jsonObject = (JSONObject)response.getJSONObject(i);
                        customerarr[i] = jsonObject.optString("Customer");
                    }


                    custNamearr = parseAndGetCustomerNameList(customerarr);
                    if(custNamearr == null)
                        return;

                    /*
                    String[] arr = new String[100];
                    for(int i = 0; i< 100; i++)
                    {
                        arr[i] = "RES "+i;
                    }
                    */



                    if(adapter == null)
                        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1, custNamearr);
                    else
                    {
                        //adapter.clear();
                        //adapter.notifyDataSetChanged();
                        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1, custNamearr);
                    }

                    Log.e("CNameArr length  ",""+custNamearr.length);
                    /*
                    for (int i =0; i<custNamearr.length; i++)
                        Log.e("CName ",""+custNamearr[i]);
                    */


                    adapter.notifyDataSetChanged();
                    if(lview == null)
                        Log.e("Excepion CSearch ", "Lview null");
                    lview.setAdapter(adapter);


                }

            }catch (Exception e)
            {
                Log.e("Excepion CSearch ",""+e.getMessage());
            }
    }

    @Override
    public void onFailureResponse(int Type)
    {

        try
        {
            if(pdialog != null)
                pdialog.dismiss();
            Globals.showPrompt("Bad Network. Try Again!", ctx);
        }
        catch (Exception e) {

        }
    }

    private String[] parseAndGetCustomerNameList(String[] inputarr)
    {
        String[] custNList = null;
        try
        {
            //parse and return customer name list;
            custNList = new String[inputarr.length];
            for (int i =0; i<inputarr.length; i++)
            {
                custNList[i] = inputarr[i].split("~")[0];
            }
            return custNList;
        }
        catch (Exception e)
        {

        }

        return null;
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
