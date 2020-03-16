package com.gojek.spark_test.utils;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import com.gojek.spark_test.view.activity.GmailActivity;

public class SessionManager {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Activity _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "AppPref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "Username";
    // Constructor
    public SessionManager(Activity context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();

    }


    /**
     * Create login session
     * */
    public void createLoginSession(String uniqueName){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_NAME, uniqueName);


        // commit changes
        editor.commit();
    }





    public boolean checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, GmailActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
            _context.finish();
            return false;
        }

        return true;

    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.remove(IS_LOGIN);
        editor.remove(KEY_NAME);
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, GmailActivity.class);
        // Closing all the Activities
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Add new Flag to start new Activity

        // Staring Login Activity
        _context.startActivity(i);
        _context.finish();
    }


    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
