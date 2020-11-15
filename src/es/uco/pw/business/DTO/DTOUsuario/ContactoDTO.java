package es.uco.pw.business.DTO.DTOUsuario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class ContactoDTO implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    private String email;
    private String name;
    private String lastName;
    private Date birthDate;
    private ArrayList<String> tagsLists;

    public ContactoDTO(String email, String name, String lastName, Date birthDate, ArrayList<String> tagsLists) {
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.tagsLists = tagsLists;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public ArrayList<String> getTagsLists() {
        return tagsLists;
    }

    public void setTagsLists(ArrayList<String> tagsLists) {
        this.tagsLists = tagsLists;
    }


    
}
