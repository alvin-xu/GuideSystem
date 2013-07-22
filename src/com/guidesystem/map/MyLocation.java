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

		//����������������Ӧ�������ġ������޷���λ�������ӵ��ġ�
		locClient = new LocationClient(context.getApplicationContext());
		locData = new LocationData();
		locListener = new MyLocationListener();
		locationButton = (Button) context.findViewById(R.id.locationButton);

		// ע�ᶨλ������
		locClient.registerLocationListener(locListener);

		// ���ö�λ����
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setCoorType("bd09ll");//����ϵ����
		option.setScanSpan(5000);//��λ�������С��1s���Զ���λ����Ҫrequest���󣬴���1s��ɶ�ʱ��λ��
		option.setPriority(LocationClientOption.GpsFirst);
		locClient.setLocOption(option);
		
		//��λͼ���ʼ��
		locOverlay=new MyLocationOverlay(mapView);
		mapView.getOverlays().add(locOverlay);
		locOverlay.enableCompass();
		
		//������λ
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

	// ��λ��
	class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub
			Log.d("location", "begin listener");
			if (location == null)
				return;
			locData.latitude = location.getLatitude();
			locData.longitude = location.getLongitude();
			// �������ʾ��λ����Ȧ����accuracy��ֵΪ0����
			locData.accuracy = location.getRadius();
			locData.direction = location.getDerect();
			// ���¶�λ����
			locOverlay.setData(locData);
			// ����ͼ������ִ��ˢ�º���Ч

			mapView.refresh();
			
			Log.d("location", "after refresh" + locData.latitude + "-"
					+ locData.longitude);

			// �ƶ���ͼ����λ��
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
