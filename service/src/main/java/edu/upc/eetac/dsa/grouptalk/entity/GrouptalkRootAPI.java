package edu.upc.eetac.dsa.grouptalk.entity;

import edu.upc.eetac.dsa.grouptalk.*;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.List;

/**
 * Created by User on 29/10/2015.
 */
public class GrouptalkRootAPI {
    @InjectLinks({
            @InjectLink(resource = GrouptalkRootAPIResource.class, style = InjectLink.Style.ABSOLUTE, rel = "self bookmark home", title = "Grouptalk Root API"),
            @InjectLink(resource = UserResource.class, style = InjectLink.Style.ABSOLUTE, rel = "registro-usuario", title = "Registro usuario", type= GrouptalkMediaType.GROUPTALK_AUTH_TOKEN),
            @InjectLink(resource = GroupResource.class,method="obtenerGrupo", style = InjectLink.Style.ABSOLUTE, rel = "Obtener-grupos", title = "Obtener-grupos", type= GrouptalkMediaType.GROUPTALK_GRUPO_COLLECTION),
            @InjectLink(resource = LoginResource.class,method="login", style = InjectLink.Style.ABSOLUTE, rel = "Logo-in", title = "Logo in", type= GrouptalkMediaType.GROUPTALK_AUTH_TOKEN),

    })
    private List<Link> links;

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }
}
