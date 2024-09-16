package com.example.musicplayer;


import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    ArrayList<Music> musicArrayList = new ArrayList<>();
    RecyclerView recyclerView ;
    RecyclerViewAdapter adapter = new RecyclerViewAdapter(this , musicArrayList);



    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    recyclerView = findViewById(R.id.rlMusic);

CheckPermisson();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getMusicFiles() {
        ContentResolver contentResolver = this.getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        Cursor cursor = contentResolver.query(songUri ,null , null , null);

        if(cursor != null && cursor.moveToFirst()){
            int songid = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
                    int songTitle = cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME);
                            int songArtist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
                                    int songData = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
                                            int date = cursor.getColumnIndex(MediaStore.Audio.Media.DATE_MODIFIED);
                                                    int album = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);

          while (cursor.moveToNext()){

              Long currentId   = cursor.getLong(songid);
              String title     =  cursor.getString(songTitle);
              String artist    = cursor.getString(songArtist);
              String data      = cursor.getString(songData);
              Long Date        = cursor.getLong(date) ;
              Long Album       = cursor.getLong(album);

              if (title.endsWith(".mp3")){
                  Music music = new Music(currentId , title , artist , data , Date , Album);
                  musicArrayList.add(music);
              }


          }
        }
        adapter.notifyDataSetChanged();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void CheckPermisson() {
        if(ActivityCompat.checkSelfPermission(this , Manifest.permission.READ_MEDIA_AUDIO)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this , new String[]{Manifest.permission.READ_MEDIA_AUDIO}
                    , 100);

        }
        else {

            getMusicFiles();
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 100 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this , "Permission Granted" , Toast.LENGTH_SHORT);

        }
        else{
            Toast.makeText(this, "Chai Why you no gree?", Toast.LENGTH_SHORT).show();
        }
    }
}









