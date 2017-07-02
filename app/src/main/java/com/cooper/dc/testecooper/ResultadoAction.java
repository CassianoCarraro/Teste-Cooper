package com.cooper.dc.testecooper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ResultadoAction implements AdapterView.OnItemLongClickListener {
    private ResultadoFragment contexto;
    private ArrayList<String> listaItens;
    private ArrayList<Integer> listaItensIds;
    private ArrayAdapter listaAdapter;
    private DecimalFormat formatoDecimal;
    private int itemSelecionado;

    public ResultadoAction(ResultadoFragment contexto) {
        this.contexto = contexto;

        formatoDecimal = new DecimalFormat("0.00");
        listaItens = new ArrayList<>();
        listaItensIds = new ArrayList<>();

        listaAdapter = new ArrayAdapter<>(contexto.getActivity(), android.R.layout.simple_list_item_1, listaItens);
        contexto.listaResultados.setAdapter(listaAdapter);
        carregarResultados();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        itemSelecionado = position;

        AlertDialog.Builder alert = new AlertDialog.Builder(contexto.getActivity());
        alert.setTitle("Resultado");
        alert.setMessage("Deseja excluir o resultado?");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                removerResultado();
            }
        });
        alert.setNegativeButton("Cancelar", null);
        alert.show();

        return false;
    }

    public void carregarResultados() {
        Cursor c = contexto.getActivity().getContentResolver().query(ResultadoContentProvider.CONTENT_URI, null, null, null, null);;
        if (c.moveToFirst() == true) {
            do {
                listaItensIds.add(c.getInt(2));
                listaItens.add("Classificação: " + c.getString(4) +
                    "\nVelocidade média: " + formatoDecimal.format(c.getDouble(3)) + " m/s" +
                    "\nDistância: " + formatoDecimal.format(c.getDouble(0)) + " m" +
                    "\nIdade: " + c.getInt(1));
            } while (c.moveToNext());

            contexto.tvResultados.setVisibility(View.GONE);
        } else {
            mostrarMsgResultados();
        }
    }

    public void removerResultado() {
        contexto.getActivity().getContentResolver().delete(ResultadoContentProvider.CONTENT_URI, ResultadoContentProvider._ID + "=" + listaItensIds.get(itemSelecionado), null);
        listaItens.remove(itemSelecionado);
        listaItensIds.remove(itemSelecionado);
        listaAdapter.notifyDataSetChanged();

        if (listaItens.isEmpty()) {
            mostrarMsgResultados();
        }
    }

    private void mostrarMsgResultados() {
        contexto.tvResultados.setText(R.string.tvResultados);
        contexto.tvResultados.setVisibility(View.VISIBLE);
    }
}