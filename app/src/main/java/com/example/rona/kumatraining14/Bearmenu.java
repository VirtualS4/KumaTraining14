package com.example.rona.kumatraining14;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Bearmenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bearmenu);
        Button btn1 = findViewById(R.id.btn_lat1);
        Button btn2 = findViewById(R.id.btn_lat2);
        initbtn(btn1,Beartivity.class);
        initbtn(btn2, AddBear.class);
    }

    private void initbtn(Button btn, final Class Bearclass){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bearsense = new Intent(Bearmenu.this,Bearclass);
                startActivity(bearsense);
            }
        });
    }
}
