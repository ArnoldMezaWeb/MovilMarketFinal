package com.upc.movilmarket.entidades;

public class Proveedor {

    private String id;
    private String nombre;
    private String distrito;
    private String ruc;
    private String direccion;

    public Proveedor(String id, String nombre, String distrito, String ruc, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.distrito = distrito;
        this.ruc = ruc;
        this.direccion = direccion;
    }

    public Proveedor() {
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

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
