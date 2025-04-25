/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import java.util.ArrayList;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import Conceptos.Servicio;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author evans
 */
public class HandlerServicios {
    // Para obtener todo menos el id.
    private static String obtenerValor(String etiqueta, Element elemento) {
        NodeList nodos = elemento.getElementsByTagName(etiqueta).item(0).getChildNodes();
        Node nodo = (Node)nodos.item(0);
        return nodo.getNodeValue();
    }
    
    private static String obtenerID(String atributo, Element elemento) {
        return elemento.getAttribute(atributo);
    }
    
    public static ArrayList<Servicio> cargar(String nombreXML) {
        ArrayList<Servicio> servicios = new ArrayList();
        try { 
            File archivo = new File(nombreXML); //abre el archivo XML
            DocumentBuilderFactory industria = DocumentBuilderFactory.newInstance();
            DocumentBuilder creador = industria.newDocumentBuilder();
            Document documento = creador.parse(archivo);
            
            NodeList nodos = documento.getElementsByTagName("servicio");
            
            for (int i=0; i < nodos.getLength(); i++) {
                Node nodoServicio = nodos.item(i);
                if (nodoServicio.getNodeType() == Node.ELEMENT_NODE) {
                    Element elemento = (Element)nodoServicio;
                    int id = Integer.parseInt(obtenerID("id", elemento));
                    String nombre = obtenerValor("nombre", elemento);
                    int precio = Integer.parseInt(obtenerValor("precio", elemento));
                    Servicio servicio = new Servicio(id, nombre, precio);
                    servicios.add(servicio);
                }
            }
                    
        } catch (IOException | NumberFormatException | ParserConfigurationException | SAXException e) {
        }
        return servicios;
    }
}
