package com.asunalxh.wheelview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<String>list=new ArrayList<>();
        for(int i=1;i<=12;i++)
            list.add(String.valueOf(i));

        SelecterViewHelper viewHelper=new SelecterViewHelper(this);
        viewHelper.setList(list,list,list);
        LinearLayout layout=findViewById(R.id.content);
        layout.addView(viewHelper.getView());
    }
}
