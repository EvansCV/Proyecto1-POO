/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Aplicación;

import Conceptos.Abogado;
import java.util.ArrayList;
import Conceptos.Servicio;
import Conceptos.Cliente;

/**
 *
 * @author evans
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Cargar cada uno de los archivos XML
        ArrayList<Servicio> listaServicios;
        listaServicios = Util.HandlerServicios.cargar("Data\\servicios.xml");
        ArrayList<Cliente> listaClientes;
        listaClientes = Util.HandlerClientes.cargar("Data\\clientes.xml");
        ArrayList<Abogado> listaAbogados;
        listaAbogados = Util.HandlerAbogados.cargar("Data\\abogados.xml");
        
        for (Abogado a: listaAbogados) {
            System.out.println(a);
        }
    }   
}
