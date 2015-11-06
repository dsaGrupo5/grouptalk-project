package edu.upc.eetac.dsa.grouptalk.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.upc.eetac.dsa.grouptalk.GrouptalkMediaType;
import edu.upc.eetac.dsa.grouptalk.GrouptalkRootAPIResource;
import edu.upc.eetac.dsa.grouptalk.LoginResource;
import edu.upc.eetac.dsa.grouptalk.UserResource;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.security.acl.Group;
import java.util.List;

/**
 * Created by User on 20/10/2015.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthToken {
    @InjectLinks({})
    private List<Link> links;
    private String userid;
    private String token;
    private String role;



    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getToken() {return token;}

    public void setToken(String token) {this.token = token; }
    public String getRole() {return role;}
    public void setRole(String role) {this.role = role;}

}
