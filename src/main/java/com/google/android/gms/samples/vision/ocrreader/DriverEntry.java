package com.google.android.gms.samples.vision.ocrreader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.HashMap;


public class DriverEntry extends AppCompatActivity {

    Button register, ccase;
    EditText Permit_No, Name, Tel, Car_Reg, Car_Make, Offence, Place, Date, Amount, Owner ;
    String Permit_No_Holder, Name_Holder, Tel_Holder, Car_Reg_Holder, Car_Make_Holder, Offence_Holder, Place_Holder, Date_Holder, Amount_Holder, Owner_Holder;
    String finalResult ;
    String HttpURL = "https://csc1304.000webhostapp.com/User/DriverCase.php";
    Boolean CheckEditText ;
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_entry);

        getSupportActionBar().hide();

        //Assign Id'S
        Permit_No = (EditText)findViewById(R.id.permit);
        Name = (EditText)findViewById(R.id.dname);
        Tel = (EditText)findViewById(R.id.dtel);
        Car_Reg = (EditText)findViewById(R.id.creg);
        Car_Make = (EditText)findViewById(R.id.cmake);
        Offence = (EditText)findViewById(R.id.offence);
        Place = (EditText)findViewById(R.id.oplace);
        Date = (EditText)findViewById(R.id.odate);
        Amount = (EditText)findViewById(R.id.oamt);
        Owner = (EditText)findViewById(R.id.cown);

        //register = (Button)findViewById(R.id.Submit);
        ccase = (Button)findViewById(R.id.Submit);

        //Adding Click Listener on button.
        ccase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Checking whether EditText is Empty or Not
                CheckEditTextIsEmptyOrNot();

                if(CheckEditText){

                    // If EditText is not empty and CheckEditText = True then this block will execute.

                    UserRegisterFunction(Permit_No_Holder,Name_Holder,Tel_Holder,Car_Reg_Holder,Car_Make_Holder,Offence_Holder,Place_Holder,Date_Holder,Amount_Holder,Owner_Holder);

                }
                else {

                    // If EditText is empty then this block will execute .
                    Toast.makeText(DriverEntry.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();

                }


            }
        });

    }

    public void CheckEditTextIsEmptyOrNot(){

        Permit_No_Holder = Permit_No.getText().toString();
        Name_Holder = Name.getText().toString();
        Tel_Holder = Tel.getText().toString();
        Car_Reg_Holder = Car_Reg.getText().toString();
        Car_Make_Holder = Car_Make.getText().toString();
        Offence_Holder = Offence.getText().toString();
        Place_Holder = Place.getText().toString();
        Date_Holder = Date.getText().toString();
        Amount_Holder = Amount.getText().toString();
        Owner_Holder = Owner.getText().toString();


        if(TextUtils.isEmpty(Permit_No_Holder) || TextUtils.isEmpty(Name_Holder) || TextUtils.isEmpty(Tel_Holder) || TextUtils.isEmpty(Car_Reg_Holder) || TextUtils.isEmpty(Car_Make_Holder) || TextUtils.isEmpty(Offence_Holder) || TextUtils.isEmpty(Place_Holder) || TextUtils.isEmpty(Date_Holder) || TextUtils.isEmpty(Amount_Holder) || TextUtils.isEmpty(Owner_Holder))
        {

            CheckEditText = false;

        }
        else {

            CheckEditText = true ;
        }

    }

    public void UserRegisterFunction(final String D_Permit, final String D_Name, final String D_Tel, final String D_Reg, final String D_Make, final String D_Offence, final String D_Place, final String D_Date, final String D_Amount, final String D_Owner){

        class UserRegisterFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(DriverEntry.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                Toast.makeText(DriverEntry.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("id_no",params[0]);

                hashMap.put("name",params[1]);

                hashMap.put("tel_no",params[2]);

                hashMap.put("vehicle_no",params[3]);

                hashMap.put("make",params[4]);

                hashMap.put("offence",params[5]);

                hashMap.put("offence_place",params[6]);

                hashMap.put("offence_date",params[7]);

                hashMap.put("amount",params[8]);

                hashMap.put("car_owner",params[9]);


                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        UserRegisterFunctionClass userRegisterFunctionClass = new UserRegisterFunctionClass();

        userRegisterFunctionClass.execute(D_Permit,D_Name,D_Tel,D_Reg,D_Make,D_Offence,D_Place,D_Date,D_Amount,D_Owner);
    }

}
