/**
 * File Name : ListActivity.java
 * Created by: Humaira Patel
 * Date: 01/04/2016
 *
 */
package edu.sdsu.cs.cs646.assign5;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

/**
 * This class displays the list of potholes in the form of list view.
 * The user will get a detailed description of the pothole on selecting
 * a particular pothole from the list of potholes.
 *
 */
public class ListActivity extends HomeActivity implements TitlesFragment.OnPotholeSelectedListener {

    private FragmentManager fragmentManager;

    /**
     * This over-riden method sets up the GUI, fills in the value, and
     * creates the list view.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // set the content view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        // Clear the volley cache
        VolleyQueue.instance(getApplicationContext()).getRequestQueue().getCache().clear();

        try {
            fragmentManager = getSupportFragmentManager();
            Fragment titlesFragment = fragmentManager.findFragmentById(R.id.list_fragment_container);
            if (titlesFragment == null) {
                titlesFragment = new TitlesFragment();
                fragmentManager.beginTransaction()
                        .add(R.id.list_fragment_container, titlesFragment)
                        .commit();
            }
        }
        catch (Exception e) {
            Log.e("Exception", e.toString());
        }


    }

    /**
     * Overriding the method of the OnPotholeSelectedListener interface to retrieve
     * value from the titles fragment.
     * @param pothole
     */
    @Override
    public void potholeSelected(Pothole pothole){
        if (findViewById(R.id.detail_fragment_container) == null) {
            Intent goToDetail = new Intent(this, DetailedViewActivity.class);
            goToDetail.putExtra("pothole", pothole);
            startActivity(goToDetail);
        } else {
            DetailsFragment detailsFragment = new DetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("Pothole", pothole);
            detailsFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, detailsFragment)
                    .commit();
        }

    }
}
