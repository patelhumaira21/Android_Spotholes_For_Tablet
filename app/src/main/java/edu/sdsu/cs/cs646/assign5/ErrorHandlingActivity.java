/**
 * File Name : ErrorHandlingActivity.java
 * Created by: Humaira Patel
 * Date: 01/04/2016
 *
 */
package edu.sdsu.cs.cs646.assign5;

import android.os.Bundle;
import android.widget.TextView;

/***
 * This class represents the error page if any network error exists,
 *
 */
public class ErrorHandlingActivity extends HomeActivity {

    /**
     * This over-riden method sets up the GUI, and
     * creates the error page view.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_handling);
        String error_msg = getIntent().getStringExtra("error");
        TextView errorTextView = (TextView)findViewById(R.id.errorLabel);
        errorTextView.setText(error_msg);
    }

}
