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

public class CarActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextId;
    private Button buttonGet;
    private TextView textViewResult;

    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);

        getSupportActionBar().hide();

        editTextId = (EditText) findViewById(R.id.editTextId);
        buttonGet = (Button) findViewById(R.id.buttonGet);
        textViewResult = (TextView) findViewById(R.id.textViewResult);

        buttonGet.setOnClickListener(this);
        
        Intent intent = getIntent();
        String car_no = intent.getStringExtra("carNo");
        editTextId.setText(car_no);
        
    }

    private void getData() {
        String id = editTextId.getText().toString().trim();
        if (id.equals("")) {
            Toast.makeText(this, "Please enter an id", Toast.LENGTH_LONG).show();
            return;
        }
        loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);

        String url = CarConfig.DATA_URL+editTextId.getText().toString().trim();

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
                        Toast.makeText(CarActivity.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response){
        String color="";
        String model="";
        String fuel_type = "";
        String crime_attachment = "";
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(CarConfig.JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);
            color = collegeData.getString(CarConfig.KEY_COLOR);
            model = collegeData.getString(CarConfig.KEY_MODEL);
            fuel_type = collegeData.getString(CarConfig.KEY_FUEL);
            crime_attachment = collegeData.getString(CarConfig.KEY_CRIME);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        textViewResult.setText("Color:\t"+color+"\nModel:\t"+model+
                "\nFuel Type:\t"+fuel_type+"\nCrime Attachment:\t"+crime_attachment);
    }

    @Override
    public void onClick(View v) {
        getData();
    }
}
