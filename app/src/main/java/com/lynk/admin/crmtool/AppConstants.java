package com.lynk.admin.crmtool;

import android.content.Context;
import android.widget.Toast;

/**
 * This call handles the constants for this app.
 *
 * @author Jeevanandhan
 *
 */
public class AppConstants {

    public static final String USER_NAME = "Enter username";
    public static final String PHONE_NUMBER = "Enter phone number";
    public static final String REGISTRATION_ID = "Enter Registration Id";

    public static boolean offLineFlag = false;



    /** This interface handles the url.
     *
     */

    public interface urlConstants {
          // Dev Base URL
        String baseUrl = "http://52.76.45.17:8081/ServerService.svc/";


        // Production Base URL
//       String baseUrl = "http://52.74.7.137/DriverApp/ServerService.svc/";

//        String baseUrl = "https://services.lynk.co.in/DriverApp/ServerService.svc/";

        String NewDrvrAuth = "NewDrvrAuth";
        String languageStr = "language";
        String SaveDevice = "savedevice";
        String SENDER_ID = "308009122369";
        String EXTRA_MESSAGE = "message";
        String DISPLAY_MESSAGE_ACTION = "com.lynk.DISPLAY_MESSAGE";
        String SAVE_LANGUAGE ="Savelanguage";
        String DRIVER_APP_OFF="DriverAppOff";
        String DRIVER_APP_ON="DriverAppOn";
        String DriverProfile = "DriverProfile";
        String RecordDriverGeoPoint = "RecordDriverGeoPoint";
        String DriverJourneyHistory = "DriverJourneyHistory";
        String retrieveMyOffer = "RetriveMyOffer";
        String acceptOffer = "AcceptOffer/";
        String driverJourneyDetails = "DriverJourneyDetails/";
        String startJourney = "StartJourney";
        String endJourney = "EndJourney";
        String PromoInfo = "PromoInfo";
        String StoreJourneyTrack = "StoreJourneyTrack";
        String StoreWaypointImages="StoreWaypointImages";
        String PushNotificationACK="PushNotificationACK";
        String StoreWaypointSignature = "StoreWaypointSignature";
        String VerifyOrderStatus = "VerifyOrderStatus";
    }

    /** This interface handles the shared preference key.
     *
     */

    public interface SharedConstants {
        String preferenceName = "Lynk";
        String isAppOffline = "isAppOffline";

        String hasLoggedIn = "hasLoggedIn";
        String selectLanguage = "selectLanguage";
        //New driver Auth
        String DriverID = "DriverID";
        String DrivingLicense = "DrivingLicense";
        String GoodsType = "GoodsType";
        String Name = "Name";
        String TruckType = "TruckType";
        String UU_ID = "UUID";
        String GCM_ID = "GCMID";
        String OFFER_ID = "OFFER_ID";
        String OFFER_FLAG = "OFFERFLAG";
        String Uname = "Uname";
        String VehicleNo = "VehicleNo";
        String age = "age";
        String isFirstTime = "isFirstTime";
        String mobile = "mobile";
        String password = "password";
        String PopupHideMins = "PopupHideMins";
        String consumerKey = "ConsumerKey";
        String consumerSecret = "ConsumerSecret";


        String startJourneyTime = "STartTime";
        String endJourneyTime = "EndTime";

    }
}
