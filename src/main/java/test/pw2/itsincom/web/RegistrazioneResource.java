package test.pw2.itsincom.web;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.net.URI;

@Path("/registrazione")
public class RegistrazioneResource {

    private final Template registrazione;
    private final UtentiManager utentiManager;

    public RegistrazioneResource(Template registrazione, UtentiManager utentiManager) {
        this.registrazione = registrazione;
        this.utentiManager = utentiManager;
    }

    @GET
    public TemplateInstance mostraPaginaRegistrazione() {
        return registrazione.data("message", null);
    }

    @POST
    public Response processaRegistrazione(
            @FormParam("username") String username,
            @FormParam("password") String password) {
        // 1. Validazione input

        String messaggioErrore = null;
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            // Scritta di errore
            // Status code 400
            messaggioErrore = "Username o password non validi";
        } else if (password.length() < 8) {
            messaggioErrore = "Password troppo corta";
        }

        if (messaggioErrore != null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(registrazione.data("message", messaggioErrore))
                    .build();
        }

        boolean aggiunto = utentiManager.add(username, password);
        if (!aggiunto) {
            messaggioErrore = "Utente già esistente";
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(registrazione.data("message", messaggioErrore))
                    .build();

        }
        return Response.seeOther(URI.create("/login")).build();

    }
}