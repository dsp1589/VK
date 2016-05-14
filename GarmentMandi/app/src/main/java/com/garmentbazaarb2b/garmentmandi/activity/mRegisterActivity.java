package com.garmentbazaarb2b.garmentmandi.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.garmentbazaarb2b.garmentmandi.R;
import com.garmentbazaarb2b.garmentmandi.Utils.Utility;
import com.garmentbazaarb2b.garmentmandi.api.CallAPI;
import com.garmentbazaarb2b.garmentmandi.common.BrandImageDetails;
import com.garmentbazaarb2b.garmentmandi.common.City;
import com.garmentbazaarb2b.garmentmandi.common.Country;
import com.garmentbazaarb2b.garmentmandi.interfaces.IcRegister;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class mRegisterActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener,  IcRegister {


    //-----Image Processing--------------
    LocationPermissions permissions;


    private String encoded_string, image_name;
    private Bitmap bitmap;

    private String filePath;
    private int PICK_IMAGE =1001;
    int iCurrentImageBeingUploaded=0;

    BrandImageDetails brandImageDetails1= new BrandImageDetails();
    BrandImageDetails brandImageDetails2= new BrandImageDetails();
    BrandImageDetails brandImageDetails3= new BrandImageDetails();
    BrandImageDetails brandImageDetails4= new BrandImageDetails();
    BrandImageDetails brandImageDetails5= new BrandImageDetails();
    BrandImageDetails brandImageDetails6= new BrandImageDetails();
    BrandImageDetails brandImageDetails7= new BrandImageDetails();
    BrandImageDetails brandImageDetails8= new BrandImageDetails();
    BrandImageDetails brandImageDetails9= new BrandImageDetails();
    BrandImageDetails brandImageDetails10= new BrandImageDetails();
    //----------------



    private ProgressDialog pDialog;

    CallAPI jsonParser = new CallAPI();
    int mSelectedCountryID=24;
    int mSelectedCityID=0;
    String mRegistrationStatus="";
    // url to create new product

    CheckBox chkConfirm;
    ScrollView MainScrollView;

    String sFirstName ,sLastName, sEmail,sAddress1,sAddress2,sPhone, sBankName,sBankAccountName,sBankAccountNumber;
    String sNEFTCode, sServiceTaxNumber, sMobileNumber,sBusinessName, sZipcode;
    String sState, sDiscountToB2B ;



    LinearLayout LLContainerForBrandNames2, LLContainerForBrandNames3,LLContainerForBrandNames4, LLContainerForBrandNames5,LLContainerForBrandNames6, LLContainerForBrandNames7,LLContainerForBrandNames8, LLContainerForBrandNames9, LLContainerForBrandNames10;
    EditText AddBrand1,AddBrand2,AddBrand3,AddBrand4,AddBrand5,AddBrand6,AddBrand7,AddBrand8,AddBrand9,AddBrand10;
    String sAddBrand1,sAddBrand2,sAddBrand3,sAddBrand4,sAddBrand5,sAddBrand6,sAddBrand7,sAddBrand8,sAddBrand9,sAddBrand10;
    Button bUploadFile1, bUploadFile2,bUploadFile3,bUploadFile4, bUploadFile5,bUploadFile6,bUploadFile7, bUploadFile8,bUploadFile9,bUploadFile10;
    Button bAddMoreBrand;


    // JSON Node names
    private static final String TAG_SUCCESS = "OK";
    String sErrorMsg="";

    RelativeLayout Tab1, Tab2, Tab3;
    Button bNext, bSaveAndContinue, bGoBack, bFinish;


    EditText FirstName,LastName,Email,Address1,Address2,Phone,BankName,BankAccountName,BankAccountNumber,NEFTCode,ServiceTaxNumber,MobileNumber,BusinessName, Zipcode;
    EditText VerificationCode;
    EditText State, DiscountToB2B;
    Spinner Country;
    List<Country> countries = new ArrayList<Country>();
    ArrayAdapter dataAdapter;
    List<String> countryNames = new ArrayList<String>();
    final List<String> mCountryNames = new ArrayList<String>();




    Spinner City;
    List<City> cities = new ArrayList<City>();
    ArrayAdapter dataAdapterCity;
    List<String> cityNames = new ArrayList<String>();
    final List<String> mCityNames= new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MainScrollView = (ScrollView) findViewById(R.id.MainScrollView);

        Tab1 = (RelativeLayout) findViewById(R.id.Tab1);
        Tab2 = (RelativeLayout) findViewById(R.id.Tab2);
        Tab3 = (RelativeLayout) findViewById(R.id.Tab3);

        bNext = (Button) findViewById(R.id.bNext);
        bSaveAndContinue = (Button) findViewById(R.id.bSaveAndContinue);
        bGoBack = (Button) findViewById(R.id.bGoBack);
        bFinish = (Button) findViewById(R.id.bFinish);

        bUploadFile1 = (Button) findViewById(R.id.bUploadFile1);
        bUploadFile2 = (Button) findViewById(R.id.bUploadFile2);
        bUploadFile3 = (Button) findViewById(R.id.bUploadFile3);
        bUploadFile4 = (Button) findViewById(R.id.bUploadFile4);
        bUploadFile5 = (Button) findViewById(R.id.bUploadFile5);
        bUploadFile6 = (Button) findViewById(R.id.bUploadFile6);
        bUploadFile7 = (Button) findViewById(R.id.bUploadFile7);
        bUploadFile8 = (Button) findViewById(R.id.bUploadFile8);
        bUploadFile9 = (Button) findViewById(R.id.bUploadFile9);
        bUploadFile10 = (Button) findViewById(R.id.bUploadFile10);


        LLContainerForBrandNames2 = (LinearLayout) findViewById(R.id.LLContainerForBrandNames2);
        LLContainerForBrandNames3 = (LinearLayout) findViewById(R.id.LLContainerForBrandNames3);
        LLContainerForBrandNames4 = (LinearLayout) findViewById(R.id.LLContainerForBrandNames4);
        LLContainerForBrandNames5 = (LinearLayout) findViewById(R.id.LLContainerForBrandNames5);
        LLContainerForBrandNames6 = (LinearLayout) findViewById(R.id.LLContainerForBrandNames6);
        LLContainerForBrandNames7 = (LinearLayout) findViewById(R.id.LLContainerForBrandNames7);
        LLContainerForBrandNames8 = (LinearLayout) findViewById(R.id.LLContainerForBrandNames8);
        LLContainerForBrandNames9 = (LinearLayout) findViewById(R.id.LLContainerForBrandNames9);
        LLContainerForBrandNames10 = (LinearLayout) findViewById(R.id.LLContainerForBrandNames10);



        bAddMoreBrand = (Button) findViewById(R.id.bAddMoreBrand);
        bAddMoreBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(LLContainerForBrandNames2.getVisibility() == View.GONE)
                {
                    LLContainerForBrandNames2.setVisibility(View.VISIBLE);
                }
                else
                {
                    if(LLContainerForBrandNames3.getVisibility() == View.GONE)
                    {
                        LLContainerForBrandNames3.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        if(LLContainerForBrandNames4.getVisibility() == View.GONE)
                        {
                            LLContainerForBrandNames4.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            if(LLContainerForBrandNames5.getVisibility() == View.GONE)
                            {
                                LLContainerForBrandNames5.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                if(LLContainerForBrandNames6.getVisibility() == View.GONE)
                                {
                                    LLContainerForBrandNames6.setVisibility(View.VISIBLE);
                                }
                                else
                                {
                                    if(LLContainerForBrandNames7.getVisibility() == View.GONE)
                                    {
                                        LLContainerForBrandNames7.setVisibility(View.VISIBLE);
                                    }
                                    else
                                    {
                                        if(LLContainerForBrandNames8.getVisibility() == View.GONE)
                                        {
                                            LLContainerForBrandNames8.setVisibility(View.VISIBLE);
                                        }
                                        else
                                        {
                                            if(LLContainerForBrandNames9.getVisibility() == View.GONE)
                                            {
                                                LLContainerForBrandNames9.setVisibility(View.VISIBLE);
                                            }
                                            else
                                            {
                                                if(LLContainerForBrandNames10.getVisibility() == View.GONE)
                                                {
                                                    LLContainerForBrandNames10.setVisibility(View.VISIBLE);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });



        chkConfirm = (CheckBox) findViewById(R.id.chkConfirm);

        FirstName = (EditText) findViewById(R.id.FirstName);
        LastName = (EditText) findViewById(R.id.LastName);
        Email = (EditText) findViewById(R.id.Email);

        Address1 = (EditText) findViewById(R.id.Address1);
        Address2 = (EditText) findViewById(R.id.Address2);
        Phone = (EditText) findViewById(R.id.Phone);
        BankName = (EditText) findViewById(R.id.BankName);
        BankAccountName = (EditText) findViewById(R.id.BankAccountName);
        BankAccountNumber = (EditText) findViewById(R.id.BankAccountNumber);
        NEFTCode = (EditText) findViewById(R.id.NEFTCode);
        ServiceTaxNumber = (EditText) findViewById(R.id.ServiceTaxNumber);
        MobileNumber = (EditText) findViewById(R.id.MobileNumber);
        BusinessName = (EditText) findViewById(R.id.BusinessName);
        Zipcode = (EditText) findViewById(R.id.Zipcode);

        State = (EditText) findViewById(R.id.State);
        DiscountToB2B = (EditText) findViewById(R.id.DiscountToB2B);
        AddBrand1 = (EditText) findViewById(R.id.AddBrand1);
        AddBrand2 = (EditText) findViewById(R.id.AddBrand2);
        AddBrand3 = (EditText) findViewById(R.id.AddBrand3);
        AddBrand4 = (EditText) findViewById(R.id.AddBrand4);
        AddBrand5 = (EditText) findViewById(R.id.AddBrand5);
        AddBrand6 = (EditText) findViewById(R.id.AddBrand6);
        AddBrand7 = (EditText) findViewById(R.id.AddBrand7);
        AddBrand8 = (EditText) findViewById(R.id.AddBrand8);
        AddBrand9 = (EditText) findViewById(R.id.AddBrand9);
        AddBrand10 = (EditText) findViewById(R.id.AddBrand10);

        Country = (Spinner) findViewById(R.id.Country);
        Country.setOnItemSelectedListener(this);

        City = (Spinner) findViewById(R.id.City);
        City.setOnItemSelectedListener(this);

        VerificationCode = (EditText) findViewById(R.id.VerificationCode);

        bNext.setOnClickListener(this);
        bSaveAndContinue.setOnClickListener(this);
        bGoBack.setOnClickListener(this);
        bFinish.setOnClickListener(this);

        bUploadFile1.setOnClickListener(this);
        bUploadFile2.setOnClickListener(this);
        bUploadFile3.setOnClickListener(this);
        bUploadFile4.setOnClickListener(this);
        bUploadFile5.setOnClickListener(this);
        bUploadFile6.setOnClickListener(this);
        bUploadFile7.setOnClickListener(this);
        bUploadFile8.setOnClickListener(this);
        bUploadFile9.setOnClickListener(this);
        bUploadFile10.setOnClickListener(this);


        ArrayList<String> passing = new ArrayList<String>();
        passing.add("1111");

        IcRegister icRegister= new mRegisterActivity();
        GetCountry asyncTask =new GetCountry(this, this, icRegister);
        asyncTask.execute(passing);


        /*
        GetCountry asyncTask =new GetCountry(this, new IcRegister() {
            @Override
            public void CountryFetched(List<Country> countries)
            {

            }
        });
        */

    }


    private boolean validateInput()
    {
        boolean result=true;

         sFirstName =  FirstName.getText().toString();
              sLastName= LastName.getText().toString();
         sEmail= Email.getText().toString();

         sAddress1= Address1.getText().toString();
          sAddress2= Address2.getText().toString();
         sPhone= Phone.getText().toString();
          sBankName= BankName.getText().toString();
         sBankAccountName= BankAccountName.getText().toString();
          sBankAccountNumber= BankAccountNumber.getText().toString();
         sNEFTCode= NEFTCode.getText().toString();
          sServiceTaxNumber= ServiceTaxNumber.getText().toString();
         sMobileNumber= MobileNumber.getText().toString();
          sBusinessName= BusinessName.getText().toString();

         sZipcode= Zipcode.getText().toString();

        sState= State.getText().toString();
        sDiscountToB2B= DiscountToB2B.getText().toString();
        sAddBrand1= AddBrand1.getText().toString();
        sAddBrand2= AddBrand2.getText().toString();
        sAddBrand3= AddBrand3.getText().toString();
        sAddBrand4= AddBrand4.getText().toString();
        sAddBrand5= AddBrand5.getText().toString();
        sAddBrand6= AddBrand6.getText().toString();
        sAddBrand7= AddBrand7.getText().toString();
        sAddBrand8= AddBrand8.getText().toString();
        sAddBrand9= AddBrand9.getText().toString();
        sAddBrand10= AddBrand10.getText().toString();


        if (Utility.isNotNull(sEmail) )
        {
            if(Utility.validate(sEmail))
            {
                Email.setError(null);
            }
            else {
                Email.setError("Enter Valid Email");
                Email.requestFocus();
                result=false;
            }
        }
        else {
            Email.setError("Enter Email");
            Email.requestFocus();
            result=false;
        }

        if (Utility.isNotNull(sFirstName) )
        {
            FirstName.setError(null);
        }
        else {
            FirstName.setError("Enter First Name");
            FirstName.requestFocus();
            result=false;
        }


        if (Utility.isNotNull(sLastName) )
        {
            LastName.setError(null);
        }
        else {
            LastName.setError("Enter Last Name");
            LastName.requestFocus();
            result=false;
        }


        if (Utility.isNotNull(sMobileNumber) )
        {
            if(sMobileNumber.length() < 10)
            {
                MobileNumber.setError("Invalid Mobile Number");
                MobileNumber.requestFocus();
                result=false;
            }
            else
            {
                MobileNumber.setError(null);
            }

        }
        else {
            MobileNumber.setError("Enter Mobile Number");
            MobileNumber.requestFocus();
            result=false;
        }


        if (Utility.isNotNull(sAddress1) )
        {
            Address1.setError(null);
        }
        else {
            Address1.setError("Enter Address1");
            Address1.requestFocus();
            result=false;
        }


        if (Utility.isNotNull(sAddress2) )
        {
            Address2.setError(null);
        }
        else {
            Address2.setError("Enter Address2");
            Address2.requestFocus();
            result=false;
        }



        if (Utility.isNotNull(sBusinessName) )
        {
            BusinessName.setError(null);
        }
        else {
            BusinessName.setError("Enter Business Name");
            BusinessName.requestFocus();
            result=false;
        }


        if (Utility.isNotNull(sState) )
        {
            State.setError(null);
        }
        else {
            State.setError("Enter State");
            State.requestFocus();
            result=false;
        }


        if (Utility.isNotNull(sAddBrand1) )
        {
            AddBrand1.setError(null);
        }
        else {
            AddBrand1.setError("Enter Brand Name");
            AddBrand1.requestFocus();
            result=false;
        }

        if (Utility.isNotNull(sBankName) )
        {
            BankName.setError(null);
        }
        else {
            BankName.setError("Enter Bank Name");
            BankName.requestFocus();
            result=false;
        }



        if (Utility.isNotNull(sBankAccountName) )
        {
            BankAccountName.setError(null);
        }
        else {
            BankAccountName.setError("Enter Bank Account Name");
            BankAccountName.requestFocus();
            result=false;
        }


        if (Utility.isNotNull(sBankAccountNumber) )
        {
            BankAccountNumber.setError(null);
        }
        else {
            BankAccountNumber.setError("Enter Bank Account Number");
            BankAccountNumber.requestFocus();
            result=false;
        }


        if (Utility.isNotNull(sNEFTCode) )
        {
            NEFTCode.setError(null);
        }
        else {
            NEFTCode.setError("Enter NEFT Code");
            NEFTCode.requestFocus();
            result=false;
        }



        if (Utility.isNotNull(sDiscountToB2B) )
        {
            DiscountToB2B.setError(null);
        }
        else {
            DiscountToB2B.setError("Enter Discount in Percent");
            DiscountToB2B.requestFocus();
            result=false;
        }



        if (Utility.isNotNull(sServiceTaxNumber) )
        {
            ServiceTaxNumber.setError(null);
        }
        else {
            ServiceTaxNumber.setError("Enter Service Tax Number");
            ServiceTaxNumber.requestFocus();
            result=false;
        }


        return  result;

    }


    @Override
    public void onClick(View v)
    {

        switch (v.getId()) {
            case R.id.bNext:
                    Tab1.setVisibility(View.GONE);
                    Tab2.setVisibility(View.VISIBLE);
                    Tab3.setVisibility(View.GONE);
                     MainScrollView.fullScroll(ScrollView.FOCUS_UP);
                break;
            case R.id.bSaveAndContinue:

                    if(validateInput())
                    {
                        Tab1.setVisibility(View.GONE);
                        Tab2.setVisibility(View.GONE);
                        Tab3.setVisibility(View.VISIBLE);
                        MainScrollView.fullScroll(ScrollView.FOCUS_UP);

                    }


                break;
            case R.id.bGoBack:
                Tab1.setVisibility(View.GONE);
                Tab2.setVisibility(View.VISIBLE);
                Tab3.setVisibility(View.GONE);
                MainScrollView.fullScroll(ScrollView.FOCUS_UP);
                break;

            case R.id.bFinish:

                if (chkConfirm.isChecked() == false)
                {
                    Toast.makeText(mRegisterActivity.this, "Please Select Checkbox" , Toast.LENGTH_LONG).show();
                }
                else
                {

                    //--------------------------------------------------
                    ArrayList<String> passing = new ArrayList<String>();
                    passing.add("1");

                    SaveCustRegistration asyncTask =new SaveCustRegistration(this, this, new IcRegister() {
                        @Override
                        public void OnVerificationCodeFetched() {
                            //Toast.makeText(this, "Error Occurred", Toast.LENGTH_LONG).show();


                        }


                        public  void CountryFetched(List<Country> countries)
                        {}


                        public void OnCityFetched(List<City> cities)
                        {}

                        public void OnDataSaved(String status)
                        {

                            if (status.length() > 0)
                            {
                                // errorMsg.setText(sErrorMsg);
                                Toast.makeText(mRegisterActivity.this, "Registration Successful", Toast.LENGTH_LONG).show();
                            }
                            else
                            {

                            }
                        }

                    });
                    asyncTask.execute(passing);

                    //----------------------------------------------------

                }



                break;

            case R.id.bUploadFile1:
                if(AddBrand1.getText().toString().length() ==0)
                {
                    Toast.makeText(mRegisterActivity.this, "Enter Brand Name", Toast.LENGTH_LONG).show();
                    AddBrand1.requestFocus();
                }
                else
                {
                    iCurrentImageBeingUploaded =R.id.bUploadFile1;
                    openFileWrapper();
                }

                break;
            case R.id.bUploadFile2:

                if(AddBrand2.getText().toString().length() ==0)
                {
                    Toast.makeText(mRegisterActivity.this, "Enter Brand Name", Toast.LENGTH_LONG).show();
                    AddBrand2.requestFocus();
                }
                else
                {
                    iCurrentImageBeingUploaded =R.id.bUploadFile2;
                    openFileWrapper();
                }

                break;
            case R.id.bUploadFile3:

                if(AddBrand3.getText().toString().length() ==0)
                {
                    Toast.makeText(mRegisterActivity.this, "Enter Brand Name", Toast.LENGTH_LONG).show();
                    AddBrand3.requestFocus();
                }
                else
                {
                    iCurrentImageBeingUploaded =R.id.bUploadFile3;
                    openFileWrapper();
                }

                break;
            case R.id.bUploadFile4:
                if(AddBrand4.getText().toString().length() ==0)
                {
                    Toast.makeText(mRegisterActivity.this, "Enter Brand Name", Toast.LENGTH_LONG).show();
                    AddBrand4.requestFocus();
                }
                else
                {
                    iCurrentImageBeingUploaded =R.id.bUploadFile4;
                    openFileWrapper();
                }

                break;
            case R.id.bUploadFile5:

                if(AddBrand5.getText().toString().length() ==0)
                {
                    Toast.makeText(mRegisterActivity.this, "Enter Brand Name", Toast.LENGTH_LONG).show();
                    AddBrand5.requestFocus();
                }
                else
                {
                    iCurrentImageBeingUploaded =R.id.bUploadFile5;
                    openFileWrapper();
                }

                break;
            case R.id.bUploadFile6:

                if(AddBrand6.getText().toString().length() ==0)
                {
                    Toast.makeText(mRegisterActivity.this, "Enter Brand Name", Toast.LENGTH_LONG).show();
                    AddBrand6.requestFocus();
                }
                else
                {
                    iCurrentImageBeingUploaded =R.id.bUploadFile6;
                    openFileWrapper();
                }

                break;

            case R.id.bUploadFile7:

                if(AddBrand7.getText().toString().length() ==0)
                {
                    Toast.makeText(mRegisterActivity.this, "Enter Brand Name", Toast.LENGTH_LONG).show();
                    AddBrand7.requestFocus();
                }
                else
                {
                    iCurrentImageBeingUploaded =R.id.bUploadFile7;
                    openFileWrapper();
                }

                break;
            case R.id.bUploadFile8:
                if(AddBrand8.getText().toString().length() ==0)
                {
                    Toast.makeText(mRegisterActivity.this, "Enter Brand Name", Toast.LENGTH_LONG).show();
                    AddBrand8.requestFocus();
                }
                else
                {
                    iCurrentImageBeingUploaded =R.id.bUploadFile8;
                    openFileWrapper();
                }

                break;
            case R.id.bUploadFile9:

                if(AddBrand9.getText().toString().length() ==0)
                {
                    Toast.makeText(mRegisterActivity.this, "Enter Brand Name", Toast.LENGTH_LONG).show();
                    AddBrand9.requestFocus();
                }
                else
                {
                    iCurrentImageBeingUploaded =R.id.bUploadFile9;
                    openFileWrapper();
                }

                break;
            case R.id.bUploadFile10:

                if(AddBrand10.getText().toString().length() ==0)
                {
                    Toast.makeText(mRegisterActivity.this, "Enter Brand Name", Toast.LENGTH_LONG).show();
                    AddBrand10.requestFocus();
                }
                else
                {
                    iCurrentImageBeingUploaded =R.id.bUploadFile10;
                    openFileWrapper();
                }

                break;

            default:

                break;
        }

    }



    private void openFileWrapper() {

        permissions = new LocationPermissions(this);
        permissions.checkAndRequestPermission();

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        this.permissions.onRequestPermissionResult(requestCode, permissions, grantResults);

    }




    @Override
    public void CountryFetched(List<Country> countries) {
       // Toast.makeText(cRegisterActivity.this, countries.size(), Toast.LENGTH_LONG).show();
        this.countries = countries;
        Country country;
        for(int i=0; i<countries.size() ; i++)
        {
            country = countries.get(i);
            this.mCountryNames.add(country.getCountry_name().toString());
        }


    }


    @Override
    public void OnCityFetched(List<City> cities) {
        // Toast.makeText(cRegisterActivity.this, countries.size(), Toast.LENGTH_LONG).show();
        this.cities = cities;




    }

    @Override
    public void OnVerificationCodeFetched() {

    }

    @Override
    public void OnDataSaved(String status) {


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId())
        {
            case R.id.Country:
                // On selecting a spinner item
                String item = parent.getItemAtPosition(position).toString();

                Country country;
                for(int i=0; i<countries.size(); i++)
                {
                    country = countries.get(i);
                    if(country.getCountry_name().equals(item))
                    {
                        mSelectedCountryID = country.getCountry_id();
                        break;
                    }
                }

                String str1 = String.valueOf(mCountryNames.size());
                str1 += String.valueOf(countries.size());
                str1 += item;
                // Showing selected spinner item
              //  Toast.makeText(parent.getContext(), "Selected: " + str1, Toast.LENGTH_LONG).show();

                ArrayList<String> passing = new ArrayList<String>();
                passing.add("1");

                IcRegister icRegister= new mRegisterActivity();
                GetCity asyncTask1 =new GetCity(this, this, icRegister);
                asyncTask1.execute(passing);

                break;

            case R.id.City:
                String selectedCity = parent.getItemAtPosition(position).toString();

                City city;
                for(int i=0; i<cities.size(); i++)
                {
                    city = cities.get(i);
                    if(city.getCity_name().equals(selectedCity))
                    {
                        mSelectedCityID = city.getCity_id();
                        break;
                    }
                }

              //  Toast.makeText(parent.getContext(), "Selected: " + selectedCity, Toast.LENGTH_LONG).show();

                break;

            default:
                break;

        }




    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }




    public static boolean isMediaDocument(Uri uri)
    {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs)
    {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst())
            {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * Retrives the result returned from selecting image, by invoking the method
     * <code>selectImageFromGallery()</code>
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


     //   private String encoded_string, image_name;
     //   private Bitmap bitmap;
     //   private File file;
      //  private Uri file_uri;

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && null != data)
        {
            Uri selectedImage = data.getData();

//----------------------------------------------------

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && isMediaDocument(selectedImage))
            {
                String wholeID = DocumentsContract.getDocumentId(selectedImage);

                // Split at colon, use second item in the array
                String id = wholeID.split(":")[1];

                String[] column = { MediaStore.Images.Media.DATA };

                // where id is equal to
                String sel = MediaStore.Images.Media._ID + "=?";

                Cursor cursor = getContentResolver().
                        query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                column, sel, new String[]{ id }, null);

                filePath = "";

                int columnIndex = cursor.getColumnIndex(column[0]);

                if (cursor.moveToFirst()) {
                    filePath = cursor.getString(columnIndex);
                }
                cursor.close();
            }
            else
            {
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                filePath = picturePath;
                cursor.close();
            }




            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && isMediaDocument(selectedImage))
            {
                String wholeID = DocumentsContract.getDocumentId(selectedImage);

                // Split at colon, use second item in the array
                String id = wholeID.split(":")[1];

                String[] column = { MediaStore.Images.Media.TITLE };

                // where id is equal to
                String sel = MediaStore.Images.Media._ID + "=?";

                Cursor cursor = getContentResolver().
                        query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                column, sel, new String[]{ id }, null);

                image_name = "";

                int columnIndex = cursor.getColumnIndex(column[0]);

                if (cursor.moveToFirst()) {
                    image_name = cursor.getString(columnIndex);
                }
                cursor.close();
            }
            else
            {
                String[] fileNameColumn = { MediaStore.Images.Media.TITLE };
                Cursor cursorFile = getContentResolver().query(selectedImage,
                        fileNameColumn, null, null, null);
                cursorFile.moveToFirst();

                int columnIndex1 = cursorFile.getColumnIndex(fileNameColumn[0]);
                image_name = cursorFile.getString(columnIndex1);

                cursorFile.close();
            }







           //new Encode_image.execute();
            Encode_image encode_image = new Encode_image();
            encode_image.execute();

        }
    }


    private class Encode_image extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected Void doInBackground(Void... params) {
/*
           // bitmap = BitmapFactory.decodeFile(file_uri.getPath()); //Put file path..filePath
           // Bitmap bitmap;

            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;

           // Bitmap bm = BitmapFactory.decodeFile(strPath,options);

            bitmap = BitmapFactory.decodeFile(filePath, options);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] array = stream.toByteArray();
            */
//--------------------------
            FileInputStream fin = null;
            try{
                fin = new FileInputStream(filePath);
                // Max size of buffer
                int bSize = 20 * 512;
                // Buffer
                byte[] buf = new byte[bSize];
                // Actual size of buffer
                int len = 0;
                encoded_string="";
                while((len = fin.read(buf)) != -1)
                {
                    byte[] encoded = Base64.encode(buf, 0);
                    encoded_string += Base64.encodeToString(buf,0);
                }

            }
            catch (IOException e)
            {
                if(null != fin) {
                    try{
                        fin.close();
                    }
                    catch (java.io.IOException ex1)
                    {}

                }
            }

            //-----------------------

/*
            try{
                System.gc();
                encoded_string=Base64.encodeToString(array, Base64.DEFAULT);
                if(bitmap!=null)
                {
                    bitmap.recycle();
                    bitmap=null;
                }
            }catch(Exception e){
                e.printStackTrace();
            }catch(OutOfMemoryError e){
                stream=new  ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,30, stream);
                array=stream.toByteArray();
                encoded_string=Base64.encodeToString(array, Base64.DEFAULT);
                if(bitmap!=null)
                {
                    bitmap.recycle();
                    bitmap=null;
                }
                Log.e("EWN", "Out of memory error catched");
            }

            */
//-----------------------------------------------------------------


            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(mRegisterActivity.this);
            pDialog.setMessage("Processing..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
           // makeRequest();
            pDialog.dismiss();
            switch (iCurrentImageBeingUploaded) {
                case R.id.bUploadFile1:
                    brandImageDetails1.setBrandName(AddBrand1.getText().toString());
                    brandImageDetails1.setImageTitle(image_name);
                    brandImageDetails1.setImage_encoded_string(encoded_string);
                    break;
                case R.id.bUploadFile2:
                    brandImageDetails2.setBrandName(AddBrand2.getText().toString());
                    brandImageDetails2.setImageTitle(image_name);
                    brandImageDetails2.setImage_encoded_string(encoded_string);

                    break;
                case R.id.bUploadFile3:
                    brandImageDetails3.setBrandName(AddBrand3.getText().toString());
                    brandImageDetails3.setImageTitle(image_name);
                    brandImageDetails3.setImage_encoded_string(encoded_string);

                    break;
                case R.id.bUploadFile4:
                    brandImageDetails4.setBrandName(AddBrand4.getText().toString());
                    brandImageDetails4.setImageTitle(image_name);
                    brandImageDetails4.setImage_encoded_string(encoded_string);
                    break;
                case R.id.bUploadFile5:
                    brandImageDetails5.setBrandName(AddBrand5.getText().toString());
                    brandImageDetails5.setImageTitle(image_name);
                    brandImageDetails5.setImage_encoded_string(encoded_string);

                    break;
                case R.id.bUploadFile6:
                    brandImageDetails6.setBrandName(AddBrand6.getText().toString());
                    brandImageDetails6.setImageTitle(image_name);
                    brandImageDetails6.setImage_encoded_string(encoded_string);

                    break;
                case R.id.bUploadFile7:
                    brandImageDetails7.setBrandName(AddBrand7.getText().toString());
                    brandImageDetails7.setImageTitle(image_name);
                    brandImageDetails7.setImage_encoded_string(encoded_string);
                    break;
                case R.id.bUploadFile8:
                    brandImageDetails8.setBrandName(AddBrand8.getText().toString());
                    brandImageDetails8.setImageTitle(image_name);
                    brandImageDetails8.setImage_encoded_string(encoded_string);

                    break;
                case R.id.bUploadFile9:
                    brandImageDetails9.setBrandName(AddBrand9.getText().toString());
                    brandImageDetails9.setImageTitle(image_name);
                    brandImageDetails9.setImage_encoded_string(encoded_string);

                    break;
                case R.id.bUploadFile10:
                    brandImageDetails10.setBrandName(AddBrand10.getText().toString());
                    brandImageDetails10.setImageTitle(image_name);
                    brandImageDetails10.setImage_encoded_string(encoded_string);

                    break;
                default:
                    break;
            }

        }
    }


    //makeRequest class is used to make API call so that Encoded String (Image) and Image Name can be saved on server..
private void makeRequest()
{
    com.android.volley.RequestQueue requestQueue = Volley.newRequestQueue(this);
    StringRequest request = new StringRequest(Request.Method.POST, "http://garmentbazaarb2b.com/api/v1.0/merchant/signup.json",
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    }
    ) {

        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            //return super.getParams();
            HashMap<String,String> map = new HashMap<>();
            map.put("encoded_string" , encoded_string);
            map.put("image_name", image_name);
            return map;
        }
    };
    requestQueue.add(request);
}

    public class GetCountry extends AsyncTask<ArrayList<String>, String, List<Country>> {
        private IcRegister listener;

        private Context mContext;
        private Activity mActivity;
        // Progress Dialog
        private ProgressDialog pDialog;

        CallAPI jsonParser = new CallAPI();


        // url to create new product

        String url_create_product = "http://garmentbazaarb2b.com/api/v1.0/utility/getcountries.json";

        // JSON Node names
        private static final String TAG_SUCCESS = "OK";

        @Override
        protected List<Country> doInBackground(ArrayList<String>... passing) {

          //  List<Country> countries = new ArrayList<Country>();
            ArrayList<String> passed = passing[0];
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();


            //  params.add(new BasicNameValuePair("email", passed.get(0)));
            //  params.add(new BasicNameValuePair("password", passed.get(1)));
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_product,
                    "POST", params);

            // check log cat fro response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                // int success = json.getInt(TAG_SUCCESS);

                if (json.get("status").equals(TAG_SUCCESS)) {
                    //errorMsg.setVisibility(View.INVISIBLE);
                    // successfully created product
                    //  sErrorMsg="";
                    countries= parseResponseCountries(json);

                } else {

                    //Toast.makeText(getApplicationContext(), "Error Occurred",Toast.LENGTH_LONG).show();
                    //   sErrorMsg=json.get("info").toString();
                    //  errorMsg.setVisibility(View.VISIBLE);
                    // failed to create product
                    //  errorMsg.setText("Error Occured");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return countries;
        }

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(mContext);
            pDialog.setMessage("Processing..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }


        public GetCountry(Activity activity, Context context, IcRegister listener){
            this.listener=listener;
            this.mContext = context;
            this.mActivity=activity;
        }



        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(List<Country> countries1) {
            countries =countries1;
            // dismiss the dialog once done
            pDialog.dismiss();
           // List<String> countryNames = new ArrayList<String>();
            ArrayAdapter dataAdapter;
           // Spinner Country = (Spinner)mActivity.findViewById(R.id.Country);
            Country country;
            countryNames.add("Select Country");
            for(int i=0; i<countries.size() ; i++)
            {
                country = countries.get(i);
                countryNames.add(country.getCountry_name().toString());
            }


            dataAdapter = new ArrayAdapter<String>(mContext,
                    android.R.layout.simple_spinner_item, countryNames);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Country.setAdapter(dataAdapter);

            listener.CountryFetched(countries);
        }


        private List<Country> parseResponseCountries(JSONObject response) {

            List<Country> result = new ArrayList<Country>();

            try {
                JSONArray countries = new JSONArray(response.get("result").toString());


                for (int i = 0; i < countries.length(); i++) {

                    JSONObject jsonObjCountry = countries.getJSONObject(i);
                    Country country=new Country();

                    country.setCountry_id(jsonObjCountry.getInt("country_id"));
                    country.setCountry_name(jsonObjCountry.getString("country_name"));
                    country.setCountry_code(jsonObjCountry.getString("country_code"));
                    country.setCurrency_symbol(jsonObjCountry.getString("currency_symbol"));
                    country.setCurrency_code(jsonObjCountry.getString("currency_code"));

                    result.add(country);
                }

            }
            catch (JSONException e) {
                e.printStackTrace();
            }

            return result;
        }

    }


public class GetCity extends AsyncTask<ArrayList<String>, String, List<City>> {
    private IcRegister listener;

    private Context mContext;
    private  Activity mActivity;
    // Progress Dialog
    private ProgressDialog pDialog;

    CallAPI jsonParser = new CallAPI();


    // url to create new product

    String url_create_product = "http://garmentbazaarb2b.com/api/v1.0/utility/getcitybycountry/" + String.valueOf(mSelectedCountryID) + ".json";

    // JSON Node names
    private static final String TAG_SUCCESS = "OK";

    @Override
    protected List<City> doInBackground(ArrayList<String>... passing) {

       // List<City> cities = new ArrayList<City>();
        ArrayList<String> passed = passing[0];
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();


        //  params.add(new BasicNameValuePair("email", passed.get(0)));
        //  params.add(new BasicNameValuePair("password", passed.get(1)));
        // getting JSON Object
        // Note that create product url accepts POST method
        JSONObject json = jsonParser.makeHttpRequest(url_create_product,
                "POST", params);

        // check log cat fro response
        Log.d("Create Response", json.toString());

        // check for success tag
        try {
            // int success = json.getInt(TAG_SUCCESS);

            if (json.get("status").equals(TAG_SUCCESS)) {
                //errorMsg.setVisibility(View.INVISIBLE);
                // successfully created product
                //  sErrorMsg="";
                cities= parseResponseCountries(json);

            } else {

                //Toast.makeText(getApplicationContext(), "Error Occurred",Toast.LENGTH_LONG).show();
                //   sErrorMsg=json.get("info").toString();
                //  errorMsg.setVisibility(View.VISIBLE);
                // failed to create product
                //  errorMsg.setText("Error Occured");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return cities;
    }

    /**
     * Before starting background thread Show Progress Dialog
     * */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Processing..");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }


    public GetCity(Activity activity, Context context, IcRegister listener){
        this.listener=listener;
        this.mContext = context;
        this.mActivity=activity;
    }



    /**
     * After completing background task Dismiss the progress dialog
     * **/
    protected void onPostExecute(List<City> cities1) {
        // dismiss the dialog once done
        cities = cities1;
        pDialog.dismiss();
        ArrayAdapter dataAdapterCity;
        City city;

        int ctr  = cityNames.size();
        for(int i=0; i<ctr ; i++)
        {
            cityNames.remove(0);
        }

        cityNames.add("Select City");



        for(int i=0; i<cities.size() ; i++)
        {
            city = cities.get(i);
            cityNames.add(city.getCity_name().toString());
        }


        dataAdapterCity = new ArrayAdapter<String>(mContext,
                android.R.layout.simple_spinner_item, cityNames);
        dataAdapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        City.setAdapter(dataAdapterCity);

        listener.OnCityFetched(cities);
    }


    private List<City> parseResponseCountries(JSONObject response) {

        List<City> result = new ArrayList<City>();

        try {
            JSONArray cities = new JSONArray(response.get("result").toString());

            City city;
            for (int i = 0; i < cities.length(); i++) {

                JSONObject jsonObjCity = cities.getJSONObject(i);
                city=new City();

                city.setCity_id(jsonObjCity.getInt("city_id"));
                city.setCity_name(jsonObjCity.getString("city_name"));

                result.add(city);
            }

        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

}


    public class SaveCustRegistration extends AsyncTask<ArrayList<String>, String, String> {
        private IcRegister listener;

        private Context mContext;
        private  Activity mActivity;
        // Progress Dialog
        private ProgressDialog pDialog;

        CallAPI jsonParser = new CallAPI();


        // url to create new product

        String url_create_product = "http://garmentbazaarb2b.com/api/v1.0/merchant/signup.json";

        // JSON Node names
        private static final String TAG_SUCCESS = "OK";

        @Override
        protected String doInBackground(ArrayList<String>... passing) {


            ArrayList<String> passed = passing[0];
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();


            params.add(new BasicNameValuePair("firstname", sFirstName));
            params.add(new BasicNameValuePair("lastname",sLastName ));
            params.add(new BasicNameValuePair("email", sEmail));

            params.add(new BasicNameValuePair("mraddress1",sAddress1 ));
            params.add(new BasicNameValuePair("mraddress2", sAddress2));

            //TODO: NOte: Loki: On screen field name is phone and Mobile and in API there are two fields mobile_number and mr_mobile
            params.add(new BasicNameValuePair("mr_mobile", sPhone));

            params.add(new BasicNameValuePair("bank_name", sBankName));
            params.add(new BasicNameValuePair("account_name", sBankAccountName));
            params.add(new BasicNameValuePair("account_number", sBankAccountNumber));

            params.add(new BasicNameValuePair("discount_in_percent", sDiscountToB2B));
            params.add(new BasicNameValuePair("ship_state", sState));


            params.add(new BasicNameValuePair("neft_code", sNEFTCode));
            params.add(new BasicNameValuePair("service_tax_number", sServiceTaxNumber));
            params.add(new BasicNameValuePair("mobile_number", sMobileNumber));
            params.add(new BasicNameValuePair("businessname", sBusinessName));  //business_name in user registration.
            params.add(new BasicNameValuePair("country", String.valueOf(mSelectedCountryID)));
            params.add(new BasicNameValuePair("city", String.valueOf(mSelectedCityID)));
            params.add(new BasicNameValuePair("zipcode", sZipcode));


            String brand_name="", image_name="", image_encoded="";
            if(sAddBrand1.length() > 0)
            {
                brand_name += sAddBrand1;
                image_name += brandImageDetails1.getImageTitle().toString();
                image_encoded += brandImageDetails1.getImage_encoded_string().toString();
            }

            if(sAddBrand2.length() > 0)
            {
                brand_name += "#" + sAddBrand2;
                image_name += "#" + brandImageDetails2.getImageTitle().toString();
                image_encoded += "#" + brandImageDetails2.getImage_encoded_string().toString();
            }

            if(sAddBrand3.length() > 0)
            {
                brand_name += "#" + sAddBrand3;
                image_name += "#" + brandImageDetails3.getImageTitle().toString();
                image_encoded += "#" + brandImageDetails3.getImage_encoded_string().toString();
            }


            if(sAddBrand4.length() > 0)
            {
                brand_name += "#" + sAddBrand4;
                image_name += "#" + brandImageDetails4.getImageTitle().toString();
                image_encoded += "#" + brandImageDetails4.getImage_encoded_string().toString();
            }

            if(sAddBrand5.length() > 0)
            {
                brand_name += "#" + sAddBrand5;
                image_name += "#" + brandImageDetails5.getImageTitle().toString();
                image_encoded += "#" + brandImageDetails5.getImage_encoded_string().toString();
            }

            if(sAddBrand6.length() > 0)
            {
                brand_name += "#" + sAddBrand6;
                image_name += "#" + brandImageDetails6.getImageTitle().toString();
                image_encoded += "#" + brandImageDetails6.getImage_encoded_string().toString();
            }


            if(sAddBrand7.length() > 0)
            {
                brand_name += "#" + sAddBrand7;
                image_name += "#" + brandImageDetails7.getImageTitle().toString();
                image_encoded += "#" + brandImageDetails7.getImage_encoded_string().toString();
            }

            if(sAddBrand8.length() > 0)
            {
                brand_name += "#" + sAddBrand8;
                image_name += "#" + brandImageDetails8.getImageTitle().toString();
                image_encoded += "#" + brandImageDetails8.getImage_encoded_string().toString();
            }

            if(sAddBrand9.length() > 0)
            {
                brand_name += "#" + sAddBrand9;
                image_name += "#" + brandImageDetails9.getImageTitle().toString();
                image_encoded += "#" + brandImageDetails9.getImage_encoded_string().toString();
            }


            if(sAddBrand10.length() > 0)
            {
                brand_name += "#" + sAddBrand10;
                image_name += "#" + brandImageDetails10.getImageTitle().toString();
                image_encoded += "#" + brandImageDetails10.getImage_encoded_string().toString();
            }



            params.add(new BasicNameValuePair("brand_name", brand_name));
            params.add(new BasicNameValuePair("image_name", image_name));
            params.add(new BasicNameValuePair("image_encoded", image_encoded));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_product,
                    "POST", params);

            // check log cat fro response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                // int success = json.getInt(TAG_SUCCESS);

                if (json.get("status").equals(TAG_SUCCESS)) {
                    //errorMsg.setVisibility(View.INVISIBLE);
                    // successfully created product
                    //  sErrorMsg="";
                    mRegistrationStatus ="Success";


                } else {
                    mRegistrationStatus="";
                    //Toast.makeText(getApplicationContext(), "Error Occurred",Toast.LENGTH_LONG).show();
                    //   sErrorMsg=json.get("info").toString();
                    //  errorMsg.setVisibility(View.VISIBLE);
                    // failed to create product
                    //  errorMsg.setText("Error Occured");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return mRegistrationStatus;
        }

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(mContext);
            pDialog.setMessage("Processing..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }


        public SaveCustRegistration(Activity activity, Context context, IcRegister listener){
            this.listener=listener;
            this.mContext = context;
            this.mActivity=activity;
        }



        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String status) {
            // dismiss the dialog once done

            pDialog.dismiss();



            listener.OnDataSaved(status);
        }




    }



    public class LocationPermissions implements DialogInterface.OnClickListener {
        private static final String TAG = "LocationPermissions";
        public static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
        private Activity activity;
        public LocationPermissions(Activity activity) {
            this.activity = activity;
        }



        public void checkAndRequestPermission() {

            if (isLocationPermissionEnabled()) {
                openFile();
            }
            else {
                requestLocationPermission();
            }
        }

        public boolean isLocationPermissionEnabled() {
            return ContextCompat.checkSelfPermission(this.activity, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }

        public void requestLocationPermission() {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this.activity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                showMessageOKCancel("Garment Mandi requires storage read permission. Please grant.", this.activity, this, this);
                return;
            }
            activityRequestPermission();
        }

        private  void showMessageOKCancel(String message, Activity activity, DialogInterface.OnClickListener okListener,
                                                DialogInterface.OnClickListener cancelListener) {
            new AlertDialog.Builder(activity).setMessage(message).setPositiveButton("OK", okListener).create().show();
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (which == DialogInterface.BUTTON_POSITIVE) {
                ActivityCompat.requestPermissions(this.activity, new String[] { Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE }, LOCATION_PERMISSION_REQUEST_CODE);
            }
            else if (which == DialogInterface.BUTTON_NEGATIVE) {
                Log.e(TAG, "Application was denied permission!");

            }

        }

        private void activityRequestPermission() {
            ActivityCompat.requestPermissions(this.activity, new String[] { Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE },
                    LOCATION_PERMISSION_REQUEST_CODE);
        }

        public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) {
            if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openFile();
                }else{
                    Log.e(TAG, "Application was denied permission!");
                }

            }
        }



        public void openFile() {

            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");  //image/*    file/*
            intent.addCategory(Intent.CATEGORY_OPENABLE);

            // special intent for Samsung file manager
            Intent sIntent = new Intent("com.sec.android.app.myfiles.PICK_DATA");
            // if you want any file type, you can skip next line
            sIntent.putExtra("CONTENT_TYPE", "*/*");  //image/*
            sIntent.addCategory(Intent.CATEGORY_DEFAULT);

            Intent chooserIntent;
            if (getPackageManager().resolveActivity(sIntent, 0) != null){
                // it is device with samsung file manager
                chooserIntent = Intent.createChooser(sIntent, "Open file1");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { intent});
            }
            else {
                chooserIntent = Intent.createChooser(intent, "Open file2");
            }

            try {
                startActivityForResult(chooserIntent, PICK_IMAGE);  //CHOOSE_FILE_REQUESTCODE
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(getApplicationContext(), "No suitable File Manager was found.", Toast.LENGTH_SHORT).show();
            }
        }



    }



}
