package es.uco.pw.business.Anuncios;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Scanner;

import es.uco.pw.business.DTO.DTOAnuncio.AnuncioFlashDTO;
import es.uco.pw.business.DTO.DTOAnuncio.AnuncioGeneralDTO;
import es.uco.pw.business.DTO.DTOAnuncio.AnuncioIndividualizadoDTO;
import es.uco.pw.business.DTO.DTOAnuncio.AnuncioTematicoDTO;
import es.uco.pw.business.Usuario.Contacto;
import es.uco.pw.business.Usuario.GestorContactos;
import es.uco.pw.data.dao.Anuncios.AnuncioDAO;
import es.uco.pw.data.dao.Intereses.InteresesDAO;

public class GeneradorAnuncios extends GeneradorAnunciosAbstracto {

    //TODO En general corregir el tema de las fechas, no se guardan bien las horas
    
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

        
        


        ArrayList<String>temas = new ArrayList<String>();
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
                if(temas.size()>0){
                    insertarTemas=false;
                }else{
                    System.out.println("Tienes que añadir almenos un tema");
                }
            }else{
                
                if(listaIntereses.containsKey(opcion)){
                    String interes=listaIntereses.get(opcion);
                    if(temas.contains(interes)){
                        System.out.println("Ya has introducido ese interes");
                    }else{
                        temas.add(interes);
                    }
                }else{
                    System.out.println("Has introducido una opcion desconocida");
                }
            }
            

        }while(insertarTemas);

        
        EstadoAnuncio estadoAnuncio;
        System.out.println("Quieres guardar el anuncio en estado edicion o publicarlo");
        System.out.println("1: Publicar");
        System.out.println("2: Edicion");
        int option=Integer.parseInt(sc.nextLine());
        switch(option){
            case 1:
                if(fechaPublicacion.after(new Date(System.currentTimeMillis()))){
                    estadoAnuncio=EstadoAnuncio.enEspera;
                }else{
                    estadoAnuncio=EstadoAnuncio.publicado;
                }
                
            break;
            case 2:
                estadoAnuncio=EstadoAnuncio.editado;
            break;
            default:
                System.out.println("Opcion no contempladad");
           
                return null;
            
        }
        
        int id=0;
        
        AnuncioDAO anuncioDAO=new AnuncioDAO();
        

        
        
        AnuncioTematicoDTO anuncioDTO = new AnuncioTematicoDTO(titulo, cuerpoAnuncio, fechaPublicacion, propietario, estadoAnuncio, destinatarios, temas);
        anuncioDAO.InsertarAnuncioTematico(anuncioDTO);
        id=anuncioDAO.GetMaxID();
        AnuncioTematico anuncio= new AnuncioTematico(id, titulo, cuerpoAnuncio, fechaPublicacion, propietario, estadoAnuncio, destinatarios, temas);
        
        

        
   

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

        EstadoAnuncio estadoAnuncio;
        System.out.println("Quieres guardar el anuncio en estado edicion o publicarlo");
        System.out.println("1: Publicar");
        System.out.println("2: Edicion");
        int option=Integer.parseInt(sc.nextLine());
        switch(option){
            case 1:
                if(fechaPublicacion.after(new Date(System.currentTimeMillis()))){
                    estadoAnuncio=EstadoAnuncio.enEspera;
                }else{
                    estadoAnuncio=EstadoAnuncio.publicado;
                }

            break;
            case 2:
                estadoAnuncio=EstadoAnuncio.editado;
            break;
            default:
                System.out.println("Opcion no contempladad");
             
                return null;
            
        }

             
        AnuncioDAO anuncioDAO=new AnuncioDAO();
        

        

        
        AnuncioGeneralDTO anuncioDTO=new AnuncioGeneralDTO(titulo, cuerpoAnuncio, fechaPublicacion, propietario, estadoAnuncio, destinatarios);
        anuncioDAO.InsertarAnuncioGeneral(anuncioDTO);
        
        int id=anuncioDAO.GetMaxID();
        anuncio = new AnuncioGeneral(id, titulo, cuerpoAnuncio, fechaPublicacion, propietario, estadoAnuncio, destinatarios);

        return anuncio;
    }

    @Override
    public AnuncioFlash CrearAnuncioFlash(Contacto propietario) {
        
        Scanner sc = new Scanner(System.in);
        AnuncioFlash anuncio=null;

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
           
            return null;
        }

        Date fecha_fin;
        do{
            System.out.println("Indique la fecha de caducidad del anuncio(Ejemplo de formato 16:50:50 17/11/2020)");
            fechaString = sc.nextLine();
            try {
                fecha_fin = dateFormat.parse(fechaString);
                if(fecha_fin.before(fechaPublicacion)){
                    System.out.println("La fecha de caducidad del anuncio debe de ser superior a la de publicacion");
                }
            } catch (ParseException e) {
                e.printStackTrace();

                return null;
            }
        }while(fecha_fin.before(fechaPublicacion));  
        


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

        EstadoAnuncio estadoAnuncio;
        System.out.println("Quieres guardar el anuncio en estado edicion o publicarlo");
        System.out.println("1: Publicar");
        System.out.println("2: Edicion");
        int option=Integer.parseInt(sc.nextLine());
        switch(option){
            case 1:
                if(fechaPublicacion.after(new Date(System.currentTimeMillis()))){
                    estadoAnuncio=EstadoAnuncio.enEspera;
                }else if(fecha_fin.before(new Date(System.currentTimeMillis()))){
                    estadoAnuncio=EstadoAnuncio.archivado;
                }
                else{
                    estadoAnuncio=EstadoAnuncio.publicado;
                }
               
            break;
            case 2:
                estadoAnuncio=EstadoAnuncio.editado;
            break;
            default:
                System.out.println("Opcion no contempladad");

                return null;
            
        }

        int id=0;        
        AnuncioDAO anuncioDAO=new AnuncioDAO();
        

        AnuncioFlashDTO anuncioDTO = new AnuncioFlashDTO(titulo, cuerpoAnuncio, fechaPublicacion, fecha_fin, propietario, estadoAnuncio, destinatarios);
        
        anuncioDAO.InsertarAnuncioFlash(anuncioDTO);
       
        id=anuncioDAO.GetMaxID();
        anuncio = new AnuncioFlash(id, titulo, cuerpoAnuncio, fechaPublicacion,fecha_fin, propietario, estadoAnuncio, destinatarios);


        return anuncio;


    }

    @Override
    public AnuncioIndividualizado CrearAnuncioIndividualizado(Contacto propietario) {
        Scanner sc = new Scanner(System.in);
        AnuncioIndividualizado anuncio=null;

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

            return null;
        }
        


        ArrayList<Contacto> destinatarios=new ArrayList<Contacto>();
        boolean introducirDestinatarios=true;
        
        do{
            System.out.println("Introduce el correo de un contacto para introducirlo en tus destinatarios o exit para terminar:");
            String correo=sc.nextLine();
            if(correo.toLowerCase().equals("exit")){   
                if(destinatarios.isEmpty()){
                    System.out.println("Es un anuncio individualizado, tienes que introducir al menos un destinatario");
                }else{             
                    introducirDestinatarios=false;    
                }            
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

        EstadoAnuncio estadoAnuncio;
        System.out.println("Quieres guardar el anuncio en estado edicion o publicarlo");
        System.out.println("1: Publicar");
        System.out.println("2: Edicion");
        int option=Integer.parseInt(sc.nextLine());
        switch(option){
            case 1:
                if(fechaPublicacion.after(new Date(System.currentTimeMillis()))){
                    estadoAnuncio=EstadoAnuncio.enEspera;
                }else{
                    estadoAnuncio=EstadoAnuncio.publicado;
                }
                
            break;
            case 2:
                estadoAnuncio=EstadoAnuncio.editado;
            break;
            default:
                System.out.println("Opcion no contempladad");
          
                return null;
            
        }

        int id=0;        
        AnuncioDAO anuncioDAO=new AnuncioDAO();
        

        
        AnuncioIndividualizadoDTO anuncioDTO = new AnuncioIndividualizadoDTO(titulo, cuerpoAnuncio, fechaPublicacion, propietario, estadoAnuncio, destinatarios);
 
        
        anuncioDAO.InsertarAnuncioIndividualizado(anuncioDTO);

        
        id=anuncioDAO.GetMaxID();
        anuncio = new AnuncioIndividualizado(id, titulo, cuerpoAnuncio, fechaPublicacion, propietario, estadoAnuncio, destinatarios);


        return anuncio;
    }
    
    
    
}
