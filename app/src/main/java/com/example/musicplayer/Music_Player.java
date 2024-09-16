package com.example.musicplayer;

import static java.lang.Thread.sleep;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.session.MediaSession;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


import java.util.ArrayList;


public class Music_Player extends AppCompatActivity {
    ImageButton play, previous, next;
    ImageView btnBack;

    TextView artist_Name, songTitle, currentTime, totalTime;
    Music music;

    SeekBar seekBar;
    static MediaPlayer player;



    Thread updateSeekBar;

    ArrayList<Music> musicArrayList = new ArrayList<>();


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_player);


        play = findViewById(R.id.btnPlay);
        previous = findViewById(R.id.btnPrevious);
        next = findViewById(R.id.btnNext);

        btnBack = findViewById(R.id.back_button);
        artist_Name = findViewById(R.id.txtArtistName);
        songTitle = findViewById(R.id.txtMusticTitle);
        seekBar = findViewById(R.id.seekbar);
        currentTime = findViewById(R.id.txtCurrentTime);
        totalTime = findViewById(R.id.txtTotalTime);

        music = (Music) getIntent().getSerializableExtra("ARRAYLIST_KEY");
        musicArrayList = (ArrayList<Music>) getIntent().getSerializableExtra("ARRAYLIST_KEY2");
        final int[] position = {getIntent().getExtras().getInt("POSITION_KEY")};


        Uri uri = Uri.parse(music.getData());


        if (player != null) {
            player.release();
        }
//        AutoPlays when music is selected

        player = MediaPlayer.create(Music_Player.this, uri);
        player.start();

        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer player) {
                next.performClick();
            }
        });


//        BackPress function
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Setting TextViews
        artist_Name.setText(music.getArtist());
        songTitle.setText(music.getTitle());

        //Previous Button
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player.isPlaying()) {
                    player.stop();
                    player.release();

                    if (position[0] - 1 < 0) {
                        position[0] = musicArrayList.size() - 1;
                    } else {
                        position[0] = position[0] - 1;
                    }


                    Uri previousUri = Uri.parse(musicArrayList.get(position[0]).getData());
                    songTitle.setText(musicArrayList.get(position[0]).getTitle());
                    artist_Name.setText(musicArrayList.get(position[0]).getArtist());


                    player = MediaPlayer.create(Music_Player.this, previousUri);
                    player.start();


                } else {
                    Toast.makeText(Music_Player.this, "Play a track", Toast.LENGTH_SHORT);
                }


            }
        });

        //Play Button
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!player.isPlaying()) {
                    play.setImageDrawable(getDrawable(R.drawable.pause_button2));
                    player.start();


                }

                //Pause Button
                else {
                    play.setImageDrawable(getDrawable(R.drawable.play_button));
                    player.pause();

                }

            }

        });

        //Play Next Track
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                player.stop();
                player.release();

                if (position[0] + 1 >= musicArrayList.size()) {
                    position[0] = 0;
                } else {
                    position[0] = position[0] + 1;
                }

                Uri previousUri = Uri.parse(musicArrayList.get(position[0]).getData());
                songTitle.setText(musicArrayList.get(position[0]).getTitle());
                artist_Name.setText(musicArrayList.get(position[0]).getArtist());


                player = MediaPlayer.create(Music_Player.this, previousUri);
                player.start();


            }
        });


        //SeekBar code
        updateSeekBar = new Thread() {
            @Override
            public void run() {
                if (player.isPlaying()) {
                    int total_Duration = player.getDuration();
                    int current_Position = 0;

                    while (current_Position < total_Duration) {
                        try {

                            sleep(1000);
                            current_Position = player.getCurrentPosition();

                            seekBar.setProgress(current_Position);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }

                }


            }
        };
        seekBar.setMax(player.getDuration());
        updateSeekBar.start();


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                player.seekTo(seekBar.getProgress());
            }
        });

        //Timer for player
        player.getCurrentPosition();
        totalTime.setText(createTime(player.getDuration()));
        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                currentTime.setText(createTime(player.getCurrentPosition()));
                handler.postDelayed(this, 1000);
            }
        }, 1000);


    }

    String createTime(int duration) {
        String time = "";
        int min = (duration / 1000) / 60;
        int sec = (duration / 1000) % 60;


        time = min + ":";
        if (sec < 10) {
            time += "0";
        }
        time += sec;
        return time;

    }


}





