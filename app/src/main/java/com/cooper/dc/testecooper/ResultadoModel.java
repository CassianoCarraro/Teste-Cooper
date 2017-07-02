package com.cooper.dc.testecooper;

import java.io.Serializable;

public class ResultadoModel implements Serializable {

    private Integer idade;
    private Double velocidadeMedia;
    private Double distancia;
    private String classificacao;

    public ResultadoModel() {
        setIdade(0);
        setVelocidadeMedia(0d);
        setDistancia(0d);
        setClassificacao("");
    }

    public ResultadoModel(Integer idade, Double velocidadeMedia, Double distancia, String classificacao) {
        setIdade(idade);
        setVelocidadeMedia(velocidadeMedia);
        setDistancia(distancia);
        setClassificacao(classificacao);
    }


    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public Double getVelocidadeMedia() {
        return velocidadeMedia;
    }

    public void setVelocidadeMedia(Double velocidadeMedia) {
        this.velocidadeMedia = velocidadeMedia;
    }

    public Double getDistancia() {
        return distancia;
    }

    public void setDistancia(Double distancia) {
        this.distancia = distancia;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }
}
