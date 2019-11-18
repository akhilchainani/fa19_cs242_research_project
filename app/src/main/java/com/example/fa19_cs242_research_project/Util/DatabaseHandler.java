package com.example.fa19_cs242_research_project.Util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "playerManager";
    private static final String TABLE_PLAYERS = "players";
    private static final String KEY_ID = "player_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PH_NO = "phone_number";
    private static final String KEY_FRIENDS = "friends";
    private static final String KEY_HIGH_SCORE = "high_score";
    private static final String KEY_LOGIN = "login";
    private static final String KEY_PROFILE_PIC = "profile_pic";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PLAYERS_TABLE = "CREATE TABLE " + TABLE_PLAYERS + "("
                + KEY_ID + " TEXT PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_PH_NO + " TEXT," + KEY_EMAIL + " TEXT," + KEY_HIGH_SCORE
                + " INTEGER," + KEY_FRIENDS + " TEXT," + KEY_LOGIN + " TEXT," +
                KEY_PROFILE_PIC + " TEXT" + ")";
        db.execSQL(CREATE_PLAYERS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYERS);

        // Create tables again
        onCreate(db);
    }

    // code to add the new player
    public void addPlayer(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, player.getPlayerId());
        values.put(KEY_NAME, player.getPlayerName()); // Contact Name
        values.put(KEY_EMAIL, player.getEmail()); // Contact Phone
        values.put(KEY_HIGH_SCORE, player.getHighScore());
        values.put(KEY_PH_NO, player.getPhoneNumber());
        values.put(KEY_LOGIN, player.getLogin().getName());
        values.put(KEY_PROFILE_PIC, player.getProfilePicUri().toString());

        //turn array into delimited string
        String resultFriends = ("" + Arrays.asList(player.getFriends())).
                replaceAll("(^.|.$)", "  ").replace(", ", "  , " );
        values.put(KEY_FRIENDS, resultFriends);

        // Inserting Row
        db.insert(TABLE_PLAYERS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to get the single player
    public Player getPlayer(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PLAYERS, new String[] { KEY_ID,
                        KEY_NAME, KEY_EMAIL, KEY_HIGH_SCORE, KEY_PH_NO,
                        KEY_LOGIN, KEY_FRIENDS, KEY_PROFILE_PIC}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Player player = new Player(cursor.getString(cursor.getColumnIndex(KEY_ID)),
                    cursor.getString(cursor.getColumnIndex(KEY_NAME)),
                    cursor.getString(cursor.getColumnIndex(KEY_EMAIL)),
                    cursor.getString(cursor.getColumnIndex(KEY_PH_NO)),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_HIGH_SCORE))),
                    Constants.loginType.valueOf(cursor.getString(cursor.getColumnIndex(KEY_LOGIN))),
                    cursor.getString(cursor.getColumnIndex(KEY_PROFILE_PIC)));
            player.setFriends((ArrayList<String>) Arrays.asList(cursor.getString(cursor.getColumnIndex(KEY_FRIENDS)).split(",")));

            // return player
            return player;
        }

        return null;
    }

    // code to get all players in a list view
    public List<Player> getAllPlayers() {
        List<Player> playerList = new ArrayList<Player>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PLAYERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Player player = new Player(cursor.getString(0),
                                            cursor.getString(1),
                                            cursor.getString(2),
                                            cursor.getString(4),
                                            Integer.parseInt(cursor.getString(3)),
                                            Constants.loginType.valueOf(cursor.getString(5)),
                                            cursor.getString(7));
                // Adding contact to list
                player.setFriends((ArrayList<String>) Arrays.asList(cursor.getString(6).split(",")));
                playerList.add(player);
            } while (cursor.moveToNext());
        }

        // return player list
        return playerList;
    }

    // code to update the single player
    public int updatePlayer(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, player.getPlayerId());
        values.put(KEY_NAME, player.getPlayerName()); // Contact Name
        values.put(KEY_EMAIL, player.getEmail()); // Contact Phone
        values.put(KEY_HIGH_SCORE, player.getHighScore());
        values.put(KEY_PH_NO, player.getPhoneNumber());
        values.put(KEY_LOGIN, player.getLogin().getName());
        values.put(KEY_PROFILE_PIC, player.getProfilePicUri());

        //turn array into delimited string
        String resultFriends = ("" + Arrays.asList(player.getFriends())).
                replaceAll("(^.|.$)", "  ").replace(", ", "  , " );
        values.put(KEY_FRIENDS, resultFriends);

        // updating row
        return db.update(TABLE_PLAYERS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(player.getPlayerId()) });
    }

    // Deleting single player
    public void deletePlayer(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PLAYERS, KEY_ID + " = ?",
                new String[] { String.valueOf(player.getPlayerId()) });
        db.close();
    }

    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_PLAYERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}
