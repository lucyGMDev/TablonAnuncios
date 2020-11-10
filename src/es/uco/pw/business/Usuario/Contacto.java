package es.uco.pw.business.Usuario;

import java.util.Date;
import java.util.ArrayList;

/**
 * 
 * 
 * 
 * Clase Contacto, el cual representa a cada usuario registrado en el sistema
 * 
 * <p>
 * Los atributos almacenados de cada uno de ellos son:
 * </p>
 * 
 * <ul>
 * 
 * <li>E-mail</li>
 * 
 * <li>Nombre</li>
 * 
 * <li>Apellidos</li>
 * 
 * <li>Fecha de nacimiento</li>
 * <li>Lista de intereses</li>
 * 
 *
 * </ul>
 * 
 * 
 * 
 * 
 * 
 */
public class Contacto {
    private String email;
    private String name;
    private String lastName;
    private Date birthDate;
    private ArrayList<String> tagsLists;

    /**
     * Constructor de Contacto, al cual se le pasan como parámetros los atributos
     * descritos arriba
     * 
     * @param email
     * @param name
     * @param lastName
     * @param birthDate
     * @param tagsList
     */
    public Contacto(String email, String name, String lastName, Date birthDate, ArrayList<String> tagsLists) {
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.setTagsLists(tagsLists);
    }

    /**
     * @return La lista de tags
     */
    public ArrayList<String> getTagsLists() {
        return tagsLists;
    }


    /**
    * 
    * @param tagsLists La lista de intereses del usuario
    */
    public void setTagsLists(ArrayList<String> tagsLists) {
        this.tagsLists = tagsLists;
    }

    /**
     * @return El e-mail del usuario
     */
    public String getEmail() {
        return email;
    }

 

    /**
     * 
     * @return La fecha de nacimiento en formato "dd - mm - aaaa"
     */

    public Date getBirthDate() {
        return birthDate;
    }

   /**
    * 
    * @param birthDate La fecha de nacimiento en formato "dd - mm - aaaa"
    */

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

   
    /**
     * 
     * 
     * 
     * @return Los apellidos del usuario, siendo una cadena del tipo "apellido_1 + apellido_2"
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * 
     * @param lastName Los apellidos del usuario, siendo una cadena del tipo "apellido_1 + apellido_2"
     */

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * 
     * @return El nombre de pila del usuario
     */

    public String getName() {
        return name;
    }


    /**
     * 
     * 
     * @param name nombre de pila del usuario
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @param email El correo electrónico del usuario
     */
   
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 
     * Sobrecarga de la función toString
     * 
     * @return Devuelve una frase en forma de cadena con todos los datos del contacto
     */
    @Override
    public String toString() {
        String ret= "Soy el contacto con email "+getEmail()+" mi nombre es "+getName()+" "+getLastName()+"\n"+
        "mi cumpleaños es el dia "+getBirthDate().toString();

        return ret;
    }
}


