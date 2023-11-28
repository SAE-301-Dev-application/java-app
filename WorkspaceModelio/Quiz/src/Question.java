import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("f0ce29a0-6247-4755-86a4-9c887ca5f19c")
public class Question {
    @objid ("6457f718-b79b-4960-a75a-b9effa36599a")
    private String intitule;

    @objid ("bf828967-6c1c-41cf-8955-f2123a3ef819")
    private String reponseJuste;

    @objid ("a2206528-b5cb-47c7-b655-775193dfea29")
    private String[] reponsesFausses = new String[4];

    @objid ("8f2d8dd1-51e3-4377-9eeb-a95f06e6f217")
    private int difficulte;

    @objid ("2d9c9e69-8895-4c43-a519-05d0166ecce6")
    private String feedback;

    @objid ("0429327c-5ab2-4786-b9f3-e64193d68f4e")
    private Categorie categorie;

    @objid ("7efb52d8-fc2d-496a-93d3-927c218402da")
    public Question(final String intitule, final String reponseJuste, final List<String> reponsesFausses, final int difficulte, final String feedback, final Categorie categorie) {
    }

    @objid ("23421c4f-f409-44b2-8f16-725a7b7fba0f")
    public String getIntitule() {
        // TODO Auto-generated return
        return null;
    }

    @objid ("2cf50132-3c8b-4e1b-9a02-e71d0edd6a2d")
    public String getReponseJuste() {
        // TODO Auto-generated return
        return null;
    }

    @objid ("12ee9415-bae0-4a85-8ea8-bf3881353d9f")
    public List<String> getReponsesFausses() {
        // TODO Auto-generated return
        return null;
    }

    @objid ("105c6140-c9c8-43d5-ae49-7e9dba1db768")
    public int getDifficulte() {
        // TODO Auto-generated return
        return 0;
    }

    @objid ("17d00e6a-9068-4b98-a39f-2d7fec865e3f")
    public String getFeedback() {
        // TODO Auto-generated return
        return null;
    }

    @objid ("0559fdd3-b43a-4718-bb3e-8b273214eb44")
    public Categorie getCategorie() {
        // TODO Auto-generated return
        return null;
    }

    @objid ("05f349e1-f7ea-496e-8977-9807990aeaa0")
    public Question(final String intitule, final String reponseJuste, final List<String> reponsesFausses, final int difficulte, final Categorie categorie) {
    }

}
