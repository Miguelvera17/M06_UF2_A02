package com.iticbcn.jdbc;

public class Persona {
    private int id;
    private String DNI;
    private String nombre;
    private String telefono;
    
    public Persona(String dNI, String nombre, String telefono) {
        DNI = dNI;
        this.nombre = nombre;
        this.telefono = telefono;
    }
    public int getID() {
        return id;
    }
    public void setID(int id) {
        this.id = id;
    }
    public String getDNI() {
        return DNI;
    }
    public void setDNI(String dNI) {
        DNI = dNI;
    }
    public String getName() {
        return nombre;
    }
    public void setName(String nombre) {
        this.nombre = nombre;
    }
    public String getPhone() {
        return telefono;
    }
    public void setPhone(String telefono) {
        this.telefono = telefono;
    }
}
