package br.com.grupo.sistemacadastro.model;

import java.time.LocalDate;

public class Hospedagem {

    private Long id;
    private String nomeHospede;
    private Integer numeroQuarto;
    private LocalDate dataEntrada;
    private LocalDate dataSaida;

    // Construtor utilizado para criar uma nova hospedagem.
    public Hospedagem(
            String nomeHospede,
            Integer numeroQuarto,
            LocalDate dataEntrada,
            LocalDate dataSaida) {

        this.nomeHospede = nomeHospede;
        this.numeroQuarto = numeroQuarto;
        this.dataEntrada = dataEntrada;
        this.dataSaida = dataSaida;
    }

    // Construtor utilizado para criar uma hospedagem recuperada do banco.
    public Hospedagem(
            Long id,
            String nomeHospede,
            Integer numeroQuarto,
            LocalDate dataEntrada,
            LocalDate dataSaida) {

        this.id = id;
        this.nomeHospede = nomeHospede;
        this.numeroQuarto = numeroQuarto;
        this.dataEntrada = dataEntrada;
        this.dataSaida = dataSaida;
    }

    public Long getId() {
        return id;
    }

    // Define o ID gerado automaticamente pelo banco.
    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeHospede() {
        return nomeHospede;
    }

    public void setNomeHospede(String nomeHospede) {
        this.nomeHospede = nomeHospede;
    }

    public Integer getNumeroQuarto() {
        return numeroQuarto;
    }

    public void setNumeroQuarto(Integer numeroQuarto) {
        this.numeroQuarto = numeroQuarto;
    }

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDate dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public LocalDate getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(LocalDate dataSaida) {
        this.dataSaida = dataSaida;
    }
}
