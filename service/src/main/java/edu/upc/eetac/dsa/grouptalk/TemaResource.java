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

    /**@Path("/{id}")
    @DELETE
    public void eliminarTema(@PathParam("idtema") String id) {

        Tema temaid = securityContext.getUserPrincipal().getName();
        StingDAO stingDAO = new StingDAOImpl();
        try {
            String ownerid = stingDAO.getStingById(id).getUserid();
            if(!userid.equals(ownerid))
                throw new ForbiddenException("operation not allowed");
            if(!stingDAO.deleteSting(id))
                throw new NotFoundException("Sting with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }**/
}
