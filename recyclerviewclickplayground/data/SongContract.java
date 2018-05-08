package com.danaeldridge.recyclerviewclickplayground.data;

import android.provider.BaseColumns;

public final class SongContract {
    private SongContract() {}

    public static final class SongEntry implements BaseColumns {
        //table name
        public final static String TABLE_NAME = "songs";

        /**
         * Auto-increment ID number for the songs table
         *
         * Type: INTEGER
         *
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Title of the song
         *
         * Type: TEXT
         */
        public final static String COLUMN_SONG_TITLE="title";

        /**
         * Artist of the song
         *
         * Type: TEXT
         */
        public final static String COLUMN_SONG_ARTIST="artist";

        /**
         * Boolean for favorite.  Only use 0 or 1, default is 0.
         *
         * Type: INTEGER
         */
        public final static String COLUMN_SONG_FAVORITE="favorite";
    }
}
