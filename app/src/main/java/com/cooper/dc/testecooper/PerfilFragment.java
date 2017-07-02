package com.cooper.dc.testecooper;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class PerfilFragment extends Fragment {
    public Button btnSalvar;
    public EditText edtNome;
    public EditText edtIdade;
    public RadioGroup rdgSexo;
    public TextView tvNavNome;
    public TextView tvNavDescricao;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_perfil, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.toolbar.setTitle("Perfil");

        btnSalvar = (Button) getActivity().findViewById(R.id.btnSalvar);
        edtNome = (EditText) getActivity().findViewById(R.id.edtNome);
        edtIdade = (EditText) getActivity().findViewById(R.id.edtIdade);
        rdgSexo = (RadioGroup) getActivity().findViewById(R.id.rdgSexo);
        tvNavNome = (TextView) ((MainActivity) getActivity()).headerNavView.findViewById(R.id.tvNavNome);
        tvNavDescricao = (TextView) ((MainActivity) getActivity()).headerNavView.findViewById(R.id.tvNavDescricao);

        PerfilAction perfilAction = new PerfilAction(this);
        btnSalvar.setOnClickListener(perfilAction);
    }
}