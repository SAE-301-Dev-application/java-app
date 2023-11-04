import java.util.ArrayList;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("7c216add-6c9b-4e3d-a73c-efc9cca971ba")
public class Categorie {
    @objid ("9f07010d-c603-4aff-8935-8f84876707af")
    private String intitule;

    @objid ("a695b7f1-7263-4a7a-9754-036361f4533b")
    private Question[] questions;

    @objid ("d4218d8b-fd60-4acc-8cc3-2aa7a0f9fee6")
    public List<Question> question = new ArrayList<Question> ();

    @objid ("ead11d13-473c-4f28-9b7c-a0e82dd040bb")
    public Categorie(final String intitule) {
    }

    @objid ("a3af17bd-825e-4393-9d9a-da2d468a6022")
    public String getIntitule() {
        // TODO Auto-generated return
        return null;
    }

    @objid ("bb186624-4a26-4d63-bc78-6b9e444c1eeb")
    public void ajouterQuestion(final Question aAjouter) {
    }

    @objid ("19fb52ab-fdb9-4441-b0ab-e43597ec795f")
    public boolean estVide() {
        // TODO Auto-generated return
        return false;
    }

    @objid ("acec4eca-d5c8-400b-9779-27bcb82039f0")
    public int nbQuestions() {
        // TODO Auto-generated return
        return 0;
    }

    @objid ("ee94a938-2989-44f7-9687-12582ac2ce8d")
    public List<Question> getListeQuestions() {
        // TODO Auto-generated return
        return null;
    }

    @objid ("ab9687ad-d127-4545-9043-481b1d4519c8")
    public void setIntitule(final String nouveauIntitule) {
    }

    @objid ("5075b886-fc91-40a3-92be-db7b624cbe1b")
    public void supprimerQuestion(final Question aSupprimer) {
    }

    @objid ("6cdf565b-b7b6-4d3c-9e6a-39d7ed2dbee8")
    public void supprimerToutesQuestions() {
    }

}
