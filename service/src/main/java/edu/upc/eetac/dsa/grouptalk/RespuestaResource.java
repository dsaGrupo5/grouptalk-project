package edu.upc.eetac.dsa.grouptalk;

import edu.upc.eetac.dsa.grouptalk.dao.*;
import edu.upc.eetac.dsa.grouptalk.entity.Respuesta;
import edu.upc.eetac.dsa.grouptalk.entity.RespuestaCollection;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
@Path("grupo/tema/respuesta")
public class RespuestaResource {
    @Context
    private SecurityContext securityContext;
    @RolesAllowed({"registrado"})
    @Path(("/crear_respuesta"))
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(GrouptalkMediaType.GROUPTALK_RESPUESTA)
    public Response crearRespuesta(@FormParam("nombreTema") String nombreTema,@FormParam("nombreUser") String nombreUser,@FormParam("respuesta") String respuesta, @Context UriInfo uriInfo) throws URISyntaxException,UserNoExisteException,TemaIDNoExisteException,SQLException
    {
        if(nombreTema == null || nombreUser == null || respuesta == null )throw new BadRequestException("es necesario rellenar todos los campos");
        RespuestaDAO respuestaDAO = new RespuestaDAOImpl();
        Respuesta resp = null;
        try{resp = respuestaDAO.crear_respuesta(nombreTema,nombreUser,respuesta);}
        catch(SQLException e){throw new InternalServerErrorException();}
        catch (UserNoExisteException e) {e.printStackTrace();}
        catch (TemaIDNoExisteException e) {e.printStackTrace();}

        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + resp.getId());
        return Response.created(uri).type(GrouptalkMediaType.GROUPTALK_RESPUESTA).entity(resp).build();
    }
    @RolesAllowed({"registrado"})
    @Path(("/eliminar_respuesta"))
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response eliminarRespuesta(@FormParam("respuestaid") String respuestaid,@Context UriInfo uriInfo) throws URISyntaxException,RespuestaNoExisteException,SQLException
    {
        if(respuestaid == null )throw new BadRequestException("es necesario rellenar todos los campos");
        RespuestaDAO respuestaDAO = new RespuestaDAOImpl();
        boolean resp;
        try{resp = respuestaDAO.eliminar_respuesta(respuestaid);}
        catch(SQLException e){throw new InternalServerErrorException();}
        catch (RespuestaNoExisteException e) {e.printStackTrace();}
        return Response.ok().build();
    }




    @RolesAllowed({"registrado"})
    @Path(("/obtener_respuestas"))
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(GrouptalkMediaType.GROUPTALK_RESPUESTA)
    public RespuestaCollection obtenerRespuestas(@FormParam("nombreUser") String nombreUser,@Context UriInfo uriInfo) throws URISyntaxException,UserNoExisteException,SQLException
    {
        if(nombreUser == null )throw new BadRequestException("es necesario rellenar todos los campos");
        RespuestaDAO respuestaDAO = new RespuestaDAOImpl();
        RespuestaCollection coleccionrespuestas = null;
        try{coleccionrespuestas = respuestaDAO.obtener_respuestas_por_User(nombreUser);}
        catch(SQLException e){throw new InternalServerErrorException();}
        catch (UserNoExisteException e) {e.printStackTrace();}
        return coleccionrespuestas;
    }


}
