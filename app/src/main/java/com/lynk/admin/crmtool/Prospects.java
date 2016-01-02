package com.lynk.admin.crmtool;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Prospects extends Activity
{
    Context ctx = null;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        ctx = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.prospect);

        //Toast.makeText(ctx, SharedPref.getInstance().getStringValue(ctx, "loginuser"), Toast.LENGTH_LONG).show();


        ((TextView)findViewById(R.id.prospectname)).setText("Hi "+SharedPref.getInstance().getStringValue(ctx,"loginuser")+" ...");

        ((Button)findViewById(R.id.newPros)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ctx, NewProspsect.class);
                startActivity(i);
                finish();
            }
        });

        ((Button)findViewById(R.id.existPros)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(ctx, CustomerSearch.class);
                startActivity(i);
                finish();
            }
        });
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
                            public void onClick(DialogInterface dialog, int id)
                            {
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
