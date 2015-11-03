package edu.upc.eetac.dsa.grouptalk.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.List;

/**
 * Created by User on 20/10/2015.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Respuesta {
    @InjectLinks({})
    private List<Link> links;
    private String id;
    private String temaid;
    private String userid;
    private String respuesta;
    private long creationTimestamp;
    private long lastModified;

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTemaid() {
        return temaid;
    }

    public void setTemaid(String temaid) {this.temaid = temaid;}

    public String getUserid() {return userid;}

    public void setUserid(String userid) {
        this.userid = userid;
    }
    public String getRespuesta() {return respuesta;}
    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
    public long getCreationTimestamp() {
        return creationTimestamp;
    }
    public void setCreationTimestamp(long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }
    public long getLastModified() {
        return lastModified;
    }
    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }
}
