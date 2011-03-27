package com.linnovate.osmap;

import org.andnav.osm.DefaultResourceProxyImpl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

public class ResourceProxyImpl extends DefaultResourceProxyImpl {

	private final Context mContext;
	
	public ResourceProxyImpl(final Context pContext) {
		super(pContext);
		mContext = pContext;
	}

	@Override
	public String getString(string pResId) {
		switch(pResId) {
		case osmarender : return mContext.getString(R.string.osmarender);
		case mapnik : return mContext.getString(R.string.mapnik);
		case cyclemap : return mContext.getString(R.string.cyclemap);
		case openareal_sat : return mContext.getString(R.string.openareal_sat);
		case base : return mContext.getString(R.string.base);
		case topo : return mContext.getString(R.string.topo);
		case hills : return mContext.getString(R.string.hills);
		case cloudmade_small : return mContext.getString(R.string.cloudmade_small);
		case cloudmade_standard : return mContext.getString(R.string.cloudmade_standard);
		//case unknown : return mContext.getString(R.string.unknown);
		default : return super.getString(pResId);
		}
	}

	@Override
	public Bitmap getBitmap(bitmap pResId) {
		switch(pResId) {
		case center : return BitmapFactory.decodeResource(mContext.getResources(), R.drawable.center);
		case direction_arrow : return BitmapFactory.decodeResource(mContext.getResources(), R.drawable.direction_arrow);
		case marker_default : return BitmapFactory.decodeResource(mContext.getResources(), R.drawable.marker_default);
		case marker_default_focused_base : return BitmapFactory.decodeResource(mContext.getResources(), R.drawable.marker_default_focused_base);
		case navto_small : return BitmapFactory.decodeResource(mContext.getResources(), R.drawable.navto_small);
		case next : return BitmapFactory.decodeResource(mContext.getResources(), R.drawable.next);
		case person : return BitmapFactory.decodeResource(mContext.getResources(), R.drawable.person);
		case previous : return BitmapFactory.decodeResource(mContext.getResources(), R.drawable.previous);
		default : return super.getBitmap(pResId);
		}
	}

	@Override
	public Drawable getDrawable(bitmap pResId) {
		switch(pResId) {
		case center : return mContext.getResources().getDrawable(R.drawable.center);
		case direction_arrow : return mContext.getResources().getDrawable(R.drawable.direction_arrow);
		case marker_default : return mContext.getResources().getDrawable(R.drawable.marker_default);
		case marker_default_focused_base : return mContext.getResources().getDrawable(R.drawable.marker_default_focused_base);
		case navto_small : return mContext.getResources().getDrawable(R.drawable.navto_small);
		case next : return mContext.getResources().getDrawable(R.drawable.next);
		case person : return mContext.getResources().getDrawable(R.drawable.person);
		case previous : return mContext.getResources().getDrawable(R.drawable.previous);
		default : return super.getDrawable(pResId);
		}
	}
}
