package com.aquarium.mashazavolnyuk.testfirebase;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.aquarium.mashazavolnyuk.testfirebase.cardstickers.CardImage;
import com.aquarium.mashazavolnyuk.testfirebase.data.CardStickers;
import com.aquarium.mashazavolnyuk.testfirebase.fragments.MyFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements INavigatorFragment {

    private JSONObject jsonStickers;
    @BindView(R.id.imgImageLoad) Button load;
   // private Button load;
    private FragmentManager fragmentManager = getFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        checkOutConnection();
        final AsyncTaskLoadJsonModel asyncTaskLoadJsonModel = new AsyncTaskLoadJsonModel();
        asyncTaskLoadJsonModel.execute();
        Log.d("load task", "status" + asyncTaskLoadJsonModel.getStatus());
      //load = (Button) findViewById(R.id.imgImageLoad);

        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTaskParse parse = new AsyncTaskParse();
                if (asyncTaskLoadJsonModel.getStatus() == AsyncTask.Status.FINISHED) {  //finish doInBackground() and onPostExecute was called
                    Log.d("load task", "status" + asyncTaskLoadJsonModel.getStatus());
                    parse.execute(jsonStickers);
                    Log.d("parse task", "status" + parse.getStatus());
                }
                if (parse.getStatus() == AsyncTask.Status.FINISHED)
                    toListCard();

            }
        });


    }

    private void readTest() {
        //imageView = (ImageView) findViewById(R.id.imgImageLoad);
        String t = "https://firebasestorage.googleapis.com/v0/b/testproject-5e7bb.appspot.com/o/cat%2Fcat.png?alt=media&token=81639aef-0a34-493e-adb5-e249f9f40607";
        Uri myUri = Uri.parse(t);
        // Picasso.with(MainActivity.this).load(myUri).into(imageView);
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

    @Override
    public void toListCard() {
        if (CardStickers.getInstance().getCardImageList() != null) {
            FragmentTransaction fragmentTransaction = fragmentManager
                    .beginTransaction();

            // добавляем фрагмент
            MyFragment myFragment = new MyFragment();
            fragmentTransaction.add(R.id.container, myFragment);
            fragmentTransaction.commit();
        }
    }


    private class AsyncTaskLoadJsonModel extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            jsonStickers = Json.getJson("https://firebasestorage.googleapis.com/v0/b/testproject-5e7bb.appspot.com/o/objectsStickers.json?alt=media&token=da163db4-8064-44ad-b880-0c5ce65145f8");
            return null;
        }
    }//AsyncTaskLoadJsonModel

    private class AsyncTaskParse extends AsyncTask<JSONObject, Void, Void> {
        @Override
        protected Void doInBackground(JSONObject... params) {
            JSONObject jsonObject = params[0];
            parseJson(jsonObject);
            return null;
        }
    }

    private void parseJson(JSONObject jsonObject) {
        Log.d("json", "name == null");
        if (jsonObject != null) {
            Log.d("json", "name" + jsonObject.toString());
            try {

                JSONArray array = jsonObject.getJSONArray("categories");
                for (int index = 0; index < array.length(); index++) {
                    Log.d("loop", "index" + index);
                    CardStickers.getInstance().addCardImage(new CardImage(array.getJSONObject(index)));
                }
            } catch (JSONException e) {
                e.printStackTrace();

            }
        }
    }
}
