package com.lynk.admin.crmtool;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Admin on 12/23/2015.
 */
public interface Lynk_listener
{
    public void onSuccessResponse(int Type, JSONObject response);
    public void onSuccessResponse(int Type, JSONArray response);
    public void onFailureResponse(int Type);

}
