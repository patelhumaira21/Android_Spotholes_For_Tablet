/**
 * File Name : HomeActivity.java
 * Created by: Humaira Patel
 * Date: 1/04/2016
 *
 */
package edu.sdsu.cs.cs646.assign5;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

/**
 * This class represents the main activity which is the entry point
 * for the app. The user can view the list of potholes
 * from this point.
 *
 */
public class HomeActivity extends AppCompatActivity {

    /**
     * This over-riden method sets up the GUI, and
     * creates the home page view.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    /**
     * This method takes the user to list activity where the user can view the list
     * potholes.
     *
     * @param button
     */
    public void goToListActivity(View button){
        if (LocAndConnUtils.getNetworkInfo(getApplicationContext())) {
            Intent goToList = new Intent(this, ListActivity.class);
            startActivity(goToList);
        }
        else{
            goToErrorPage("Network Unavailable");
        }
    }

    /**
     * This method takes the user to error handling activity which can be a result of a
     * network issue or exception.
     *
     * @param error
     */
    public void goToErrorPage(String error){
        Log.i("Go to error page", error);
        Intent goToErrPg = new Intent(this,ErrorHandlingActivity.class);
        goToErrPg.putExtra("error",error);
        startActivity(goToErrPg);
    }

    /**
     * This method takes the user to home activity. This method is used
     * by the child class of this activity.
     *
     * @param button
     */
    public void goToHome(View button){
        Intent goTohome = new Intent(this, HomeActivity.class);
        navigateUpTo(goTohome);
    }
}
