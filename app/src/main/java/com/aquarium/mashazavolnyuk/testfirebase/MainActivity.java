package com.aquarium.mashazavolnyuk.testfirebase;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.aquarium.mashazavolnyuk.testfirebase.cardstickers.CardImage;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private JSONObject jsonStickers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkOutConnection();
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute();
//        parseJson(jsonStickers);

    }

    private void readTest() {
        imageView = (ImageView) findViewById(R.id.imgImageLoad);
        String t = "https://firebasestorage.googleapis.com/v0/b/testproject-5e7bb.appspot.com/o/cat%2Fcat.png?alt=media&token=81639aef-0a34-493e-adb5-e249f9f40607";
        Uri myUri = Uri.parse(t);
        Picasso.with(MainActivity.this).load(myUri).into(imageView);
        Log.d("httpsReference", "ref" + t);
    }

    private boolean checkOutConnection() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }


    private class MyAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            jsonStickers = Json.getJson("https://firebasestorage.googleapis.com/v0/b/testproject-5e7bb.appspot.com/o/objectsStickers.json?alt=media&token=aa3828de-d5f1-4b33-a60d-434489f6b164");
            parseJson(jsonStickers);

            return null;
        }
    }//MyAsyncTask

    private void parseJson(JSONObject jsonObject) {
        Log.d("json", "name == null");
        if (jsonObject != null) {
            Log.d("json", "name" + jsonObject.toString());
            List<CardImage> card = new ArrayList<>();
            try {
                JSONArray array = jsonObject.getJSONArray("categories");
                for (int index = 0; index < array.length(); index++) {
                    card.add(new CardImage(array.getJSONObject(index)));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static CardImage fromJson(final JSONObject object) {
        final String image = object.optString("name");

        final int price = object.optInt("price", 0);
        final int weight = object.optInt("weight", 0);
        final int kkal = object.optInt("kkal", 0);
        final String description = object.optString("description", "");
        return new CardImage(kkal, image, null);
    }


}
