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
    
    public static void guardar(String nombreXML, ArrayList<Servicio> servicios) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document documento = builder.newDocument();

            // Crear el elemento raíz
            Element raiz = documento.createElement("servicios");
            documento.appendChild(raiz);

            // Recorrer la lista de abogados y crear nodos
            for (Servicio s : servicios) {
                Element servicio = documento.createElement("servicio");
                servicio.setAttribute("id", String.valueOf(s.getId()));

                Element nombre = documento.createElement("nombre");
                nombre.appendChild(documento.createTextNode(s.getNombre()));
                servicio.appendChild(nombre);

                Element puesto = documento.createElement("precio");
                puesto.appendChild(documento.createTextNode(Integer.toString(s.getPrecio())));
                servicio.appendChild(puesto);

                raiz.appendChild(servicio);
            }

            // Escribir el contenido en un archivo XML
            javax.xml.transform.TransformerFactory tf = javax.xml.transform.TransformerFactory.newInstance();
            javax.xml.transform.Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");

            javax.xml.transform.dom.DOMSource source = new javax.xml.transform.dom.DOMSource(documento);
            javax.xml.transform.stream.StreamResult result = new javax.xml.transform.stream.StreamResult(new File(nombreXML));

            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
