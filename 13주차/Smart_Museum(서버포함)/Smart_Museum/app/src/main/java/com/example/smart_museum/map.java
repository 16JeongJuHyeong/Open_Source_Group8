package com.example.smart_museum;
import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class map extends AppCompatActivity {
    Timer timer;
    TimerTask timerTask;
    WifiManager wifiManager;
    private List newRssi = new ArrayList(4);
    boolean isPermitted = false;
    final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private List<ScanResult> scanResultList;
    int count = 0;
    private String html = "";
    private Handler mHandler;
    private SocketChannel socket;
    private Selector selector;
    private sendDataThread SendData;
    ImageView iv;
    String data;
    private String ip = "서버 ip";            // IP 번호  '집 ip로 바꾸기'
    private int port = 9999;                          // port 번호
    int pr,tp;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (action.equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                getwifiInfo(); }
        }
    };

    private void getwifiInfo() {
        scanResultList = wifiManager.getScanResults();
        for (int i = 0; i < scanResultList.size(); i++) {
            ScanResult result = scanResultList.get(i);

            if (result.SSID.equals("radioactivity") || result.SSID.equals("U+NetA990") || result.SSID.equals("olleh_WiFi_075B")) {
                newRssi.add(result.level * (-1));
                if(result.SSID.equals("radioactivity")) {
                    pr = 1;
                    if(count == 2) {
                        Collections.swap(newRssi, 0, count);
                        Collections.swap(newRssi, count-1, count);
                    } else if(tp > pr) Collections.swap(newRssi, 0, count);
                    tp = pr;
                } else if(result.SSID.equals("U+NetA990"))  {
                    pr = 2;
                    if(tp > pr) Collections.swap(newRssi, count-1, count);
                    if(count == 2 && tp == 1) Collections.swap(newRssi, count-1, count);
                    tp = pr;
                }
                else  {
                    pr = 3;
                    tp = pr;
                }
                count++;
                if (count == 3) {
                    sendData(String.valueOf(newRssi));
                    count = 0;
                    newRssi.clear();
                    break;
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        requestRuntimePermission();
        connect();
        wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        iv = (ImageView) findViewById(R.id.cs);


        Handler hand = new Handler();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                if (isPermitted) {
                    // wifi 스캔 시작
                    // wifi가 활성화되어있는지 확인 후 꺼져 있으면 켠다
                    if (wifiManager.getWifiState() != WifiManager.WIFI_STATE_ENABLING) {
                        wifiManager.setWifiEnabled(true);
                    }
                    wifiManager.startScan();
                    try {
                        ByteBuffer buffer = ByteBuffer.allocate(48);
                        int byteCount = socket.read(buffer);
                        buffer.flip();
                        Charset charset = Charset.forName("UTF-8");
                        data = charset.decode(buffer).toString();
                        Log.w("%d", data);
                        if (data.equals("C")) {
                            Intent goto_menu = new Intent(map.this, work1.class);
                            startActivity(goto_menu);
                        } else {
                            iv.setImageResource(R.drawable.cursur);
                            if (data.equals("A")) {
                                iv.setX(32);
                                iv.setY(150);
                                iv.invalidate();
                            } else if (data.equals("B")) {
                                iv.setX(385);
                                iv.setY(150);
                                iv.invalidate();
                            } else if (data.equals("D")) {
                                iv.setX(760);
                                iv.setY(150);
                                iv.invalidate();
                            } else if (data.equals("E")) {
                                iv.setX(32);
                                iv.setY(510);
                                iv.invalidate();
                            } else if (data.equals("F")) {
                                iv.setX(385);
                                iv.setY(510);
                                iv.invalidate();
                            } else if (data.equals("G")) {
                                iv.setX(580);
                                iv.setY(510);
                                iv.invalidate();
                            } else if (data.equals("H")) {
                                iv.setX(760);
                                iv.setY(510);
                                iv.invalidate();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Location access 권한이 없습니다..", Toast.LENGTH_LONG).show();
                }
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 0, 1000);
    }


    protected void connect() {
        mHandler = new Handler();
        Log.w("connect", "연결 하는중");
        // 받아오는거
        Thread checkUpdate = new Thread() {
            public void run() {
                // 서버 접속
                try {
                   setSocket(ip, port);
                    Log.w("서버 접속됨", "서버 접속됨");
                } catch (IOException e1) {
                    Log.w("서버접속못함", "서버접속못함");
                    e1.printStackTrace();
                }
                Log.w("edit 넘어가야 할 값 : ", "안드로이드에서 서버로 연결요청");
                sendData("안드로이드에서 서버로 연결요청");
                Log.w("버퍼", "버퍼생성 잘됨");
            }
        };
        // 소켓 접속 시도, 버퍼생성
        checkUpdate.start();
    }
    private void setSocket(String ip, int port) throws IOException {
        selector = Selector.open();
        socket = SocketChannel.open(new InetSocketAddress(ip, port));
        socket.configureBlocking(false);
        socket.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ | SelectionKey.OP_WRITE);
    }

    public void sendData(String data) {
        SendData = new sendDataThread(socket, data);
        SendData.start();
    }

    public class sendDataThread extends Thread {
        private SocketChannel sdt_hSocketChannel;
        private String data;

        public sendDataThread(SocketChannel sc, String d) {
            sdt_hSocketChannel = sc;
            data = d;
        }

        public void run() {
            try { // 데이터 전송.
                sdt_hSocketChannel.write(ByteBuffer.wrap(data.getBytes()));
            } catch (Exception e1) {

            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // wifi scan 결과가 나왔을 때 전송되는 broadcast를 받기 위해
        // IntentFilter 객체를 생성하고 이를 이용하여 BroadcastReceiver 객체를 등록한다
        IntentFilter filter = new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        registerReceiver(mReceiver, filter);

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }


    private void requestRuntimePermission() {
        //*******************************************************************
        // Runtime permission check
        //*******************************************************************
        if (ContextCompat.checkSelfPermission(map.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(map.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(map.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
        } else {
            // ACCESS_FINE_LOCATION 권한이 있는 것
            isPermitted = true;
        }
        //*********************************************************************
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {



                    // ACCESS_FINE_LOCATION 권한을 얻음
                    isPermitted = true;

                } else {



                    // 권한을 얻지 못 하였으므로 location 요청 작업을 수행할 수 없다
                    // 적절히 대처한다
                    isPermitted = false;

                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}