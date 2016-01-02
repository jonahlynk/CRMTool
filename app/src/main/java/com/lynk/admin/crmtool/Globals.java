package com.lynk.admin.crmtool;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import org.json.JSONObject;

/**
 * Created by Admin on 12/23/2015.
 */
public class Globals
{
    public static final String BASE_URL = "http://lynk.co.in:8081/serverservice.svc/";
    public static final String LOGIN_URL = BASE_URL+"Loginverify";
    public static final String LEADCOLLECTION_URL = BASE_URL+"LeadsCollection";
    public static final String BUSINESSTYPE_URL = BASE_URL+"GetBusinessType";
    public static final String CUSTOMERTYPE_URL = BASE_URL+"GetCustomerType";
    public static final String CUSTOMER_REASON_URL = BASE_URL+"GetCustomerReason";
    public static final String LEADCOLLECTION_REVISIT_URL = BASE_URL+"LeadsCollectionRevisit";
    public static final String GETCUSTOMER_DATA_URL = BASE_URL+"GetCustomerData";
    public static final String CUSTOMER_SEARCH_URL = BASE_URL+"CustomerSearch";
    public static final String EXIST_CUST_LATLNG_URL = BASE_URL+"GetExistCustomerLatLng";


    public static  JSONObject prepareNewProspectJSON(Beans.NewProspectData obj)
    {
        try
        {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("BusinessName",obj.getBusinessName());
            jsonObj.put("BusinessType",obj.getBusinessType());
            jsonObj.put("ContactPerson",obj.getContactPerson());
            jsonObj.put("CreatedBy",obj.getCreatedBy());
            jsonObj.put("CustLatlng",obj.getCustLatlng());
            jsonObj.put("CustomerType",obj.getCustomerType());
            jsonObj.put("HaveOwnTruck",obj.getHaveOwnTruck());
            jsonObj.put("IsDiffLoadingPoint",obj.getIsDiffLoadingPoint());
            jsonObj.put("IsLabourRequired",obj.getIsLabourRequired());
            jsonObj.put("IsTheyUseTruck",obj.getIsTheyUseTruck());
            jsonObj.put("Landmark",obj.getLandmark());
            jsonObj.put("LoadingAddress",obj.getLoadingAddress());
            jsonObj.put("Phone1",obj.getPhone1());
            jsonObj.put("Phone2",obj.getPhone2());
            jsonObj.put("ProspectCode",obj.getProspectCode());
            jsonObj.put("Remarks",obj.getRemarks());
            jsonObj.put("TrucksBookPerWeek",obj.getTrucksBookPerWeek());
            jsonObj.put("VehicleType",obj.getVehicleType());
            return  jsonObj;
        }
        catch (Exception e)
        {
            Log.e("ErrorLog", "Exception in prepareNewProspectJSON" + e.getMessage());
        }
        return null;
    }


    public static  void showPrompt(String msg,Context ctx)
    {
        try
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setMessage(msg)
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
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

        }
        catch (Exception e)
        {

        }
    }


}
