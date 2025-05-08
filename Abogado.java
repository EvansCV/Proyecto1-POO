/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conceptos;

import java.util.ArrayList;

/**
 *
 * @author evans
 */
public class Abogado {
    private int id;
    private String nombre;
    private String telefono;
    private String puesto;
    ArrayList<Integer> servicios = new ArrayList();
    
    public Abogado() {
        this.id = 1;
        this.nombre = "";
        this.telefono = "";
        this.puesto = "";
    }

    public Abogado(int id, String nombre, String telefono, String puesto, ArrayList<Integer> servicios) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.puesto = puesto;
        this.servicios = servicios;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getPuesto() {
        return puesto;
    }

    public ArrayList<Integer> getServicios() {
        return servicios;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public void setServicios(ArrayList<Integer> servicios) {
        this.servicios = servicios;
    }
    
    public void añadirServicio(int idServicio) {
        servicios.add(idServicio);
    }
    
    public void eliminarServicio(int idServicio) {
        servicios.remove(idServicio);
    }
    
    public String toString() {
        return "Abogado: " + this.id + " nombre: " + this.nombre + " telefono: " + this.telefono + " servicios: " + this.servicios;
    }
}
