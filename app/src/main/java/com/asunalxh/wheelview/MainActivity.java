package com.asunalxh.wheelview;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<String>list=new ArrayList<>();
        for(int i=1;i<=3;i++)
            list.add(String.valueOf(i));
        final WheelView wheelView=findViewById(R.id.wheelview);
        wheelView.setList(list);
        wheelView.setShowCount(7);
        wheelView.setSelectLineColor(Color.BLACK);
        wheelView.setMinSelect(2);
        wheelView.setTextSize(50);
        wheelView.setIsRelcyclable(true);

        Button button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(),wheelView.getSelectString(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
