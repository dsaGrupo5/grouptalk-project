package edu.upc.eetac.dsa.grouptalk.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import edu.upc.eetac.dsa.grouptalk.GrouptalkMediaType;
import edu.upc.eetac.dsa.grouptalk.GrouptalkRootAPIResource;
import edu.upc.eetac.dsa.grouptalk.RespuestaResource;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;


import javax.ws.rs.core.Link;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 20/10/2015.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RespuestaCollection {


    @InjectLinks({
            @InjectLink(resource = GrouptalkRootAPIResource.class, style = InjectLink.Style.ABSOLUTE, rel = "home", title = "Grouptalk Root API"),
            @InjectLink(resource = RespuestaResource.class, style = InjectLink.Style.ABSOLUTE, rel = "current-respuestas", title = "Respuestas Actuales", type= GrouptalkMediaType.GROUPTALK_RESPUESTA_COLLECTION),
            @InjectLink(resource = RespuestaResource.class, method = "getRespuestas", style = InjectLink.Style.ABSOLUTE, rel = "next", title = "Nuevas Respuestas", type= GrouptalkMediaType.GROUPTALK_RESPUESTA_COLLECTION, bindings = {@Binding(name = "timestamp", value = "${instance.newestTimestamp}"), @Binding(name = "before", value = "false")}),
            @InjectLink(resource = RespuestaResource.class, method = "getRespuestas", style = InjectLink.Style.ABSOLUTE, rel = "previous", title = "Older stings", type= GrouptalkMediaType.GROUPTALK_RESPUESTA_COLLECTION, bindings = {@Binding(name = "timestamp", value = "${instance.oldestTimestamp}"), @Binding(name = "before", value = "true")}),
    })

    private List<Link> links;
    private long newestTimestamp;
    private long oldestTimestamp;
    private List<Respuesta> respuestas = new ArrayList<>();

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public long getNewestTimestamp() {
        return newestTimestamp;
    }

    public void setNewestTimestamp(long newestTimestamp) {
        this.newestTimestamp = newestTimestamp;
    }

    public long getOldestTimestamp() {
        return oldestTimestamp;
    }

    public void setOldestTimestamp(long oldestTimestamp) {
        this.oldestTimestamp = oldestTimestamp;
    }

    public List<Respuesta> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<Respuesta> respuestas) {
        this.respuestas = respuestas;
    }
}
