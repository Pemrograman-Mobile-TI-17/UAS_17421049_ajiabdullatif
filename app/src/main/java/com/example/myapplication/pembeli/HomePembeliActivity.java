package com.example.myapplication.pembeli;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.myapplication.R;
import com.example.myapplication.adapter.AdapterPancing;
import com.example.myapplication.model.ModelPancing;
import com.example.myapplication.server.BaseURL;
import com.example.myapplication.session.PrefSetting;
import com.example.myapplication.session.SessionManager;
import com.example.myapplication.users.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomePembeliActivity extends AppCompatActivity {

    ProgressDialog pDialog;

    AdapterPancing adapter;
    ListView list;

    ArrayList<ModelPancing> newsList = new ArrayList<ModelPancing>();
    private RequestQueue mRequestQueue;

    FloatingActionButton floatingExit;

    SessionManager session;
    SharedPreferences prefs;
    PrefSetting prefSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_pembeli);

        prefSetting = new PrefSetting(this);
        prefs = prefSetting.getSharePreference();

        session = new SessionManager(HomePembeliActivity.this);

        prefSetting.isLogin(session, prefs);

        getSupportActionBar().setTitle("Data Pancing");
        mRequestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        list = (ListView) findViewById(R.id.array_list);

        floatingExit = (FloatingActionButton) findViewById(R.id.exit);

        newsList.clear();
        adapter = new AdapterPancing(HomePembeliActivity.this, newsList);
        list.setAdapter(adapter);
        getAllPancing();

        floatingExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.setLogin(false);
                session.setSessid(0);
                Intent i = new Intent(HomePembeliActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void getAllPancing() {
        // Pass second argument as "null" for GET requests
        pDialog.setMessage("Loading");
        showDialog();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, BaseURL.dataPancing, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            boolean status = response.getBoolean("error");
                            if (status == false) {
                                Log.d("data buku = ", response.toString());
                                String data = response.getString("data");
                                JSONArray jsonArray = new JSONArray(data);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    final ModelPancing pancing = new ModelPancing();
                                    final String _id = jsonObject.getString("_id");
                                    final String merekPancing = jsonObject.getString("MerekPancing");
                                    final String kodePancing = jsonObject.getString("kodePancing");
                                    final String tipePancing = jsonObject.getString("TipePancing");
                                    final String ukuranPancing = jsonObject.getString("UkuranPancing");
                                    final String WarnaPancing = jsonObject.getString("WarnaPancing");
                                    final String HargaPancing = jsonObject.getString("HargaPancing");
                                    final String gambar = jsonObject.getString("gambar");
                                    pancing.setKodePancing(kodePancing);
                                    pancing.setMerekPancing(merekPancing);
                                    pancing.setTipePancing(tipePancing);
                                    pancing.setUkuranPancing(ukuranPancing);
                                    pancing.setWarnaPancing(WarnaPancing);
                                    pancing.setHargaPancing(HargaPancing);
                                    pancing.setGambar(gambar);
                                    pancing.set_id(_id);

                                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            // TODO Auto-generated method stub
                                            Intent a = new Intent(HomePembeliActivity.this, DetailPembeli.class);
                                            a.putExtra("KodePancing", newsList.get(position).getKodePancing());
                                            a.putExtra("_id", newsList.get(position).get_id());
                                            a.putExtra("MerekPancing", newsList.get(position).getMerekPancing());
                                            a.putExtra("TipePancing", newsList.get(position).getTipePancing());
                                            a.putExtra("UkuranPancing", newsList.get(position).getUkuranPancing());
                                            a.putExtra("WarnaPancing", newsList.get(position).getWarnaPancing());
                                            a.putExtra("HargaPancing", newsList.get(position).getHargaPancing());
                                            a.putExtra("gambar", newsList.get(position).getGambar());
                                            startActivity(a);
                                        }
                                    });
                                    newsList.add(pancing);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                hideDialog();
            }
        });

        /* Add your Requests to the RequestQueue to execute */
        mRequestQueue.add(req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
