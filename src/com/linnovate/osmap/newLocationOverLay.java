package com.linnovate.osmap;

import org.andnav.osm.ResourceProxy;
import org.andnav.osm.views.OpenStreetMapView;
import org.andnav.osm.views.OpenStreetMapView.OpenStreetMapViewProjection;
import org.andnav.osm.views.overlay.MyLocationOverlay;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;

public class newLocationOverLay extends MyLocationOverlay 
{
	public Bitmap man;

	public newLocationOverLay(Context ctx, OpenStreetMapView mapView) 
	{
		super(ctx, mapView);
		// TODO Auto-generated constructor stub
	}

	public newLocationOverLay(Context ctx, OpenStreetMapView mapView, ResourceProxy pResourceProxy) 
	{
		super(ctx, mapView, pResourceProxy);
		man = pResourceProxy.getBitmap(ResourceProxy.bitmap.person);
	}
	
	@Override
	protected void onDrawFinished(Canvas c, OpenStreetMapView osmv) {}
	
	@Override
	public void onDraw(final Canvas c, final OpenStreetMapView osmv) 
	{		
		if(Map.gPoint != null)
		{
			int center_x = man.getWidth() / 2;
			int center_y = man.getHeight() / 2;
			
			float[] values = new float[3];
			float[] mtx = new float[9];
			Matrix direction = new Matrix();
			Point MapCoords = new Point();
			
			c.getMatrix().getValues(mtx);
			
			final OpenStreetMapViewProjection pj = osmv.getProjection();
			pj.toMapPixels(Map.gPoint, MapCoords);
			
			direction.setRotate(values[0], center_x, center_y);
			direction.setTranslate(center_x, center_y);
			direction.postScale(1/mtx[Matrix.MSCALE_X], 1/mtx[Matrix.MSCALE_Y]);
			direction.postTranslate(MapCoords.x, MapCoords.y);
			
			Paint person_paint = new Paint();
			c.drawBitmap(man, direction, person_paint);
		}
		else
			super.onDraw(c, osmv);
	}

}
