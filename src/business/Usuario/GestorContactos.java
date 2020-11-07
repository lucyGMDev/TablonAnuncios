package business.Usuario;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import data.dao.Usuario.ContactoDAO;


public class GestorContactos {
    private static GestorContactos instance = null;
    
    public static GestorContactos GetInstance() {
        if (instance == null) {
            instance = new GestorContactos();
        }
        return instance;
    }

    public void AddContact(){
        Contacto contact=new Contacto("i92izded@uco.es","Lucia","Izquierdo Delgado",new Date(),new ArrayList<String>());         


        AddContact(contact);

        System.out.println("Se ha añadido el contacto con email "+contact.getEmail());
        return;
    }

    public void AddContact(Contacto contact){
        ContactoDAO contactDAO = new ContactoDAO();
        

        contactDAO.InsertarContacto(contact);

        
        return;
    }

    public void RemoveContact(){
        Contacto contact=new Contacto("i92izded@uco.es","Lucia","Izquierdo Delgado",new Date(),new ArrayList<String>());         
        RemoveContact(contact);
        System.out.println("Se ha borrado el contacto con email "+contact.getEmail());
    }

    public void RemoveContact(Contacto contact){
        ContactoDAO contactoDAO = new ContactoDAO();
        contactoDAO.BorrarContacto(contact);
    }
    public void UpdateContact(){
        Contacto contact=new Contacto("i92izded@uco.es","Sofia","Izquierdo Delgado",new Date(),new ArrayList<String>());         
        UpdateContact(contact);
        System.out.println("Se ha modificado el contacto con email "+contact.getEmail());
    }

    public void UpdateContact(Contacto contact){
        ContactoDAO contactoDAO = new ContactoDAO();
        contactoDAO.ModificarContacto(contact);
    }

    public void GetContactById(String email){
        ContactoDAO contactoDAO = new ContactoDAO();
       
        Hashtable<String,String> query = contactoDAO.ObtenerContacto(email);

        System.out.println("Email: "+query.get("Email"));
        System.out.println("Nombre: "+query.get("Nombre"));
        System.out.println("Apellidos: "+query.get("Apellidos"));
        System.out.println("Fecha Nacimiento: "+query.get("Fecha_Nacimiento"));
        System.out.println("Intereses: "+query.get("Intereses"));
        return;
    }
}

