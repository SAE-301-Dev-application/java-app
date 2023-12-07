import java.util.ArrayList;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("c077ff57-7a26-446b-afac-94f05f363110")
public class GestionCategorie {
    @objid ("9dd6a4a2-cc85-4db6-9800-29ee2b1611b9")
    private Categorie[] listeCategories;

    @objid ("a061121a-d1a5-446c-a9f7-2335fd88cb11")
    private List<Categorie> categorie = new ArrayList<Categorie> ();

    @objid ("7c53ca94-c617-4c1d-b05f-1292dd3fe2a8")
    public void supprimer() {
    }

    @objid ("53f5bb74-527f-43e9-9e1d-fcd2c3f3b45e")
    public void ajouter(final Categorie aAjouter) {
    }

    @objid ("a793509a-1972-4f77-8f4e-75fe7758f685")
    public GestionCategorie() {
    }

    @objid ("d86b7fd4-1600-415f-b3d6-4b6bfeae6e11")
    public List<Categorie> getListeCategories() {
        // TODO Auto-generated return
        return null;
    }

}
