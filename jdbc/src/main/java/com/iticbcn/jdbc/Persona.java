package com.iticbcn.jdbc;

public class Persona {
    private int ID;
    private String DNI;
    private String name;
    private String phone;
    
    public Persona(int iD, String dNI, String name, String phone) {
        ID = iD;
        DNI = dNI;
        this.name = name;
        this.phone = phone;
    }
    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public String getDNI() {
        return DNI;
    }
    public void setDNI(String dNI) {
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
