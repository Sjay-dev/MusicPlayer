package com.example.musicplayer;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.zip.Inflater;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    Context context;

    ArrayList<Music> musicArrayList = new ArrayList<>();

    public RecyclerViewAdapter(Context context, ArrayList<Music> musicArrayList) {
        this.context = context;
        this.musicArrayList = musicArrayList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.music_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        holder.title.setText(musicArrayList.get(position).getTitle());
        holder.desc.setText(musicArrayList.get(position).getData());

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , Music_Player.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("ARRAYLIST_KEY" , musicArrayList.get(position));
                bundle.putSerializable("ARRAYLIST_KEY2" , musicArrayList);
                bundle.putInt("POSITION_KEY" , position);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return musicArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, desc;

        RelativeLayout relativeLayout;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.txtName);
            desc = itemView.findViewById(R.id.txtDesc);
            relativeLayout = itemView.findViewById(R.id.rlSong);



        }
    }
}
