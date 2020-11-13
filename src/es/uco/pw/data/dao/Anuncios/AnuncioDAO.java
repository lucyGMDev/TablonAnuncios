package es.uco.pw.data.dao.Anuncios;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;
import java.util.Properties;
import es.uco.pw.business.Anuncios.EstadoAnuncio;
import es.uco.pw.business.Anuncios.TipoAnuncio;
import es.uco.pw.business.DTO.DTOAnuncio.AnuncioDTO;
import es.uco.pw.business.DTO.DTOAnuncio.AnuncioFlashDTO;
import es.uco.pw.business.DTO.DTOAnuncio.AnuncioGeneralDTO;
import es.uco.pw.business.DTO.DTOAnuncio.AnuncioIndividualizadoDTO;
import es.uco.pw.business.DTO.DTOAnuncio.AnuncioTematicoDTO;
import es.uco.pw.business.Usuario.Contacto;
import es.uco.pw.data.dao.common.DAO;

public class AnuncioDAO extends DAO{
   
   
    public int GetMaxID(){
        int maxID=-1;
        Statement stmt=null;
        
        try{
            Connection conect = getConection();
            Properties sqlProp = new Properties();
            InputStream is = new FileInputStream("sql.properties");
            sqlProp.load(is);
            
            stmt=conect.createStatement();
            ResultSet rs=stmt.executeQuery(sqlProp.getProperty("getMaxID.anuncios"));
            while(rs.next()){
                maxID=rs.getInt(1);
            }

            if(stmt != null)
                stmt.close();
            
        }catch(Exception e){
            System.out.println(e);
        }


        return maxID;
    }

    public int InsertarAnuncioTematico(AnuncioTematicoDTO anuncio){
        int status=0;
        try{
            Connection conect = getConection();
            Properties sqlProp = new Properties();
            InputStream is = new FileInputStream("sql.properties");
            sqlProp.load(is); 
            PreparedStatement ps= conect.prepareStatement(sqlProp.getProperty("insertar.AnuncioTematico"));
            ps.setString(1, anuncio.getTipoAnuncio().toString());
            ps.setString(2, anuncio.getTitulo());
            ps.setString(3, anuncio.getCuerpo());
            java.sql.Date fechaPublicacion=new java.sql.Date(anuncio.getFechaPublicacion().getTime());
            ps.setDate(4, fechaPublicacion);
            ps.setString(5, anuncio.getPropietario().getEmail());
            ps.setString(6, anuncio.getEstadoAnuncio().toString());
            String intereses=""; 
                      
            for(String interes : anuncio.getTemas()){
                intereses+=interes.toLowerCase()+",";
            }
            
            if(intereses.length()>0){
                
                intereses=intereses.substring(0,intereses.length()-1);
            }
            
            ps.setString(7, intereses);

            status=ps.executeUpdate();

            int idAnuncio=GetMaxID();
            for(Contacto c : anuncio.getDestinatarios()){
                PreparedStatement psDestinatario=conect.prepareStatement(sqlProp.getProperty("insertar.Destinatario"));
                psDestinatario.setInt(1, idAnuncio);
                psDestinatario.setString(2, c.getEmail());
                psDestinatario.executeUpdate();
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return status;
    }

    public int InsertarAnuncioGeneral(AnuncioGeneralDTO anuncio){
        int status=0;

        try{
            Connection conect = getConection();
            Properties sqlProp = new Properties();
            InputStream is = new FileInputStream("sql.properties");
            sqlProp.load(is);
            PreparedStatement ps = conect.prepareStatement(sqlProp.getProperty("insertar.AnuncioGeneral"));
            ps.setString(1, anuncio.getTipoAnuncio().toString());
            ps.setString(2,anuncio.getTitulo());
            ps.setString(3,anuncio.getCuerpo());
            java.sql.Date fechaPublicacion=new java.sql.Date(anuncio.getFechaPublicacion().getTime());
            ps.setDate(4, fechaPublicacion);
            ps.setString(5,anuncio.getPropietario().getEmail());
            ps.setString(6,anuncio.getEstadoAnuncio().toString());
            status=ps.executeUpdate();

            
            int idAnuncio=GetMaxID();
            for(Contacto c : anuncio.getDestinatarios()){
                PreparedStatement psDestinatario=conect.prepareStatement(sqlProp.getProperty("insertar.Destinatario"));
                psDestinatario.setInt(1, idAnuncio);
                psDestinatario.setString(2, c.getEmail());
                psDestinatario.executeUpdate();
            }

        }catch(Exception e){
            System.out.println(e);
        }

        return status;
    }

    public int InsertarAnuncioFlash(AnuncioFlashDTO anuncio){
        int status=0;

        try{
            
            Connection conect = getConection();
            Properties sqlProp = new Properties();
            InputStream is = new FileInputStream("sql.properties");
            sqlProp.load(is);
            PreparedStatement ps = conect.prepareStatement(sqlProp.getProperty("insertar.AnuncioFlash"));
            ps.setString(1, anuncio.getTipoAnuncio().toString());
            ps.setString(2, anuncio.getTitulo());
            ps.setString(3, anuncio.getCuerpo());
            java.sql.Date fechaPublicacion=new java.sql.Date(anuncio.getFechaPublicacion().getTime());
            ps.setDate(4, fechaPublicacion);
            java.sql.Date fecha_fin=new java.sql.Date(anuncio.getFechaFin().getTime());
            ps.setDate(5, fecha_fin);
            ps.setString(6, anuncio.getPropietario().getEmail());
            ps.setString(7, anuncio.getEstadoAnuncio().toString());
            
            status=ps.executeUpdate();

            int idAnuncio=GetMaxID();
            for(Contacto c : anuncio.getDestinatarios()){
                PreparedStatement psDestinatario=conect.prepareStatement(sqlProp.getProperty("insertar.Destinatario"));
                psDestinatario.setInt(1, idAnuncio);
                psDestinatario.setString(2, c.getEmail());
                psDestinatario.executeUpdate();
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return status;
    }


    public int InsertarAnuncioIndividualizado(AnuncioIndividualizadoDTO anuncio){
        int status=0;

        try{
            Connection conect = getConection();
            Properties sqlProp = new Properties();
            InputStream is = new FileInputStream("sql.properties");
            sqlProp.load(is);
            PreparedStatement ps = conect.prepareStatement(sqlProp.getProperty("insertar.AnuncioIndividualizado"));
            ps.setString(1, anuncio.getTipoAnuncio().toString());
            ps.setString(2,anuncio.getTitulo());
            ps.setString(3,anuncio.getCuerpo());
            java.sql.Date fechaPublicacion=new java.sql.Date(anuncio.getFechaPublicacion().getTime());
            ps.setDate(4, fechaPublicacion);
            ps.setString(5,anuncio.getPropietario().getEmail());
            ps.setString(6,anuncio.getEstadoAnuncio().toString());
            status=ps.executeUpdate();

            int idAnuncio=GetMaxID();
            for(Contacto c : anuncio.getDestinatarios()){
                PreparedStatement psDestinatario=conect.prepareStatement(sqlProp.getProperty("insertar.Destinatario"));
                psDestinatario.setInt(1, idAnuncio);
                psDestinatario.setString(2, c.getEmail());
                psDestinatario.executeUpdate();
            }

        }catch(Exception e){
            System.out.println(e);
        }

        return status;
    }

    
    public Hashtable<Integer, AnuncioDTO> ObtenerAnunciosUsuario(String email){
        Hashtable<Integer,AnuncioDTO> ret = new Hashtable<Integer,AnuncioDTO>();
        try{
            Connection conect = getConection();
            Properties sqlProp = new Properties();
            InputStream is = new FileInputStream("sql.properties");
            sqlProp.load(is);
            PreparedStatement ps = conect.prepareStatement(sqlProp.getProperty("getByEmailPropietario.Anuncio"));
            ps.setString(1, email);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                int id=rs.getInt(1);
                TipoAnuncio tipoAnuncio=TipoAnuncio.valueOf(rs.getString(2));
                String titulo = rs.getString(3);
                String cuerpo = rs.getString(4);
                Date fechaPublicacion = new Date(rs.getDate(5).getTime());
                Date fechaFin=null;
                if(tipoAnuncio.equals(TipoAnuncio.Flash)){
                    fechaFin= new Date(rs.getDate(6).getTime());
                }
                String emailPropietario = rs.getString(7);
                EstadoAnuncio estadoAnuncio = EstadoAnuncio.valueOf(rs.getString(8));
                
                ArrayList<String>temas = null;
                if(tipoAnuncio.equals(TipoAnuncio.Tematico))
                    temas=new ArrayList<String>(Arrays.asList(rs.getString(9).split(",")));
                ArrayList<String> destinatarios= ObtenerDestinatariosAnuncio(id);
                AnuncioDTO anuncioDTO=new AnuncioDTO(id, tipoAnuncio, titulo, cuerpo, fechaPublicacion, fechaFin, emailPropietario, estadoAnuncio, temas,destinatarios);

                ret.put(id, anuncioDTO);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return ret;
    }

    public int BorrarAnuncioDestinatarios(int id){
        int status=0;
        try{
            Connection conect = getConection();
            Properties sqlProp = new Properties();
            InputStream is = new FileInputStream("sql.properties");
            sqlProp.load(is); 
            PreparedStatement ps = conect.prepareStatement(sqlProp.getProperty("borrar.Destinatario"));
            ps.setInt(1, id);
            status=ps.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
        return status;
    }
    public int BorrarAnuncioID(int id){
        int status=0;
        try{
            Connection conect = getConection();
            Properties sqlProp = new Properties();
            InputStream is = new FileInputStream("sql.properties");
            sqlProp.load(is); 
            BorrarAnuncioDestinatarios(id);
            PreparedStatement ps = conect.prepareStatement(sqlProp.getProperty("borrarId.Anuncio"));
            ps.setInt(1, id);
            status=ps.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
        return status;
    }

    public Hashtable<Integer, AnuncioDTO> ObtenerAnuncios(){
        Hashtable<Integer, AnuncioDTO> ret=new Hashtable<Integer, AnuncioDTO>();

        try{
            Connection conect = getConection();
            Properties sqlProp = new Properties();
            InputStream is = new FileInputStream("sql.properties");
            sqlProp.load(is);
            PreparedStatement ps = conect.prepareStatement(sqlProp.getProperty("getAll.Anuncio"));
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int id=rs.getInt(1);
                TipoAnuncio tipoAnuncio=TipoAnuncio.valueOf(rs.getString(2));
                String titulo = rs.getString(3);
                String cuerpo = rs.getString(4);
                Date fechaPublicacion = new Date(rs.getDate(5).getTime());
                Date fechaFin=null;
                if(tipoAnuncio.equals(TipoAnuncio.Flash)){
                    fechaFin= new Date(rs.getDate(6).getTime());
                }
                String emailPropietario = rs.getString(7);
                EstadoAnuncio estadoAnuncio = EstadoAnuncio.valueOf(rs.getString(8));
                
                ArrayList<String>temas = null;
                if(tipoAnuncio.equals(TipoAnuncio.Tematico))
                    temas=new ArrayList<String>(Arrays.asList(rs.getString(9).split(",")));
                ArrayList<String>destinatarios = ObtenerDestinatariosAnuncio(id);
                AnuncioDTO anuncioDTO=new AnuncioDTO(id, tipoAnuncio, titulo, cuerpo, fechaPublicacion, fechaFin, emailPropietario, estadoAnuncio, temas,destinatarios);

                ret.put(id, anuncioDTO);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return ret;
    }

    public ArrayList<String> ObtenerDestinatariosAnuncio(int id){
        ArrayList<String> destinatarios = new ArrayList<String>();
        try{
            Connection conect = getConection();
            Properties sqlProp = new Properties();
            InputStream is = new FileInputStream("sql.properties");
            sqlProp.load(is);
            PreparedStatement ps = conect.prepareStatement(sqlProp.getProperty("obtenerById.Destinatario"));
            ps.setInt(1, id);
            ResultSet rs= ps.executeQuery();
            while(rs.next()){
                destinatarios.add(rs.getString(1));
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return destinatarios;
    }

    public int ArchivarAnuncio(int id){
        int status=0;
        try{
            Connection conect = getConection();
            Properties sqlProp = new Properties();
            InputStream is = new FileInputStream("sql.properties");
            sqlProp.load(is);
            PreparedStatement ps = conect.prepareStatement(sqlProp.getProperty("archivarId.Anuncio"));
            ps.setInt(1, id);
            status=ps.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }

        return status;
    }

    public AnuncioDTO ObtenerAnuncioID(int id){
        AnuncioDTO anuncioDTO=null;
        try{
            Connection conect = getConection();
            Properties sqlProp = new Properties();
            InputStream is = new FileInputStream("sql.properties");
            sqlProp.load(is);
            PreparedStatement ps = conect.prepareStatement(sqlProp.getProperty("getById.Anuncio"));
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                
                TipoAnuncio tipoAnuncio = TipoAnuncio.valueOf(rs.getString(2));
                String titulo = rs.getString(3);
                String cuerpo = rs.getString(4);
                Date fechaPublicacion = new Date(rs.getDate(5).getTime());
                Date fechaFin=null;
                if(tipoAnuncio.equals(TipoAnuncio.Flash)){
                    fechaFin = new Date(rs.getDate(6).getTime());
                }
                String emailPropietario = rs.getString(7);
                EstadoAnuncio estadoAnuncio = EstadoAnuncio.valueOf(rs.getString(8));
                
                ArrayList<String>temas = null;
                if(tipoAnuncio.equals(TipoAnuncio.Tematico))
                    temas=new ArrayList<String>(Arrays.asList(rs.getString(9).split(",")));

                ArrayList<String>destinatarios = ObtenerDestinatariosAnuncio(id);
                anuncioDTO= new AnuncioDTO(id, tipoAnuncio, titulo, cuerpo, fechaPublicacion, fechaFin, emailPropietario, estadoAnuncio, temas, destinatarios);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return anuncioDTO;
    }

    public int ModificarAnuncioGeneral(AnuncioDTO anuncioDTO){
        int status=0;
        try{
            Connection conect = getConection();
            Properties sqlProp = new Properties();
            InputStream is = new FileInputStream("sql.properties");
            sqlProp.load(is);
            PreparedStatement ps = conect.prepareStatement(sqlProp.getProperty("modificar.AnuncioGeneral"));
            ps.setString(1, anuncioDTO.getTitulo());
            ps.setString(2, anuncioDTO.getCuerpo());
            ps.setString(3, anuncioDTO.getEstadoAnuncio().toString());
            ps.setInt(4, anuncioDTO.getId());
            status=ps.executeUpdate();

            BorrarAnuncioDestinatarios(anuncioDTO.getId());

            PreparedStatement psAddDestinatario = conect.prepareStatement(sqlProp.getProperty("insertar.Destinatario"));
            psAddDestinatario.setInt(1, anuncioDTO.getId());
            for(String destinatario : anuncioDTO.getDestinatarios()){
                psAddDestinatario.setString(2, destinatario);
                ps.executeUpdate();
            }          

        }catch(Exception e){
            e.printStackTrace();
        }

        return status;
    }
    
    public int ModificarAnuncioIndividualizado(AnuncioDTO anuncioDTO){
        int status=0;
        try{
            Connection conect = getConection();
            Properties sqlProp = new Properties();
            InputStream is = new FileInputStream("sql.properties");
            sqlProp.load(is);
            PreparedStatement ps = conect.prepareStatement(sqlProp.getProperty("modificar.AnuncioIndividualizado"));
            ps.setString(1, anuncioDTO.getTitulo());
            ps.setString(2, anuncioDTO.getCuerpo());
            ps.setString(3, anuncioDTO.getEstadoAnuncio().toString());
            ps.setInt(4, anuncioDTO.getId());
            status=ps.executeUpdate();

            BorrarAnuncioDestinatarios(anuncioDTO.getId());

            PreparedStatement psAddDestinatario = conect.prepareStatement(sqlProp.getProperty("insertar.Destinatario"));
            psAddDestinatario.setInt(1, anuncioDTO.getId());
            for(String destinatario : anuncioDTO.getDestinatarios()){
                psAddDestinatario.setString(2, destinatario);
                ps.executeUpdate();
            }          

        }catch(Exception e){
            e.printStackTrace();
        }

        return status;
    }

    public int ModificarAnuncioTematico(AnuncioDTO anuncioDTO){
        int status=0;
        try{
            Connection conect = getConection();
            Properties sqlProp = new Properties();
            InputStream is = new FileInputStream("sql.properties");
            sqlProp.load(is);
            PreparedStatement ps = conect.prepareStatement(sqlProp.getProperty("modificar.AnuncioTematico"));
            ps.setString(1, anuncioDTO.getTitulo());
            ps.setString(2, anuncioDTO.getCuerpo());
            ps.setString(3, anuncioDTO.getEstadoAnuncio().toString());            
            String intereses=""; 
                      
            for(String interes : anuncioDTO.getTemas()){
                intereses+=interes.toLowerCase()+",";
            }
            
            if(intereses.length()>0){
                
                intereses=intereses.substring(0,intereses.length()-1);
            }
            
            ps.setString(4, intereses);

            ps.setInt(5, anuncioDTO.getId());

            status=ps.executeUpdate();

            BorrarAnuncioDestinatarios(anuncioDTO.getId());

            PreparedStatement psAddDestinatario = conect.prepareStatement(sqlProp.getProperty("insertar.Destinatario"));
            psAddDestinatario.setInt(1, anuncioDTO.getId());
            for(String destinatario : anuncioDTO.getDestinatarios()){
                psAddDestinatario.setString(2, destinatario);
                ps.executeUpdate();
            }          

        }catch(Exception e){
            e.printStackTrace();
        }

        return status;
    }

    public int ModificarAnuncioFlash(AnuncioDTO anuncioDTO){
        int status=0;
        try{
            Connection conect = getConection();
            Properties sqlProp = new Properties();
            InputStream is = new FileInputStream("sql.properties");
            sqlProp.load(is);
            PreparedStatement ps = conect.prepareStatement(sqlProp.getProperty("modificar.AnuncioFlash"));
            ps.setString(1, anuncioDTO.getTitulo());
            ps.setString(2, anuncioDTO.getCuerpo());
            ps.setString(3, anuncioDTO.getEstadoAnuncio().toString());
            java.sql.Date fecha_fin=new java.sql.Date(anuncioDTO.getFechaFin().getTime());
            ps.setDate(4, fecha_fin);
            ps.setInt(5, anuncioDTO.getId());
            
            status=ps.executeUpdate();

            BorrarAnuncioDestinatarios(anuncioDTO.getId());

            PreparedStatement psAddDestinatario = conect.prepareStatement(sqlProp.getProperty("insertar.Destinatario"));
            psAddDestinatario.setInt(1, anuncioDTO.getId());
            for(String destinatario : anuncioDTO.getDestinatarios()){
                psAddDestinatario.setString(2, destinatario);
                ps.executeUpdate();
            }          

        }catch(Exception e){
            e.printStackTrace();
        }

        return status;
    }
}