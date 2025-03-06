# Sito web per le recensioni dei locali
## Requisiti

    - Devo potermi registrare sul sito con username e password e accedere con questi dati
        - username è univoco.
    - (Solo dopo login) Devo poter aggiungere un nuovo locale alla lista dei locali
        - Nom (univoco);
        - Indirizzo;
        - (Opzionale) Foto;
    - (Solo dopo login) Devo poter aggiungere una recensione a un locale esistente
        - Titolo;
        - Data (data di creazione della recensione);
        - Autore (username dell'utente che sta inserendo la recensione);
        - Voto (1-5);
        - Testo.
    - (Anche senza la login) Devo poter cercare un locale per nome, anche parziale e case insensitive
        - Es: se cerco "Pizzeria" otterrò come risultati:
            - Pizzeria da Gigi;
            - Mario's Pizza;
            - ...
    - La ricerca si può fare solo se scrivo almeno 3 caratteri
    - (Anche senza la login) Una volta selezionato un locale vedo:
        - La foto, se presente;
        - Media di voti derivanti dalle recensioni (oppure "Nessun voto" se non ci sono recensioni);
        - Le ultime 10 recensioni in ordine temporale decrescente;
        - C'è un menu a tendina per filtrare le recensioni sul voto massimo (default: 5).