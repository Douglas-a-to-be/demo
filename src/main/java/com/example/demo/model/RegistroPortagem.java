package com.example.demo.model;

import java.util.Date;

public class RegistroPortagem {

    private Date dataEntrada;
    private Date dataSaida;
    private Veiculo veiculo;

    /**
     * @return the dataEntrada
     */
    public Date getDataEntrada() {

        return dataEntrada;
    }

    /**
     * @param dataEntrada the dataEntrada to set
     */
    public void setDataEntrada(Date dataEntrada) {

        this.dataEntrada = dataEntrada;
    }

    /**
     * @return the dataSaida
     */
    public Date getDataSaida() {

        return dataSaida;
    }

    /**
     * @param dataSaida the dataSaida to set
     */
    public void setDataSaida(Date dataSaida) {

        this.dataSaida = dataSaida;
    }

    /**
     * @return the veiculo
     */
    public Veiculo getVeiculo() {

        return veiculo;
    }

    /**
     * @param veiculo the veiculo to set
     */
    public void setVeiculo(Veiculo veiculo) {

        this.veiculo = veiculo;
    }
}
