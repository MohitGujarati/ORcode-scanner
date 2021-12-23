package com.example.qrcodescanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import eu.livotov.labs.android.camview.ScannerLiveView;
import eu.livotov.labs.android.camview.scanner.decoder.zxing.ZXDecoder;

import static android.Manifest.permission.VIBRATE;
import static android.Manifest.permission_group.CAMERA;

public class ScaneQrCodeAct extends AppCompatActivity {

    TextView scannerTv;
    private ScannerLiveView scannerLiveView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scane_qr_code);

        scannerTv=findViewById(R.id.scannerTv);
        scannerLiveView=findViewById(R.id.canView);

        if (checkPermission()){
            Toast.makeText(this, "Cool", Toast.LENGTH_SHORT).show();
        }else{
            reqPer();
        }

        scannerLiveView.setScannerViewEventListener(new ScannerLiveView.ScannerViewEventListener() {
            @Override
            public void onScannerStarted(ScannerLiveView scanner) {
                Toast.makeText(ScaneQrCodeAct.this, "stared", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onScannerStopped(ScannerLiveView scanner) {
                Toast.makeText(ScaneQrCodeAct.this, "stopped", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onScannerError(Throwable err) {


            }

            @Override
            public void onCodeScanned(String data) {

                scannerTv.setText(data);

            }
        });



    }

    private  boolean checkPermission(){
        int camera_permission= ContextCompat.checkSelfPermission(getApplicationContext(),CAMERA);
        int vibrate_permission= ContextCompat.checkSelfPermission(getApplicationContext(),VIBRATE);
        return camera_permission== PackageManager.PERMISSION_GRANTED && vibrate_permission==PackageManager.PERMISSION_GRANTED;
    }

    private void reqPer(){
        int Permission_code=200;
        ActivityCompat.requestPermissions(this,new String[]{CAMERA,VIBRATE},Permission_code);
    }

    @Override
    protected void onPause() {
        scannerLiveView.stopScanner();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ZXDecoder decoder=new ZXDecoder();
        decoder.setScanAreaPercent(0.8);
        scannerLiveView.setDecoder(decoder);
        scannerLiveView.startScanner();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length>0){
            boolean cameraAccepted=grantResults[0]==PackageManager.PERMISSION_GRANTED;
            boolean vibrationAcc=grantResults[1]==PackageManager.PERMISSION_GRANTED;
            if (cameraAccepted && vibrationAcc){
                Toast.makeText(this, "Granted", Toast.LENGTH_SHORT).show();
            }else
                Toast.makeText(this, "NOT NOT Granted", Toast.LENGTH_SHORT).show();

        }
    }
}