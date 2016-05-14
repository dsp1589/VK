package com.garmentbazaarb2b.garmentmandi.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.garmentbazaarb2b.garmentmandi.OnTaskCompleted;
import com.garmentbazaarb2b.garmentmandi.R;
import com.garmentbazaarb2b.garmentmandi.Utils.Utility;
import com.garmentbazaarb2b.garmentmandi.api.CallAPI;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MLoginActivity extends AppCompatActivity implements OnTaskCompleted {

    // Progress Dialog
    private ProgressDialog pDialog;

    CallAPI jsonParser = new CallAPI();


    // url to create new product

    private static String url_create_product = "http://garmentbazaarb2b.com/api/v1.0/merchant/login.json";

    // JSON Node names
     private static final String TAG_SUCCESS = "OK";


    // Progress Dialog Object
    ProgressDialog prgDialog;
    // Error Msg TextView Object
    TextView errorMsg , tvCallForgotPassword;
    // Email Edit View Object
    EditText emailET;
    // Passwprd Edit View Object
    EditText pwdET;
    Button bLogin;
    String sErrorMsg="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mlogin);

        // Find Error Msg Text View control by ID
        errorMsg = (TextView)findViewById(R.id.login_error);
        // Find Email Edit View control by ID
        emailET = (EditText)findViewById(R.id.loginEmail);
        // Find Password Edit View control by ID
        pwdET = (EditText)findViewById(R.id.loginPassword);

        bLogin = (Button) findViewById(R.id.bLogin);

        tvCallForgotPassword = (TextView) findViewById(R.id.tvCallForgotPassword);

        tvCallForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MLoginActivity.this, MForgotPasswordActivity.class);
                startActivity(intent);
            }
        });






        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInput())
                {

                    String email="";
                    String password="";

                    email = emailET.getText().toString();
                    password = pwdET.getText().toString();

                    ArrayList<String> passing = new ArrayList<String>();
                    passing.add(email);
                    passing.add(password);

                    Login asyncTask =new Login(new OnTaskCompleted() {
                        @Override
                        public void onTaskCompleted() {
                            //Toast.makeText(this, "Error Occurred", Toast.LENGTH_LONG).show();
                            if (sErrorMsg.length() > 0)
                            {
                                // errorMsg.setText(sErrorMsg);
                                Toast.makeText(MLoginActivity.this, sErrorMsg, Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                errorMsg.setText("");
                                //TODO: Lokendra: Call Home Page at below line..
                               // Intent i = new Intent(MLoginActivity.this, WelcomeActivity.class);
                              //  startActivity(i);


                             //   finish();
                            }

                        }
                    });
                    asyncTask.execute(passing);
                }


            }
        });




    }



    private boolean validateInput()
    {
        boolean result=true;
        String email = emailET.getText().toString();
        String password = pwdET.getText().toString();

        if (Utility.isNotNull(email) )
        {
            if(Utility.validate(email))
            {}
            else {
                emailET.setError("Enter Valid Email");
                result=false;
            }
        }
        else {
            emailET.setError("Enter Email");
            result=false;
        }

        if (Utility.isNotNull(password) )
        {

        }
        else {
            pwdET.setError("Enter Password");
            result=false;
        }




        return  result;

    }



    @Override
    public void onTaskCompleted() {

    }


    /**
     * Background Async Task to Create new product
     * */
    class Login extends AsyncTask<ArrayList<String>, String, String> {
        private OnTaskCompleted listener;

        @Override
        protected String doInBackground(ArrayList<String>... passing) {


            ArrayList<String> passed = passing[0];
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();


            params.add(new BasicNameValuePair("email", passed.get(0)));
            params.add(new BasicNameValuePair("password", passed.get(1)));
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
            pDialog = new ProgressDialog(MLoginActivity.this);
            pDialog.setMessage("Processing..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }


        public Login(OnTaskCompleted listener){
            this.listener=listener;
        }



        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();

            listener.onTaskCompleted();
        }

    }







}
