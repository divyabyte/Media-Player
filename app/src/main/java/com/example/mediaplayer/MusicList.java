package com.example.mediaplayer;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.lang.*;

public class MusicList extends AppCompatActivity {

    RecyclerView audioListView;
    MusicListAdapter musicListAdapter;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_list);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
                return;
            }
        }
        getSongs();
    }

   public void getSongs(){
        context = MusicList.this;
        audioListView = findViewById(R.id.music_list);

        List allAudioFiles = getAllAudioFromDevice(context);

        musicListAdapter = new MusicListAdapter(context,allAudioFiles);
        audioListView.setLayoutManager(new LinearLayoutManager(context));
        audioListView.setAdapter(musicListAdapter);
    }

    public List getAllAudioFromDevice(final Context context) {

        final List tempList = new ArrayList<>();

        Uri uri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Audio.AudioColumns.DATA, MediaStore.Audio.AudioColumns.ALBUM, MediaStore.Audio.ArtistColumns.ARTIST,};
        Cursor c = context.getContentResolver().query(uri,
                projection,
                null,
                null,
                null);


        if (c != null) {
            while (c.moveToNext()) {

                Model model = new Model();

                String path = c.getString(0);
                String album = c.getString(1);
                String artist = c.getString(2);

                String name = path.substring(path.lastIndexOf("/") + 1);

                model.setaName(name);
                model.setaAlbum(album);
                model.setaArtist(artist);
                model.setaPath(path);

                tempList.add(model);
            }
            c.close();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"cursor is null",Toast.LENGTH_SHORT).show();
        }
        return tempList;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getSongs();
                    Toast.makeText(getApplicationContext(),"Permission granted",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(),"Permission denied",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}