package com.example.takvimdeneme;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SinavEditActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private TextView textView;
    private EditText editText1;
    private EditText editText2;
    private Button buttonekle;
    private TextView textView2;
    private Database database;
    private Fragment fragment;
    private TextView textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sinav_edit);

        textView = findViewById(R.id.gun);
        textView2 = findViewById(R.id.saat);
        textView3 = findViewById(R.id.sinif);
        editText1 = findViewById(R.id.dersAdi);
        editText2 = findViewById(R.id.hocaismi);
        buttonekle = findViewById(R.id.ekle);
        Bundle bundle = getIntent().getExtras();
        final String gun = bundle.getString("GUN");
        String clock = bundle.getString("SAAT");

        textView.setText(gun);
        textView2.setText(clock);
        final String gunn = textView.getText().toString();

        //////saatlerin tablo şeklinde gösterilmesi
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(SinavEditActivity.this, textView2);
                popup.setOnMenuItemClickListener(SinavEditActivity.this);
                popup.inflate(R.menu.popup_menu);
                popup.show();
            }
        });

        ////Sınıfların Gösterilmesi
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu sinifPopup = new PopupMenu(SinavEditActivity.this, textView3);
                sinifPopup.setOnMenuItemClickListener(SinavEditActivity.this);
                sinifPopup.inflate(R.menu.sinif_menu);
                sinifPopup.show();
            }
        });


        buttonekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    database = new Database(getApplicationContext());
                    final String x1 = textView.getText().toString();
                    final String x2 = textView2.getText().toString();
                    final String x3 = textView3.getText().toString();
                    final String x4 = editText1.getText().toString();
                    final String x5 = editText2.getText().toString();

                    ////////////////////////////////////////////////
                    final int[] kontrol = {0};
                    final int[] sinif_kontrol = {0};
                    ArrayList<SinavTable> gelenler = database.SinavVerileri();
                    for (SinavTable e : gelenler) {
                        String gunn = e.getSinav_gun();
                        String saat = e.getSinav_saat();
                        String sinif = e.getSinav_sinif();
                        String ders = e.getSinav_ders();
                        String hoca = e.getSinav_hoca();

                        if (gunn.contentEquals(textView.getText().toString()) && saat.contentEquals(textView2.getText().toString())) {
                            kontrol[0] = 1;
                        }
                        if(gunn.contentEquals(textView.getText().toString()) && sinif.contentEquals(textView3.getText().toString())){
                            sinif_kontrol[0] = 1;
                        }
                    }
                    if (kontrol[0] == 1) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(SinavEditActivity.this);
                        alert.setTitle("Eklemek istediğiniz alan dolu");
                        alert.setMessage("Eklemek istediğinize emin misiniz ?");
                        alert.setIcon(R.drawable.unlem);
                        alert.setPositiveButton("EVET", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(sinif_kontrol[0] == 1){
                                    AlertDialog.Builder alert = new AlertDialog.Builder(SinavEditActivity.this);
                                    alert.setTitle("Eklemek İstediğiniz Sınıf Eklenmiş");
                                    alert.setMessage("Bir günde bir sınıf için sadece bir sınav eklenebilir.");
                                    alert.setIcon(R.drawable.unlem);

                                    alert.setNegativeButton("TAMAM", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(getApplicationContext(), "Veri Eklenmedi", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    alert.create().show();
                                }
                                else{
                                    boolean ed = database.SinavGuncelle(x1, x2, x3, x4,x5);

                                    if(ed == true){
                                        Toast.makeText(getApplicationContext(),"Veri Güncellendi", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(SinavEditActivity.this,SinavMainActivity.class);
                                        startActivity(intent);

                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(),"Veri Güncellenmedi", Toast.LENGTH_SHORT).show();
                                    }

                                }

                            }
                        });
                        alert.setNegativeButton("HAYIR", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(), "Veri Eklenmedi", Toast.LENGTH_SHORT).show();
                            }
                        });
                        alert.create().show();
                    }
                    else{
                        if (sinif_kontrol[0] == 1){
                            AlertDialog.Builder alert = new AlertDialog.Builder(SinavEditActivity.this);
                            alert.setTitle("Eklemek İstediğiniz Sınıf Eklenmiş");
                            alert.setMessage("Bir günde bir sınıf için sadece bir sınav eklenebilir.");
                            alert.setIcon(R.drawable.unlem);

                            alert.setNegativeButton("TAMAM", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(getApplicationContext(), "Veri Eklenmedi", Toast.LENGTH_SHORT).show();
                                }
                            });
                            alert.create().show();
                        }
                        else {
                            boolean dd = database.SinavEkle(textView.getText().toString(), textView2.getText().toString(), textView3.getText().toString(), editText1.getText().toString(), editText2.getText().toString());
                            if (dd == true) {
                                Toast.makeText(getApplicationContext(), "Veri Yüklendi", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SinavEditActivity.this, SinavMainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "Veri Yüklenemedi", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"HATA", Toast.LENGTH_SHORT).show();
                }
                textView3.setText("");
                editText1.setText("");
                editText2.setText("");

            }
        });

    }

    public void gecis(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.sinav_frame,fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                textView2.setText("08.00");
                return true;
            case R.id.item2:
                textView2.setText("09.00");
                return true;
            case R.id.item3:
                textView2.setText("10.00");
                return true;
            case R.id.item4:
                textView2.setText("11.00");
                return true;
            case R.id.item5:
                textView2.setText("12.00");
                return true;
            case R.id.item6:
                textView2.setText("13.00");
                return true;
            case R.id.item7:
                textView2.setText("14.00");
                return true;
            case R.id.item8:
                textView2.setText("15.00");
                return true;
            case R.id.item9:
                textView2.setText("16.00");
                return true;
            case R.id.item10:
                textView2.setText("17.00");
                return true;
            case R.id.item11:
                textView2.setText("18.00");
                return true;
            case R.id.item12:
                textView2.setText("19.00");
                return true;
            case R.id.item13:
                textView2.setText("20.00");
                return true;
            case R.id.item14:
                textView2.setText("21.00");
                return true;
            case R.id.item15:
                textView2.setText("22.00");
                return true;
            case R.id.item16:
                textView2.setText("23.00");
                return true;
            case R.id.item17:
                textView2.setText("00.00");
                return true;
            case R.id.sinif1:
                textView3.setText("1.Sınıf");
                return true;
            case R.id.sinif2:
                textView3.setText("2.Sınıf");
                return true;
            case R.id.sinif3:
                textView3.setText("3.Sınıf");
                return true;
            case R.id.sinif4:
                textView3.setText("4.Sınıf");
                return true;
            default:
                return false;
        }
    }

}