/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.text.DateFormat;
import java.util.Date;

/**
 *
 * @author Renato
 */
public class Locacao {
    private int id;
    private Cliente cliente;
    private Carro carro;
    private Date data_de_saida;
    private Date data_de_chegada;
    private Double valor_total;
    


    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Carro getCarro() {
        return carro;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    public Date getData_de_saida() {
        return data_de_saida;
    }

    public void setData_de_saida(Date data_de_saida) {
        this.data_de_saida = data_de_saida;
    }

    public Date getData_de_chegada() {
        return data_de_chegada;
    }

    public void setData_de_chegada(Date data_de_chegada) {
        this.data_de_chegada = data_de_chegada;
    }

    public Double getValor_total() {
        return valor_total;
    }

    public void setValor_total(Double valor_total) {
        this.valor_total = valor_total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
  

 
    
    
}
