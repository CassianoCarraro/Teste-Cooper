package com.cooper.dc.testecooper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DBHelper extends SQLiteOpenHelper {

    private static String DB_PATH = "/data/data/com.cooper.dc.testecooper/databases/";
    private static String DB_NAME = "testecooper";
    private static final String SCHEMA_DATABASE = "create table resultado " +
            "(_id integer primary key autoincrement, " +
            " idade integer not null," +
            " velocidade_media real not null," +
            " distancia real not null," +
            " classificacao text not null);";

    private final Context myContext;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    public DBHelper(Context context, boolean createDatabase) {
        this(context);
        if (createDatabase) {
            create();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {}

    public void create() {
        InputStream myInput = null;
        OutputStream myOutput = null;
        SQLiteDatabase database = null;

        if (!checkDataBaseExistence()) {

            database = this.getReadableDatabase();
            try {
                myInput = myContext.getAssets().open(DB_NAME);

                String outFileName = DB_PATH + DB_NAME;
                myOutput = new FileOutputStream(outFileName);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
            } finally {
                try {
                    myOutput.flush();
                    myOutput.close();
                    myInput.close();

                    if (database != null && database.isOpen()) {
                        database.close();
                    }

                } catch (Exception e) {}
            }

            database.execSQL(SCHEMA_DATABASE);
            database.execSQL(PerfilModel.SCHEMA_TABLE);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    private boolean checkDataBaseExistence() {
        SQLiteDatabase dbToBeVerified = null;

        try {
            String dbPath = DB_PATH + DB_NAME;
            dbToBeVerified = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);

        } catch (SQLiteException e) {}

        if (dbToBeVerified != null) {
            dbToBeVerified.close();

        }

        return dbToBeVerified != null ? true : false;
    }
}