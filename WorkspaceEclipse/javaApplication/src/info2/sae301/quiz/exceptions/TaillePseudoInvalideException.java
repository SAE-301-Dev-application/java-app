/*
 * TaillePseudoInvalideException.java					 15 nov. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */
package info2.sae301.quiz.exceptions;

/**
 * Classe d'exception héritée de RuntimeException rapportant l'erreur
 * d'un pseudo dont la taille n'est pas suffisante (1 caractère) 
 * ou trop grande (+ de 20).
 * 
 * @author Florian Fabre
 * @author Loïc Faugières
 * @author Jonathan Guil
 * @author Simon Guiraud
 * @author Samuel Lacam
 */
public class TaillePseudoInvalideException extends RuntimeException {

    /**
     * Numéro de version UID de l'exception.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Construit une nouvelle exception d'exécution avec null comme
     * message de détail.
     */
    public TaillePseudoInvalideException() {
        super();
    }

    /**
     * Construit une nouvelle exception d'exécution avec un message
     * de détail passé en paramètre.
     * 
     * @param message Message de détail donné pour l'exception 
     *                d'exécution.
     */
    public TaillePseudoInvalideException(String message) {
        super(message);
    }
}
