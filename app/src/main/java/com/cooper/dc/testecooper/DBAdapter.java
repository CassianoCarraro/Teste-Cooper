package com.cooper.dc.testecooper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBAdapter {
    private final Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context);
    }

    public void close() {
        dbHelper.close();
    }

    public long salvar(PerfilModel perfilModel) {
        ContentValues dados = new ContentValues();
        dados.put(PerfilModel.KEY_NOME, perfilModel.getNome());
        dados.put(PerfilModel.KEY_IDADE, perfilModel.getIdade());
        dados.put(PerfilModel.KEY_SEXO, perfilModel.getSexo());

        if (perfilModel.getId() == 0) {
            return db.insert(PerfilModel.TABLE_NAME, null, dados);
        } else {
            String linhaAcessada = PerfilModel.KEY_ID + "=" + perfilModel.getId();
            int linhasAfetadas = db.update(PerfilModel.TABLE_NAME, dados, linhaAcessada, null);

            return (linhasAfetadas > 0 ? perfilModel.getId() : -1);
        }
    }

    public Cursor getAll() {
        String colunas[] = {PerfilModel.KEY_ID, PerfilModel.KEY_NOME, PerfilModel.KEY_IDADE, PerfilModel.KEY_SEXO};
        return db.query(PerfilModel.TABLE_NAME, colunas, null, null, null, null, null);
    }

    public DBAdapter open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }
}