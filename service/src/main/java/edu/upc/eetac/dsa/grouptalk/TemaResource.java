package edu.upc.eetac.dsa.grouptalk;

import edu.upc.eetac.dsa.grouptalk.dao.*;
import edu.upc.eetac.dsa.grouptalk.entity.Grupo;
import edu.upc.eetac.dsa.grouptalk.entity.Tema;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Created by User on 27/10/2015.
 */
@RolesAllowed({"registrado"})
@Path("grupo/tema")
public class TemaResource {
    @Context
    private SecurityContext securityContext;
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(GrouptalkMediaType.GROUPTALK_TEMA)
    public Response crearTema(@FormParam("nombreusuario") String nombreusuario, @FormParam("nombregrupo") String nombregrupo, @FormParam("nombretema") String nombretema, @FormParam("comentario") String comentario, @Context UriInfo uriInfo) throws URISyntaxException, SQLException {
        if(nombreusuario == null || nombregrupo == null || nombretema == null || comentario == null)
            throw new BadRequestException("es necesario rellenar todos los campos");

        temaDAO temaDao = new temaDAOImpl();
        Tema tema = null;

        try{
            tema = temaDao.crear_tema(nombreusuario,nombregrupo,nombretema,comentario);


        }catch(SQLException e){
            throw new InternalServerErrorException();
        } catch (UserAlreadyExistsException e) {
            e.printStackTrace();
        } catch (UserNoExisteException e) {
            e.printStackTrace();
        } catch (GrupoNoExisteException e) {
            e.printStackTrace();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + tema.getId());
        return Response.created(uri).type(GrouptalkMediaType.GROUPTALK_TEMA).entity(tema).build();

    }

    @Path("/{id}")
    @DELETE
    public void eliminarTema(@PathParam("id") String id) throws TemaIDNoExisteException {

        String temaid = securityContext.getUserPrincipal().getName();
        temaDAO temadao = new temaDAOImpl();
        try {
            String idusercreador = temadao.obtener_tema_por_id(id).getUserid();
            if(!temaid.equals(idusercreador))
                throw new ForbiddenException("este usuario no ha creado este tema!");
            if(!temadao.eliminar_tema(id))
                throw new TemaIDNoExisteException();
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }

    @Path("/{id}")
    @PUT
    @Consumes(GrouptalkMediaType.GROUPTALK_TEMA)
    @Produces(GrouptalkMediaType.GROUPTALK_TEMA)
    public Tema updateTema(@PathParam("id") String id, Tema tema) throws TemaIDNoExisteException {
        if(tema == null)
            throw new BadRequestException("no se ha pasado el tema a modificar!");
        if(!id.equals(tema.getId()))
            throw new BadRequestException("path parameter id and entity parameter id doesn't match");

        String userid = securityContext.getUserPrincipal().getName();
        if(!userid.equals(tema.getUserid()))
            throw new ForbiddenException("operation not allowed");
        temaDAO userDAO = new temaDAOImpl();
        try {
            tema = userDAO.modificar_comentario(tema.getId(),tema.getComentario());

            if(tema == null)
                throw new NotFoundException("El tema con id = "+id+" no existe");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return tema;
    }
}
