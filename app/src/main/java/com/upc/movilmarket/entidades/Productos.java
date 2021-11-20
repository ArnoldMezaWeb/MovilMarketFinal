package com.upc.movilmarket.entidades;

public class Productos {

    private String id;
    private String nombre;
    private String categoria;
    private String foto;
    private int costo;

    public Productos(String id, String nombre, String categoria, String foto, int costo) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.foto = foto;
        this.costo = costo;
    }

    public Productos() {
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }
}
