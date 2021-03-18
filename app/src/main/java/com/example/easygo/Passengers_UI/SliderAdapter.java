package com.example.easygo.Passengers_UI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.easygo.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.ViewHolder> {
    List<Integer> imageList;
    SliderAdapter(List<Integer> list){
        this.imageList=list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.firstSlide.setImageResource(imageList.get(position));

    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    class ViewHolder extends SliderViewAdapter.ViewHolder{
        ImageView firstSlide;

        public ViewHolder(View itemView) {
            super(itemView);
            firstSlide=itemView.findViewById(R.id.firstSlide);
        }
    }

}

