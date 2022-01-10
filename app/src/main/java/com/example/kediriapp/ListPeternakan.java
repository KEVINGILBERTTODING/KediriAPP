package com.example.kediriapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class ListPeternakan extends AppCompatActivity {

    ListView listview;
    DataAdapter adapter;
    String url="http://192.168.0.101/geoserver/kediri/ows?service=WFS&version=1.0.0&request=GetFeature&typeName=kediri%3Apeternakan1&maxFeatures=50&outputFormat=application%2Fjson";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_peternakan);
        listview = findViewById(R.id.listview);
        adapter = new DataAdapter(this);
        aksesData();
        listview.setAdapter(adapter);
    }

    public void aksesData(){
        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                try {
                    URL alamat = new URL(url);
                    HttpURLConnection koneksi =(HttpURLConnection) alamat.openConnection();
                    InputStream is =koneksi.getInputStream();
                    InputStreamReader reader =new InputStreamReader(is);
                    BufferedReader buffread = new BufferedReader(reader);

                    String barisutuh ="";
                    String baris =buffread.readLine();
                    while(baris!=null){
                        barisutuh=barisutuh+baris;
                        baris = buffread.readLine();
                    }

                    try{
                        JSONObject jo = new JSONObject(barisutuh);
                        return jo;


                    }catch (Exception e){
                        Log.e("Error On JSONObject : ",e.toString());
                    }


                }catch (Exception e){
                    Log.e("Error On Read Stream :",e.toString());
                }
                return null;
            }
            @Override
            protected void onPostExecute(Object o){
                JSONObject jo = (JSONObject) o;
                adapter.loadData(jo);

            }
        };
        task.execute();

    }
}