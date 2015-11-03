package edu.upc.eetac.dsa.grouptalk;

import edu.upc.eetac.dsa.grouptalk.dao.AuthTokenDAO;
import edu.upc.eetac.dsa.grouptalk.dao.AuthTokenDAOImpl;
import edu.upc.eetac.dsa.grouptalk.dao.UserDAO;
import edu.upc.eetac.dsa.grouptalk.dao.UserDAOImpl;
import edu.upc.eetac.dsa.grouptalk.entity.AuthToken;
import edu.upc.eetac.dsa.grouptalk.entity.User;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import java.sql.SQLException;


@Path("login")
public class LoginResource {

    @Context
    SecurityContext securityContext;
    @Path(("/login_out"))
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void logout(@FormParam("login") String loginid, @Context UriInfo uriInfo){
        if(loginid == null ) throw new BadRequestException("all parameters are mandatory");
        AuthTokenDAO authTokenDAO = new AuthTokenDAOImpl();
        try {authTokenDAO.deleteToken(loginid);}
        catch (SQLException e) {throw new InternalServerErrorException();}
    }
    @Path(("/login_in"))
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(GrouptalkMediaType.GROUPTALK_AUTH_TOKEN)
    public AuthToken login(@FormParam("login") String loginid, @FormParam("password") String password){
        if(loginid == null || password == null)
            throw new BadRequestException("all parameters are mandatory");

        User user = null;
        AuthToken authToken = null;
        try{
            UserDAO userDAO = new UserDAOImpl();
            user = userDAO.obtener_UserByLoginid(loginid);
            if(user == null)
                throw new BadRequestException("loginid " + loginid + " not found.");
            if(!userDAO.check_Password(user.getId(), password))
                throw new BadRequestException("incorrect password");

            AuthTokenDAO authTokenDAO = new AuthTokenDAOImpl();
            authTokenDAO.deleteToken(user.getLoginid());
            authToken = authTokenDAO.createAuthToken(user.getId());
        }catch(SQLException e){
            throw new InternalServerErrorException();
        }
        return authToken;
    }

    //@RolesAllowed({"registrado"})

}