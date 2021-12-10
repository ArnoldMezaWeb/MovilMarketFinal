package com.upc.movilmarket.entidades;

public class Pedidos {

    private String id;
    private String nombre;
    private String Direccion;
    private int cantidad;
    private int costo;

    public Pedidos(String id, String nombre, String direccion, int cantidad, int costo) {
        this.id = id;
        this.nombre = nombre;
        Direccion = direccion;
        this.cantidad = cantidad;
        this.costo = costo;
    }
    public Pedidos() {
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

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }
}
