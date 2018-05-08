package com.danaeldridge.recyclerviewclickplayground;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.danaeldridge.recyclerviewclickplayground.data.DBController;
import com.danaeldridge.recyclerviewclickplayground.data.SongContract;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private RecyclerView.LayoutManager layoutManager;
    private List<String> listTitle = new ArrayList<>();
    private List<String> listArtist = new ArrayList<>();
    private List<Song> songs = new ArrayList<>();

    DBController dbController = new DBController(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //createDatabase();
        readDatabase();

        //setupSongArray();
        setupListByTitle();



        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager((layoutManager));
        RecyclerAdapter adapter = new RecyclerAdapter(listTitle, listArtist, this);
        recyclerView.setAdapter(adapter);
    }




    private void setupListByTitle() {

        for (Song s:songs) {
            listTitle.add(s.getTitle());
            listArtist.add(s.getArtist());
        }
    }

    private void setupSongArray() {
        InputStream is = getResources().openRawResource(R.raw.song_list);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );

        Scanner scan = new Scanner(reader);
        scan.useDelimiter(";");

        while (scan.hasNext() ) {
            //Read the data
            Song song = new Song();
            song.setTitle(scan.next());
            song.setArtist(scan.next());

            songs.add(song);
        }
        scan.close();

    }

    private void createDatabase() {
        SQLiteDatabase db = dbController.getWritableDatabase();

        //set up raw csv for reading
        InputStream is = getResources().openRawResource(R.raw.song_list);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );

        Scanner scan = new Scanner(reader);
        scan.useDelimiter(";");
        long newRowId = 0;

        while (scan.hasNext()) {
            //read the data
            Song song = new Song();
            song.setTitle(scan.next());
            song.setArtist(scan.next());

            //insert into database
            ContentValues values = new ContentValues();

            values.put(SongContract.SongEntry.COLUMN_SONG_TITLE, song.getTitle());
            values.put(SongContract.SongEntry.COLUMN_SONG_ARTIST, song.getArtist());
            values.put(SongContract.SongEntry.COLUMN_SONG_FAVORITE, 0);

            newRowId = db.insert(SongContract.SongEntry.TABLE_NAME, null, values);

            //check to be sure database is created
            if (newRowId == -1) {
                Toast.makeText(this, "Error creating database record", Toast.LENGTH_LONG).show();
            }
        }

        //feedback to say how many records were created in the database
        Toast.makeText(this, newRowId + " songs were added to the database", Toast.LENGTH_LONG).show();






        //Song song = new Song();
        //song.setTitle("9 to 5");
        //song.setArtist("Dolly Parton");

        //ContentValues values = new ContentValues();

        //values.put(SongContract.SongEntry.COLUMN_SONG_TITLE, song.getTitle());
        //values.put(SongContract.SongEntry.COLUMN_SONG_ARTIST, song.getArtist());
        //values.put(SongContract.SongEntry.COLUMN_SONG_FAVORITE, 0);

        //long newRowId = db.insert(SongContract.SongEntry.TABLE_NAME, null, values);

        //if (newRowId == -1) {
            //Toast.makeText(this, "Error with saving song", Toast.LENGTH_SHORT).show();
        //}
        //else {
            //Toast.makeText(this, "Song saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        //}
    }

    private void readDatabase() {
        SQLiteDatabase db = dbController.getReadableDatabase();

        String[] projection = {
                //SongContract.SongEntry._ID,  //uncomment if primary key is needed
                SongContract.SongEntry.COLUMN_SONG_TITLE,
                SongContract.SongEntry.COLUMN_SONG_ARTIST
                //SongContract.SongEntry.COLUMN_SONG_FAVORITE  //uncomment when favorite is implemented
        };

        Cursor cursor = db.query(
                SongContract.SongEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        //figure out the index of each column
        //int idColumnIndex = cursor.getColumnIndex(SongContract.SongEntry._ID);
        int titleColumnIndex = cursor.getColumnIndex(SongContract.SongEntry.COLUMN_SONG_TITLE);
        int artistColumnIndex = cursor.getColumnIndex(SongContract.SongEntry.COLUMN_SONG_ARTIST);
        //int favoriteColumnIndex = cursor.getColumnIndex(SongContract.SongEntry.COLUMN_SONG_FAVORITE);


        try {
            while (cursor.moveToNext()) {
                Song song = new Song();
                song.setTitle(cursor.getString(titleColumnIndex));
                song.setArtist(cursor.getString(artistColumnIndex));

                songs.add(song);
            }
        }
        finally {
            cursor.close();
        }



    }
}
