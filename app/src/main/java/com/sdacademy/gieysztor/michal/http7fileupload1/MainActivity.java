package com.sdacademy.gieysztor.michal.http7fileupload1;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.*;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


public class MainActivity extends AppCompatActivity {

    Button mUploadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUploadButton = (Button) findViewById(R.id.upload_button);

        mUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermissions()) {
                    uploadFile();
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        uploadFile();
    }

    private void uploadFile() {
        File file = new File("/storage/emulated/0/DCIM/Camera/a20170227_184321.jpg");
        MediaType mediaType = MediaType.parse("application/octet-stream");

        JSONObject jsonObject = new JSONObject();

        Request.Builder builder = new Request.Builder();
        builder.url("https://content.dropboxapi.com/2/files/upload");
        builder.addHeader("Authorization", "Bearer dDMgJ7xRgDAAAAAAAAAAFlo5Xr4cFa7E8x1P-sz3C7H6acYCupXe2d8TSWobo3g9");
        builder.addHeader("Dropbox-API-Arg", "{\"path\":\"/pliki/keyboard.jpg\"}");
//        builder.addHeader("User-Agent", "api-explorer-client");
        builder.post(RequestBody.create(mediaType, file));
        Request request = builder.build();
        OkHttpClient client = new OkHttpClient();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("TEST", "fail", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("TEST", "onResponse " + response.body().string());
            }
        });
    }

    private boolean checkPermissions() {
        int status = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (status == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this, permissions, 0);
            return false;
        }
    }
}

//POST /2/files/upload
//        Host: https://content.dropboxapi.com
//        User-Agent: api-explorer-client


//        Dropbox-API-Arg: {"path":"/pliki/keyboard.jpg"}
//        Content-Length: 705
