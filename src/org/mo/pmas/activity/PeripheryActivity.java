package org.mo.pmas.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;

import org.mo.common.activity.BaseFramgmentActivity;
import org.mo.common.util.ToastUtil;

/**
 * Created by moziqi on 2015/1/14 0014.
 */
public class PeripheryActivity extends BaseFramgmentActivity implements OnGetPoiSearchResultListener, OnGetSuggestionResultListener {
    private static final float MAP_ZOOM_LEVEL = 15;

    private LocationClient mLocationClient;

    public MyLocationListenner myListener = new MyLocationListenner();
    private MyLocationConfiguration.LocationMode mCurrentMode;
    private BitmapDescriptor mCurrentMarker;

    boolean isFirstLoc = true;// 是否首次定位
    private Button requestLocButton;
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private PoiSearch mPoiSearch = null;
    private SuggestionSearch mSuggestionSearch = null;
    private LatLng latLng;
    private int load_Index = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_periphery);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void toInitUI() {
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(this);
        mMapView = (MapView) findViewById(R.id.bmapView);
        requestLocButton = (Button) findViewById(R.id.modeBtn);
        mBaiduMap = mMapView.getMap();
        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        //开启交通图
        mBaiduMap.setTrafficEnabled(true);
        //开启交通图
        mBaiduMap.setBaiduHeatMapEnabled(true);
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        requestLocButton.setText("普通模式");
        requestLocButton.setOnClickListener(btnClickListener);
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true, mCurrentMarker));
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(MAP_ZOOM_LEVEL));
        // 定位初始化
        mLocationClient = new LocationClient(this);
        mLocationClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//设置定位模式
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setIsNeedAddress(true);//返回的定位结果包含地址信息
        option.setNeedDeviceDirect(true);//返回的定位结果包含手机机头的方向
        //option.setScanSpan(1000);//设置定位的时间间隔
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    @Override
    protected void toUIOper() {

    }

    @Override
    protected void onStop() {
        mLocationClient.stop();
        super.onStop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return true;
        }
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mLocationClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mSuggestionSearch.destroy();
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }

    @Override
    public void onGetPoiResult(PoiResult poiResult) {
        if (poiResult == null || poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
            Toast.makeText(PeripheryActivity.this, "未找到结果", Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {
            mBaiduMap.clear();
            PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
            mBaiduMap.setOnMarkerClickListener(overlay);
            overlay.setData(poiResult);
            overlay.addToMap();
            overlay.zoomToSpan();
            return;
        }
        if (poiResult.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {

            // 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
            String strInfo = "在";
            for (CityInfo cityInfo : poiResult.getSuggestCityList()) {
                strInfo += cityInfo.city;
                strInfo += ",";
            }
            strInfo += "找到结果";
            Toast.makeText(PeripheryActivity.this, strInfo, Toast.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
//        if (poiDetailResult.error != SearchResult.ERRORNO.NO_ERROR) {
//            ToastUtil.showLongToast(PeripheryActivity.this, "查找失败");
//        } else {
//            final String detailUrl = poiDetailResult.getDetailUrl();
//            new AlertDialog.Builder(this).setTitle(poiDetailResult.getName()).setMessage(poiDetailResult.getAddress() + ":" + poiDetailResult.getTelephone()).setPositiveButton(R.string.cancel,new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                }
//            }).setNegativeButton(R.string.search_inter_url,new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    Intent intent = new Intent();
//                    intent.putExtra("url",detailUrl);
//                    intent.setClass(PeripheryActivity.this, BrowserWebInfoActivity.class);
//                    PeripheryActivity.this.startActivity(intent);
//                    dialog.dismiss();
//                }
//
//            }).show();
//        }
    }

    @Override
    public void onGetSuggestionResult(SuggestionResult suggestionResult) {
        if (suggestionResult == null || suggestionResult.getAllSuggestions() == null) {
            return;
        }
    }

    private class MyPoiOverlay extends PoiOverlay {

        public MyPoiOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public boolean onPoiClick(int index) {
            super.onPoiClick(index);
            PoiInfo poi = getPoiResult().getAllPoi().get(index);
            // if (poi.hasCaterDetails) {
            mPoiSearch.searchPoiDetail((new PoiDetailSearchOption())
                    .poiUid(poi.uid));
            // }
            return true;
        }
    }


    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置

            ToastUtil.showShortToast(PeripheryActivity.this, "启动完成");
            if (location == null || mMapView == null)
                return;
            // 构造定位数据
            MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(MAP_ZOOM_LEVEL));
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                latLng = new LatLng(location.getLatitude(), location.getLongitude());
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(latLng);
                mBaiduMap.animateMapStatus(u);
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    View.OnClickListener btnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (mCurrentMode) {
                case NORMAL:
                    requestLocButton.setText("跟随模式");
                    mCurrentMode = MyLocationConfiguration.LocationMode.FOLLOWING;
                    mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true, mCurrentMarker));
                    break;
                case COMPASS:
                    requestLocButton.setText("普通模式");
                    mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
                    mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true, mCurrentMarker));
                    break;
                case FOLLOWING:
                    requestLocButton.setText("罗盘模式");
                    mCurrentMode = MyLocationConfiguration.LocationMode.COMPASS;
                    mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true, mCurrentMarker));
                    break;
            }
        }
    };
}