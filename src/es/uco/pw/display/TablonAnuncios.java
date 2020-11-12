package es.uco.pw.display;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;

import es.uco.pw.business.Anuncios.GestorAnuncios;
import es.uco.pw.business.DTO.DTOAnuncio.AnuncioDTO;
import es.uco.pw.business.Usuario.Contacto;
import es.uco.pw.business.Usuario.GestorContactos;


public class TablonAnuncios {
    Contacto usuario;
    public static void main(String args[]){
        TablonAnuncios tablon = new TablonAnuncios();
        do{
            if(tablon.UserConected()){
                Scanner sc = new Scanner(System.in);
                System.out.println("Que desea hacer");
                System.out.println("1-Cerrar sesion");
                System.out.println("2-Mostrar los anuncios que has creado");
                System.out.println("3-Añadir anuncio");
                System.out.println("4-Borrar anuncio");
                System.out.println("-1-Salir");
                int opcion=Integer.parseInt(sc.nextLine());
                
                switch(opcion){
                    case 1:
                        tablon.LogOut();
                    break;
                    case 2:
                        tablon.MostrarAnunciosUsuario();
                    break;
                    case 3:
                        tablon.CrearAnuncio();
                    break;
                    case -1:
                        sc.close();
                        return;
                    
                }
           

            }else{
                Scanner sc = new Scanner(System.in);
                System.out.println("Estas desconectado, debe loguearse para acceder al tablon");
                System.out.println("1-Login");
                System.out.println("2-Registrarse");
                System.out.println("3-Salir");
                int opcion= Integer.parseInt(sc.nextLine());
        
                switch(opcion){
                    case 1:
                        tablon.LogIn();
                    break;
                    case 2:
                        tablon.SingUp();
                    break;
                    case 3:
                        sc.close();
                    return;
                }
                
            }
        }while(true);
    }



    public void LogIn(){
        Scanner sc = new Scanner(System.in);
        GestorContactos gestorContactos = new GestorContactos();
        System.out.println("Introduzca su email");
        String email=sc.nextLine();
        
        if(gestorContactos.ContactExits(email)){
            this.usuario=gestorContactos.GetContactById(email);
            System.out.println("Se ha establecido la conexion con el usuario de email "+usuario.getEmail());
        }else{
            System.out.println("El email introducido no se corresponde con el de ningun usuario");
        }
        
       

    }

    public void LogOut(){
        this.usuario=null;
    }

    public boolean UserConected(){
        if(this.usuario==null){
            return false;
        }else{
            return true;
        }
    }

    public TablonAnuncios() {
        this.usuario=null;
    }

    public void MostrarAnunciosUsuario(){
        GestorAnuncios gestorAnuncios = new GestorAnuncios();
        Hashtable<Integer,AnuncioDTO> anuncios=gestorAnuncios.ObtenerAnunciosUsuario(this.usuario);
        Enumeration<Integer> anuncio=anuncios.keys();
        while(anuncio.hasMoreElements()){
            int key=anuncio.nextElement();
            System.out.println("/****************************************************/");
            System.out.println(anuncios.get(key).toString());
        }
    }

    public void CrearAnuncio(){
        GestorAnuncios gestorAnuncios= new GestorAnuncios();
        gestorAnuncios.CrearAnuncio(this.usuario);
    }

    public void SingUp(){
        this.usuario=GestorContactos.GetInstance().AddContact();
    }
}


