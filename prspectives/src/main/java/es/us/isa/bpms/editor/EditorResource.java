package es.us.isa.bpms.editor;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.io.InputStream;

/**
 * User: resinas
 * Date: 12/05/13
 * Time: 21:38
 */
@Path("/service/editor")
public class EditorResource {
    @Path("/plugins")
    @GET
    @Produces("application/xml")
    public InputStream getPlugins() {
        return this.getClass().getResourceAsStream("/plugins.xml");
    }

    /**
    * Returns the stencilset by default. Right now, there is no support for 
    * multiple stencilsets in the same editor.
    */
    @Path("/stencilset")
    @GET
    @Produces("application/json")
    public InputStream getStencilset() {
        return this.getClass().getResourceAsStream("/stencilsets/bpmn2.0/bpmn2.0.json");
    }

    @Path("/ssextensions")
    @GET
    @Produces("application/json")
    public InputStream getStencilSetExtensions() {
        return this.getClass().getResourceAsStream("/json/extensions.json");
    }


    @GET
    @Produces("text/html")
    public InputStream getEditor(@QueryParam("id") String id) {
        return this.getClass().getResourceAsStream("/editor.html");
    }
}
