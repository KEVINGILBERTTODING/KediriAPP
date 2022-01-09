package com.example.kediriapp;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class InputPeternakan extends AppCompatActivity {

    EditText namaPeternakan, jenisPeternakan, alamatPeternakan, latitude, longitude;
    Button InsertButton;
    ProgressDialog progressDialog;


    RequestQueue requestQueue;
    String NameHolder, JenisHolder, AlamatHolder, LatitudeHolder, LongitudeHolder;// String Variables To Hold EditText Values.

    String HttpUrl = "http://192.168.0.100/kediri-uas/peternakan.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_peternakan);

        namaPeternakan = findViewById(R.id.editTextName);
        jenisPeternakan = findViewById(R.id.editTextJenis);
        alamatPeternakan = findViewById(R.id.editTextAlamat);
        latitude = findViewById(R.id.editTextLatitude);
        longitude = findViewById(R.id.editTextLongitude);
        InsertButton = findViewById(R.id.ButtonInsert);

        requestQueue = Volley.newRequestQueue(InputPeternakan.this);

        progressDialog = new ProgressDialog(InputPeternakan.this);

        // Adding click listener to button.
        InsertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Showing progress dialog at user registration time.
                progressDialog.setMessage("Tunggu Sebentar");
                progressDialog.show();

                // Calling method to get value from EditText.
                GetValueFromEditText();

                // Creating string request with post method.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String ServerResponse) {
                                // Hiding the progress dialog after all task complete.
                                progressDialog.dismiss();

                                // Showing response message coming from server.
                                Toast.makeText(InputPeternakan.this, ServerResponse, Toast.LENGTH_LONG).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                // Hiding the progress dialog after all task complete.
                                progressDialog.dismiss();

                                // Showing error message if something goes wrong.
                                Toast.makeText(InputPeternakan.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {

                        Map<String, String> params = new HashMap<>();// Creating Map String Params.

                        // Adding All values to Params.
                        params.put("nama", NameHolder);
                        params.put("jenis", JenisHolder);
                        params.put("alamat", AlamatHolder);
                        params.put("lat", LatitudeHolder);
                        params.put("lng", LongitudeHolder);

                        return params;
                    }

                };

                RequestQueue requestQueue = Volley.newRequestQueue(InputPeternakan.this);// Creating RequestQueue.
                requestQueue.add(stringRequest);// Adding the StringRequest object into requestQueue.

            }
        });

    }

    // Creating method to get value from EditText.
    public void GetValueFromEditText() {
        NameHolder = namaPeternakan.getText().toString().trim();
        JenisHolder = jenisPeternakan.getText().toString().trim();
        AlamatHolder = alamatPeternakan.getText().toString().trim();
        LatitudeHolder = latitude.getText().toString().trim();
        LongitudeHolder = longitude.getText().toString().trim();

    }
}