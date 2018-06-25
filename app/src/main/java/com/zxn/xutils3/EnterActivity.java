package com.zxn.xutils3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.x;

@ContentView(R.layout.activity_enter)
public class EnterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_enter);
        x.view().inject(this);
    }

    @Event(type = View.OnClickListener.class, value = {R.id.btn1_enter, R.id.btn2_enter, R.id.btn3_enter, R.id.btn4_enter})
    private void testEnter(View view) {
        switch (view.getId()) {
            case R.id.btn1_enter:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.btn2_enter:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.btn3_enter:
                startActivity(new Intent(this,ImageActivity.class));
                break;
            case R.id.btn4_enter:
                startActivity(new Intent(this,DbActivity.class));
                break;
        }
    }
}
