package es.uco.pw.business.Usuario;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Scanner;
import java.text.ParseException; //Para manejar el parse de la fecha de nacimiento

import es.uco.pw.business.DTO.DTOUsuario.ContactoDTO;
import es.uco.pw.data.dao.Intereses.InteresesDAO;
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
        
        Scanner sc= new Scanner(System.in);

        System.out.println("Introduzca sus datos de contacto");

        System.out.print("Correo: ");

        String email=sc.nextLine();

        if(ContactExits(email)){
            System.out.println("Ya existe un contacto con ese email");
            return null;
        }

        System.out.print("Nombre: ");

        String nombre=sc.nextLine();

        System.out.print("Apellidos: ");

        String apellidos=sc.nextLine();

        System.out.print("Fecha de nacimiento (dd/mm/aaaa): ");
        String fecha_string=sc.nextLine();
        SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");
        Date fecha_nacimiento=null;
        try
        {
            fecha_nacimiento=sdf.parse(fecha_string);
        }catch(ParseException e)
        {
            e.printStackTrace();
        }
        ArrayList<String> intereses=new ArrayList<String>();
        InteresesDAO interesesDAO = new InteresesDAO();
        Hashtable<Integer,String> listaIntereses = interesesDAO.DevolverIntereses();
        boolean insertarTemas=true;
        do{
            System.out.println("Introduzca un tema o escriba -1 para salir");
            Enumeration<Integer> e=listaIntereses.keys();
            while(e.hasMoreElements()){
                int key = e.nextElement();
                System.out.println(key+" - "+listaIntereses.get(key));
            }
            int opcion= Integer.parseInt(sc.nextLine());
            if(opcion==-1){
                insertarTemas=false;              
            }else{
                
                if(listaIntereses.containsKey(opcion)){
                    String interes=listaIntereses.get(opcion);
                    if(intereses.contains(interes)){
                        System.out.println("Ya has introducido ese interes");
                    }else{
                        intereses.add(interes);
                    }
                }else{
                    System.out.println("Has introducido una opcion desconocida");
                }
            }
        }while(insertarTemas);
       
        ContactoDTO contact=new ContactoDTO(email,nombre,apellidos,fecha_nacimiento,intereses); 

        AddContact(contact);

        System.out.println("Se ha añadido el contacto con email "+contact.getEmail());
        Contacto ret= new Contacto(email, nombre, apellidos, fecha_nacimiento, intereses);
        return ret;
    }

    public void AddContact(ContactoDTO contact){
        ContactoDAO contactDAO = new ContactoDAO();
        

        contactDAO.InsertarContacto(contact);

        
        return;
    }

    public void RemoveContact(){

        Scanner sc= new Scanner(System.in);

        System.out.print("Correo del usuario a eliminar: ");

        String email=sc.nextLine();
        
       Contacto contact= GetContactById(email);

        RemoveContact(contact);
        System.out.println("Se ha borrado el contacto con email "+contact.getEmail());
    }

    public void RemoveContact(Contacto contact){
        ContactoDAO contactoDAO = new ContactoDAO();
        contactoDAO.BorrarContacto(contact);
    }
    public void UpdateContact(String email){

        Scanner sc = new Scanner(System.in);        
        Contacto contact= GetContactById(email);
        boolean modificarContacto = true;
        do{
            System.out.println("Que desea modificar de su usuario");
            System.out.println("1-Nombre");
            System.out.println("2-Apelldos");
            System.out.println("3-Fecha nacimiento");
            System.out.println("4-Intereses");
            System.out.println("-1-Salir");
            int opcion = Integer.parseInt(sc.nextLine());
            switch(opcion){
                case 1:
                    System.out.println("Introduzca el nuevo nombre");
                    String nombre=sc.nextLine();
                    contact.setName(nombre);
                break;
                case 2:
                    System.out.println("Introduzca el nuevo apellido");
                    String apellido=sc.nextLine();
                    contact.setLastName(apellido);
                break;
                case 3:
                    System.out.print("Introduzca la nueva fecha de nacimiento (dd/mm/aaaa): ");
                    String fecha_string=sc.nextLine();
                    SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");
                    Date fecha_nacimiento=null;
                    try
                    {
                        fecha_nacimiento=sdf.parse(fecha_string);
                    }catch(ParseException e)
                    {
                        e.printStackTrace();
                        return;
                    }
                    contact.setBirthDate(fecha_nacimiento);
                break;
                case 4:
                    System.out.println("Introduzca la nueva lista de intereses que desea tener");
                    ArrayList<String> intereses=new ArrayList<String>();
                    InteresesDAO interesesDAO = new InteresesDAO();
                    Hashtable<Integer,String> listaIntereses = interesesDAO.DevolverIntereses();
                    boolean insertarTemas=true;
                    do{
                        System.out.println("Introduzca un tema:");
                        Enumeration<Integer> e=listaIntereses.keys();
                        while(e.hasMoreElements()){
                            int key = e.nextElement();
                            System.out.println(key+" - "+listaIntereses.get(key));
                        }
                        System.out.println("-1-Salir");
                        int opcionTemas= Integer.parseInt(sc.nextLine());
                        if(opcionTemas==-1){
                            insertarTemas=false;              
                        }else{
                            
                            if(listaIntereses.containsKey(opcionTemas)){
                                String interes=listaIntereses.get(opcionTemas);
                                if(intereses.contains(interes)){
                                    System.out.println("Ya has introducido ese interes");
                                }else{
                                    intereses.add(interes);
                                }
                            }else{
                                System.out.println("Has introducido una opcion desconocida");
                            }
                        }
                    }while(insertarTemas);
                    contact.setTagsLists(intereses);
                break;
                case -1:
                    modificarContacto=false;
                break;
            }
        }while(modificarContacto);
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

