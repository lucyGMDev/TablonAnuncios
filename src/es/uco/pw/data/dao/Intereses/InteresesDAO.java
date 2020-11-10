package es.uco.pw.data.dao.Intereses;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Properties;

import es.uco.pw.data.dao.common.DAO;

public class InteresesDAO extends DAO {

    public Hashtable<Integer, String> DevolverIntereses() {
        Hashtable<Integer, String> ret = new Hashtable<Integer, String>();

        try {
            Connection conect = getConection();
            Properties sqlProp = new Properties();
            InputStream is = new FileInputStream("sql.properties");
            sqlProp.load(is);
            PreparedStatement ps = conect.prepareStatement(sqlProp.getProperty("mostrar.Intereses"));
            ResultSet result = ps.executeQuery();

            while(result.next()){
                
                ret.put(result.getInt(1),result.getString(2));
            }
        
        }catch(Exception e){
            e.printStackTrace();
        }

        return ret;

    }

}
