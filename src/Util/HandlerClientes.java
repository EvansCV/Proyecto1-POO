/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import java.util.ArrayList;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import Conceptos.Cliente;
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
public class HandlerClientes {
    // Para obtener todo menos el id.
    private static String obtenerValor(String etiqueta, Element elemento) {
        NodeList nodos = elemento.getElementsByTagName(etiqueta).item(0).getChildNodes();
        Node nodo = (Node)nodos.item(0);
        return nodo.getNodeValue();
    }
    
    private static String obtenerID(Element elemento) {
        return elemento.getAttribute("id");
    }
    
    public static ArrayList<Cliente> cargar(String nombreXML) {
        ArrayList<Cliente> clientes = new ArrayList();
        try { 
            File archivo = new File(nombreXML); //abre el archivo XML
            DocumentBuilderFactory industria = DocumentBuilderFactory.newInstance();
            DocumentBuilder creador = industria.newDocumentBuilder();
            Document documento = creador.parse(archivo);
            
            NodeList nodos = documento.getElementsByTagName("cliente");
            
            for (int i=0; i < nodos.getLength(); i++) {
                Node nodoCliente = nodos.item(i);
                if (nodoCliente.getNodeType() == Node.ELEMENT_NODE) {
                    Element elemento = (Element)nodoCliente;
                    int id = Integer.parseInt(obtenerID(elemento));
                    String nombre = obtenerValor("nombre", elemento);
                    String telefono = obtenerValor("telefono", elemento);
                    String email = obtenerValor("email", elemento);
                    Cliente cliente = new Cliente(id, nombre, telefono, email);
                    clientes.add(cliente);
                }
            }
                    
        } catch (IOException | NumberFormatException | ParserConfigurationException | SAXException e) {
        }
        return clientes;
    }
    
    public static void guardar(String nombreXML, ArrayList<Cliente> clientes) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document documento = builder.newDocument();

            // Crear el elemento raíz
            Element raiz = documento.createElement("clientes");
            documento.appendChild(raiz);

            // Recorrer la lista de abogados y crear nodos
            for (Cliente c : clientes) {
                Element cliente = documento.createElement("cliente");
                cliente.setAttribute("id", String.valueOf(c.getId()));

                Element nombre = documento.createElement("nombre");
                nombre.appendChild(documento.createTextNode(c.getNombre()));
                cliente.appendChild(nombre);

                Element puesto = documento.createElement("email");
                puesto.appendChild(documento.createTextNode(c.getEmail()));
                cliente.appendChild(puesto);

                Element telefono = documento.createElement("telefono");
                telefono.appendChild(documento.createTextNode(c.getTelefono()));
                cliente.appendChild(telefono);

                raiz.appendChild(cliente);
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
