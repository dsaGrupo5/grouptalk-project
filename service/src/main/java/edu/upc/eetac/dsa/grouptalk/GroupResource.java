package edu.upc.eetac.dsa.grouptalk;

import edu.upc.eetac.dsa.grouptalk.dao.*;
import edu.upc.eetac.dsa.grouptalk.entity.AuthToken;
import edu.upc.eetac.dsa.grouptalk.entity.Grupo;
import edu.upc.eetac.dsa.grouptalk.entity.GrupoCollection;
import edu.upc.eetac.dsa.grouptalk.entity.User;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;



@Path("grupo")
public class GroupResource {
    @Context
    private SecurityContext securityContext;
    @RolesAllowed({"administrador"})
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(GrouptalkMediaType.GROUPTALK_GRUPO)
    public Response crearGrupo(@FormParam("nombre") String nombre, @Context UriInfo uriInfo) throws URISyntaxException, GrupoAlreadyExistException, SQLException
    {
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
    @RolesAllowed({"registrado"})
    @Path(("/ingresar_grupo"))
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
     public Response ingresarGrupo(@FormParam("nombregrupo") String nombregrupo,@FormParam("nombreuser") String nombreuser, @Context UriInfo uriInfo) throws URISyntaxException, GrupoNoExisteException, UserNoExisteException, SQLException
    {

        if(nombregrupo == null||nombreuser == null)
            throw new BadRequestException("es necesario rellenar todos los campos");
        GrupoDAO grupoDAO = new GrupoDAOImpl();
        try
        {
            grupoDAO.ingresar_grupo(nombregrupo,nombreuser);

        }
        catch (GrupoNoExisteException e)
        {
            throw new WebApplicationException("este grupo no existe!!", Response.Status.CONFLICT);
        }
        catch (UserNoExisteException e) {
            throw new WebApplicationException("este grupo no existe!!", Response.Status.CONFLICT);
        }
        catch(SQLException e)
            {
            throw new InternalServerErrorException();
        }
        return Response.ok().build();

    }
    @RolesAllowed({"registrado"})
    @Path(("/abandonar_grupo"))
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response abandonarGrupo(@FormParam("nombregrupo") String nombregrupo,@FormParam("nombreuser") String nombreuser, @Context UriInfo uriInfo) throws URISyntaxException, GrupoNoExisteException, UserNoExisteException, SQLException,RelacionNoExisteException
    {
        if(nombregrupo == null||nombreuser == null)
            throw new BadRequestException("es necesario rellenar todos los campos");
        GrupoDAO grupoDAO = new GrupoDAOImpl();
        try
        {
            grupoDAO.abandonar_grupo(nombregrupo, nombreuser);

        }
        catch (GrupoNoExisteException e)
        {
            throw new WebApplicationException("este grupo no existe!!", Response.Status.CONFLICT);
        }
        catch (UserNoExisteException e) {
            throw new WebApplicationException("este usuario no existe!!", Response.Status.CONFLICT);
        }
        catch(SQLException e)
        {
            throw new InternalServerErrorException();
        }
        return Response.ok().build();

    }

    @Path(("/obtenergrupos"))
    @GET
    @Produces(GrouptalkMediaType.GROUPTALK_GRUPO_COLLECTION)
    public GrupoCollection obtenerGrupo() throws URISyntaxException, SQLException
    {
        GrupoDAO grupoDAO = new GrupoDAOImpl();
        GrupoCollection colecciongrupos = new GrupoCollection();
        try
        {
            colecciongrupos = grupoDAO.obtener_coleccion();

        }
        catch(SQLException e)
        {
            throw new InternalServerErrorException();
        }
        return colecciongrupos;
    }

    @RolesAllowed({"administrador"})
    @Path("/eliminar/{nombre}")
    @DELETE
    public Response eliminarGrupo(@PathParam("nombre") String nombregrupo) throws GrupoNoExisteException {

        GrupoDAO grupoDAO = new GrupoDAOImpl();

        try{
            if(!grupoDAO.eliminar_grupo(nombregrupo))
                throw new GrupoNoExisteException();
        }catch (GrupoNoExisteException e){

        }catch(SQLException e){
            throw new InternalServerErrorException();
        }
        return Response.ok().build();
    }

    @RolesAllowed({"administrador"})
    @Path("/editar/{id}")
    @PUT
    @Consumes(GrouptalkMediaType.GROUPTALK_GRUPO)
    @Produces(GrouptalkMediaType.GROUPTALK_GRUPO)
    public Response editarGrupo(@PathParam("id") String nombregrupo,@FormParam("nombregrupo") String nombrenuevo ) throws GrupoNoExisteException {

        GrupoDAO grupoDAO = new GrupoDAOImpl();

        try{
            if(!grupoDAO.eliminar_grupo(nombregrupo))
                throw new GrupoNoExisteException();
        }catch (GrupoNoExisteException e){

        }catch(SQLException e){
            throw new InternalServerErrorException();
        }
        return Response.ok().build();
    }
}
