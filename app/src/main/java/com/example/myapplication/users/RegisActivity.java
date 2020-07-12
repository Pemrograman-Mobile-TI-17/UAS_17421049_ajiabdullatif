package com.example.myapplication.users;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;
import com.example.myapplication.server.BaseURL;
import com.ornach.nobobutton.NoboButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.example.myapplication.server.BaseURL.registrasi;
import static com.example.myapplication.session.PrefSetting.email;
import static com.example.myapplication.session.PrefSetting.namaLengkap;
import static com.example.myapplication.session.PrefSetting.noTelp;
import static com.example.myapplication.session.PrefSetting.userName;

public class RegisActivity extends AppCompatActivity {
    private Button btnklog;
    private NoboButton btnregis;
    private EditText edtusname, edtnamaleng, edtemail, edtnotel, edtpsw;
    ProgressDialog pDialog;

    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis);
        mRequestQueue = Volley.newRequestQueue(this);

        edtusname = findViewById(R.id.edtusname);
        edtnamaleng = findViewById(R.id.edtnamaleng);
        edtemail = findViewById(R.id.edtemail);
        edtnotel = findViewById(R.id.edtnotel);
        edtpsw = findViewById(R.id.edtpsw);

        btnklog = findViewById(R.id.btnklog);
        btnregis = findViewById(R.id.btnregis);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        btnklog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnregis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strusname = edtusname.getText().toString();
                String strnamaleng = edtnamaleng.getText().toString();
                String stremail = edtemail.getText().toString();
                String strnotel = edtnotel.getText().toString();
                String strpsw = edtpsw.getText().toString();

                if (strusname.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Username Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                } else if (strnamaleng.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Nama Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                } else if (stremail.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Email Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                } else if (strnotel.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "No Telepon Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                } else if (strpsw.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Password Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                } else {
                    registrasi(strusname, strnamaleng, stremail, strnotel, strpsw);
                }
            }
        });

        getSupportActionBar().hide();
    }


        public void registrasi(String userName, String namaLengkap, String email, String noTelp, String password){

            HashMap<String, String> params = new HashMap<String, String>();
            params.put("userName", userName);
            params.put("namaLengkap", namaLengkap);
            params.put("email", email);
            params.put("noTelp", noTelp);
            params.put("role", "2");
            params.put("password", password);

            pDialog.setMessage("Mohon Tunggu...");
            showDialog();

            JsonObjectRequest req = new JsonObjectRequest(BaseURL.registrasi, new JSONObject(params),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            hideDialog();
                            try {
                                String strmsg = response.getString("msg");
                                boolean status = response.getBoolean("error");

                                if (status == false){
                                    Toast.makeText(getApplicationContext(), strmsg, Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(RegisActivity.this, LoginActivity.class);
                                    startActivity(i);
                                    finish();
                                }else {
                                    Toast.makeText(getApplicationContext(), strmsg, Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.e("Error: ", error.getMessage());
                    hideDialog();
                }
            });
            mRequestQueue.add(req);

        }

        public void openLoginActivity(){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        private void showDialog(){
            if (!pDialog.isShowing()){
                pDialog.show();
            }
        }
        private void hideDialog(){
            if (pDialog.isShowing()){
                pDialog.dismiss();
            }
        }
}