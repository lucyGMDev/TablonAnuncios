#!/bin/bash

jar -cf ../../../../TablonAnuncio.jar data/dao/Anuncios/AnuncioDAO.class business/Anuncios/*.class business/DTO/DTOAnuncio/*.class business/Usuario/Contacto.class business/Usuario/GestorContactos.class data/dao/common/DAO.class data/dao/Usuario/ContactoDAO.class data/dao/Intereses/InteresesDAO.class business/DTO/DTOUsuario/ContactoDTO.class display/TablonAnuncios.class
