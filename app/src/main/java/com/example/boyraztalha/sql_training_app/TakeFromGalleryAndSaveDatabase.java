package com.example.boyraztalha.sql_training_app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class TakeFromGalleryAndSaveDatabase extends AppCompatActivity {

    ImageView img;
    Button al,kaydet,DbdenAl;
    Bitmap selectedimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_from_gallery_and_save_database);

        img = findViewById(R.id.img);
        al = findViewById(R.id.btnal);
        DbdenAl = findViewById(R.id.btnDB);
        kaydet = findViewById(R.id.btnkaydet);

        al.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TakePictureFfromGallery();
            }
        });

        kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveImageToDatabase();
            }
        });

        DbdenAl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TakePictureFromDatabase();
            }
        });
    }

    private void TakePictureFromDatabase() {
        SQLiteDatabase db = this.openOrCreateDatabase("sqltraining.db",MODE_PRIVATE,null);
        db.execSQL("create table if not exists resimler2 (image BLOB)");
        Cursor cursor = db.rawQuery("SELECT * FROM resimler2",null);
        if (cursor.moveToFirst()){
            int columnIndex = cursor.getColumnIndex("image");
            byte[] byteImg = cursor.getBlob(columnIndex);
            Bitmap bitmapImg = BitmapFactory.decodeByteArray(byteImg,0,byteImg.length);

            img.setImageBitmap(bitmapImg);
        }
    }

    private void SaveImageToDatabase() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        selectedimg.compress(Bitmap.CompressFormat.PNG,50,byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();

        SQLiteDatabase db = this.openOrCreateDatabase("sqltraining.db",MODE_PRIVATE,null);
        db.execSQL("create table if not exists resimler2 (image BLOB)");

        String queryString = "INSERT INTO resimler2 VALUES (?)";
        SQLiteStatement sqLiteStatement = db.compileStatement(queryString);
        sqLiteStatement.bindBlob(1,bytes);
        sqLiteStatement.execute();
    }

    private void TakePictureFfromGallery() {

        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},2);
        }else{
            Intent i = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 2){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent i = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 1);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data!= null){
            Uri image = data.getData();

            try {
                selectedimg = MediaStore.Images.Media.getBitmap(this.getContentResolver(),image);
                img.setImageBitmap(selectedimg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
