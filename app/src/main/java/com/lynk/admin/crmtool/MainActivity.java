package com.lynk.admin.crmtool;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements Lynk_listener,AppConstants.urlConstants
{

    Context ctx = null;

    private SharedPref sharedPref = null;
    EditText unameedt = null;
    EditText pwdedt = null;
    InputMethodManager imm = null;
    ProgressDialog pdialog  = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        ctx = this;
        final Lynk_listener lynk_listener = this;

        setContentView(R.layout.activity_main);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        sharedPref = SharedPref.getInstance();

        unameedt = (EditText) findViewById(R.id.uname);

        pwdedt = (EditText) findViewById(R.id.pwd);

        ((Button)findViewById(R.id.loginbtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObj = null;
                try
                {



                    //Hiding keyboard
                    imm.hideSoftInputFromWindow(unameedt.getWindowToken(), 0);
                    imm.hideSoftInputFromWindow(pwdedt.getWindowToken(), 0);

                    if (unameedt.getText().toString().length() <= 0 || pwdedt.getText().toString().length() <= 0) {
                        Toast.makeText(ctx, "Fields must not be empty", Toast.LENGTH_LONG).show();
                        return;
                    }



                    jsonObj = new JSONObject();
                    jsonObj.put("UserName", unameedt.getText().toString());
                    jsonObj.put("Password", pwdedt.getText().toString());
                    //jsonObj.put("Password", "harish12*");
                    //jsonObj.put("UserName", "harish");


                //Hard coded values to be removed.
                //sharedPref.setSharedValue(ctx, "ConsumerKey", "harish");
                //sharedPref.setSharedValue(ctx, "ConsumerSecret", "harish");


                sharedPref.setSharedValue(ctx, "ConsumerKey", unameedt.getText().toString());
                sharedPref.setSharedValue(ctx, "ConsumerSecret", unameedt.getText().toString());


                    Log.e("User", "" + jsonObj.toString());


                    pdialog = ProgressDialog.show(ctx,"","Logging in...",true);
                    new Network().doLogin(jsonObj, ctx,lynk_listener);

                } catch (Exception e) {

                }
            }
        });

        /*
        ListView listView;
        String[] from = { "Jeeva","Vinoth","Satyam","harish","Kumar","Jeeva","Vinoth","Satyam","harish","Kumar" };
        ArrayAdapter arrayAdapter;
        listView = (ListView) findViewById(R.id.existproslistview);
        arrayAdapter = new ArrayAdapter(this,R.layout.prospectlist_row, R.id.rowview, from);
        listView.setAdapter(arrayAdapter);
        */

    }

    @Override
    public void onSuccessResponse(int Type, JSONObject response)
    {

        try
        {
            if(pdialog != null)
                pdialog.dismiss();

            switch (Type)
            {
                case 1:
                    if(response != null)
                    {
                    /*
                    1 - success
                    0 - failure
                     */

                        if(response.optString("text").equalsIgnoreCase("1"))
                        {
                            SharedPref.getInstance().setSharedValue(ctx, "loginuser",unameedt.getText().toString() );
                            Intent i = new Intent(ctx, Prospects.class);
                            startActivity(i);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(ctx,"Login failed!", Toast.LENGTH_LONG).show();
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        catch (Exception e)
        {

        }

    }

    @Override
    public void onSuccessResponse(int Type, JSONArray response) {


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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (event.getKeyCode()==KeyEvent.KEYCODE_BACK)
        {

            try {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setMessage("Do you want to exit application?")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        finish();
                                        dialog.dismiss();
                                    }
                                }
                        ).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.setTitle("LYNK");
                alert.show();

                return true;
            }
            catch (Exception e)
            {

            }
        }
        return  false;
    }


}
