/*
 * PartieEnCours.java             									14 nov. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz.modeles;

import java.util.ArrayList;

/**
 * TODO javadoc
 * 
 * @author Florian Fabre
 * @author Loïc Faugières
 * @author Jonathan Guil
 * @author Simon Guiraud
 * @author Samuel Lacam
 */
public class PartieEnCours {

	/** TODO javadoc */
	private ArrayList<Question> questionsProposees;

	/** TODO javadoc */
	private ArrayList<Question> reponsesUtilisateur;
	
	/** TODO javadoc */
	private ParametresPartie parametresPartie;
	
	/** TODO javadoc */
	private int indiceQuestionCourante;
	
	/** TODO javadoc */
	public PartieEnCours() {
		this.questionsProposees = new ArrayList<Question>();
		this.reponsesUtilisateur = new ArrayList<Question>();
		this.parametresPartie = new ParametresPartie();
		this.indiceQuestionCourante = 0;
	}

	/** TODO javadoc */
	public ArrayList<Question> getQuestionsProposees() {
		return questionsProposees;
	}

	/** TODO javadoc */
	public ArrayList<Question> getReponsesUtilisateur() {
		return reponsesUtilisateur;
	}
	
	/** TODO javadoc */
	public ParametresPartie getParametresPartie() {
		return parametresPartie;
	}
	
	/** TODO javadoc */
	public int getIndiceQuestionCourante() {
		return indiceQuestionCourante;
	}
	
	/** TODO javadoc */
	public void setIndiceQuestionCourante(int indiceQuestionCourante) {
		this.indiceQuestionCourante = indiceQuestionCourante;
	}
	
	/** TODO javadoc */
	public void melangerQuestionsProposees() {
		// TODO
	}

	@Override
	public String toString() {
		return "";
	}
}