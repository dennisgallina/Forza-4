# Forza 4 con Comunicazione TCP

Questo progetto implementa un'applicazione dell'iconico gioco da tavolo Forza 4 che consente a due giocatori di partecipare a una partita tramite comunicazione TCP.

## Funzionalità

- Creazione di un server di gioco che accetta connessioni TCP dai giocatori.
- Gestione delle regole di Forza 4, inclusi i turni dei giocatori, l'inserimento delle pedine e la disconnessione dei giocatori.
- Comunicazione bidirezionale tra il server e i giocatori tramite socket TCP.
- Visualizzazione delle informazioni di gioco, come il campo da gioco e l'esito della partita.

## Struttura Repository

- Client: contiene il progetto per eseguire il gioco dal lato del Player;
- Docs: documentazione del progetto;
- Server: contiene il progetto che gestisce la partita e le connessioni coi giocatori.

## Requisiti di installazione

Installare le seguenti estensioni su Visual Studio Code:
- Debugger for Java
- Extension Pack for Java

## Istruzioni per l'uso

1. Clonare il repository del progetto:

    ```bash
    git clone https://github.com/dennisgallina/Forza-4.git
    ```

2. Avviare il server di gioco:

    ```bash
    App.java
    ```

3. Avviare l'applicazione client su ogni computer dei giocatori:

    ```bash
    App.java
    ```

4. Seguire le istruzioni visualizzate sull'applicazione client per partecipare alla partita.

## Developers

- Dennis Gallina
- Nicolò Salomoni
