import java.util.ArrayList;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("5e59827e-5727-4a8e-963e-50ee6167bc6b")
public class PartieEnCours {
    @objid ("69aff9b0-70df-4eb3-9897-d37d64e2304c")
    private Question[] toutesLesQuestions = new Question[20];

    @objid ("14bc80c1-1d1d-4196-a265-4be6369c8f1f")
    private int[] indiceQuestionCourante = new int[];

    @objid ("2b9e04b3-a73b-4f39-8d27-8211017f8945")
    private List<Question> question = new ArrayList<Question> ();

    @objid ("94494bf7-5e97-41a2-a1e3-2104a550d2a2")
    private void ordreQuestionsAleatoires() {
    }

    @objid ("c82e23b7-5807-4806-a429-b4cbc45002a4")
    private void ordreReponsesAleatoires(final Question question) {
    }

}
