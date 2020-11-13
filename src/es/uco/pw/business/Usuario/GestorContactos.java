package es.uco.pw.business.Usuario;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;
import java.text.ParseException; //Para manejar el parse de la fecha de nacimiento
import es.uco.pw.data.dao.Usuario.ContactoDAO;


public class GestorContactos {
    private static GestorContactos instance = null;
    
    public static GestorContactos GetInstance() {
        if (instance == null) {
            instance = new GestorContactos();
        }
        return instance;
    }

    public Contacto AddContact(){
        
        Scanner in= new Scanner(System.in);

        System.out.println("Introduzca sus datos de contacto");

        System.out.print("Correo: ")

        String email=in.nextLine();

        System.out.print("Nombre: ")

        String nombre=in.nextLine();

        System.out.print("Apellidos: ")

        String apellidos=in.nextLine();

        System.out.print("Fecha de nacimiento (dd-mm-aaaa): ")
        String fecha_string=in.nextLine();
        SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");

        try
        {
             Date fecha_nacimiento=sdf.parse(fecha_string);
        }

        catch(ParseException e)
        {
            e.printStackTrace();
        }

        String intereses="por poner algo xd"; //No sé cómo pedir los intereses
       
     Contacto contact=new Contacto(email,nombre,apellidos,fecha_nacimiento,intereses); //

        AddContact(contact);

        System.out.println("Se ha añadido el contacto con email "+contact.getEmail());
        return contact;
    }

    public void AddContact(Contacto contact){
        ContactoDAO contactDAO = new ContactoDAO();
        

        contactDAO.InsertarContacto(contact);

        
        return;
    }

    public void RemoveContact(){

        Scanner in= new Scanner(System.in);

        System.out.print("Correo del usuario a eliminar: ")

        String email=in.nextLine();
        
       Contacto contact= GetContactById(email);

        RemoveContact(contact);
        System.out.println("Se ha borrado el contacto con email "+contact.getEmail());
    }

    public void RemoveContact(Contacto contact){
        ContactoDAO contactoDAO = new ContactoDAO();
        contactoDAO.BorrarContacto(contact);
    }
    public void UpdateContact(){

         System.out.print("Correo del usuario a modificar: ")

        String email=in.nextLine();
        
       Contacto contact= GetContactById(email);
       
        UpdateContact(contact);
        System.out.println("Se ha modificado el contacto con email "+contact.getEmail());
    }

    public void UpdateContact(Contacto contact){
        ContactoDAO contactoDAO = new ContactoDAO();
        contactoDAO.ModificarContacto(contact);
    }

    public Contacto GetContactById(String email){
        if(!ContactExits(email)){
            System.out.println("No hay ningun contacto en la base de datos con el siguiente email: "+email);
            return null;
        }
        ContactoDAO contactoDAO = new ContactoDAO();
        Contacto contact=null;
        Hashtable<String,String> query = contactoDAO.ObtenerContacto(email);
        String nombre=query.get("Nombre");
        String apellidos=query.get("Apellidos");
        
        DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",Locale.ENGLISH);
        Date fechaNacimiento=null;
        try{
            fechaNacimiento=dateFormat.parse(query.get("Fecha_Nacimiento"));
        }catch(Exception error){
            error.printStackTrace();
            return null;
        }

        ArrayList<String> intereses= new ArrayList<String>(Arrays.asList(query.get("Intereses").split(",")));
        contact = new Contacto(email,nombre,apellidos,fechaNacimiento,intereses);

        
        return contact;
    }

    public boolean ContactExits(String email){
        ContactoDAO contactoDAO = new ContactoDAO();
        
        if(contactoDAO.ObtenerContacto(email).isEmpty()){
            return false;
        }else{
            return true;
        }
    }
}

