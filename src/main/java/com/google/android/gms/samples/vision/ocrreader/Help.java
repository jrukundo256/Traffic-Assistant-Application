package com.google.android.gms.samples.vision.ocrreader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Help extends AppCompatActivity implements View.OnClickListener {

    TextView HelpText;
    Button Home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        getSupportActionBar().hide();

        HelpText = (TextView) findViewById(R.id.help_text);
        Home = (Button) findViewById(R.id.go_home);

        HelpText.setText("1. \t"+"A first time user is required to register."+"\n\n2. \t"+"After registration, a user can go on to login."+
                "\n\n3. \t"+"Click on 'Detect Text' to scan for ID."+"\n\n4. \t"+"Click on 'Submit Driver ID' to get driver details"+"\n\n5. \t"+
                "Click on 'Submit Car ID' to get car details");

        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Help.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }

    public void onClick(View v) {

    }
}
