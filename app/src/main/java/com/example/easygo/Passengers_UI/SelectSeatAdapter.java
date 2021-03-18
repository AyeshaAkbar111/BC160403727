package com.example.easygo.Passengers_UI;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.easygo.R;
import com.example.easygo.SeatModel;

import java.util.List;

public class SelectSeatAdapter extends BaseAdapter {
    private List<SeatModel> ThumbId;
    private Context context;

    public SelectSeatAdapter(List<SeatModel> thumbId, Context context) {
        ThumbId = thumbId;
        this.context = context;
    }

    @Override
    public int getCount() {
      //  return ThumbId.size();
        return ThumbId.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return ThumbId.get(position).getSeatImg();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

       ImageView imageView=(ImageView) convertView;
       if (imageView==null){
           imageView=new ImageView(context);
           imageView.setLayoutParams(new ViewGroup.LayoutParams(120,100));
           imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
       }
       imageView.setImageResource(ThumbId.get(position).getSeatImg());
        return imageView;
    }
}
