package com.google.android.gms.samples.vision.ocrreader;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DriverActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextId;
    private Button buttonGet;
    private TextView textViewResult;

    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        getSupportActionBar().hide();

        editTextId = (EditText) findViewById(R.id.editTextIdDriver);
        buttonGet = (Button) findViewById(R.id.buttonGetDriver);
        textViewResult = (TextView) findViewById(R.id.textViewResultDriver);

        buttonGet.setOnClickListener(this);
        
        Intent intent = getIntent();
        String driver_no = intent.getStringExtra("driverNo");
        editTextId.setText(driver_no);
    }

    private void getData() {
        String id = editTextId.getText().toString().trim();
        if (id.equals("")) {
            Toast.makeText(this, "Please enter an id", Toast.LENGTH_LONG).show();
            return;
        }
        loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);

        String url = DriverConfig.DATA_URL+editTextId.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DriverActivity.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response){
        String name="";
        String dob="";
        String restriction = "";
        String crime_attachment = "";
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(DriverConfig.JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);
            name = collegeData.getString(DriverConfig.KEY_NAME);
            dob = collegeData.getString(DriverConfig.KEY_DOB);
            restriction = collegeData.getString(DriverConfig.KEY_RES);
            crime_attachment = collegeData.getString(DriverConfig.KEY_CRIME);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        textViewResult.setText("Driver Name:\t"+name+"\nDate of Birth:\t"+dob+
                "\nDriver Restriction:\t"+restriction+"\nCrime Attachment:\t"+crime_attachment);
    }

    @Override
    public void onClick(View v) {
        getData();
    }
}
