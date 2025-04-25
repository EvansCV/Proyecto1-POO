/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import java.util.ArrayList;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import Conceptos.Abogado;
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
public class HandlerAbogados {
    // Para obtener todo menos el id.
    private static String obtenerValor(String etiqueta, Element elemento) {
        NodeList nodos = elemento.getElementsByTagName(etiqueta).item(0).getChildNodes();
        Node nodo = (Node)nodos.item(0);
        return nodo.getNodeValue();
    }
    
    private static String obtenerID(Element elemento) {
        return elemento.getAttribute("id");
    }
    
    private static ArrayList<Integer> obtenerServicios(Element elemento) {
        NodeList nodos = elemento.getElementsByTagName("id");
        ArrayList<Integer> idS = new ArrayList(); 

        for (int i = 0; i < nodos.getLength(); i++) {
            Element idElemento = (Element)nodos.item(i);
            String valor = idElemento.getTextContent().trim();
            if (!valor.isEmpty()) {
                idS.add(Integer.valueOf(valor));
            }
        }
        return idS;
    }
    
    public static ArrayList<Abogado> cargar(String nombreXML) {
        ArrayList<Abogado> abogados = new ArrayList();
        try { 
            File archivo = new File(nombreXML); //abre el archivo XML
            DocumentBuilderFactory industria = DocumentBuilderFactory.newInstance();
            DocumentBuilder creador = industria.newDocumentBuilder();
            Document documento = creador.parse(archivo);
            
            NodeList nodos = documento.getElementsByTagName("abogado");
            
            for (int i=0; i < nodos.getLength(); i++) {
                Node nodoCliente = nodos.item(i);
                if (nodoCliente.getNodeType() == Node.ELEMENT_NODE) {
                    Element elemento = (Element)nodoCliente;
                    int id = Integer.parseInt(obtenerID(elemento));
                    String nombre = obtenerValor("nombre", elemento);
                    String puesto = obtenerValor("puesto", elemento);
                    String telefono = obtenerValor("telefono", elemento);
                    ArrayList<Integer> servicios = obtenerServicios(elemento);
                    Abogado abogado = new Abogado(id, nombre, puesto, telefono, servicios);
                    abogados.add(abogado);
                }
            }
                    
        } catch (IOException | NumberFormatException | ParserConfigurationException | SAXException e) {
        }
        return abogados;
    }
}
