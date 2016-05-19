/**
 * File Name : DetailedViewActivity.java
 * Created by: Humaira Patel
 * Date: 01/04/2016
 *
 */
package edu.sdsu.cs.cs646.assign5;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;

/**
 * This class sets up the DetailsFragment if in one pane.
 */
public class DetailedViewActivity extends HomeActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);
        // Get the pothole that was selected.
        Pothole pothole = (Pothole)getIntent().getSerializableExtra("pothole");

        Bundle bundle = new Bundle();
        bundle.putSerializable("Pothole", pothole);
        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            // set up the DetailsFragment if in one pane.
            Fragment detailsFrag = fragmentManager.findFragmentById(R.id.detail_fragment_container);
            if (detailsFrag == null) {
                detailsFrag = new DetailsFragment();
                detailsFrag.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .replace(R.id.detail_fragment_container, detailsFrag)
                        .commit();
            }
        }
        catch (Exception e) {
            Log.e("Exception", e.toString());
        }
    }
}
