/*
 * PartieEnCours.java             									14 nov. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz.modeles;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Partie de jeu de Quiz en cours contenant les questions posées,
 * les réponses de l'utilisateur, les paramètres ainsi que la question courante.
 * 
 * @author Florian Fabre
 * @author Loïc Faugières
 * @author Jonathan Guil
 * @author Simon Guiraud
 * @author Samuel Lacam
 */
public class PartieEnCours {

	/** ArrayList de questions proposées pour le quiz de la partie en cours */
	private ArrayList<Question> questionsProposees;

	/** ArrayList de réponses de l'utilisateur sur les questions */
	private ArrayList<String> reponsesUtilisateur;
	
	/** Instance de ParametresPartie précédemment créée dans ParametresPartie*/
	private ParametresPartie parametresPartie;
	
	/** Indice permettant de savoir l'indice de la question en cours */
	private int indiceQuestionCourante;
	
	
	/**
	 * Initialisation d'une partie de quiz avec ses paramètres, 
	 * les questions proposées et les réponses de l'utilisateur
	 */
	public PartieEnCours() {
		
		setQuestionsProposees(new ArrayList<Question>());
		this.reponsesUtilisateur = new ArrayList<String>();
		setIndiceQuestionCourante(0);
	}
	
	
	/** @return l'ArrayList des questions proposées pour la partie en cours */
	public ArrayList<Question> getQuestionsProposees() {
		return questionsProposees;
	}
	
	
	/** @param questionsProposees la liste des questions à proposer */
	public void setQuestionsProposees(ArrayList<Question> questionsProposees) {
		this.questionsProposees = questionsProposees;
	}
	
	
	/** @return l'ArrayList des réponses actuelles de l'utilisateur */
	public ArrayList<String> getReponsesUtilisateur() {
		return reponsesUtilisateur;
	}
	
	
	/** @return l'instance de ParametresPartie */
	public ParametresPartie getParametresPartie() {
		return parametresPartie;
	}
	
	
	/** @param parametres Les nouveaux paramètres. */
	public void setParametresPartie(ParametresPartie parametres) {
		this.parametresPartie = parametres;
	}
	
	
	/**  @return l'indice de la question courante */
	public int getIndiceQuestionCourante() {
		return indiceQuestionCourante;
	}
	
	
	/** Remplace l'indice de la question courante par celui en paramètre */
	public void setIndiceQuestionCourante(int indiceQuestionCourante) {
		this.indiceQuestionCourante = indiceQuestionCourante;
	}
	
	
	/**
	 * Ajoute la réponse de l'utilisateur sur la question courante
	 * à l'ArrayList reponsesUtilisateur
	 */ 
	public void ajouterReponseUtilisateur(String repAAjouter) {
		this.reponsesUtilisateur.add(repAAjouter);
	}
	
	
	/**
	 * Passe à la question suivante en sauvegardant la réponses de 
	 * l'utilisateur.
	 * Si l'utilisateur passe la question, une String vide sera rajoutée
	 * dans la liste reponsesUtilisateur
	 */
	public void passerQuestionSuivante() {
		this.indiceQuestionCourante +=1;
	}
	
	
	/**
	 * Retour à la question précédente en sauvegardant la réponses de 
	 * l'utilisateur.
	 * Si l'utilisateur passe la question, une String vide sera rajoutée
	 * dans la liste reponsesUtilisateur
	 */
	public void retourQuestionPrecedente() {
		this.indiceQuestionCourante -=1;
	}

	
	/**
	 * Vérifie la réponse de l'utilisateur par rapport à la réponse juste
	 * de la question
	 * @param questionRepondue question répondue par l'utilisateur
	 * @param reponseUser réponse de l'utilisateur
	 * @return true si réponseUser == reponseJusteQuestion, false sinon
	 */
	public boolean verifierReponse(Question questionRepondue, String reponseUser) {
		return questionRepondue.getReponseJuste().equals(reponseUser);
	}
	
	
	/**
	 * Comptabilise les réponses justes de l'utilisateur
	 * @return le nombre de réponses justes de l'utilisateur, 0 si tout faux
	 */
	public int nbReponsesJustes() {
		int nbRepJustes;
		nbRepJustes = 0;
		for (int i = 0; i < questionsProposees.size(); i++ ) {
			nbRepJustes += questionsProposees.get(i).getReponseJuste()
					.equals(reponsesUtilisateur.get(i)) ? 1 : 0;
		}
		return nbRepJustes;
	}
	
	
	/** non javadoc - @see {@link java.util.Objects#toString()}. */
	@Override
	public String toString() {
		String resultat = "";
		
		for (Question question : this.questionsProposees) {
			resultat += question.getIntitule() + " :\n";
			
			ArrayList<String> reponses = question.melangerReponses();
			
			for (int i = 0; i < reponses.size(); i++) {
				resultat += "- " + reponses.get(i) + "\n";
			}
		}
		return resultat;
	}
}