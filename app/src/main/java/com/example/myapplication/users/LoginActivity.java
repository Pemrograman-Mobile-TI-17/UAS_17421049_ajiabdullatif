package com.example.myapplication.users;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;
import com.example.myapplication.admin.HomeAdminActivity;
import com.example.myapplication.pembeli.HomePembeliActivity;
import com.example.myapplication.server.BaseURL;
import com.example.myapplication.session.PrefSetting;
import com.example.myapplication.session.SessionManager;
import com.ornach.nobobutton.NoboButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private Button btnregis;
    private NoboButton btnlogin;
    private EditText edtusname, edtpsw;
    ProgressDialog pDialog;

    private RequestQueue mRequestQueue;

    SessionManager session;
    SharedPreferences prefs;
    PrefSetting prefSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mRequestQueue = Volley.newRequestQueue(this);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        btnlogin = findViewById(R.id.btnlogin);
        btnregis = findViewById(R.id.edtbutton);

        edtusname = findViewById(R.id.edtUsername);
        edtpsw = findViewById(R.id.edtPassword);

        prefSetting = new PrefSetting(this);
        prefs = prefSetting.getSharePreference();

        session = new SessionManager(this);

        prefSetting.checkLogin(session, prefs);

        btnregis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegistrasiActivity();
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strusname = edtusname.getText().toString();
                String strpsw = edtpsw.getText().toString();

                if (strusname.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Username Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                }else if (strpsw.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Password Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                }else{
                    login(strusname, strpsw);
                }
            }
        });

        getSupportActionBar().hide();
    }
    public void openRegistrasiActivity(){
        Intent intent = new Intent(this, RegisActivity.class);
        startActivity(intent);
        finish();
    }

    public void login(String userName, String password) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("userName", userName);
        params.put("password", password);

        pDialog.setMessage("Mohon Tunggu...");
        showDialog();

        JsonObjectRequest req = new JsonObjectRequest(BaseURL.login, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            String strmsg = response.getString("msg");
                            boolean status = response.getBoolean("error");

                            if (status == false) {
                                Toast.makeText(getApplicationContext(), strmsg, Toast.LENGTH_LONG).show();
                                String data = response.getString("data");
                                JSONObject jsonObject = new JSONObject(data);
                                String role = jsonObject.getString("role");
                                String _id = jsonObject.getString("_id");
                                String userName = jsonObject.getString("userName");
                                String namaLengkap = jsonObject.getString("namaLengkap");
                                String email = jsonObject.getString("email");
                                String noTelp = jsonObject.getString("noTelp");
                                session.setLogin(true);
                                prefSetting.storeRegIdSharedPreferences(LoginActivity.this, _id, userName, namaLengkap, email, noTelp, role, prefs);
                                if (role.equals("1")) {
                                    Intent i = new Intent(LoginActivity.this, HomeAdminActivity.class);
                                    startActivity(i);
                                    finish();
                                }else {
                                    Intent i = new Intent(LoginActivity.this, HomePembeliActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                            } else {
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
