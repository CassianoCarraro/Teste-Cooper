package com.cooper.dc.testecooper;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

public class TesteCooperFragment extends Fragment {
    Button btnIniciar;
    Button btnResetar;
    Chronometer cronometro;
    TextView tvDistancia;
    TextView tvVelocidade;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_teste_cooper, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnIniciar = (Button) view.findViewById(R.id.btnIniciar);
        btnResetar = (Button) view.findViewById(R.id.btnResetar);
        cronometro = (Chronometer) view.findViewById(R.id.cronometro);
        tvDistancia = (TextView) view.findViewById(R.id.tvDistancia);
        tvVelocidade = (TextView) view.findViewById(R.id.tvVelocidade);

        TesteCooperAction testeCooperAction = new TesteCooperAction(this);
        btnIniciar.setOnClickListener(testeCooperAction);
        btnResetar.setOnClickListener(testeCooperAction);
        cronometro.setOnChronometerTickListener(testeCooperAction);

        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.toolbar.setTitle("Teste Cooper");
    }
}
