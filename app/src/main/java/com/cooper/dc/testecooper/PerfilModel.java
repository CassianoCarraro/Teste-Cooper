package com.cooper.dc.testecooper;

import java.io.Serializable;

public class PerfilModel implements Serializable {
    public static final String SCHEMA_TABLE = "create table perfil " +
            "(_id integer primary key autoincrement, " +
            " nome text not null," +
            " idade integer not null," +
            " sexo integer not null);";
    public static final String TABLE_NAME = "perfil";

    public static final String KEY_ID = "_id";
    public static final String KEY_NOME = "nome";
    public static final String KEY_IDADE = "idade";
    public static final String KEY_SEXO = "sexo";

    public static final Integer SEXO_MASCULINO = 1;
    public static final Integer SEXO_FEMININO = 2;

    private Long id;
    private String nome;
    private Integer idade;
    private Integer sexo;

    public PerfilModel() {
        setId(0l);
        setNome("");
        setIdade(0);
        setSexo(SEXO_MASCULINO);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public Integer getSexo() {
        return sexo;
    }

    public void setSexo(Integer sexo) {
        this.sexo = sexo;
    }
}
