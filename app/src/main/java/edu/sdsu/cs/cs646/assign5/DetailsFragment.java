/**
 * File Name : DetailsFragment.java
 * Created by: Humaira Patel
 * Date: 01/04/2016
 *
 */

package edu.sdsu.cs.cs646.assign5;

import android.support.v4.app.Fragment;
import android.graphics.Bitmap;
import android.location.Address;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

/**
 * This class displays the detailed description of a pothole.
 */
public class DetailsFragment extends Fragment implements OnMapReadyCallback {
    private Pothole mPothole;
    private static final String URL="http://bismarck.sdsu.edu/city/image?id=";
    private ImageView mImageView;
    private TextView mDate;
    private TextView mLocation;
    private Address mAddress = null;
    private TextView mDescription;

    /**
     * This over-riden method retrieves the pothole that was selected.
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null){
            mPothole= (Pothole) bundle.getSerializable("Pothole");
        }
    }

    /**
     * This over-riden method sets up the GUI, fills in the value, and
     * inflates the fragment with the description of the pothole.
     *
     * @param savedInstanceState
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        mImageView = (ImageView)view.findViewById(R.id.imageLabel);
        mDate = (TextView)view.findViewById(R.id.dateLabel);
        mDescription = (TextView)view.findViewById(R.id.descLabel);
        mLocation = (TextView)view.findViewById(R.id.locationLabel);
        mAddress = LocAndConnUtils.getAddress(getActivity().getApplicationContext(), mPothole.getLatitude(), mPothole.getLongitude());

        // setting up the map fragment
        SupportMapFragment mMapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.mapview);
        mMapFragment.getMapAsync(this);
        if(mPothole!=null) {
            updateUI();
        }
        return view;
    }


    /***
     * This method creates a volley request for the image and
     * gets the image from the given url.
     *
     * @param url
     */
    public void getImageRequest(String url){
        // Success listener for image
        Response.Listener<Bitmap> success = new Response.Listener<Bitmap>() {
            public void onResponse(Bitmap image) {
                Log.i("Image found", "Success");
                //checking for the image
                if(image != null) {
                    mImageView.setImageBitmap(image);
                }
                else{
                    mImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.img_not_found, null));
                }
            }
        };
        // Error listener for image
        Response.ErrorListener failure = new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.e("Image request", error.toString());
                mImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.img_not_found, null));
            }
        };

        // create the image request to get the image.
        ImageRequest imageRequest = new ImageRequest(url,
                success, 0, 0, ImageView.ScaleType.CENTER_INSIDE, null, failure);
        VolleyQueue.instance(getActivity().getApplicationContext()).add(imageRequest);
    }

    /**
     * This method updates the UI with the details about the pothole.
     */
    public void updateUI(){
        // Check if image was uploaded
        if(!mPothole.getImageType().equals("none"))
            getImageRequest( URL+ mPothole.getId());
        else
            mImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.img_not_found, null));
        // Set the values
        mDescription.setText(mPothole.getDescription());
        mDate.setText(mPothole.getCreatedDate());
        String addressString = (mAddress != null)
                ? mAddress.getAddressLine(0) + "\n" + mAddress.getAddressLine(1) + "\n" + mAddress.getAddressLine(2)
                : "Invalid Address from Server";
        Log.i("AddressString", "received");
        mLocation.setText(addressString);
    }

    /**
     * This method marks the location of the pothole in the map.
     *
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        LocAndConnUtils.displayMap(googleMap, mPothole.getLatitude(), mPothole.getLongitude());
    }


}
