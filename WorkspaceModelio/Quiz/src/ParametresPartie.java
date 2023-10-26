import java.util.ArrayList;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("add3c9b4-0b82-4006-8f19-4ce10dde56b7")
public class ParametresPartie {
    @objid ("0acd448c-7e96-4c7b-8e37-5d59a03c80a2")
    private Categorie[] categories;

    @objid ("60511609-cb37-4adb-9be2-b8c11818ab8d")
    private int difficulte;

    @objid ("af927e36-4e40-4615-861d-5a1f32fb3017")
    private int nombreQuestions;

    @objid ("5cfb6c89-70ed-4a7b-a4dc-b913d6e27cbe")
    private List<Categorie> categorie = new ArrayList<Categorie> ();

    @objid ("d915aae6-283b-457f-8a2e-68c46ef602bb")
    public void ajouterCategories(final List<Categorie> aAjouter) {
    }

    @objid ("95a68763-7327-4cfd-b6bf-c4152ca76ac3")
    public void setDifficulte(final int difficulte) {
    }

    @objid ("2ae252ab-177a-46e5-b0ef-04f588330769")
    public void setNombreQuestions(final int nbQuestion) {
    }

    @objid ("429636f2-6e49-40c5-adff-d472548215cc")
    public void supprimerCategorie(final Categorie aEnlever) {
    }

    @objid ("0c33c6c1-7c21-4e93-a805-abbadf81bee3")
    public boolean aAssezQuestions() {
        // TODO Auto-generated return
        return false;
    }

    @objid ("32f363ec-2629-4203-9975-edcf4be51411")
    public int nbQuestionsCategoriesSelectionnees() {
        // TODO Auto-generated return
        return 0;
    }

}
