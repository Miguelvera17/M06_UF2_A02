package com.iticbcn.jdbc;

public class Persona {
    private int id;
    private int DNI;
    private String name;
    private String phone;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getDNI() {
        return DNI;
    }
    public void setDNI(int dNI) {
        DNI = dNI;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
