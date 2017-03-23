package com.nightssky.baidutime;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener{

    private TextView value;
    private TextView add;
    private TextView delete;
    private float count;
    private int time;
    private Timer timer;
    private boolean start;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initTimer();

    }

    private void initTimer() {
        timer = new Timer();
        timer.schedule(task,0, 100);
    }

    private void initView() {
        value = (TextView) findViewById(R.id.value);
        add = (TextView) findViewById(R.id.add);
        delete = (TextView) findViewById(R.id.delete);
        add.setOnTouchListener(this);
        delete.setOnTouchListener(this);
    }
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            //更新界面
            count +=0.01f*msg.arg1*type;
            count = (float)(Math.round(count*100))/100;
            value.setText(count+"");

        }
    };
    TimerTask task= new TimerTask() {
        @Override
        public void run() {
            if (start) {
                time++;
                Message message = Message.obtain();
                message.arg1 = time/5;//加速效果为time/5
                handler.sendMessage(message);

            }
        }
    };
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if (view.getId() == R.id.add) {
            type=1;
        } else {
            type=-1;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                start = true;//开始计时

                break;
            case MotionEvent.ACTION_UP:
                start = false;//结束计时
                time=0;//重置时间
                break;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
