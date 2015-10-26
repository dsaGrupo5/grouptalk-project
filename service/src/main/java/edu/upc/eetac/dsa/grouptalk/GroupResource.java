package edu.upc.eetac.dsa.grouptalk;

import edu.upc.eetac.dsa.grouptalk.dao.*;
import edu.upc.eetac.dsa.grouptalk.entity.AuthToken;
import edu.upc.eetac.dsa.grouptalk.entity.Grupo;
import edu.upc.eetac.dsa.grouptalk.entity.User;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Created by carlos on 26/10/2015.
 */

@Path("grupo")
public class GroupResource {
    @Context
    private SecurityContext securityContext;
    @POST
    public Response crearGrupo(@FormParam("nombre") String nombre, @Context UriInfo uriInfo) throws URISyntaxException, GrupoAlreadyExistException, SQLException {
        if(nombre == null)
            throw new BadRequestException("es necesario un nombre de grupo");
        GrupoDAO grupoDAO = new GrupoDAOImpl();
        Grupo grupo = null;

        try{
            grupo = grupoDAO.crear_grupo(nombre);

        }catch (GrupoAlreadyExistException e){
            throw new WebApplicationException("este grupo ya existe!!", Response.Status.CONFLICT);
        }catch(SQLException e){
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + grupo.getId());
        return Response.created(uri).type(GrouptalkMediaType.GROUPTALK_GRUPO).entity(grupo).build();

    }

    @Path("/{id}")
    @POST
    public void ingresarGrupo(@FormParam("nombregrupo") String nombregrupo,@FormParam("nombreuser") String nombreuser, @Context UriInfo uriInfo) throws URISyntaxException, GrupoNoExisteException, UserNoExisteException, SQLException {

        String userid;
        GrupoDAO grupoDAO = new GrupoDAOImpl();

        Grupo grupo = null;
        User user = null;
        grupo = grupoDAO.obtener_ID_grupo_por_NOMBRE(nombregrupo);
        //fata comprobar si el usuario existe

        if(grupo == null)
            throw new BadRequestException("es necesario un id de grupo");
        if(user == null)
            throw new BadRequestException("es necesario un id de usuario");



    }



}
