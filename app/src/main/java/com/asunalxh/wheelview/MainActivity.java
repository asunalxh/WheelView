package com.asunalxh.wheelview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CourseSelectorHelper courseSelectorHelper=new CourseSelectorHelper(MainActivity.this);
                BaseDialog.Builder builder=new BaseDialog.Builder(MainActivity.this,courseSelectorHelper.getView());
                builder.setContentHeight(600);
                builder.setContentWidth(750);
                builder.setRightButtonOnClickListener(new BaseDialog.Builder.onButtonClickListener() {
                    @Override
                    public void onClick() {
                        Toast.makeText(getBaseContext(),courseSelectorHelper.getDayOfWeek()+" "+courseSelectorHelper.getStartCourse()+" "+courseSelectorHelper.getEndCourse(),Toast.LENGTH_SHORT).show();
                    }
                });
                BaseDialog dialog=builder.create();
                dialog.show();
            }
        });

    }
}
