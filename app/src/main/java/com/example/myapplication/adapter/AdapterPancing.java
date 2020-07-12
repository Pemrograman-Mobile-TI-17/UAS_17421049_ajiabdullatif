package com.example.myapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.ModelPancing;
import com.example.myapplication.server.BaseURL;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterPancing extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<ModelPancing> item;

    public AdapterPancing(Activity activity, List<ModelPancing> item) {
        this.activity = activity;
        this.item = item;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int position) {
        return item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.content_pancing, null);


        TextView merekPancing       = (TextView) convertView.findViewById(R.id.txtMerekPancing);
        TextView tipePancing         = (TextView) convertView.findViewById(R.id.txtTipePancing);
        TextView ukuranPancing       = (TextView) convertView.findViewById(R.id.txtUkuranPancing);
        TextView warnaPancing        = (TextView) convertView.findViewById(R.id.txtWarnaPancing);
        TextView hargaPancing        = (TextView) convertView.findViewById(R.id.txtHargaPancing);
        ImageView gambarPancing         = (ImageView) convertView.findViewById(R.id.gambarPancing);

        merekPancing.setText(item.get(position).getMerekPancing());
        tipePancing.setText(item.get(position).getTipePancing());
        ukuranPancing.setText(item.get(position).getUkuranPancing());
        warnaPancing.setText("Rp." + item.get(position).getWarnaPancing());
        hargaPancing.setText(item.get(position).getHargaPancing());
        Picasso.get().load(BaseURL.baseUrl + "gambar/" + item.get(position).getGambar())
                .resize(40, 40)
                .centerCrop()
                .into(gambarPancing);
        return convertView;
    }

}