package com.cooper.dc.testecooper;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by DANIEL on 03/07/2017.
 */

class PostAsync extends AsyncTask<Void,Void,String>{

    private int idade;
    private int sexo;
    private double distanciaPerc;




    public PostAsync(int idade, double distanciaPerc, int sexo){
        this.idade = idade;
        this.distanciaPerc = distanciaPerc;
        this.sexo = sexo;
    }

    @Override
    protected String doInBackground(Void... params) {
        String result ;
        try {
            URL url = new URL("http://ad5c9536.ngrok.io/testecooper/cooper.php");//TESTE NGROK LOCAL
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.connect();

            JSONObject jsonObjectMain = new JSONObject();
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("idade", this.idade);
            jsonObject.put("distanciaPerc", this.distanciaPerc);
            jsonObject.put("sexo", this.sexo);

            jsonObjectMain.put("resultado", jsonObject);

            OutputStream os = conn.getOutputStream();

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(jsonObjectMain.toString());
            writer.close();
            os.close();


            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            String line = null;
            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            br.close();
            result = sb.toString();
            JSONObject jsonResult = new JSONObject(result);
            result = jsonResult.get("classificacao").toString();
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

}