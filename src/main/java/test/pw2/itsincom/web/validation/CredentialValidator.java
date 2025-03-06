package test.pw2.itsincom.web.validation;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CredentialValidator {

    public CredentialValidationErrors validateUsername(String username) {
        boolean ok = username != null && !username.isEmpty();
        if (!ok) {
            return CredentialValidationErrors.EMPTY_USERNAME;
        }
        return null;
    }

    public CredentialValidationErrors validatePassword(String password) {
        boolean ok = password != null && !password.isEmpty();
        if (!ok) {
            return CredentialValidationErrors.EMPTY_PASSWORD;
        }
        if (password.length() < 8) {
            return CredentialValidationErrors.PASSSWORD_TOO_SHORT;
        }
        return null;
    }

}