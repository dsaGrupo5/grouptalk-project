package edu.upc.eetac.dsa.grouptalk;

import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

/**
 * Created by User on 20/10/2015.
 */
public class GrouptalkResourceConfig extends ResourceConfig {
    public GrouptalkResourceConfig() {
        packages("edu.upc.eetac.dsa.grouptalk");
        packages("edu.upc.eetac.dsa.grouptalk.auth");
        register(RolesAllowedDynamicFeature.class);
        register(DeclarativeLinkingFeature.class);
        packages("edu.upc.eetac.dsa.grouptalk.cors");
    }
}
