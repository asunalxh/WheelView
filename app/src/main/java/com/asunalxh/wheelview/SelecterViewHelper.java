package com.asunalxh.wheelview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import java.util.List;

public class SelecterViewHelper {

    private View view;
    private WheelView leftView;
    private WheelView rightView;
    private WheelView centerView;


    public SelecterViewHelper(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.item_select,null);
        leftView=view.findViewById(R.id.leftView);
        centerView=view.findViewById(R.id.centerView);
        rightView=view.findViewById(R.id.rightView);

        centerView.setOnSelectChangeListener(new WheelView.onSelectChangeListener() {
            @Override
            public void onChange(int index) {
                Log.d("test",String.valueOf(index));
                rightView.setMinSelect(index);
            }
        });
    }

    public void setList(List<String> left,List<String> center,List<String> right){
        leftView.setList(left);
        centerView.setList(center);
        rightView.setList(right);
    }

    public View getView(){return view;}
}
