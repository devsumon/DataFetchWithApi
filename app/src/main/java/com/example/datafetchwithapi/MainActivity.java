package com.example.datafetchwithapi;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {




    private RecyclerView rv;
    String data;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv =findViewById(R.id.recyclerViewid);
        new myAsyncTask().execute();
        //for listView code
        rv.setLayoutManager(new LinearLayoutManager(this));

        //for GridView code
        //rv.setLayoutManager(new GridLayoutManager(this, 2));
        
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public  String fetchDataFromUrl(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

public class myAsyncTask extends AsyncTask{

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            data = fetchDataFromUrl("https://api.androidhive.info/contacts/");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);


        MyContacts myContacts = new Gson().fromJson(data, MyContacts.class);
        rv.setAdapter(new MyAdapter(MainActivity.this, myContacts.getContacts()));

    }
}



}
