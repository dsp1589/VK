package com.blogspot.hongthaiit.slidemenu;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
//import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.garmentbazaarb2b.garmentmandi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Modal.MostSellingProduct;
import garmentmandi.utils.AppUrls;
import garmentmandi.web.CheckInternetConnectio;
import garmentmandi.web.IResponse;
import garmentmandi.web.Web;


public class SlidingActivity extends AppCompatActivity implements IResponse,NavigationView.OnNavigationItemSelectedListener {


    private ArrayList<Product> pProductArrayList;

    private ArrayList<LinearLayout> pLytArrayList=new ArrayList<>();
    private ArrayList<RelativeLayout> pLytArrayListparent=new ArrayList<>();
    private ArrayList<LinearLayout> pLytArrayList1=new ArrayList<>();
    private ArrayList<TextView> pLytArrayListparent1=new ArrayList<>();
    private ArrayList<LinearLayout> pLytArrayList2=new ArrayList<>();
    private ArrayList<TextView> pLytArrayListparent2=new ArrayList<>();


    private LinearLayout mLinearListView;
    boolean isFirstViewClick=false;
    boolean isSecondViewClick=false;
    boolean isThirdViewClick=false;
    LayoutInflater inflater;
    String mProductNamecheck="",mProductNamechecksub="",mProductNamechecksub2="";
    int check1,check2;

    int check;


    TextView sellproductTxt,arrivedTxt,sellbrandTxt;
    LinearLayout Lyt,boyLyt,girlLyt,menLyt,womenLyt;
    private 	ProgressDialog progress,progress2,progress3;
    private Handler handler;
    ArrayList<MostSellingProduct> mostSellingProductArrayList=new ArrayList<>();
    ArrayList<MostSellingProduct> boyArrayList=new ArrayList<>();
    ArrayList<MostSellingProduct> girlArrayList=new ArrayList<>();
    ArrayList<MostSellingProduct> menArrayList=new ArrayList<>();
    ArrayList<MostSellingProduct> womenArrayList=new ArrayList<>();

    private DisplayImageOptions options;
    String url=AppUrls.mostSellingProductUrl;
    View headerView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_view_top);
        pProductArrayList=new ArrayList<Product>();
//        getSupportActionBar().hide();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        toolbar.setNavigationIcon(R.drawable.more);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(Gravity.LEFT)) {
                    drawer.closeDrawer(Gravity.LEFT);
                } else {
                    drawer.openDrawer(Gravity.LEFT);
                }
            }
        });
        headerView = navigationView.inflateHeaderView(R.layout.nav_header_main1);
        mLinearListView = (LinearLayout) headerView.findViewById(R.id.linear_listview);

        initView();

        sellproductTxt.setTextColor(getResources().getColor(R.color.color_orange));
        sellproductTxt.setBackgroundResource(R.drawable.bg_line);

        sellproductTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sellproductTxt.setTextColor(getResources().getColor(R.color.color_orange));
                arrivedTxt.setTextColor(getResources().getColor(R.color.color_black));
                sellbrandTxt.setTextColor(getResources().getColor(R.color.color_black));
                sellproductTxt.setBackgroundResource(R.drawable.bg_line);
                sellbrandTxt.setBackgroundColor(getResources().getColor(R.color.color_gray));
                arrivedTxt.setBackgroundColor(getResources().getColor(R.color.color_gray));
                url = AppUrls.mostSellingProductUrl;
        GetData();
    }
});
        arrivedTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sellproductTxt.setTextColor(getResources().getColor(R.color.color_black));
                arrivedTxt.setTextColor(getResources().getColor(R.color.color_orange));
                sellbrandTxt.setTextColor(getResources().getColor(R.color.color_black));
                sellproductTxt.setBackgroundColor(getResources().getColor(R.color.color_gray));
                sellbrandTxt.setBackgroundColor(getResources().getColor(R.color.color_gray));
                arrivedTxt.setBackgroundResource(R.drawable.bg_line);
                url = AppUrls.newarrivedUrl;
                GetData();


            }
        });
        sellbrandTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sellproductTxt.setTextColor(getResources().getColor(R.color.color_black));
                arrivedTxt.setTextColor(getResources().getColor(R.color.color_black));
                sellbrandTxt.setTextColor(getResources().getColor(R.color.color_orange));
                sellproductTxt.setBackgroundColor(getResources().getColor(R.color.color_gray));
                sellbrandTxt.setBackgroundResource(R.drawable.bg_line);
                arrivedTxt.setBackgroundColor(getResources().getColor(R.color.color_gray));
                url = AppUrls.mostSellingbrandUrl;
                GetData();

            }
        });


    }
    public static void initImageLoader(Context context) {
// This configuration tuning is custom. You can tune every option, you may tune some of them,
// or you can create default configuration by
//  ImageLoaderConfiguration.createDefault(this);
// method.
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

// Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }
   void initView()
    {

        initImageLoader(getApplicationContext());
        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.icon)
                .showImageOnFail(R.drawable.icon)
                .cacheInMemory(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        sellproductTxt=(TextView)findViewById(R.id.mostsellproTxt);
        arrivedTxt=(TextView)findViewById(R.id.newarrivedTxt);
        sellbrandTxt=(TextView)findViewById(R.id.sellbrandTxt);
        Lyt=(LinearLayout)findViewById(R.id.topLyt);
        boyLyt=(LinearLayout)findViewById(R.id.boysLyt);
        girlLyt=(LinearLayout)findViewById(R.id.girlsLyt);
        menLyt=(LinearLayout)findViewById(R.id.menLyt);
        womenLyt=(LinearLayout)findViewById(R.id.womenLyt);

        handler=new Handler();
        CheckInternetConnectio _checkInternetConnection = new CheckInternetConnectio(
                SlidingActivity.this);

        if(_checkInternetConnection.checkInterntConnection())
        {
            GetDataDrawer();
            GetData();
            GetBoyData();
        }
        else {
            Toast.makeText(SlidingActivity.this, "Check Internet Connection",
                    Toast.LENGTH_SHORT).show();

        }

    }


    /*get data from server*/
    public void GetData() {
        progress = ProgressDialog.show(SlidingActivity.this, null,
                "Please Wait....", true);
        progress.setCancelable(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
//
                new Web().requestGet(url,
                        SlidingActivity.this, 100);



            }
        }).start();
    }
    /*get data from server*/
    public void GetBoyData() {

        progress3 = ProgressDialog.show(SlidingActivity.this, null,
                "Please Wait....", true);
        progress3.setCancelable(true);

        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
//
                new Web().requestGet(AppUrls.boysProductUrl,
                        SlidingActivity.this, 200);



            }
        }).start();
    }
    /*get data from server*/
    public void GetGirlData() {
     /*   progress = ProgressDialog.show(SlidingActivity.this, null,
                "Please Wait....", true);
        progress.setCancelable(true);
     */   new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
//
                new Web().requestGet(AppUrls.girlsProductUrl,
                        SlidingActivity.this, 300);



            }
        }).start();
    }

    /*get data from server*/
    public void GetMenData() {
        /*progress = ProgressDialog.show(SlidingActivity.this, null,
                "Please Wait....", true);
        progress.setCancelable(true);*/
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
//
                new Web().requestGet(AppUrls.menProductUrl,
                        SlidingActivity.this, 400);



            }
        }).start();
    }

    /*get data from server*/
    public void GetWomenData() {
     /*   progress = ProgressDialog.show(SlidingActivity.this, null,
                "Please Wait....", true);
        progress.setCancelable(true);*/
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
//
                new Web().requestGet(AppUrls.womenProductUrl,
                        SlidingActivity.this, 500);



            }
        }).start();
    }



    @Override
    public void onComplete(String result, int i) {

        if(result.length()>0) {

            if (i == 50) {
                jsonParserDrawer(result, i);
            } else {
                jsonParser(result, i);
            }
        }else {
            progress.cancel();
        }
    }

    /*parse json result*/
    void jsonParser(final  String result,final int i)
    {  if(i==100)
        {
            mostSellingProductArrayList.clear();
            progress.cancel();
            handler.post(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    JSONObject obj2;

                    JSONObject obj = null;
                    try {
                        obj = new JSONObject(result);
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    JSONArray array = null;
                    try {
                        array = obj.getJSONArray("result");
                    } catch (JSONException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }

                    for (int j = 0; j < array.length(); j++) {
                        try {
                            obj2 = (JSONObject) array.get(j);
                            mostSellingProductArrayList.add(new MostSellingProduct(obj2.getString("deal_title"), obj2.getString("deal_price"), obj2.getString("store_name"), obj2.getString("category_name"), obj2.getString("product_image")));

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                    addTopView();

                }
            });
        }else if(i==200)
        {
//            progress.cancel();
//            Log.i("hello", i+"");
            handler.post(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    JSONObject obj2;

                    JSONObject obj = null;
                    try {
                        obj = new JSONObject(result);
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    JSONArray array = null;
                    try {
                        array = obj.getJSONArray("result");
                    } catch (JSONException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }

                    for (int j = 0; j < array.length(); j++) {
                        try {
                            obj2 = (JSONObject) array.get(j);
                                boyArrayList.add(new MostSellingProduct(obj2.getString("deal_title"), obj2.getString("deal_price"), obj2.getString("store_name"), obj2.getString("category_name"), obj2.getString("product_image")));
                               } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                    for(int i=0;i<boyArrayList.size();i++)
                    {
                        LayoutInflater inflater2 = null;
                        inflater2 = (LayoutInflater) SlidingActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View mLinearView2 = inflater2.inflate(R.layout.custom_view2, null);
                        ImageView productimg=(ImageView)mLinearView2.findViewById(R.id.productIView);
                        TextView deal_nameTxt=(TextView)mLinearView2.findViewById(R.id.deal_titleTxt);
                        TextView store_nameTxt=(TextView)mLinearView2.findViewById(R.id.storenameTxt);
                        TextView priceTxt=(TextView)mLinearView2.findViewById(R.id.priceTxt);
                        deal_nameTxt.setText(boyArrayList.get(i).getDeal_title());
                        store_nameTxt.setText(boyArrayList.get(i).getStore_name());
                        priceTxt.setText("Rs."+boyArrayList.get(i).getDeal_price());


//                        ImageLoader.getInstance()
//                                .displayImage(boyArrayList.get(i).getImageUrl(), productimg, options, new SimpleImageLoadingListener() {
//                                    @Override
//                                    public void onLoadingStarted(String imageUri, View view) {
//                                    }
//                                });

                        boyLyt.addView(mLinearView2);

                    }

GetGirlData();
                }
            });
        }else if(i==300)
        {
//            progress.cancel();
//            Log.i("hello", i+"");
            handler.post(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    JSONObject obj2;

                    JSONObject obj = null;
                    try {
                        obj = new JSONObject(result);
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    JSONArray array = null;
                    try {
                        array = obj.getJSONArray("result");
                    } catch (JSONException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }

                    for (int j = 0; j < array.length(); j++) {
                        try {
                            obj2 = (JSONObject) array.get(j);
                            girlArrayList.add(new MostSellingProduct(obj2.getString("deal_title"), obj2.getString("deal_price"), obj2.getString("store_name"), obj2.getString("category_name"), obj2.getString("product_image")));
//                            Log.i("hello", i + obj2.getString("product_image"));

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                    for(int i=0;i<girlArrayList.size();i++)
                    {
                        LayoutInflater inflater2 = null;
                        inflater2 = (LayoutInflater) SlidingActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View mLinearView2 = inflater2.inflate(R.layout.custom_view2, null);
                        ImageView productimg=(ImageView)mLinearView2.findViewById(R.id.productIView);
                        TextView deal_nameTxt=(TextView)mLinearView2.findViewById(R.id.deal_titleTxt);
                        TextView store_nameTxt=(TextView)mLinearView2.findViewById(R.id.storenameTxt);
                        TextView priceTxt=(TextView)mLinearView2.findViewById(R.id.priceTxt);
                        deal_nameTxt.setText(girlArrayList.get(i).getDeal_title());
                        store_nameTxt.setText(girlArrayList.get(i).getStore_name());
                        priceTxt.setText("Rs."+girlArrayList.get(i).getDeal_price());


//                        ImageLoader.getInstance()
//                                .displayImage(girlArrayList.get(i).getImageUrl(), productimg, options, new SimpleImageLoadingListener() {
//                                    @Override
//                                    public void onLoadingStarted(String imageUri, View view) {
//                                    }
//                                });

                        girlLyt.addView(mLinearView2);

                    }
                    GetMenData();
                }
            });
        }else if(i==400){
//            progress.cancel();
//            Log.i("hello", i+"");
            handler.post(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    JSONObject obj2;

                    JSONObject obj = null;
                    try {
                        obj = new JSONObject(result);
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    JSONArray array = null;
                    try {
                        array = obj.getJSONArray("result");
                    } catch (JSONException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }

                    for (int j = 0; j < array.length(); j++) {
                        try {
                            obj2 = (JSONObject) array.get(j);
                            menArrayList.add(new MostSellingProduct(obj2.getString("deal_title"), obj2.getString("deal_price"), obj2.getString("store_name"), obj2.getString("category_name"), obj2.getString("product_image")));


                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                    for(int i=0;i<menArrayList.size();i++)
                    {
                        LayoutInflater inflater2 = null;
                        inflater2 = (LayoutInflater) SlidingActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View mLinearView2 = inflater2.inflate(R.layout.custom_view2, null);
                        ImageView productimg=(ImageView)mLinearView2.findViewById(R.id.productIView);
                        TextView deal_nameTxt=(TextView)mLinearView2.findViewById(R.id.deal_titleTxt);
                        TextView store_nameTxt=(TextView)mLinearView2.findViewById(R.id.storenameTxt);
                        TextView priceTxt=(TextView)mLinearView2.findViewById(R.id.priceTxt);
                        deal_nameTxt.setText(menArrayList.get(i).getDeal_title());
                        store_nameTxt.setText(menArrayList.get(i).getStore_name());
                        priceTxt.setText("Rs."+menArrayList.get(i).getDeal_price());


//                        ImageLoader.getInstance()
//                                .displayImage(menArrayList.get(i).getImageUrl(), productimg, options, new SimpleImageLoadingListener() {
//                                    @Override
//                                    public void onLoadingStarted(String imageUri, View view) {
//                                    }
//                                });

                        menLyt.addView(mLinearView2);

                    }

                    GetWomenData();

                }
            });
        }
else if(i==500)      {
            progress3.cancel();
//            Log.i("hello", i+"");
            handler.post(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    JSONObject obj2;

                    JSONObject obj = null;
                    try {
                        obj = new JSONObject(result);
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    JSONArray array = null;
                    try {
                        array = obj.getJSONArray("result");
                    } catch (JSONException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }

                    for (int j = 0; j < array.length(); j++) {
                        try {
                            obj2 = (JSONObject) array.get(j);

                                womenArrayList.add(new MostSellingProduct(obj2.getString("deal_title"), obj2.getString("deal_price"), obj2.getString("store_name"), obj2.getString("category_name"), obj2.getString("product_image")));

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                    for(int i=0;i<womenArrayList.size();i++)
                    {
                        LayoutInflater inflater2 = null;
                        inflater2 = (LayoutInflater) SlidingActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View mLinearView2 = inflater2.inflate(R.layout.custom_view2, null);
                        ImageView productimg=(ImageView)mLinearView2.findViewById(R.id.productIView);
                        TextView deal_nameTxt=(TextView)mLinearView2.findViewById(R.id.deal_titleTxt);
                        TextView store_nameTxt=(TextView)mLinearView2.findViewById(R.id.storenameTxt);
                        TextView priceTxt=(TextView)mLinearView2.findViewById(R.id.priceTxt);
                        deal_nameTxt.setText(womenArrayList.get(i).getDeal_title());
                        store_nameTxt.setText(womenArrayList.get(i).getStore_name());
                        priceTxt.setText("Rs."+womenArrayList.get(i).getDeal_price());

//
//                        ImageLoader.getInstance()
//                                .displayImage(womenArrayList.get(i).getImageUrl(), productimg, options, new SimpleImageLoadingListener() {
//                                    @Override
//                                    public void onLoadingStarted(String imageUri, View view) {
//                                    }
//                                });

                        womenLyt.addView(mLinearView2);

                    }

                }
            });
        }
    }
   void addTopView()
    {

        Log.i("hello", mostSellingProductArrayList.size() + "....");
        Lyt.removeAllViews();
            for (int i=0;i<mostSellingProductArrayList.size();i++) {
              final   int pos=i;
            LayoutInflater inflater2 = null;
            inflater2 = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View mLinearView2 = inflater2.inflate(R.layout.custom_view2, null);
                ImageView imageView = (ImageView) mLinearView2.findViewById(R.id.productIView);
                String imgUrl=mostSellingProductArrayList.get(i).getImageUrl();
                TextView deal_nameTxt=(TextView)mLinearView2.findViewById(R.id.deal_titleTxt);
                TextView store_nameTxt=(TextView)mLinearView2.findViewById(R.id.storenameTxt);
                TextView priceTxt=(TextView)mLinearView2.findViewById(R.id.priceTxt);
                deal_nameTxt.setText(mostSellingProductArrayList.get(i).getDeal_title());
                store_nameTxt.setText(mostSellingProductArrayList.get(i).getStore_name());
                priceTxt.setText("Rs."+mostSellingProductArrayList.get(i).getDeal_price());

//
//                ImageLoader.getInstance()
//                        .displayImage(imgUrl, imageView, options, new SimpleImageLoadingListener() {
//                            @Override
//                            public void onLoadingStarted(String imageUri, View view) {
//                            }
//                        });


if(mostSellingProductArrayList.get(i).getCategory_name().equalsIgnoreCase("men")) {
    mLinearView2.findViewById(R.id.categoryView).setBackgroundResource(R.drawable.men);
}           else if(mostSellingProductArrayList.get(i).getCategory_name().equalsIgnoreCase("women")) {
    mLinearView2.findViewById(R.id.categoryView).setBackgroundResource(R.drawable.women);
}     else if(mostSellingProductArrayList.get(i).getCategory_name().equalsIgnoreCase("girls")) {
    mLinearView2.findViewById(R.id.categoryView).setBackgroundResource(R.drawable.girls);
}     else {
    mLinearView2.findViewById(R.id.categoryView).setBackgroundResource(R.drawable.boys);
}

/*                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(SlidingActivity.this, "hello" + pos, Toast.LENGTH_LONG).show();
                    }
                });*/
                Lyt.addView(mLinearView2);

                        }

//GetBoyData();

            }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(Gravity.LEFT)) {
            drawer.closeDrawer(Gravity.LEFT);
        } else {
            drawer.openDrawer(Gravity.LEFT);
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

       /* if (id == R.id.nav_camara) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(Gravity.LEFT);
        return true;
    }



    void addSlideMenuView()
    {


        /***
         * adding item into listview
         */
        for (int i = 0; i < pProductArrayList.size(); i++) {
            inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View mLinearView = inflater.inflate(R.layout.row_first, null);

            final TextView mProductName = (TextView) mLinearView.findViewById(R.id.textViewName);
            final RelativeLayout mLinearFirstArrow=(RelativeLayout)mLinearView.findViewById(R.id.linearFirst);
//			final ImageView mImageArrowFirst=(ImageView)mLinearView.findViewById(R.id.imageFirstArrow);
            final LinearLayout mLinearScrollSecond=(LinearLayout)mLinearView.findViewById(R.id.linear_scroll);

            if(isFirstViewClick==false){
                mLinearScrollSecond.setVisibility(View.GONE);

            }
            else{
                mLinearScrollSecond.setVisibility(View.VISIBLE);
                mLinearFirstArrow.setBackgroundResource(R.drawable.button_bghh);
            }

            pLytArrayList.add(mLinearScrollSecond);
            pLytArrayListparent.add(mLinearFirstArrow);
            mLinearFirstArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    for (int m=0;m<pProductArrayList.size();m++)
                    {
                        if(pLytArrayList.get(m).getVisibility()==View.VISIBLE)
                        {

                            pLytArrayListparent.get(m).setBackgroundColor(Color.WHITE);

                            pLytArrayList.get(m).setVisibility(View.GONE);
                            if(mProductNamecheck.equals(mProductName.getText())) {
                                Log.i("hello", mProductName.getText() + "");
                                isFirstViewClick=true;
                            }else {
                                isFirstViewClick = false;
                            }
                        }

                    }

                    if(isFirstViewClick==false){
                        isFirstViewClick=true;
                        mLinearFirstArrow.setBackgroundResource(R.drawable.button_bghh);
                        mLinearScrollSecond.setVisibility(View.VISIBLE);
                        mProductNamecheck=(String)mProductName.getText();

                    }else{
                        isFirstViewClick=false;
                        mLinearFirstArrow.setBackgroundColor(Color.WHITE);
                        mLinearScrollSecond.setVisibility(View.GONE);
                    }
                }

            });

            final String name = pProductArrayList.get(i).getpName();
            mProductName.setText(name);

            /**
             *
             */
            for ( int j = 0; j < pProductArrayList.get(i).getmSubCategoryList().size(); j++) {

                LayoutInflater inflater2 = null;
                inflater2 = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View mLinearView2 = inflater2.inflate(R.layout.row_second, null);

                final TextView mSubItemName = (TextView) mLinearView2.findViewById(R.id.textViewTitle);
                final RelativeLayout mLinearSecondArrow=(RelativeLayout)mLinearView2.findViewById(R.id.linearSecond);
                final LinearLayout mLinearScrollThird=(LinearLayout)mLinearView2.findViewById(R.id.linear_scroll_third);

                if(isSecondViewClick==false){
                    mLinearScrollThird.setVisibility(View.GONE);
                    mSubItemName.setBackgroundColor(Color.WHITE);
                    mSubItemName.setTextColor(Color.BLACK);
                }
                else{
                    mLinearScrollThird.setVisibility(View.VISIBLE);
                    mSubItemName.setBackgroundResource(R.drawable.subbutton);
                    mSubItemName.setTextColor(getResources().getColor(R.color.color_orange));

                }
                pLytArrayListparent1.add(mSubItemName);
                pLytArrayList1.add(mLinearScrollThird);
                mLinearSecondArrow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        for (int m=0;m<pLytArrayListparent1.size();m++)
                        {
                            if(pLytArrayList1.get(m).getVisibility()==View.VISIBLE)
                            {

                                pLytArrayListparent1.get(m).setBackgroundColor(Color.WHITE);

                                pLytArrayList1.get(m).setVisibility(View.GONE);
                                pLytArrayListparent1.get(m).setTextColor(Color.BLACK);

                                if(mProductNamechecksub.equals(mSubItemName.getText())) {
                                    Log.i("hello", mSubItemName.getText() + "");
                                    isSecondViewClick=true;
                                }else {
                                    isSecondViewClick = false;
                                }
                            }

                        }
                        if(isSecondViewClick==false){
                            isSecondViewClick=true;
                            mSubItemName.setBackgroundResource(R.drawable.subbutton);
                            mSubItemName.setTextColor(getResources().getColor(R.color.color_orange));
                            mLinearScrollThird.setVisibility(View.VISIBLE);
                            mProductNamechecksub=(String)mSubItemName.getText();

                            Log.i("hello", mSubItemName.getText() + "");

                        }else{
                            isSecondViewClick=false;
                            mSubItemName.setBackgroundColor(Color.WHITE);
                            mLinearScrollThird.setVisibility(View.GONE);
                            mSubItemName.setTextColor(Color.BLACK);

                        }
                    }

                });

                final String catName = pProductArrayList.get(i).getmSubCategoryList().get(j).getpSubCatName();
                mSubItemName.setText(catName);
                /**
                 *
                 */
                for (int k = 0; k < pProductArrayList.get(i).getmSubCategoryList().get(j).getmItemListArray().size(); k++) {

                    LayoutInflater inflater3 = null;
                    inflater3 = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View mLinearView3 = inflater3.inflate(R.layout.row_third, null);
                    final LinearLayout mLinearScrollFourth=(LinearLayout)mLinearView3.findViewById(R.id.linear_scroll_fourth);

                    final TextView mItemName = (TextView) mLinearView3.findViewById(R.id.textViewItemName);
                    TextView mItemPrice = (TextView) mLinearView3.findViewById(R.id.textViewItemPrice);
                    final String itemName = pProductArrayList.get(i).getmSubCategoryList().get(j).getmItemListArray().get(k).getItemName();
                    final String itemPrice = pProductArrayList.get(i).getmSubCategoryList().get(j).getmItemListArray().get(k).getItemPrice();
                    mItemName.setText(itemName);
                    mItemPrice.setText(itemPrice);
                    if(isThirdViewClick==false){
                        mLinearScrollFourth.setVisibility(View.GONE);
                        mItemName.setBackgroundColor(Color.WHITE);
                        mSubItemName.setTextColor(Color.BLACK);
                    }
                    else{
                        mLinearScrollFourth.setVisibility(View.VISIBLE);
                        mItemName.setBackgroundResource(R.drawable.subbutton);
                        mSubItemName.setTextColor(getResources().getColor(R.color.color_orange));

                    }

                    pLytArrayList2.add(mLinearScrollFourth);
                    pLytArrayListparent2.add(mItemName);
                    mItemName.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            for (int m=0;m<pLytArrayListparent2.size();m++)
                            {
                                if(pLytArrayList2.get(m).getVisibility()==View.VISIBLE)
                                {

                                    pLytArrayListparent2.get(m).setBackgroundColor(Color.WHITE);

                                    pLytArrayList2.get(m).setVisibility(View.GONE);
                                    pLytArrayListparent2.get(m).setTextColor(Color.BLACK);

                                    if(mProductNamechecksub2.equals(mItemName.getText())) {
                                        Log.i("hello", mItemName.getText() + "");
                                        isThirdViewClick=true;
                                    }else {
                                        isThirdViewClick = false;
                                    }
                                }

                            }
                            if (isThirdViewClick == false) {
                                isThirdViewClick = true;
                                mItemName.setBackgroundResource(R.drawable.subbutton);
                                mItemName.setTextColor(getResources().getColor(R.color.color_orange));

                                mLinearScrollFourth.setVisibility(View.VISIBLE);
                                mProductNamechecksub2=(String)mItemName.getText();

                            } else {
                                isThirdViewClick = false;
                                mItemName.setBackgroundColor(Color.WHITE);
                                mLinearScrollFourth.setVisibility(View.GONE);
                                mItemName.setTextColor(Color.BLACK);

                            }
                        }

                    });



                    for (int l = 0; l <  pProductArrayList.get(i).getmSubCategoryList().get(j).getmItemListArray().get(k).getmItemListArray().size(); l++) {
                        LayoutInflater inflater4 = null;
                        inflater3 = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View mLinearView4 = inflater3.inflate(R.layout.row_fourth, null);
                        TextView mLastItemname=(TextView)mLinearView4.findViewById(R.id.textViewItemNamelast);
                        mLastItemname.setText(pProductArrayList.get(i).getmSubCategoryList().get(j).getmItemListArray().get(k).getmItemListArray().get(l).getItemName());
                        mLinearScrollFourth.addView(mLinearView4);
                    }

                    mLinearScrollThird.addView(mLinearView3);


                }

                mLinearScrollSecond.addView(mLinearView2);

            }

            mLinearListView.addView(mLinearView);
        }

    }
    /*get data from server*/
    public void GetDataDrawer() {
        progress2 = ProgressDialog.show(SlidingActivity.this, null,
                "Please Wait....", true);
        progress2.setCancelable(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
//
                new Web().requestGet(AppUrls.slideMenuListUrl,
                        SlidingActivity.this, 50);



            }
        }).start();
    }
    /*parse json result*/
    void jsonParserDrawer(final  String result,int i)
    {
        progress2.cancel();

        handler.post(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                JSONObject obj2,obj3,obj4,obj5;

                JSONObject obj = null;
                try {
                    obj = new JSONObject(result);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                JSONArray array = null;
                try {
                    array = obj.getJSONArray("result");
                } catch (JSONException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }


                for (int j = 0; j < array.length(); j++) {

                    try {
                        obj2 = (JSONObject) array.get(j);

                        JSONArray arraysub = null;
                        ArrayList<Product.SubCategory>pSubItemArrayList=new ArrayList<Product.SubCategory>();
                        try {
                            arraysub = obj2.getJSONArray("subcat");

                            for (int k = 0; k < arraysub.length(); k++) {
                                obj3 = (JSONObject) arraysub.get(k);
                                ArrayList<Product.SubCategory.ItemList> mSecSubItemListArray=new ArrayList<Product.SubCategory.ItemList>();
                                if(obj3.has("subcat")) {
                                    JSONArray arraysub2 = null;
                                    arraysub2 = obj3.getJSONArray("subcat");



                                    for (int l = 0; l < arraysub2.length(); l++) {
                                        obj4 = (JSONObject) arraysub2.get(l);
                                        ArrayList<Product.SubCategory.ItemList4> mThirdSubItemListArray=new ArrayList<Product.SubCategory.ItemList4>();
                                        if(obj4.has("subcat")) {
                                            JSONArray arraysub3 = null;
                                            arraysub3 = obj4.getJSONArray("subcat");



                                            for (int m = 0; m < arraysub3.length(); m++) {
                                                obj5 = (JSONObject) arraysub3.get(m);
                                                mThirdSubItemListArray.add(new Product.SubCategory.ItemList4(obj5.getString("category_name")));
                                            }
                                        }
                                        mSecSubItemListArray.add(new Product.SubCategory.ItemList(obj4.getString("category_name"), mThirdSubItemListArray));
                                    }
                                }
                                pSubItemArrayList.add(new Product.SubCategory(obj3.getString("category_name"), mSecSubItemListArray));

                            }

                            pProductArrayList.add(new Product(obj2.getString("category_name"), pSubItemArrayList));

                        } catch (JSONException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
                addSlideMenuView();

            }
        });
    }

}
