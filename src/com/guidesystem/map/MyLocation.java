package com.guidesystem.map;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.guidesystem.login.R;

public class MyLocation {

	private Activity context;
	private MapView mapView;
	private Button locationButton;

	private LocationClient locClient;
	private MyLocationOverlay locOverlay;
	private LocationData locData;
	private MyLocationListener locListener;
	
	private boolean firstL=true;

	public MyLocation(Activity context, MapView mapView) {
		this.context = context;
		this.mapView = mapView;

		//这个，参数如果不是应用上下文。。。无法定位！！！坑爹的。
		locClient = new LocationClient(context.getApplicationContext());
		locData = new LocationData();
		locListener = new MyLocationListener();
		locationButton = (Button) context.findViewById(R.id.locationButton);

		// 注册定位监听器
		locClient.registerLocationListener(locListener);

		// 设置定位参数
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setCoorType("bd09ll");//坐标系类型
		option.setScanSpan(5000);//定位间隔，若小于1s则不自动定位，需要request请求，大于1s则可定时定位。
		option.setPriority(LocationClientOption.GpsFirst);
		locClient.setLocOption(option);
		
		//定位图层初始化
		locOverlay=new MyLocationOverlay(mapView);
		mapView.getOverlays().add(locOverlay);
		locOverlay.enableCompass();
		
		//开启定位
		locClient.start();
		Log.d("location","start location");
		locClient.requestLocation();
		
		locationButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				firstL=true;
				Log.d("location", "click the location button");
				locClient.requestLocation();
				Log.d("location", "after request");
			}
		});

	}

	// 定位类
	class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub
			Log.d("location", "begin listener");
			if (location == null)
				return;
			locData.latitude = location.getLatitude();
			locData.longitude = location.getLongitude();
			// 如果不显示定位精度圈，将accuracy赋值为0即可
			locData.accuracy = location.getRadius();
			locData.direction = location.getDerect();
			// 更新定位数据
			locOverlay.setData(locData);
			// 更新图层数据执行刷新后生效

			mapView.refresh();
			
			Log.d("location", "after refresh" + locData.latitude + "-"
					+ locData.longitude);

			// 移动地图到定位点
			if(firstL==true){
				mapView.getController().animateTo(
						new GeoPoint((int) (locData.latitude * 1e6),
								(int) (locData.longitude * 1e6)));
				Log.d("location", "animate to ....");
				firstL=false;
			}

		}

		@Override
		public void onReceivePoi(BDLocation arg0) {
			// TODO Auto-generated method stub

		}

	}
}
