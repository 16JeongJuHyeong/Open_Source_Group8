package com.example.four_buttons;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
{
    Button button1;
    Button button2;
    Button button3;
    Button button4;
//    버튼이 작업을 수행할 수 있게 Button이라는 클래스로 선언한다.

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_launcher);
        button1 = (Button) findViewById(R.id.naver);
        //button1(네이버 홈페이지 열기)가 선언됐지만 정의되진 않았다.이를 위해 findviewById를 사용하는데
        //이는 id로부터 찾는다는 뜻으로,R.id.naver부분은 차례대로 보자면
        //R은 res파일,id.이름 은 res파일 내부 id 중 원하는 위젯의 id이다.
        //위젯의 값이 나오면 이를 Button이라는 클래스 형으로 바꿔준다.
        button1.setOnClickListener(new View.OnClickListener()
        {
            //버튼이 클릭되었을 때를 정의한다.
            @Override
            public void onClick(View v)
            {
                Intent nate = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.nate.com"));
                startActivity(nate);
                //Intetn는 일종의 메시지 객체로,다른 앱 구성요소로 작업을 요청하게 한다.
                //홈페이지를 키는 변수를 선언 후 이를 startActivity로 실행한다.

            }
        });
        button2 = (Button) findViewById(R.id.call_911);
        button2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent call = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:911"));
                startActivity(call);
                //button1의 기능과 같다.
            }
        });
        button3 = (Button) findViewById(R.id.gallery);
        button3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent gallery = new Intent(Intent.ACTION_VIEW, Uri.parse("content://media/internal/images/media"));
                startActivity(gallery);
            }
        });
        button4 = (Button) findViewById(R.id.exit);
        button4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
                // 앱 전체를 종료한다.
            }
        });

    }
}
