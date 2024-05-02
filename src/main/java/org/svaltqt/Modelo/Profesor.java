package org.svaltqt.Modelo;

import java.util.Objects;

public class Profesor implements Comparable<Profesor> {
    private String cedula;
    private String tipoContrato;
    private String nombreCompleto;
    private String sexo;
    private String facultad;
    private String titulo;
    private int numAsignaturas;
    private int numHoras;
    private String fecha;

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(String tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getFacultad() {
        return facultad;
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getNumAsignaturas() {
        return numAsignaturas;
    }

    public void setNumAsignaturas(int numAsignaturas) {
        this.numAsignaturas = numAsignaturas;
    }

    public int getNumHoras() {
        return numHoras;
    }

    public void setNumHoras(int numHoras) {
        this.numHoras = numHoras;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profesor profesor = (Profesor) o;
        return Objects.equals(cedula, profesor.cedula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cedula);
    }

    @Override
    public int compareTo(Profesor otroProfesor) {
        return this.tipoContrato.compareTo(otroProfesor.getTipoContrato());
    }

    @Override
    public String toString() {
        return "Profesor{" +
                "cedula='" + cedula + '\'' +
                ", tipoContrato='" + tipoContrato + '\'' +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", sexo='" + sexo + '\'' +
                ", facultad='" + facultad + '\'' +
                ", titulo='" + titulo + '\'' +
                ", numAsignaturas=" + numAsignaturas +
                ", numHoras=" + numHoras +
                ", fecha='" + fecha + '\'' +
                '}';
    }
}