package com.aquarium.mashazavolnyuk.testfirebase;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private JSONObject jsonStickers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyAsyncTask myAsyncTask=new MyAsyncTask();
        myAsyncTask.execute();



    }
    private void readTest(){
        imageView = (ImageView) findViewById(R.id.imgImageLoad);
        String t="https://firebasestorage.googleapis.com/v0/b/testproject-5e7bb.appspot.com/o/cat%2Fcat.png?alt=media&token=81639aef-0a34-493e-adb5-e249f9f40607";
        Uri myUri = Uri.parse(t);
        Picasso.with(MainActivity.this).load(myUri).into(imageView);
        Log.d("httpsReference", "ref" + t);

    }
   private class MyAsyncTask extends AsyncTask<Void, Void, Void> {

       @Override
       protected Void doInBackground(Void... params) {
           try {
               jsonStickers=LoaderJsonModel.readJsonFromUrl("https://firebasestorage.googleapis.com/v0/b/testproject-5e7bb.appspot.com/o/objectsStickers.json?alt=media&token=aa3828de-d5f1-4b33-a60d-434489f6b164");
           } catch (IOException e) {
               e.printStackTrace();
           } catch (JSONException e) {
               e.printStackTrace();
           }
           return null;
       }
   }//MyAsyncTask

    



}
