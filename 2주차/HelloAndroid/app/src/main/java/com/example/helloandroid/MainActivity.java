package com.example.helloandroid;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setContenView는 원하는 인자를 넘겨주면 이를 뷰(View)로 생성한다.
        //이는 화면을 생성해준다고 생각하면 된다.
        //R은 res파일,layout은 res 내 파일,그 뒤는 원하는 파일의 이름을 지정한다.
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_launcher);
    }
}