package actor.ali.linh.sempre;

import java.util.logging.Logger;

import edu.stanford.nlp.sempre.Builder;
import edu.stanford.nlp.sempre.Derivation;
import edu.stanford.nlp.sempre.Example;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// Copied from edu.stanford.nlp.sempre.Master
public class SempreResponse {
    // Example that was parsed, if any.
    public Example ex;
    private Builder builder;

    // Which derivation we're selecting to show
    int candidateIndex = -1;

    // Detailed information
    public Map<String, Object> stats = new LinkedHashMap<>();
    public List<String> lines = new ArrayList<>();

    public String getFormulaAnswer() {
        if (ex.getPredDerivations().size() == 0)
            return "(no answer)";
        else if (candidateIndex == -1)
            return "(not selected)";
        else {
            Derivation deriv = getDerivation();
            return deriv.getFormula() + " => " + deriv.getValue();
        }
    }
    public String getAnswer() {
        if (ex.getPredDerivations().size() == 0)
            return "(no answer)";
        else if (candidateIndex == -1)
            return "(not selected)";
        else {
            Derivation deriv = getDerivation();
            deriv.ensureExecuted(builder.executor, ex.context);
            return deriv.getValue().toString();
        }
    }
    public List<String> getLines() { return lines; }
    public Example getExample() { return ex; }
    public int getCandidateIndex() { return candidateIndex; }

    public Derivation getDerivation() {
        return ex.getPredDerivations().get(candidateIndex);
    }

    public SempreResponse(Builder b) {
        this.builder = b;
    }
}

