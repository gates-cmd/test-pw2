package test.pw2.itsincom.web;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@ApplicationScoped
public class UtentiManager {
    private final Map<String, String> utenti = new HashMap<>();

    public boolean add(String username, String password) {
        if (utenti.containsKey(username)) {
            return false;
        }
        utenti.put(username, password);
        return true;
    }

    public boolean checkPassword(String inputUsername, String inputPassword) {
        Set<Map.Entry<String, String>> credentials = utenti.entrySet();
        for (Map.Entry<String, String> credential : credentials) {
            String username = credential.getKey();
            String password = credential.getValue();
            if (password.equals(inputPassword) && username.equals(inputUsername)) {
                return true;
            }
        }
        return false;
    }
}