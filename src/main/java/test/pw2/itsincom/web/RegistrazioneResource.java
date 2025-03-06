package test.pw2.itsincom.web;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.security.credential.Credential;
import test.pw2.itsincom.service.UtentiManager;
import test.pw2.itsincom.web.validation.CredentialValidationErrors;
import test.pw2.itsincom.web.validation.CredentialValidator;
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
    private final CredentialValidator credentialValidator;

    public RegistrazioneResource(Template registrazione, UtentiManager utentiManager, CredentialValidator credentialValidator) {
        this.registrazione = registrazione;
        this.utentiManager = utentiManager;
        this.credentialValidator = credentialValidator;
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
        CredentialValidationErrors usernameError = credentialValidator.validateUsername(username);

        if (usernameError != null) {
            messaggioErrore = "Username non valido, motivo: " + usernameError.name();
        }

        CredentialValidationErrors passwordError = credentialValidator.validatePassword(password);
        if (passwordError != null) {
            messaggioErrore = "Password non valida, motivo: " + passwordError.name();
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