package com.guidesystem.map;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.PoiOverlay;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.mapapi.search.MKPoiInfo;
import com.baidu.mapapi.search.MKSearch;
import com.guidesystem.places.Comments;

public class SearchPoiOverlay extends PoiOverlay {

	private MKSearch mkSearch;
	private MapView mapView;
	private Activity activity;

	public SearchPoiOverlay(Activity activity, MapView mapView,
			MKSearch mkSearch) {
		super(activity, mapView);
		// TODO Auto-generated constructor stub
		this.mkSearch = mkSearch;
		this.mapView = mapView;
		this.activity = activity;
	}

	@Override
	protected boolean onTap(int arg0) {
		// TODO Auto-generated method stub
		super.onTap(arg0);

		final MKPoiInfo mkPoiInfo = getPoi(arg0);

		// 创建pop对象，注册点击事件监听接口
		PopupOverlay pop = new PopupOverlay(mapView, new PopupClickListener() {

			@Override
			public void onClickedPopup(int arg0) {
				// 在此处理pop点击事件，index为点击区域索引,点击区域最多可有三个
				if (arg0 == 1) {
					if (mkPoiInfo.hasCaterDetails) {
						mkSearch.poiDetailSearch(mkPoiInfo.uid);
					}else{
						Intent comment=new Intent(activity, Comments.class);
						activity.startActivity(comment);
					}
				}
			}
		});
		Bitmap[] bmps = new Bitmap[3];
		try {
			bmps[0] = BitmapFactory.decodeStream(activity.getAssets().open(
					"accept.png"));
			bmps[1] = BitmapFactory.decodeStream(activity.getAssets().open(
					"add.png"));
			bmps[2] = BitmapFactory.decodeStream(activity.getAssets().open(
					"back.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		pop.showPopup(bmps, mkPoiInfo.pt, 32);
		super.onTap(arg0);
		return true;
	}
	

}
