package com.example.kediriapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DataAdapter extends BaseAdapter {

    List<Data> daftar;
    Context context;

    public DataAdapter(Context ctx){
        this.context=ctx;
        daftar = new ArrayList<>();
    }

    public void loadData(JSONObject jo){

        try{
            Log.i("Fitur","oke");
            JSONArray data = jo.getJSONArray("features");
            daftar.clear();

            for(int i=0; i<data.length();i++){
                JSONObject obj = data.getJSONObject(i);
                Data d = new Data(obj.getJSONObject("properties").getString("id"),
                        obj.getJSONObject("properties").getString("nama"),
                        obj.getJSONObject("properties").getString("lat"),
                        obj.getJSONObject("properties").getString("lng")


                );
                daftar.add(d);
            }

        }catch (Exception e){
            e.printStackTrace();
            Log.e("Error on loadData : ",e.toString());
        }
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return daftar.size();
    }

    @Override
    public Object getItem(int i) {
        return daftar.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view =inflater.inflate(R.layout.peternakan_list,viewGroup,false);
        TextView tvId = view.findViewById(R.id.tvId);
        TextView tvNama = view.findViewById(R.id.tvNama);
        TextView tvLat = view.findViewById(R.id.tvLat);
        TextView tvlng = view.findViewById(R.id.tvLng);
        Data d = daftar.get(i);
        tvId.setText(d.id);
        tvNama.setText(d.nama);
        tvLat.setText(d.lat);
        tvlng.setText(d.lng);

        return view;
    }
}
