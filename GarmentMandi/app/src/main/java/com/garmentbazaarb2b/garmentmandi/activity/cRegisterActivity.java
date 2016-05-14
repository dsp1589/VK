package com.garmentbazaarb2b.garmentmandi.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.garmentbazaarb2b.garmentmandi.R;
import com.garmentbazaarb2b.garmentmandi.Utils.Utility;
import com.garmentbazaarb2b.garmentmandi.api.CallAPI;
import com.garmentbazaarb2b.garmentmandi.common.City;
import com.garmentbazaarb2b.garmentmandi.common.Country;
import com.garmentbazaarb2b.garmentmandi.interfaces.IcRegister;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class cRegisterActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, IcRegister {


    private ProgressDialog pDialog;

    CallAPI jsonParser = new CallAPI();
    int mSelectedCountryID=24;
    int mSelectedCityID=0;
    String mRegistrationStatus="";
    // url to create new product

    CheckBox chkConfirm;
    ScrollView MainScrollView;

    String sFirstName ,sLastName, sEmail,sPanCardNumber,sAddress1,sAddress2,sPhone, sBankName,sBankAccountName,sBankAccountNumber;
    String sNEFTCode, sServiceTaxNumber, sMobileNumber,sBusinessName, sZipcode;


    // JSON Node names
    private static final String TAG_SUCCESS = "OK";
    String sErrorMsg="";

    RelativeLayout Tab1, Tab2, Tab3;
    Button bNext, bSaveAndContinue, bGoBack, bFinish;

    String mMobileNumber, mVerificationCode;

    EditText FirstName,LastName,Email,PanCardNumber,Address1,Address2,Phone,BankName,BankAccountName,BankAccountNumber,NEFTCode,ServiceTaxNumber,MobileNumber,BusinessName, Zipcode;
    EditText VerificationCode;

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
        setContentView(R.layout.activity_c_register);
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

        chkConfirm = (CheckBox) findViewById(R.id.chkConfirm);

        FirstName = (EditText) findViewById(R.id.FirstName);
        LastName = (EditText) findViewById(R.id.LastName);
        Email = (EditText) findViewById(R.id.Email);
        PanCardNumber = (EditText) findViewById(R.id.PanCardNumber);
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


        Country = (Spinner) findViewById(R.id.Country);
        Country.setOnItemSelectedListener(this);

        City = (Spinner) findViewById(R.id.City);
        City.setOnItemSelectedListener(this);

        VerificationCode = (EditText) findViewById(R.id.VerificationCode);

        bNext.setOnClickListener(this);
        bSaveAndContinue.setOnClickListener(this);
        bGoBack.setOnClickListener(this);
        bFinish.setOnClickListener(this);



        ArrayList<String> passing = new ArrayList<String>();
        passing.add("1111");

        IcRegister icRegister= new cRegisterActivity();
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
                 sPanCardNumber= PanCardNumber.getText().toString();
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
            FirstName.requestFocus();
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


        if (Utility.isNotNull(sPanCardNumber) )
        {
            PanCardNumber.setError(null);
        }
        else {
            PanCardNumber.setError("Enter Pan Card Number");
            PanCardNumber.requestFocus();
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



                        String mobileNumber="";

                        mobileNumber = MobileNumber.getText().toString();

                        ArrayList<String> passing = new ArrayList<String>();
                        passing.add(mobileNumber);

                        GetVerificationCode asyncTask =new GetVerificationCode(new IcRegister() {
                            @Override
                            public void OnVerificationCodeFetched() {
                                //Toast.makeText(this, "Error Occurred", Toast.LENGTH_LONG).show();
                                if (sErrorMsg.length() > 0)
                                {
                                    // errorMsg.setText(sErrorMsg);
                                    Toast.makeText(cRegisterActivity.this, sErrorMsg, Toast.LENGTH_LONG).show();
                                }
                                else
                                {
                                    Toast.makeText(cRegisterActivity.this, "Verification Code Sent." , Toast.LENGTH_LONG).show();
                                    //TODO: LOKI: Comment Below line.. It is for testing purpose only..
                                    VerificationCode.setText(mVerificationCode);

                                    Tab1.setVisibility(View.GONE);
                                    Tab2.setVisibility(View.GONE);
                                    Tab3.setVisibility(View.VISIBLE);
                                    MainScrollView.fullScroll(ScrollView.FOCUS_UP);
                                }

                            }


                            public  void CountryFetched(List<Country> countries)
                            {}


                            public void OnCityFetched(List<City> cities)
                            {}

                            public void OnDataSaved(String status)
                            {}

                        });
                        asyncTask.execute(passing);

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
                    Toast.makeText(cRegisterActivity.this, "Please Select Checkbox" , Toast.LENGTH_LONG).show();
                }
                else
                {
                    String sCode="";
                    sCode = VerificationCode.getText().toString();
                    if (sCode.length() == 0)
                    {
                        VerificationCode.setError("Enter Verification Code");

                    }
                    else {
                        if(sCode.equals(mVerificationCode))
                        {
                            //Call Save registration..
                            //   Toast.makeText(cRegisterActivity.this, "Ready to Save" , Toast.LENGTH_LONG).show();





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
                                        Toast.makeText(cRegisterActivity.this, "Registration Successful", Toast.LENGTH_LONG).show();
                                    }
                                    else
                                    {

                                    }
                                }

                            });
                            asyncTask.execute(passing);

                            //----------------------------------------------------



                        }

                    }

                }



                break;

            default:

                break;
        }

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

                IcRegister icRegister= new cRegisterActivity();
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


    /**
     * Background Async Task to Create new product
     * */
    class GetVerificationCode extends AsyncTask<ArrayList<String>, String, String> {
        private IcRegister listener;

        @Override
        protected String doInBackground(ArrayList<String>... passing) {

            String url_create_product = "http://garmentbazaarb2b.com/api/v1.0/users/send_verify_msg.json";
            ArrayList<String> passed = passing[0];
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();


            params.add(new BasicNameValuePair("mobile_number", passed.get(0)));
           // params.add(new BasicNameValuePair("password", passed.get(1)));
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
                    sErrorMsg="";
                    //JSONObject jsonValues = new JSONObject();

                    // try parse the string to a JSON object
                    try {
                        String strVal= json.get("result").toString();
                        JSONObject jsonValues = new JSONObject(strVal);
                        mMobileNumber=jsonValues.get("mobile_number").toString();
                        mVerificationCode=jsonValues.get("ver_code").toString();
                    } catch (JSONException e) {
                        Log.e("JSON Parser", "Error parsing data " + e.toString());
                    }



                } else {

                    //Toast.makeText(getApplicationContext(), "Error Occurred",Toast.LENGTH_LONG).show();
                    sErrorMsg=json.get("info").toString();
                    //  errorMsg.setVisibility(View.VISIBLE);
                    // failed to create product
                    //  errorMsg.setText("Error Occured");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(cRegisterActivity.this);
            pDialog.setMessage("Processing..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }


        public GetVerificationCode(IcRegister listener){
            this.listener=listener;
        }



        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();

            listener.OnVerificationCodeFetched();
        }

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

        String url_create_product = "http://garmentbazaarb2b.com/api/v1.0/users/signup.json";

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
            params.add(new BasicNameValuePair("pan_number",sPanCardNumber ));
            params.add(new BasicNameValuePair("mraddress1",sAddress1 ));
            params.add(new BasicNameValuePair("mraddress2", sAddress2));
            params.add(new BasicNameValuePair("mphone_number", sPhone));

            params.add(new BasicNameValuePair("bank_name", sBankName));
            params.add(new BasicNameValuePair("account_name", sBankAccountName));
            params.add(new BasicNameValuePair("account_number", sBankAccountNumber));

            params.add(new BasicNameValuePair("neft_code", sNEFTCode));
            params.add(new BasicNameValuePair("service_tax_number", sServiceTaxNumber));
            params.add(new BasicNameValuePair("mobile_number", sMobileNumber));
            params.add(new BasicNameValuePair("business_name", sBusinessName));
            params.add(new BasicNameValuePair("country", String.valueOf(mSelectedCountryID)));
            params.add(new BasicNameValuePair("city", String.valueOf(mSelectedCityID)));
            params.add(new BasicNameValuePair("zipcode", sZipcode));




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


}
