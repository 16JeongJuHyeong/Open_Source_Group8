package com.example.smart_museum;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class work1 extends AppCompatActivity {
    private TextToSpeech tts;   //스피커 기능
    ImageButton speaker;   //스피커 버튼
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work1);
        speaker = (ImageButton)findViewById(R.id.speaker);
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            //tts(TextToSpeech)로 작품의 설명을 읽을 수 있도록 설정
            @Override
            public void onInit(int status) {
                    tts.setLanguage(Locale.KOREAN);
                    //한국어 선택
            }
        });
        speaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.speak("빗살무늬토기.그릇 표면을 빗살같이 길게 이어진 무늬새기개로 누르거나 그어서 점,금,동그라미 등의 기하학무늬를 나타낸 신석기시대의 대표적인 토기.‘즐목문토기’라고도 한다. 또한 겉면에 무늬를 새기고 있기 때문에 ‘유문토기’라고도 하며, 무늬 모양의 특징을 따서 ‘어골문토기’ 또는 ‘기하학문토기’라고도 부른다.",TextToSpeech.QUEUE_FLUSH,null);
                //작품의 설명을 저장 후 스피커를 누르면 읽음.

            }
        });
    }
    }



