package com.example.myapplication.pembeli;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.server.BaseURL;
import com.squareup.picasso.Picasso;

public class DetailPembeli extends AppCompatActivity {

    EditText edtKodePancing, edtMerekPancing, edtTipePancing, edtUkuranPancing, edtWarnaPancing, edtHargaPancing;
    ImageView imgGambarPancing;

    String strKodePancing, strMerekPancing, strTipePancing, strUkuranPancing, strWarnaPancing, strHargaPancing, strGambar, _id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pembeli);

        edtKodePancing = (EditText) findViewById(R.id.edtKodePancing);
        edtMerekPancing = (EditText) findViewById(R.id.edtMerekPancing);
        edtTipePancing = (EditText) findViewById(R.id.edtTipePancing);
        edtUkuranPancing= (EditText) findViewById(R.id.edtUkuranPancing);
        edtWarnaPancing = (EditText) findViewById(R.id.edtWarnaPancing);
        edtHargaPancing= (EditText) findViewById(R.id.edtHargaPancing);

        imgGambarPancing= (ImageView) findViewById(R.id.gambar);

        Intent i = getIntent();
        strKodePancing = i.getStringExtra("KodePancing");
        strMerekPancing = i.getStringExtra("MerekPancing");
        strTipePancing = i.getStringExtra("pengarang");
        strUkuranPancing = i.getStringExtra("penerbit");
        strWarnaPancing = i.getStringExtra("tahunTerbit");
        strHargaPancing = i.getStringExtra("hargaBuku");
        strGambar = i.getStringExtra("gambar");
        _id = i.getStringExtra("_id");

        edtKodePancing.setText(strKodePancing);
        edtMerekPancing.setText(strMerekPancing);
        edtTipePancing.setText(strTipePancing);
        edtUkuranPancing.setText(strUkuranPancing);
        edtWarnaPancing.setText(strWarnaPancing);
        edtHargaPancing.setText(strHargaPancing);
        Picasso.get().load(BaseURL.baseUrl + "gambar/" + strGambar)
                .into(imgGambarPancing);
    }
}
