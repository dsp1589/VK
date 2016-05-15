package com.blogspot.hongthaiit.slidemenu;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import garmentmandi.utils.AppUrls;
import garmentmandi.web.CheckInternetConnectio;
import garmentmandi.web.IResponse;
import garmentmandi.web.Web;
import com.garmentbazaarb2b.garmentmandi.R;

/**
 * Created by Hong Thai.
 */
public class SliderMenuFragment extends Fragment implements IResponse {

    private View rootView;

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
    private 	ProgressDialog progress;
    private Handler handler;
    LayoutInflater inflater;
    String mProductNamecheck="",mProductNamechecksub="",mProductNamechecksub2="";
    int check1,check2;

    int check;
    public static Fragment newInstance() {
        SliderMenuFragment fragment = new SliderMenuFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*Initialise handler*/
        handler = new Handler();

        /**
         *Check internet connection and get data from server
         */

        pProductArrayList=new ArrayList<Product>();
          CheckInternetConnectio _checkInternetConnection = new CheckInternetConnectio(
                getActivity());

        if(_checkInternetConnection.checkInterntConnection())
        {
            GetData();
        }
        else {
            Toast.makeText(getActivity(), "Check Internet Connection",
                    Toast.LENGTH_SHORT).show();

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.slider_menu, container, false);
        mLinearListView = (LinearLayout) rootView.findViewById(R.id.linear_listview);
        this.inflater=inflater;
        return rootView;
    }


   void addSlideMenuView()
    {


        /***
         * adding item into listview
         */
        for (int i = 0; i < pProductArrayList.size(); i++) {
            inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                inflater2 = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                    inflater3 = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                        inflater3 = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        setListViewAdapter();
    }

    @Override
    public void onComplete( String result, int i) {
        jsonParser(result,i);
    }

    /*get data from server*/
    public void GetData() {
        progress = ProgressDialog.show(getActivity(), null,
                "Please Wait....", true);
        progress.setCancelable(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
//
                new Web().requestGet(AppUrls.slideMenuListUrl,
                        SliderMenuFragment.this, 100);



            }
        }).start();
    }
/*parse json result*/
 void jsonParser(final  String result,int i)
{
    progress.cancel();

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