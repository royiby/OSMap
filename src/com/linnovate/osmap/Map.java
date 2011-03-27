package com.linnovate.osmap;

import org.andnav.osm.ResourceProxy;
import org.andnav.osm.util.GeoPoint;
import org.andnav.osm.views.OpenStreetMapView;
import org.andnav.osm.views.overlay.MyLocationOverlay;
import org.andnav.osm.views.util.Mercator;
import org.andnav.osm.views.util.OpenStreetMapRendererInfo;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class Map extends Activity 
{
	public OpenStreetMapView mOSMap;
	public MyLocationOverlay mLocationOverlay;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
      //Capturing all of the screen (haahaaahaaaa!!!)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        					 WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
      //create the layout that you will use.
        RelativeLayout mLayout = new RelativeLayout(this); 
      //create the map view that you will use.  
        mOSMap = new OpenStreetMapView(this.getApplicationContext(), OpenStreetMapRendererInfo.values()[OpenStreetMapRendererInfo.MAPNIK.ordinal()]);
      //set built in zoom handles.   
        mOSMap.setBuiltInZoomControls(true);
      //set multi-touch to true.
        mOSMap.setMultiTouchControls(true);
      //init your resource proxy (the file you created earlier).
        ResourceProxy mResourceProxy = new ResourceProxyImpl(getApplicationContext());
      //create a layer - i created a location layer so that it will track the device's location.
        mLocationOverlay = new MyLocationOverlay(this.getBaseContext(), mOSMap, mResourceProxy);
      //add the layer you created to the map's layers.
        mOSMap.getOverlays().add(mLocationOverlay);
      //add the map to the layout while telling it to grab the whole screen.
        mLayout.addView(mOSMap, new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
      //set the layout you created as the content view (what is visible since the creation of the activity).
        this.setContentView(mLayout);
      //set zoom (the number was chosen randomlly to get you acquainted with this feature). 
        mOSMap.getController().setZoom(17);
      //set center of map to your location(or to 32.000000,35.000000 if the gps isn't on or functioning).  
        setMapCenter(mLocationOverlay.getMyLocation());
        
    }
    
    public void setMapCenter(GeoPoint geoPoint)
    {
    	final org.andnav.osm.util.Point p;
    	
    	if(geoPoint != null)
    		p = Mercator.projectGeoPoint(geoPoint, this.getPixelZoomLevel(), null);
    	else
    		p = Mercator.projectGeoPoint(new GeoPoint(32.000000,35.000000), this.getPixelZoomLevel(), null);
    	
		final int worldSize_2 = this.getWorldSizePx()/2;
		mOSMap.scrollTo(p.x - worldSize_2, p.y - worldSize_2);
    }
    
    int getWorldSizePx() {
		return (1 << getPixelZoomLevel());
	}

	/**
	 * Get the equivalent zoom level on pixel scale
	 */
	int getPixelZoomLevel() {
		return mOSMap.getZoomLevel() + mOSMap.getRenderer().MAPTILE_ZOOM;
	}
    
    @Override
    public void onPause() 
    {
    	//this feature is turned off when the activity is paused to save battery life.
    	mLocationOverlay.disableMyLocation();
    	super.onPause();
    }
    
    public void onResume() 
    {
    	super.onResume();
    	mOSMap.setRenderer(OpenStreetMapRendererInfo.values()[OpenStreetMapRendererInfo.MAPNIK.ordinal()]);
    	mLocationOverlay.enableMyLocation();
    	mLocationOverlay.followLocation(true); 
    }
     
    @Override
	public void onRestart()
	{    		
		super.onRestart();
	}
    
	@Override
	public void onStop()
	{
		super.onStop();
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	  //Handle the back button
	  if (keyCode == KeyEvent.KEYCODE_BACK) {
	    //Ask the user if they want to quit
	    new AlertDialog.Builder(this)
	      .setIcon(android.R.drawable.ic_dialog_alert)
	      .setTitle("Exit")
	      .setMessage("Are you sure you want to leave?")
	      .setNegativeButton(android.R.string.cancel, null)
	      .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
	        @Override
	        public void onClick(DialogInterface dialog, int which){
	          // Exit the activity
	          Map.this.finish();
	        }
	      })
	      .show();

	    // Say that we've consumed the event
	    return true;
	  }

	  return super.onKeyDown(keyCode, event);
	} 

}