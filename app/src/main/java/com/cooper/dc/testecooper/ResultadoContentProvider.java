package com.cooper.dc.testecooper;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.HashMap;

public class ResultadoContentProvider extends ContentProvider {

    private DBHelper dbHelper;
    private static HashMap<String, String> RESULTADO_PROJECTION_MAP;
    private static final String TABLE_NAME = "resultado";
    private static final String AUTHORITY = "com.cooper.dc.testecooper";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);
    public static final String DEFAULT_SORT_ORDER = "_id ASC";

    private static final UriMatcher URL_MATCHER;

    private static final int RESULTADO = 0;

    public static final String _ID = "_id";
    public static final String IDADE = "idade";
    public static final String VELOCIDADE_MEDIA = "velocidade_media";
    public static final String DISTANCIA = "distancia";
    public static final String CLASSIFICACAO = "classificacao";

    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext(), true);
        return (dbHelper == null) ? false : true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sort) {
        SQLiteDatabase mDB = dbHelper.getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        switch (URL_MATCHER.match(uri)) {
            case RESULTADO:
                qb.setTables(TABLE_NAME);
                qb.setProjectionMap(RESULTADO_PROJECTION_MAP);
                break;
            default:
                throw new IllegalArgumentException("URI inválida " + uri);
        }

        String orderBy = "";
        if (TextUtils.isEmpty(sort)) {
            orderBy = DEFAULT_SORT_ORDER;
        } else {
            orderBy = sort;
        }

        Cursor c = qb.query(mDB, projection, selection, selectionArgs, null, null, orderBy);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return "vnd.android.cursor.dir/vnd.com.cooper.dc.testecooper.resultado";
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues initialValues) {
        SQLiteDatabase mDB = dbHelper.getWritableDatabase();
        long rowID;
        ContentValues values;
        if (initialValues != null) {
            values = new ContentValues(initialValues);
        } else {
            values = new ContentValues();
        }
        if (URL_MATCHER.match(uri) != RESULTADO) {
            throw new IllegalArgumentException("URI inválida " + uri);
        }

        rowID = mDB.insert("resultado", "resultado", values);
        if (rowID > 0) {
            Uri cUri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(cUri, null);
            return cUri;
        }
        throw new SQLException("Falha ao inserir registro em " + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase mDB = dbHelper.getWritableDatabase();
        int count;
        String segment = "";
        switch (URL_MATCHER.match(uri)) {
            case RESULTADO:
                count = mDB.delete(TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URL " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    static {
        URL_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase(), RESULTADO);

        RESULTADO_PROJECTION_MAP = new HashMap<String, String>();
        RESULTADO_PROJECTION_MAP.put(_ID, "_id");
        RESULTADO_PROJECTION_MAP.put(IDADE, "idade");
        RESULTADO_PROJECTION_MAP.put(VELOCIDADE_MEDIA, "velocidade_media");
        RESULTADO_PROJECTION_MAP.put(DISTANCIA, "distancia");
        RESULTADO_PROJECTION_MAP.put(CLASSIFICACAO, "classificacao");
    }
}
