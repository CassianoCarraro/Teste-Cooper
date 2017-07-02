package com.cooper.dc.testecooper;

import android.app.Activity;
import android.database.Cursor;
import android.view.View;
import android.widget.Toast;

public class PerfilAction implements View.OnClickListener {
    private PerfilFragment contexto;
    private DBAdapter db;
    private PerfilModel perfil;

    public PerfilAction(Activity contexto) {
        db = new DBAdapter(contexto);
        carregarPerfil();
    }

    public PerfilAction(PerfilFragment contexto) {
        this.contexto = contexto;
        db = new DBAdapter(contexto.getActivity());
        carregarPerfil();
        mostrarPerfil();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSalvar) {
            salvarPerfil();
        }
    }

    public PerfilModel getPerfil() {
        return perfil;
    }

    private void carregarPerfil() {
        perfil = new PerfilModel();

        db.open();
        Cursor c = db.getAll();
        if (c.moveToFirst() == true) {
            perfil.setId(c.getLong(0));
            perfil.setNome(c.getString(1));
            perfil.setIdade(c.getInt(2));
            perfil.setSexo(c.getInt(3));
        }
        db.close();
    }

    private void salvarPerfil() {
        perfil.setNome(contexto.edtNome.getText().toString());
        perfil.setIdade(Integer.parseInt(contexto.edtIdade.getText().toString()));

        if (contexto.rdgSexo.getCheckedRadioButtonId() == R.id.rbFeminino) {
            perfil.setSexo(PerfilModel.SEXO_FEMININO);
        } else {
            perfil.setSexo(PerfilModel.SEXO_MASCULINO);
        }

        db.open();
        perfil.setId(db.salvar(perfil));
        db.close();

        atualizarNavBar();
        Toast.makeText(contexto.getActivity(), "Perfil salvo com sucesso!", Toast.LENGTH_SHORT).show();
    }

    private void mostrarPerfil() {
        contexto.edtNome.setText(perfil.getNome());
        contexto.edtIdade.setText(perfil.getIdade().toString());

        if (perfil.getSexo() == PerfilModel.SEXO_FEMININO) {
            contexto.rdgSexo.check(R.id.rbFeminino);
        } else {
            contexto.rdgSexo.check(R.id.rbMasculino);
        }

        atualizarNavBar();
    }

    private void atualizarNavBar() {
        if (perfil.getId() > 0) {
            contexto.tvNavNome.setText(perfil.getNome());
            contexto.tvNavDescricao.setText((perfil.getSexo() == PerfilModel.SEXO_FEMININO ? "Feminino" : "Masculino") + " - " + perfil.getIdade() + " anos");
        } else {
            contexto.tvNavNome.setText(R.string.lbNavNome);
            contexto.tvNavDescricao.setText("");
        }
    }
}