import java.util.ArrayList;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("cef55573-6e17-4b04-a86b-e6430efbc3c5")
public class GestionQuestions {
    @objid ("46718c86-23d3-48f1-ba9d-d41b0095a37f")
    private Question[] listeQuestions;

    @objid ("1fddb9c4-be65-4526-abd5-57d54d9177e7")
    private List<Question> question = new ArrayList<Question> ();

    @objid ("169ccb6d-042d-4198-93aa-be1ca69c642e")
    public void supprimer() {
    }

    @objid ("a4ef71af-9940-48b7-a5f4-6d82834e5098")
    public void ajouter(final Question aAjouter) {
    }

    @objid ("d7b0312a-ac37-441c-8142-9f83ad460133")
    public GestionQuestions() {
    }

    @objid ("454e44e1-ab34-4b14-8dcf-0bd99dd003e7")
    public List<Question> getListeQuestions() {
        // TODO Auto-generated return
        return null;
    }

}
