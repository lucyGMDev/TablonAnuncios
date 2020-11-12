package es.uco.pw.business.Anuncios;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

import es.uco.pw.business.DTO.DTOAnuncio.AnuncioDTO;
import es.uco.pw.business.Usuario.Contacto;
import es.uco.pw.data.dao.Anuncios.AnuncioDAO;

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
                sc.close();
                return;
            
            default:
                System.out.println("Opción no contemplada");
            break;
        }

        sc.close();
    }
    public Hashtable<Integer,AnuncioDTO> ObtenerAnunciosUsuario(Contacto contacto){
        AnuncioDAO anuncioDAO = new AnuncioDAO();
        Hashtable<Integer,AnuncioDTO> ret=anuncioDAO.ObtenerAnunciosUsuario(contacto.getEmail());
        
        return ret;
    }

    public void BorrarAnuncioId(int id){
        AnuncioDAO anuncioDAO = new AnuncioDAO();
        anuncioDAO.BorrarAnuncioID(id);

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

}
