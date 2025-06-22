/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import Conceptos.Abogado;
import Conceptos.Cliente;
import Conceptos.Estado;
import Conceptos.Servicio;
import java.util.ArrayList;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import Conceptos.Solicitud;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author evans
 */
public class HandlerSolicitudes {
    // Para obtener todo menos el id.
    private static String obtenerValor(String etiqueta, Element elemento) {
        NodeList nodos = elemento.getElementsByTagName(etiqueta).item(0).getChildNodes();
        Node nodo = (Node)nodos.item(0);
        return nodo.getNodeValue();
    }
    
    private static String obtenerID(Element elemento) {
        return elemento.getAttribute("id");
    }
    
    private static Cliente obtenerCliente (Element elemento) {
        Cliente  cliente = new Cliente();
        NodeList nodos = elemento.getElementsByTagName("cliente").item(0).getChildNodes();
        
        // Buscar cada atributo del objeto
        cliente.setId(Integer.parseInt(obtenerValor("id", (Element)nodos)));
        cliente.setNombre(obtenerValor("nombre", (Element)nodos));
        cliente.setTelefono(obtenerValor("telefono", (Element)nodos));
        cliente.setEmail(obtenerValor("email", (Element)nodos));
        
        return cliente;
    }           
    
    private static ArrayList<Integer> obtenerServicios(Element elemento) {
        NodeList nodos = elemento.getElementsByTagName("id");
        ArrayList<Integer> idS = new ArrayList(); 

        // Empieza desde 4 para evitar  los primeros ID's, que son de las instancias
        for (int i = 4; i < nodos.getLength(); i++) {
            Element idElemento = (Element)nodos.item(i);
            String valor = idElemento.getTextContent().trim();
            if (!valor.isEmpty()) {
                idS.add(Integer.valueOf(valor));
            }
        }
        return idS;
    }
    
    private static Abogado obtenerAbogado(Element elemento) {
        Abogado abogado = new Abogado();
        NodeList nodos = elemento.getElementsByTagName("abogado").item(0).getChildNodes();
        
        // Buscar cada atributo del objeto
        abogado.setId(Integer.parseInt(obtenerValor("id", (Element)nodos)));
        abogado.setNombre(obtenerValor("nombre", (Element)nodos));
        abogado.setTelefono(obtenerValor("telefono", (Element)nodos));
        abogado.setPuesto(obtenerValor("puesto", (Element)nodos));
        abogado.setServicios(obtenerServicios((Element)nodos));
        return abogado;
    }
    
    private static Estado obtenerEstado(Element elemento) {
        Estado estado = new Estado();
        NodeList nodos = elemento.getElementsByTagName("estado").item(0).getChildNodes();
        
        // Buscar cada atributo del objeto
        estado.setId(Integer.parseInt(obtenerValor("id", (Element)nodos)));
        estado.setNombre(obtenerValor("nombre", (Element)nodos));
 
        return estado;
    }
    
    private static Servicio obtenerServicio(Element elemento) {
        Servicio servicio = new Servicio();
        NodeList nodos = elemento.getElementsByTagName("servicio").item(0).getChildNodes();
        
        // Buscar cada atributo del objeto
        servicio.setId(Integer.parseInt(obtenerValor("id", (Element)nodos)));
        servicio.setNombre(obtenerValor("nombre", (Element)nodos));
        servicio.setPrecio(Integer.parseInt(obtenerValor("precio", (Element)nodos)));
        
        return servicio;
    }
    
    
    public static ArrayList<Solicitud> cargar(String nombreXML) {
        ArrayList<Solicitud> solicitudes = new ArrayList();
        try { 
            File archivo = new File(nombreXML); //abre el archivo XML
            DocumentBuilderFactory industria = DocumentBuilderFactory.newInstance();
            DocumentBuilder creador = industria.newDocumentBuilder();
            Document documento = creador.parse(archivo);
            
            NodeList nodos = documento.getElementsByTagName("solicitud");
         
            for (int i=0; i < nodos.getLength(); i++) {
                Node nodoCliente = nodos.item(i);
                if (nodoCliente.getNodeType() == Node.ELEMENT_NODE) {
                    Element elemento = (Element)nodoCliente;
                    int id = Integer.parseInt(obtenerID(elemento));
                    
                    // Para la fecha
                    DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    
                    LocalDateTime fecha = LocalDateTime.parse(obtenerValor("fecha_hora", elemento), formato);
                    
                    // Obtener cada objeto
                    
                    Servicio servicio = obtenerServicio(elemento);
                    Cliente cliente = obtenerCliente(elemento);
                    Abogado abogado = obtenerAbogado(elemento);
                    Estado estado = obtenerEstado(elemento);
                    
                    String observaciones = obtenerValor("observaciones", elemento);
                    
                    // Para obtener otros servicios
                    ArrayList<Integer> otrosServicios = obtenerServicios(elemento);
                    Solicitud solicitud = new Solicitud(id, fecha, servicio, cliente, abogado, estado, observaciones);
                    solicitud.setOtrosServicios(otrosServicios);
                    solicitudes.add(solicitud);
                }
            }
                    
        } catch (IOException | NumberFormatException | ParserConfigurationException | SAXException e) {
        }
        return solicitudes;
    }
    
    
    public static void guardar(String nombreXML, ArrayList<Solicitud> solicitudes) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document documento = builder.newDocument();

            // Crear el elemento raíz
            Element raiz = documento.createElement("solicitudes");
            documento.appendChild(raiz);

            // Recorrer la lista de estados y crear nodos
            for (Solicitud s : solicitudes) {
                Element solicitud = documento.createElement("solicitud");
                solicitud.setAttribute("id", String.valueOf(s.getId()));

                
                // Para guardar la fecha 
                Element fecha = documento.createElement("fecha");
                DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                fecha.appendChild(documento.createTextNode(s.getFecha().format(formato)));
                solicitud.appendChild(fecha);
                
                // Guardar cada instancia de la clase
                // Servicio
                Element servicio = documento.createElement("servicio");
                Element idS = documento.createElement("id");
                idS.appendChild(documento.createTextNode(String.valueOf(s.getServicio().getId())));
                servicio.appendChild(idS);
                

                Element nombreS = documento.createElement("nombre");
                nombreS.appendChild(documento.createTextNode(s.getServicio().getNombre()));
                servicio.appendChild(nombreS);
                
                Element precio = documento.createElement("precio");
                precio.appendChild(documento.createTextNode(String.valueOf(s.getServicio().getPrecio())));
                servicio.appendChild(precio);
                
                solicitud.appendChild(servicio);

                
                // Cliente
                Element cliente = documento.createElement("cliente");
                Element idC = documento.createElement("id");
                idC.appendChild(documento.createTextNode(String.valueOf(s.getCliente().getId())));
                cliente.appendChild(idC);
                
                Element nombreC = documento.createElement("nombre");
                nombreC.appendChild(documento.createTextNode(s.getCliente().getNombre()));
                cliente.appendChild(nombreC);
                
                Element telefonoC = documento.createElement("telefono");
                telefonoC.appendChild(documento.createTextNode(s.getCliente().getTelefono()));
                cliente.appendChild(telefonoC);
                
                Element email = documento.createElement("email");
                email.appendChild(documento.createTextNode(s.getCliente().getTelefono()));
                cliente.appendChild(email);
                        
                solicitud.appendChild(cliente);
                
                // Abogado
                Element abogado = documento.createElement("abogado");
                Element idA = documento.createElement("id");
                idA.appendChild(documento.createTextNode(String.valueOf(s.getAbogado().getId())));
                abogado.appendChild(idA);
                
                Element nombreA = documento.createElement("nombre");
                nombreA.appendChild(documento.createTextNode(s.getAbogado().getNombre()));
                abogado.appendChild(nombreA);
                
                Element telefonoA = documento.createElement("telefono");
                telefonoA.appendChild(documento.createTextNode(s.getAbogado().getTelefono()));
                abogado.appendChild(telefonoA);
                
                Element puesto = documento.createElement("puesto");
                puesto.appendChild(documento.createTextNode(s.getAbogado().getPuesto()));
                abogado.appendChild(puesto);
                
                
                // Servicios
                Element servicios = documento.createElement("servicios");
                for (int idServicio : s.getAbogado().getServicios()) {
                    Element id = documento.createElement("id");
                    id.appendChild(documento.createTextNode(String.valueOf(idServicio)));
                    servicios.appendChild(id);
                }
                abogado.appendChild(servicios);
                
                solicitud.appendChild(abogado);
                
                // Estado
                Element estado = documento.createElement("estado");
                Element idE = documento.createElement("id");
                idE.appendChild(documento.createTextNode(String.valueOf(s.getEstado().getId())));
                estado.appendChild(idE);
                
                Element nombreE = documento.createElement("nombre");
                nombreE.appendChild(documento.createTextNode(s.getEstado().getNombre()));
                estado.appendChild(nombreE);
                
                solicitud.appendChild(estado);
                
                // Observaciones
                Element observaciones = documento.createElement("observaciones");
                observaciones.appendChild(documento.createTextNode(s.getObservaciones()));
                solicitud.appendChild(observaciones);
                
                // Otros servicios
                Element otrosServicios = documento.createElement("otros_servicios");
                for (int idServicio : s.getOtrosServicios()) {
                    Element id = documento.createElement("id");
                    id.appendChild(documento.createTextNode(String.valueOf(idServicio)));
                    otrosServicios.appendChild(id);
                }
                
                solicitud.appendChild(otrosServicios);
                
                raiz.appendChild(solicitud);
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
