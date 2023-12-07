/*
 * ClientDejaConnecteException.java					    01 déc. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */
package info2.sae301.quiz.exceptions;

/**
 * Classe d'exception héritée de RuntimeException rapportant 
 * l'erreur d'un client déjà connecté au serveur.
 * 
 * @author Florian Fabre
 * @author Loïc Faugières
 * @author Jonathan Guil
 * @author Simon Guiraud
 * @author Samuel Lacam
 */
public class ClientDejaConnecteException extends RuntimeException {

    /**
     * Numéro de version UID de l'exception.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Construit une nouvelle exception d'exécution avec null 
     * comme message de détail.
     */
    public ClientDejaConnecteException() {
        super();
    }

    /**
     * Construit une nouvelle exception d'exécution avec un message 
     * de détail passé en paramètre.
     * 
     * @param message Message de détail donné pour l'exception 
     *                d'exécution.
     */
    public ClientDejaConnecteException(String message) {
        super(message);
    }
}
