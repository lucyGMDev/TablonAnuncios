package business.Anuncios;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

import business.Usuario.Contacto;
import business.Usuario.GestorContactos;
import data.dao.Anuncios.AnunciosDAO;

public class GeneradorAnuncios extends GeneradorAnunciosAbstracto {

    //TODO En general corrgir el tema de las fechas, no se guardan bien las horas
    @Override
    public AnuncioTematico CrearAnuncioTematico(Contacto propietario){
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce el titulo de tu anuncio");
        String titulo=sc.nextLine();
        
        
        System.out.println("Introduce el cuerpo de tu anuncio(Introduce texto hasta que escribas una linea con exit): ");
        String cuerpoAnuncio = "";
        String linea;
        do {
            linea = sc.nextLine();
            if (!linea.toLowerCase().equals("exit")) {
                if(!cuerpoAnuncio.equals(""))
                    cuerpoAnuncio+="\n";
                cuerpoAnuncio += linea;
                
            }
        } while (!linea.toLowerCase().equals("exit"));

        Date fechaPublicacion=null;
        DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",Locale.ENGLISH);
        System.out.println("Indique la fecha de publicacion del anuncio(Ejemplo de formato Sat Nov 07 16:50:50 CET 2020)");
        String fechaString = sc.nextLine();
        try {
            fechaPublicacion = dateFormat.parse(fechaString);
        } catch (ParseException e) {
            e.printStackTrace();
            sc.close();
            return null;
        }
        


        ArrayList<Contacto> destinatarios=new ArrayList<Contacto>();
        boolean introducirDestinatarios=true;
        
        do{
            System.out.println("Introduce el correo de un contacto para introducirlo en tus destinatarios o exit para terminar:");
            String correo=sc.nextLine();
            if(correo.toLowerCase().equals("exit")){                
                introducirDestinatarios=false;                
            }else{
                if(GestorContactos.GetInstance().ContactExits(correo)){                    
                    Contacto contacto = GestorContactos.GetInstance().GetContactById(correo);
                    if(!destinatarios.contains(contacto)){
                        destinatarios.add(contacto);
                    }else{
                        System.out.println("Este contacto ya sea añadido");
                    }
                }else{
                    System.out.println("El contacto introducido no existe");
                }
            }

        }while(introducirDestinatarios);

        EstadosAnuncio estadoAnuncio;
        System.out.println("Quieres guardar el anuncio en estado edicion o publicarlo");
        System.out.println("1: Publicar");
        System.out.println("2: Edicion");
        int option=Integer.parseInt(sc.nextLine());
        switch(option){
            case 1:
                if(fechaPublicacion.after(new Date(System.currentTimeMillis()))){
                    estadoAnuncio=EstadosAnuncio.enEspera;
                }else{
                    estadoAnuncio=EstadosAnuncio.publicado;
                }
                estadoAnuncio=EstadosAnuncio.publicado;
            break;
            case 2:
                estadoAnuncio=EstadosAnuncio.editado;
            break;
            default:
                System.out.println("Opcion no contempladad");
                sc.close();
                return null;
            
        }
        


        ArrayList<String>temas = new ArrayList<String>();
        //TODO hacer temas
        
        int id=0;
        
        AnunciosDAO anuncioDAO=new AnunciosDAO();
        id=anuncioDAO.GetMaxID()+1;


        AnuncioTematico anuncio= new AnuncioTematico(id, titulo, cuerpoAnuncio, fechaPublicacion, propietario, estadoAnuncio, destinatarios, temas);
        
        
        anuncioDAO.InsertarAnuncioTematico(anuncio);

        
        sc.close();

        return anuncio;
    }

    @Override
    public AnuncioGeneral CrearAnuncioGeneral(Contacto propietario) {

        Scanner sc = new Scanner(System.in);
        AnuncioGeneral anuncio=null;

        System.out.println("Introduce el titulo de tu anuncio");
        String titulo=sc.nextLine();
        
        
        System.out.println("Introduce el cuerpo de tu anuncio(Introduce texto hasta que escribas una linea con exit): ");
        String cuerpoAnuncio = "";
        String linea;
        do {
            linea = sc.nextLine();
            if (!linea.toLowerCase().equals("exit")) {
                if(!cuerpoAnuncio.equals(""))
                    cuerpoAnuncio+="\n";
                cuerpoAnuncio += linea;
                
            }
        } while (!linea.toLowerCase().equals("exit"));

        Date fechaPublicacion=null;
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        System.out.println("Indique la fecha de publicacion del anuncio(Ejemplo de formato 16:50:50 17/11/2020)");
        String fechaString = sc.nextLine();
        try {
            fechaPublicacion = dateFormat.parse(fechaString);
        } catch (ParseException e) {
            e.printStackTrace();
            sc.close();
            return null;
        }
        


        ArrayList<Contacto> destinatarios=new ArrayList<Contacto>();
        boolean introducirDestinatarios=true;
        
        do{
            System.out.println("Introduce el correo de un contacto para introducirlo en tus destinatarios o exit para terminar:");
            String correo=sc.nextLine();
            if(correo.toLowerCase().equals("exit")){                
                introducirDestinatarios=false;                
            }else{
                if(GestorContactos.GetInstance().ContactExits(correo)){                    
                    Contacto contacto = GestorContactos.GetInstance().GetContactById(correo);
                    if(!destinatarios.contains(contacto)){
                        destinatarios.add(contacto);
                    }else{
                        System.out.println("Este contacto ya sea añadido");
                    }
                }else{
                    System.out.println("El contacto introducido no existe");
                }
            }

        }while(introducirDestinatarios);

        EstadosAnuncio estadoAnuncio;
        System.out.println("Quieres guardar el anuncio en estado edicion o publicarlo");
        System.out.println("1: Publicar");
        System.out.println("2: Edicion");
        int option=Integer.parseInt(sc.nextLine());
        switch(option){
            case 1:
                if(fechaPublicacion.after(new Date(System.currentTimeMillis()))){
                    estadoAnuncio=EstadosAnuncio.enEspera;
                }else{
                    estadoAnuncio=EstadosAnuncio.publicado;
                }
                estadoAnuncio=EstadosAnuncio.publicado;
            break;
            case 2:
                estadoAnuncio=EstadosAnuncio.editado;
            break;
            default:
                System.out.println("Opcion no contempladad");
                sc.close();
                return null;
            
        }

        int id=0;        
        AnunciosDAO anuncioDAO=new AnunciosDAO();
        id=anuncioDAO.GetMaxID()+1;

        anuncio = new AnuncioGeneral(id, titulo, cuerpoAnuncio, fechaPublicacion, propietario, estadoAnuncio, destinatarios);

        //TODO añadir a la base de datos

        sc.close();
        return anuncio;
    }
    
    
    
}
