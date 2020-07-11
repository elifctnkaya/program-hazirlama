package com.example.takvimdeneme;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {

    //positionindeterminete
    private Button anasayfa;
    private ProgressDialog yukleme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        yukleme = new ProgressDialog(this);
        anasayfa = findViewById(R.id.anasayfa);

        anasayfa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yukleme.setTitle("Program Açılıyor");
                yukleme.setMessage("Lütfen Bekleyin");
                yukleme.setCanceledOnTouchOutside(false);
                yukleme.show();

                Intent intent = new Intent(StartActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

}