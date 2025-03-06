package test.pw2.itsincom.web;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import test.pw2.itsincom.service.UtentiManager;
import test.pw2.itsincom.web.service.SessionManager;
import test.pw2.itsincom.web.validation.CredentialValidator;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;

import java.net.URI;

@Path("/login")
public class LoginResource {

    private final Template login;
    private final UtentiManager utentiManager;
    private final CredentialValidator credentialValidator;
    private final SessionManager sessionManager;

    public LoginResource(Template login, UtentiManager utentiManager, CredentialValidator credentialValidator, SessionManager sessionManager) {
        this.login = login;
        this.utentiManager = utentiManager;
        this.credentialValidator = credentialValidator;
        this.sessionManager = sessionManager;
    }

    @GET
    public TemplateInstance mostraPaginaLogin() {
        return login.instance();
    }

    @POST
    public Response processaLogin(
            @FormParam("username") String username,
            @FormParam("password") String password
    ) {
        String messaggioErrore = null;
        if (credentialValidator.validatePassword(password) != null) {
            messaggioErrore = "Username o password non validi";
        } else if (credentialValidator.validateUsername(username) != null) {
            messaggioErrore = "Username o password non validi";
        }

        if (messaggioErrore != null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(login.data("message", messaggioErrore))
                    .build();
        }

        if (!utentiManager.checkPassword(username, password)) {
            messaggioErrore = "Username o password non validi";
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(login.data("message", messaggioErrore))
                    .build();
        }

        NewCookie sessionCookie = sessionManager.createUserSession(username);
        return Response
                .seeOther(URI.create("/"))
                .cookie(sessionCookie)
                .build();
    }
}