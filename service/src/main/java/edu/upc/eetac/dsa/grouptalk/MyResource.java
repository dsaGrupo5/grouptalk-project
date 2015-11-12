package edu.upc.eetac.dsa.grouptalk;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("myresource")
public class MyResource {

    @RolesAllowed({"administrador","registrado"})
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it 22!";
    }
}
