package es.uco.pw.business.Anuncios;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;

import es.uco.pw.business.DTO.DTOAnuncio.AnuncioDTO;
import es.uco.pw.business.Usuario.Contacto;
import es.uco.pw.business.Usuario.GestorContactos;
import es.uco.pw.data.dao.Anuncios.AnuncioDAO;
import es.uco.pw.data.dao.Intereses.InteresesDAO;

public class GestorAnuncios {
    public void CrearAnuncio(Contacto usuario){

        Scanner sc= new Scanner(System.in);
        GeneradorAnuncios generadorAnuncios=new GeneradorAnuncios();
        System.out.println("Que tipo de anuncio desea crear: ");
        System.out.println("1-Anuncio General");
        System.out.println("2-Anuncio Tematico");
        System.out.println("3-Anuncio Flash");
        System.out.println("4-Anuncio Individualizado");
        System.out.println("-1-Cancelar");
        int opcion = Integer.parseInt(sc.nextLine());
        switch(opcion){
            case 1:
                System.out.println("Se va a generar un anuncio general");
                generadorAnuncios.CrearAnuncioGeneral(usuario);
            break;
            case 2:
                System.out.println("Se va a generar un anuncio tematico");
                generadorAnuncios.CrearAnuncioTematico(usuario);
            break;
            case 3:
                System.out.println("Se va a generar un anuncio flash");
                generadorAnuncios.CrearAnuncioFlash(usuario);
            break;
            case 4:
                System.out.println("Se va a generar un anuncio individualizado");
                generadorAnuncios.CrearAnuncioIndividualizado(usuario);
            break;
            case -1:
            
                return;
            
            default:
                System.out.println("Opción no contemplada");
            break;
        }

      
    }
    public Hashtable<Integer,AnuncioDTO> ObtenerAnunciosUsuario(Contacto contacto){
        AnuncioDAO anuncioDAO = new AnuncioDAO();
        Hashtable<Integer,AnuncioDTO> ret=anuncioDAO.ObtenerAnunciosUsuario(contacto.getEmail());
        
        return ret;
    }

    public void BorrarAnuncioId(int id){
        AnuncioDAO anuncioDAO = new AnuncioDAO();
        anuncioDAO.ArchivarAnuncio(id);

    }

    public Hashtable<Integer,AnuncioDTO> MostrarTablonAnuncio(){
        AnuncioDAO anuncioDAO = new AnuncioDAO();
        Hashtable<Integer,AnuncioDTO> anuncios=anuncioDAO.ObtenerAnuncios();
        
        return anuncios;
    }

    public ArrayList<String> ObtenerDestinatariosAnuncio(int id){
        AnuncioDAO anuncioDAO=new AnuncioDAO();
        return anuncioDAO.ObtenerDestinatariosAnuncio(id);
    }

    public void ArchivarAnuncio(int id){
        AnuncioDAO anuncioDAO = new AnuncioDAO();
        anuncioDAO.ArchivarAnuncio(id);
        return;
    }

    public ArrayList<AnuncioDTO> ObtenerTablonUsuario(Contacto usuario){
        ArrayList<AnuncioDTO> tablonUsuario = new ArrayList<AnuncioDTO>();
       
        Hashtable<Integer,AnuncioDTO> anuncios = MostrarTablonAnuncio();        
        Enumeration<Integer> anuncio=anuncios.keys();
        while(anuncio.hasMoreElements()){
            int key = anuncio.nextElement();
            AnuncioDTO anuncioDTO = anuncios.get(key);
            if(anuncioDTO.getEstadoAnuncio().equals(EstadoAnuncio.archivado)){
                continue;
            }
            if(anuncioDTO.getEmailPropietario().equals(usuario.getEmail())){
                tablonUsuario.add(anuncioDTO);
                continue;
            }
            if(anuncioDTO.getTipo().equals(TipoAnuncio.General)){
                tablonUsuario.add(anuncioDTO);
            }else if(anuncioDTO.getTipo().equals(TipoAnuncio.Flash)){
                if(anuncioDTO.getFechaFin().before(new Date())){
                    ArchivarAnuncio(anuncioDTO.getId());
                    continue;
                }else{
                    tablonUsuario.add(anuncioDTO);
                }
            }else if(anuncioDTO.getTipo().equals(TipoAnuncio.Tematico)){
                for(String tema:anuncioDTO.getTemas()){
                    if(usuario.getTagsLists().contains(tema)){
                        tablonUsuario.add(anuncioDTO);
                        break;
                    }
                }                
            }else if(anuncioDTO.getTipo().equals(TipoAnuncio.Individualizado)){
                ArrayList<String> destinatarios=ObtenerDestinatariosAnuncio(anuncioDTO.getId());
                if(destinatarios.contains(usuario.getEmail())){
                    tablonUsuario.add(anuncioDTO);
                }
            }
        }
        return tablonUsuario;
    }

    public void ModificarAnuncio(int id){
        AnuncioDAO anuncioDAO = new AnuncioDAO();
        AnuncioDTO anuncio = anuncioDAO.ObtenerAnuncioID(id);
        if(anuncio.getTipo().equals(TipoAnuncio.General)){
            ModificarAnuncioGeneral(anuncio);
        }else if(anuncio.getTipo().equals(TipoAnuncio.Tematico)){
            ModificarAnuncioTematico(anuncio);
        }else if(anuncio.getTipo().equals(TipoAnuncio.Individualizado)){
            ModificarAnuncioIndividualizado(anuncio);
        }else if(anuncio.getTipo().equals(TipoAnuncio.Flash)){
            ModificarAnuncioFlash(anuncio);
        }
    }

    public void ModificarAnuncioGeneral(AnuncioDTO anuncio){
        boolean modificarAnuncio=true;
        Scanner sc = new Scanner(System.in);
        do{
            System.out.println("Que desea modificar");
            System.out.println("1-Titulo del anuncio");
            System.out.println("2-Cuerpo del anuncio");
            System.out.println("3-Estado Anuncio");
            System.out.println("4-Destinatarios");
            System.out.println("-1-Salir");
            int opcion = Integer.parseInt(sc.nextLine());
            switch(opcion){
                case 1:
                    System.out.println("Introduce el nuevo titulo del anuncio");
                    String titulo = sc.nextLine();
                    anuncio.setTitulo(titulo);
                break;
                case 2:
                    System.out.println("Introduce el nuevo cuerpo de tu anuncio(Introduce texto hasta que escribas una linea con exit): ");
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
                    anuncio.setCuerpo(cuerpoAnuncio);
                break;
                case 3:
                    System.out.println("Que estado quieres que tenga tu anuncio");
                    System.out.println("1-Publicado");
                    System.out.println("2-Editado");
                    System.out.println("-1-No editar");
                    int opcionEstado=Integer.parseInt(sc.nextLine());
                    switch(opcionEstado){
                        case 1:
                            anuncio.setEstadoAnuncio(EstadoAnuncio.publicado);
                        break;
                        case 2:
                            anuncio.setEstadoAnuncio(EstadoAnuncio.editado);
                        break;
                        default:
                            continue;
                    }
                break;
                case 4:
                    ArrayList<String> destinatarios=new ArrayList<String>();
                    boolean introducirDestinatarios=true;
                    System.out.println("Introduce la lista de contactos que quieres que sean los destinatarios del anuncio");
                    do{
                        System.out.println("Introduce el correo de un contacto para introducirlo en tus destinatarios o exit para terminar:");
                        String correo=sc.nextLine();
                        if(correo.toLowerCase().equals("exit")){                
                            introducirDestinatarios=false;                
                        }else{
                            if(GestorContactos.GetInstance().ContactExits(correo)){                    
                                
                                if(!destinatarios.contains(correo)){
                                    destinatarios.add(correo);
                                }else{
                                    System.out.println("Este contacto ya sea añadido");
                                }
                            }else{
                                System.out.println("El contacto introducido no existe");
                            }
                        }
            
                    }while(introducirDestinatarios);
                    anuncio.setDestinatarios(destinatarios);
                break;
                case -1:
                    modificarAnuncio=false;
                break;
            }
        }while(modificarAnuncio);

        AnuncioDAO anuncioDAO = new AnuncioDAO();
        anuncioDAO.ModificarAnuncioGeneral(anuncio);
    }

    public void ModificarAnuncioTematico(AnuncioDTO anuncio){
        boolean modificarAnuncio=true;
        Scanner sc = new Scanner(System.in);
        do{
            System.out.println("Que desea modificar");
            System.out.println("1-Titulo del anuncio");
            System.out.println("2-Cuerpo del anuncio");
            System.out.println("3-Estado Anuncio");
            System.out.println("4-Destinatarios");
            System.out.println("5-Temas");
            System.out.println("-1-Salir");
            int opcion = Integer.parseInt(sc.nextLine());
            switch(opcion){
                case 1:
                    System.out.println("Introduce el nuevo titulo del anuncio");
                    String titulo = sc.nextLine();
                    anuncio.setTitulo(titulo);
                break;
                case 2:
                    System.out.println("Introduce el nuevo cuerpo de tu anuncio(Introduce texto hasta que escribas una linea con exit): ");
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
                    anuncio.setCuerpo(cuerpoAnuncio);
                break;
                case 3:
                    System.out.println("Que estado quieres que tenga tu anuncio");
                    System.out.println("1-Publicado");
                    System.out.println("2-Editado");
                    System.out.println("-1-No editar");
                    int opcionEstado=Integer.parseInt(sc.nextLine());
                    switch(opcionEstado){
                        case 1:
                            anuncio.setEstadoAnuncio(EstadoAnuncio.publicado);
                        break;
                        case 2:
                            anuncio.setEstadoAnuncio(EstadoAnuncio.editado);
                        break;
                        default:
                            continue;
                    }
                break;
                case 4:
                    ArrayList<String> destinatarios=new ArrayList<String>();
                    boolean introducirDestinatarios=true;
                    System.out.println("Introduce la lista de contactos que quieres que sean los destinatarios del anuncio");
                    do{
                        System.out.println("Introduce el correo de un contacto para introducirlo en tus destinatarios o exit para terminar:");
                        String correo=sc.nextLine();
                        if(correo.toLowerCase().equals("exit")){                
                            introducirDestinatarios=false;                
                        }else{
                            if(GestorContactos.GetInstance().ContactExits(correo)){                    
                                
                                if(!destinatarios.contains(correo)){
                                    destinatarios.add(correo);
                                }else{
                                    System.out.println("Este contacto ya se ha añadido");
                                }
                            }else{
                                System.out.println("El contacto introducido no existe");
                            }
                        }
            
                    }while(introducirDestinatarios);
                    anuncio.setDestinatarios(destinatarios);
                break;
                case 5:
                    ArrayList<String>temas = new ArrayList<String>();
                    InteresesDAO interesesDAO = new InteresesDAO();
                    Hashtable<Integer,String> listaIntereses = interesesDAO.DevolverIntereses();
                    boolean insertarTemas=true;
                    System.out.println("Introduce los temas que deseas que tenga tu anuncio");
                    do{
                        System.out.println("Introduzca un tema o escriba -1 para salir");
                        Enumeration<Integer> e=listaIntereses.keys();
                        while(e.hasMoreElements()){
                            int key = e.nextElement();
                            System.out.println(key+" - "+listaIntereses.get(key));
                        }
                        int opcionTema= Integer.parseInt(sc.nextLine());
                        if(opcionTema==-1){
                            if(temas.size()>0){
                                insertarTemas=false;
                            }else{
                                System.out.println("Tienes que añadir almenos un tema");
                            }
                        }else{
                            
                            if(listaIntereses.containsKey(opcionTema)){
                                String interes=listaIntereses.get(opcionTema);
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
                    anuncio.setTemas(temas);
                break;
                case -1:
                    modificarAnuncio=false;
                break;
            }
        }while(modificarAnuncio);
        AnuncioDAO anuncioDAO = new AnuncioDAO();
        anuncioDAO.ModificarAnuncioTematico(anuncio);
    }

    public void ModificarAnuncioIndividualizado(AnuncioDTO anuncio){
        boolean modificarAnuncio=true;
        Scanner sc = new Scanner(System.in);
        do{
            System.out.println("Que desea modificar");
            System.out.println("1-Titulo del anuncio");
            System.out.println("2-Cuerpo del anuncio");
            System.out.println("3-Estado Anuncio");
            System.out.println("4-Destinatarios");
            System.out.println("-1-Salir");
            int opcion = Integer.parseInt(sc.nextLine());
            switch(opcion){
                case 1:
                    System.out.println("Introduce el nuevo titulo del anuncio");
                    String titulo = sc.nextLine();
                    anuncio.setTitulo(titulo);
                break;
                case 2:
                    System.out.println("Introduce el nuevo cuerpo de tu anuncio(Introduce texto hasta que escribas una linea con exit): ");
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
                    anuncio.setCuerpo(cuerpoAnuncio);
                break;
                case 3:
                    System.out.println("Que estado quieres que tenga tu anuncio");
                    System.out.println("1-Publicado");
                    System.out.println("2-Editado");
                    System.out.println("-1-No editar");
                    int opcionEstado=Integer.parseInt(sc.nextLine());
                    switch(opcionEstado){
                        case 1:
                            anuncio.setEstadoAnuncio(EstadoAnuncio.publicado);
                        break;
                        case 2:
                            anuncio.setEstadoAnuncio(EstadoAnuncio.editado);
                        break;
                        default:
                            continue;
                    }
                break;
                case 4:
                    ArrayList<String> destinatarios=new ArrayList<String>();
                    boolean introducirDestinatarios=true;
                    System.out.println("Introduce la lista de contactos que quieres que sean los destinatarios del anuncio");
                    do{
                        System.out.println("Introduce el correo de un contacto para introducirlo en tus destinatarios o exit para terminar:");
                        String correo=sc.nextLine();
                        if(correo.toLowerCase().equals("exit")){  
                            if(destinatarios.isEmpty()){
                                System.out.println("No pudes dejar la lista de destinatarios vacia, debes añadir al menos un destinatario en un anuncio individualizado");
                            }else{            
                                introducirDestinatarios=false;   
                            }             
                        }else{
                            if(GestorContactos.GetInstance().ContactExits(correo)){                    
                                
                                if(!destinatarios.contains(correo)){
                                    destinatarios.add(correo);
                                }else{
                                    System.out.println("Este contacto ya sea añadido");
                                }
                            }else{
                                System.out.println("El contacto introducido no existe");
                            }
                        }
            
                    }while(introducirDestinatarios);
                    anuncio.setDestinatarios(destinatarios);
                break;
                case -1:
                    modificarAnuncio=false;
                break;
            }
        }while(modificarAnuncio);
        AnuncioDAO anuncioDAO = new AnuncioDAO();
        anuncioDAO.ModificarAnuncioIndividualizado(anuncio);
    }

    public void ModificarAnuncioFlash(AnuncioDTO anuncio){
        boolean modificarAnuncio=true;
        Scanner sc = new Scanner(System.in);
        do{
            System.out.println("Que desea modificar");
            System.out.println("1-Titulo del anuncio");
            System.out.println("2-Cuerpo del anuncio");
            System.out.println("3-Estado Anuncio");
            System.out.println("4-Destinatarios");
            System.out.println("5-Fecha caducidad");
            System.out.println("-1-Salir");
            int opcion = Integer.parseInt(sc.nextLine());
            switch(opcion){
                case 1:
                    System.out.println("Introduce el nuevo titulo del anuncio");
                    String titulo = sc.nextLine();
                    anuncio.setTitulo(titulo);
                break;
                case 2:
                    System.out.println("Introduce el nuevo cuerpo de tu anuncio(Introduce texto hasta que escribas una linea con exit): ");
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
                    anuncio.setCuerpo(cuerpoAnuncio);
                break;
                case 3:
                    System.out.println("Que estado quieres que tenga tu anuncio");
                    System.out.println("1-Publicado");
                    System.out.println("2-Editado");
                    System.out.println("-1-No editar");
                    int opcionEstado=Integer.parseInt(sc.nextLine());
                    switch(opcionEstado){
                        case 1:
                            anuncio.setEstadoAnuncio(EstadoAnuncio.publicado);
                        break;
                        case 2:
                            anuncio.setEstadoAnuncio(EstadoAnuncio.editado);
                        break;
                        default:
                            continue;
                    }
                break;
                case 4:
                    ArrayList<String> destinatarios=new ArrayList<String>();
                    boolean introducirDestinatarios=true;
                    System.out.println("Introduce la lista de contactos que quieres que sean los destinatarios del anuncio");
                    do{
                        System.out.println("Introduce el correo de un contacto para introducirlo en tus destinatarios o exit para terminar:");
                        String correo=sc.nextLine();
                        if(correo.toLowerCase().equals("exit")){                
                            introducirDestinatarios=false;                
                        }else{
                            if(GestorContactos.GetInstance().ContactExits(correo)){                    
                                
                                if(!destinatarios.contains(correo)){
                                    destinatarios.add(correo);
                                }else{
                                    System.out.println("Este contacto ya sea añadido");
                                }
                            }else{
                                System.out.println("El contacto introducido no existe");
                            }
                        }
            
                    }while(introducirDestinatarios);
                    anuncio.setDestinatarios(destinatarios);
                break;
                case 5:
                    Date fecha_fin;
                    String fechaString;
                    DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
                    do{
                        
                        System.out.println("Indique la fecha de caducidad del anuncio(Ejemplo de formato 16:50:50 17/11/2020)");
                        fechaString = sc.nextLine();
                        try {
                            fecha_fin = dateFormat.parse(fechaString);
                            if(fecha_fin.before(anuncio.getFechaPublicacion())){
                                System.out.println("La fecha de caducidad del anuncio debe de ser superior a la de publicacion");
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                     
                            return ;
                        }
                    }while(fecha_fin.before(anuncio.getFechaPublicacion()));  
                    if(fecha_fin.before(new Date()) && anuncio.getEstadoAnuncio().equals(EstadoAnuncio.publicado)){
                        anuncio.setEstadoAnuncio(EstadoAnuncio.archivado);
                    }
                    anuncio.setFechaFin(fecha_fin);
                break;
                case -1:
                    modificarAnuncio=false;
                break;
            }
        }while(modificarAnuncio);

        AnuncioDAO anuncioDAO = new AnuncioDAO();
        anuncioDAO.ModificarAnuncioFlash(anuncio);
    }

}
