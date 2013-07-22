package com.guidesystem.map;

import java.io.IOException;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MKEvent;
import com.baidu.mapapi.map.MKMapViewListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPoiInfo;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.guidesystem.common.BMapUtil;
import com.guidesystem.common.Constants;
import com.guidesystem.login.R;
import com.guidesystem.places.Comments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GuideMap extends Activity{

	private BMapManager mapManager;
	private MapView mapView;
	private MKSearch mkSearch;
	private GeoPoint p1,p2;
	private MyLocation myLocation;
	
	private Button baseButton;
	private Button satelliteButton;
	private Button searchButton;
	private AutoCompleteTextView text;
	
	private PopupOverlay pop=null;
	private View viewCache = null;
	private TextView  popupText = null;
	private View popupInfo = null;
	private View popupRight = null;
	private OverlayItem currentItem = null;
	private SceneryOverlay sOverlay=null;
	
	private OverlayItem[] sceneryItems;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		mapManager=new BMapManager(getApplication());
		mapManager.init("48771DCB41DB9E398EDFB4BFCF4664C8DE9DD8B4", null);    
		setContentView(R.layout.activity_map);
		
		setViews();
		setListeners();
		
		mkSearch=new MKSearch();
		mkSearch.init(mapManager, new MySearchListener());
		p1=new GeoPoint((int)(24.570404*1E6),(int)(118.063301*1E6));
		p2=new GeoPoint((int)(24.412036*1E6),(int)(118.209617*1E6));
//		p1=new GeoPoint((int)(39.901375 * 1E6),(int)(116.329099 * 1E6));
//		p2=new GeoPoint((int)(39.949404 * 1E6),(int)(116.360719 * 1E6));

		mapView.setBuiltInZoomControls(true);
		MapController mapController=mapView.getController();
		GeoPoint point =new GeoPoint((int)(24.452354* 1E6),(int)(118.073631* 1E6));  
		//用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)  
		mapController.setCenter(point);//设置地图中心点  
		mapController.setZoom(17);
		mapController.enableClick(true);
		
		//初始化定位类
		myLocation=new MyLocation(this, mapView);
		
		initSceneryOverlays();
	}
	@Override  
	protected void onDestroy(){  
	        mapView.destroy();  
	        if(mapManager!=null){  
	                mapManager.destroy();  
	                mapManager=null;  
	        }  
	        super.onDestroy();  
	}  
	@Override  
	protected void onPause(){
	        mapView.onPause();  
	        if(mapManager!=null){  
	               mapManager.stop();  
	        }  
	        super.onPause();  
	}  
	@Override  
	protected void onResume(){  
	        mapView.onResume();  
	        if(mapManager!=null){  
	                mapManager.start();  
	        }  
	       super.onResume();  
	}  
	
	public void setViews(){
		baseButton=(Button) findViewById(R.id.baseButton);
		satelliteButton=(Button) findViewById(R.id.satelliteButton);
		searchButton=(Button)findViewById(R.id.button_search);
		
		text=(AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,Constants.SCENERIES);
		text.setAdapter(adapter);
		
		mapView=(MapView)findViewById(R.id.mapview);
		
	}
	public void setListeners(){
		
		satelliteButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mapView.setSatellite(true);
				mapView.refresh();
			}
		});
		baseButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mapView.setSatellite(false);
				mapView.refresh();
			}
		});
		searchButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
//				String key=searchText.getText().toString();
				String key=text.getText().toString();
				Log.d("SEARCH","key:"+key);
				mkSearch.poiSearchInCity("厦门",key);
				//矩形范围搜索无效？？？
				//mkSearch.poiSearchInbounds(key, p1, p2);
			}
		});
		
		/*mapView.regMapViewListener(mapManager, new MKMapViewListener() {
			
			@Override
			public void onMapMoveFinish() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onMapAnimationFinish() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onGetCurrentMap(Bitmap arg0) {
				// TODO Auto-generated method stub
				
			}
			
			//此监听器只监听地图上的某些公认坐标点
			@Override
			public void onClickMapPoi(MapPoi poi) {
				//创建pop对象，注册点击事件监听接口 
				PopupOverlay pop=new PopupOverlay(mapView, new PopupClickListener() {
					
					@Override
					public void onClickedPopup(int arg0) {
						//在此处理pop点击事件，index为点击区域索引,点击区域最多可有三个
						Intent comment=new Intent(GuideMap.this, Comments.class);
						startActivity(comment);
						
					}
				});
				Bitmap[] bmps = new Bitmap[3];  
				try {  
					bmps[0] = BitmapFactory.decodeStream(getAssets().open("accept.png"));  
				     bmps[1] = BitmapFactory.decodeStream(getAssets().open("add.png"));  
				     bmps[2] = BitmapFactory.decodeStream(getAssets().open("back.png"));  
				} catch (IOException e) {  
				         e.printStackTrace();  
				}  
				pop.showPopup(bmps, poi.geoPt, 32);
			}
		});*/
	}
	
	public void initSceneryOverlays(){
		sOverlay=new SceneryOverlay(getResources().getDrawable(R.drawable.icon_gcoding), mapView);
		sceneryItems=new OverlayItem[Constants.SCE_NUM];
		
		for(int i=0;i<Constants.SCE_NUM;i++){
			sceneryItems[i]=new OverlayItem(new GeoPoint((int)Constants.coordinates[i][0],(int)Constants.coordinates[i][1]), Constants.SCENERIES[i], "");
			sOverlay.addItem(sceneryItems[i]);
		}
		
		mapView.getOverlays().add(sOverlay);
		mapView.refresh();
		
		viewCache=getLayoutInflater().inflate(R.layout.custom_text_view, null);
		popupInfo=viewCache.findViewById(R.id.popinfo);
		popupText=(TextView) viewCache.findViewById(R.id.scenery_name);
		popupRight=viewCache.findViewById(R.id.popright);
		
		pop=new PopupOverlay(mapView, new PopupClickListener() {
			
			@Override
			public void onClickedPopup(int index) {
				// TODO Auto-generated method stub
				if(index==0){
					Toast.makeText(GuideMap.this, "one", Toast.LENGTH_SHORT).show();
				}else if(index==1){
					Toast.makeText(GuideMap.this, "two", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	class MySearchListener implements MKSearchListener{

		@Override
		public void onGetAddrResult(MKAddrInfo arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGetBusDetailResult(MKBusLineResult arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGetDrivingRouteResult(MKDrivingRouteResult arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGetPoiDetailSearchResult(int arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGetPoiResult(MKPoiResult res, int type, int error) {
			// TODO Auto-generated method stub
			// 错误号可参考MKEvent中的定义  
			if(error==MKEvent.ERROR_RESULT_NOT_FOUND){
				Toast.makeText(GuideMap.this, "sorry,not found the result", Toast.LENGTH_LONG).show();
				return;
			}else if(error!=0 || res==null){
				Toast.makeText(GuideMap.this, "search wrong", Toast.LENGTH_LONG).show();
				return;
			}
			Toast.makeText(GuideMap.this, "showing the result", Toast.LENGTH_LONG).show();
			//poi结果显示到图层
//			PoiOverlay poiOverlay=new PoiOverlay(GuideMap.this, mapView);
			SearchPoiOverlay poiOverlay=new SearchPoiOverlay(GuideMap.this, mapView,mkSearch);
			poiOverlay.setData(res.getAllPoi());
			mapView.getOverlays().clear();
			mapView.getOverlays().add(poiOverlay);
			mapView.refresh();
			for(MKPoiInfo info:res.getAllPoi()){
				if(info.pt!=null){
					mapView.getController().animateTo(info.pt);
					break;
				}
			}
		}

		@Override
		public void onGetSuggestionResult(MKSuggestionResult arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGetTransitRouteResult(MKTransitRouteResult arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGetWalkingRouteResult(MKWalkingRouteResult arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	class SceneryOverlay extends ItemizedOverlay<OverlayItem>{

		public SceneryOverlay(Drawable arg0, MapView arg1) {
			super(arg0, arg1);
			// TODO Auto-generated constructor stub
		}

		@Override
		public boolean onTap(GeoPoint arg0, MapView arg1) {
			// TODO Auto-generated method stub
			if (pop != null){
                pop.hidePop();
			}
			return false;
		}

		@Override
		protected boolean onTap(int index) {
			// TODO Auto-generated method stub
			currentItem=getItem(index);
			 
			popupText.setText(getItem(index).getTitle());
			 
			Bitmap[] bitMaps={
			    BMapUtil.getBitmapFromView(popupInfo), 		
			    BMapUtil.getBitmapFromView(popupRight) 		
		    };
			pop.showPopup(bitMaps,currentItem.getPoint(),32);
			return true;
		}
		
		
	}
}
