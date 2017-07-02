package com.cooper.dc.testecooper;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.Toast;

import java.text.DecimalFormat;

public class TesteCooperAction implements View.OnClickListener, Chronometer.OnChronometerTickListener{
    private TesteCooperFragment contexto;
    private GPSAction gpsAction;
    private long tempoDecorrido;
    private Double velocidadeMedia;
    private DecimalFormat formatoDecimal;
    private ResultadoModel ultimoResultado;

    public TesteCooperAction(TesteCooperFragment contexto) {
        this.contexto = contexto;
        velocidadeMedia = 0d;
        formatoDecimal = new DecimalFormat("0.00");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnIniciar:
                iniciar();
                break;
            case R.id.btnResetar:
                resetar();
                break;
        }
    }

    @Override
    public void onChronometerTick(Chronometer chronometer) {
        tempoDecorrido = (SystemClock.elapsedRealtime() - contexto.cronometro.getBase()) / 1000;
        if(tempoDecorrido >= 720){ //12min
            finalizar();
        }else{
            if (tempoDecorrido > 0) {
                velocidadeMedia = gpsAction.getDistancia() / tempoDecorrido;
                contexto.tvVelocidade.setText(formatoDecimal.format(velocidadeMedia) + " m/s");
            }
        }
    }

    public void iniciar() {
        try {
            gpsAction = new GPSAction(contexto);

            contexto.cronometro.setText(String.valueOf(tempoDecorrido).toString());
            contexto.cronometro.setBase(SystemClock.elapsedRealtime());
            contexto.cronometro.start();
            contexto.btnIniciar.setEnabled(false);
        } catch (GPSException e) {
            Toast.makeText(contexto.getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void finalizar() {
        Double distancia = gpsAction.getDistancia();
        ultimoResultado = new ResultadoModel(10, velocidadeMedia, distancia, "CLASSIFICAÇÃO");

        AlertDialog.Builder alert = new AlertDialog.Builder(contexto.getActivity());
        alert.setTitle("Teste finalizado");
        alert.setMessage("Distância percorrida: " + formatoDecimal.format(distancia) +
            "\nVelocidade média: " + formatoDecimal.format(velocidadeMedia) +
            "\n\nPreparo físico: (obter informação API)"
        );
        alert.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                salvarResultado();
            }
        });
        alert.setNegativeButton("Cancelar", null);
        alert.show();

        resetar();
    }

    public void resetar() {
        contexto.cronometro.stop();
        contexto.cronometro.setText("00:00");
        tempoDecorrido = 0;
        velocidadeMedia = 0d;
        contexto.btnIniciar.setEnabled(true);
        contexto.tvDistancia.setText(R.string.tvDistancia);
        contexto.tvVelocidade.setText(R.string.tvVelocidade);
        gpsAction.resetar();
    }

    public void salvarResultado() {
        ContentValues valores = new ContentValues();

        PerfilAction perfilAction = new PerfilAction(contexto.getActivity());

        valores.put(ResultadoContentProvider.IDADE, perfilAction.getPerfil().getIdade());
        valores.put(ResultadoContentProvider.VELOCIDADE_MEDIA, ultimoResultado.getVelocidadeMedia());
        valores.put(ResultadoContentProvider.DISTANCIA, ultimoResultado.getDistancia());
        valores.put(ResultadoContentProvider.CLASSIFICACAO, ultimoResultado.getClassificacao());

        contexto.getActivity().getContentResolver().insert(ResultadoContentProvider.CONTENT_URI, valores);
    }
}
