package com.example.smart_museum;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class menu extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button start;
        Button exit;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        start = (Button) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent goto_smart = new Intent(menu.this,map.class);
                startActivity(goto_smart);
            }
        });
        //Intent를 사용해 박물관 둘러보기를 시작함.
        exit = (Button) findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                finishAffinity();
                System.runFinalization();
                System.exit(0);
            }
        });
        //종료버튼.레이아웃이 여러개이므로 루트 레이아웃까지 끄게 됨.
    }
}