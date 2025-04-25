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
    
    
    public static void guardar(String nombreXML, ArrayList<Abogado> abogados) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document documento = builder.newDocument();

            // Crear el elemento raíz
            Element raiz = documento.createElement("abogados");
            documento.appendChild(raiz);

            // Recorrer la lista de abogados y crear nodos
            for (Abogado ab : abogados) {
                Element abogado = documento.createElement("abogado");
                abogado.setAttribute("id", String.valueOf(ab.getId()));

                Element nombre = documento.createElement("nombre");
                nombre.appendChild(documento.createTextNode(ab.getNombre()));
                abogado.appendChild(nombre);

                Element puesto = documento.createElement("puesto");
                puesto.appendChild(documento.createTextNode(ab.getPuesto()));
                abogado.appendChild(puesto);

                Element telefono = documento.createElement("telefono");
                telefono.appendChild(documento.createTextNode(ab.getTelefono()));
                abogado.appendChild(telefono);

                // Servicios
                Element servicios = documento.createElement("servicios");
                for (int idServicio : ab.getServicios()) {
                    Element id = documento.createElement("id");
                    id.appendChild(documento.createTextNode(String.valueOf(idServicio)));
                    servicios.appendChild(id);
                }
                abogado.appendChild(servicios);

                raiz.appendChild(abogado);
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
