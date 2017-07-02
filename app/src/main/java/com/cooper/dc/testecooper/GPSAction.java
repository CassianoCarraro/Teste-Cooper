package com.cooper.dc.testecooper;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import java.text.DecimalFormat;

public class GPSAction implements LocationListener {
    private Boolean mudouLocal;
    private Double finalLatitude, finalLongitude, inicialLatitude, inicialLongitude;
    private Double distancia;
    private DecimalFormat formatoDecimal;
    private TesteCooperFragment contexto;
    private Location localInicial, localFinal;
    private LocationManager servicoGPS;

    public GPSAction(TesteCooperFragment contexto) throws GPSException {
        this.contexto = contexto;

        distancia = 0d;
        mudouLocal = false;
        formatoDecimal = new DecimalFormat("0.00");
        localInicial = new Location("gps");
        localFinal = new Location("gps");
        servicoGPS = (LocationManager) contexto.getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(contexto.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(contexto.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            servicoGPS.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, this);

            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            servicoGPS.getBestProvider(criteria, true);
        } else {
            throw new GPSException();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if(!mudouLocal){
            setaLatLongInicial(location);
            mudouLocal = true;
        }else{
            setaLatLongFinal(location);
            distancia += calcularDistancia();
            setaLatLongInicial(location);
        }
        atualizarTela();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public Double getDistancia() {
        return distancia;
    }

    public void resetar() {
        distancia = 0d;
        servicoGPS.removeUpdates(this);
        mudouLocal = false;
    }

    private void setaLatLongInicial(Location location){
        inicialLatitude = location.getLatitude();
        inicialLongitude = location.getLongitude();
        localInicial.setLatitude(inicialLatitude);
        localInicial.setLongitude(inicialLongitude);
    }

    private void setaLatLongFinal(Location location){
        finalLatitude = location.getLatitude();
        finalLongitude = location.getLongitude();
        localFinal.setLatitude(finalLatitude);
        localFinal.setLongitude(finalLongitude);
    }

    private void atualizarTela() {
        contexto.tvDistancia.setText(String.valueOf(formatoDecimal.format(distancia)).toString() + " m");
    }

    private Double calcularDistancia() {
        double raioTerra = 3958.75;
        double dLat = Math.toRadians(finalLatitude - inicialLatitude);
        double dLng = Math.toRadians(finalLongitude - inicialLongitude);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(inicialLatitude))
                * Math.cos(Math.toRadians(finalLatitude)) * Math.sin(dLng / 2)
                * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = raioTerra * c;
        double fatorConversaoMetro = 1609;

        return dist * fatorConversaoMetro;
    }
}