package com.example.boyraztalha.sql_training_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> arrayList;
    Button ekle,sil,guncelle;
    EditText isim, maas;
    SqliteHelper sqliteHelper;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ekle = findViewById(R.id.btnEkle);
        sil = findViewById(R.id.btnSil);
        guncelle = findViewById(R.id.btnUpdate);
        isim = findViewById(R.id.editName);
        maas = findViewById(R.id.editSalary);
        listView = findViewById(R.id.listView);

        sqliteHelper = new SqliteHelper(this);

        arrayList = new ArrayList<String>();
        arrayList = sqliteHelper.getAllDatas();

        arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,arrayList);

        listView.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();

        ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = isim.getText().toString();
                int salary = Integer.parseInt(maas.getText().toString());

                People_info peopleInfo = new People_info();
                peopleInfo.setName(name);
                peopleInfo.setSalary(salary);

                sqliteHelper.addToTheDatabase(peopleInfo);
                arrayAdapter.add(peopleInfo.toString());
            }
        });

        guncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = isim.getText().toString();
                int salary = Integer.parseInt(maas.getText().toString());

                sqliteHelper.updateDatabase(name, salary);
            }
        });

        sil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = isim.getText().toString();

                sqliteHelper.deleteInfoFromDatabase(name);
            }
        });

    }

}
