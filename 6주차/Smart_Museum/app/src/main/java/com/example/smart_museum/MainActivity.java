package com.example.smart_museum;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Handler hand = new Handler();
        hand.postDelayed( new Runnable()
        {
            @Override
            public void run(){
                Intent goto_menu = new Intent(MainActivity.this,menu.class);
                startActivity(goto_menu);
            }
        }, 2000);
        //시작 화면. 2초 후 메뉴 화면으로 넘어감.
    }
}