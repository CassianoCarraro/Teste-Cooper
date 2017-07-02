package com.cooper.dc.testecooper;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class ResultadoFragment extends Fragment {
    public ListView listaResultados;
    public TextView tvResultados;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_resultados, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.toolbar.setTitle("Resultados");

        listaResultados = (ListView) getActivity().findViewById(R.id.listaResultados);
        tvResultados = (TextView) getActivity().findViewById(R.id.tvResultados);

        ResultadoAction resultadoAction = new ResultadoAction(this);
        listaResultados.setOnItemLongClickListener(resultadoAction);
    }
}