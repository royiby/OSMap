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
        
        RelativeLayout mLayout = new RelativeLayout(this); 
        mOSMap = new OpenStreetMapView(this.getApplicationContext(), OpenStreetMapRendererInfo.values()[OpenStreetMapRendererInfo.MAPNIK.ordinal()]);
        mOSMap.setBuiltInZoomControls(true);
        mOSMap.setMultiTouchControls(true);
        
        ResourceProxy mResourceProxy = new ResourceProxyImpl(getApplicationContext());
        mLocationOverlay = new MyLocationOverlay(this.getBaseContext(), mOSMap, mResourceProxy);
        
        mOSMap.getOverlays().add(mLocationOverlay);
        
        mLayout.addView(mOSMap, new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        this.setContentView(mLayout);
        
        mOSMap.getController().setZoom(17);
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
    	mLocationOverlay.disableMyLocation();
    	super.onPause();
    }
    
    public void onResume() 
    {
    	super.onResume();
    	mOSMap.setRenderer(OpenStreetMapRendererInfo.values()[OpenStreetMapRendererInfo.MAPNIK.ordinal()]);
    	mLocationOverlay.enableMyLocation();
    	mLocationOverlay.followLocation(true); //TODO - when in telaviv or in movement - uncomment!!!
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