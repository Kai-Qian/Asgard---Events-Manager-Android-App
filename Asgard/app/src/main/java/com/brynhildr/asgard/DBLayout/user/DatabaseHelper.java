package com.brynhildr.asgard.DBLayout.user;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by willQian on 2015/11/11.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    /**
     * Database version
     */
    private static final int VERSION = 1;
    /**
     * Get the context
     */
    private Context c;

    /**
     * Constructor
     * @param context
     * @param name
     * @param factory
     * @param version
     */
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                          int version) {
        super(context, name, factory, version);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructor
     * @param context
     * @param name
     */
    public DatabaseHelper(Context context, String name) {
        this(context,name,VERSION);
        this.c = context;
    }

    /**
     * Constructor
     * @param context
     * @param name
     * @param version
     */
    public DatabaseHelper(Context context, String name, int version){
        this(context, name,null,version);
        this.c = context;
    }

    /**
     * Used when table is created
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        System.out.println("create a Database");
        try {
            InputStreamReader inputReader = new InputStreamReader( c.getResources().getAssets().open("sql.txt") );
            BufferedReader br = new BufferedReader(inputReader);
            boolean eof = false;

            while (!eof) {
                String line = br.readLine();
                if (line == null) {
                    eof = true;
                } else {
                    db.execSQL(line);
                }
            }

            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("File sql.txt" + " was not found");
            return;
        } catch (IOException e) {
            System.out.println();
            System.out.println(e);
            return;
        }
    }

    /**
     * Used when table is opened
     * @param db
     */
    @Override
    public void onOpen(SQLiteDatabase db)
    {
        super.onOpen(db);
        if (!db.isReadOnly())
        {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    /**
     * Used when table is upgraded
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        System.out.println("update a Database");
    }

}
