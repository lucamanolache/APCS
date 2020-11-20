package truth;

public class LogicalSentence {

    private enum Type {
        SIMPLE, NEGATION, CONJUNCTION, DISJUNCTION, IMPLICATION, BICONDITIONAL
    }

    private Type type;
    private String value;
    private LogicalSentence[] subSentences;

    /**
     * Constructor for a simple logical sentence.
     */
    public LogicalSentence(String value) {
        this.type = Type.SIMPLE;
        this.value = value;
        this.subSentences = null;
    }

    /**
     * A constructor made specifically for negations.
     * @param s The logical sentence to be negated.
     */
    public LogicalSentence(LogicalSentence s) {
        this("~", s, null);
    }

    /**
     * Constructor for a complex logical sentence. If given a negation, {@param s2} should be null.
     * @param type Type can only be "~", "|", "&", "=>", or "<=>".
     */
    public LogicalSentence(String type, LogicalSentence s1, LogicalSentence s2) {
        this.subSentences = new LogicalSentence[2];
        this.subSentences[0] = s1;
        this.subSentences[1] = s2;
        this.value = null;

        switch (type) {
            case "~":
                this.type = Type.NEGATION;
                break;
            case "|":
                this.type = Type.DISJUNCTION;
                break;
            case "&":
                this.type = Type.CONJUNCTION;
                break;
            case "=>":
                this.type = Type.IMPLICATION;
                break;
            case "<=>":
                this.type = Type.BICONDITIONAL;
                break;
            default:
                throw new IllegalArgumentException("Type not valid");
        }
    }

    /**
     * Returns the value of this logical sentence with each of the values given in a truth assignment. It is assumed
     * that the truth assigment contains all of the data needed for this LogicalSentence.
     * @param assignment The truth assignment
     * @return The value of this logical sentence
     */
    public boolean getValue(TruthAssignment assignment) {
        switch (this.type) {
            case SIMPLE:
                return assignment.getValue(this.value);
            case NEGATION:
                return !this.subSentences[0].getValue(assignment);
            case DISJUNCTION:
                return this.subSentences[0].getValue(assignment) || this.subSentences[1].getValue(assignment);
            case CONJUNCTION:
                return this.subSentences[0].getValue(assignment) && this.subSentences[1].getValue(assignment);
            case IMPLICATION:
                return !this.subSentences[0].getValue(assignment) || this.subSentences[1].getValue(assignment);
            case BICONDITIONAL:
                return this.subSentences[0].getValue(assignment) == this.subSentences[1].getValue(assignment);
            default:
                throw new RuntimeException("Type is not recognized");
        }
    }

    @Override
    public String toString() {
        switch (this.type) {
            case SIMPLE:
                return this.value;
            case NEGATION:
                return String.format("~%s", this.subSentences[0].toString());
            case DISJUNCTION:
                return String.format("(%s | %s)", this.subSentences[0].toString(), this.subSentences[1].toString());
            case CONJUNCTION:
                return String.format("(%s & %s)", this.subSentences[0].toString(), this.subSentences[1].toString());
            case IMPLICATION:
                return String.format("(%s => %s)", this.subSentences[0].toString(), this.subSentences[1].toString());
            case BICONDITIONAL:
                return String.format("(%s <=> %s)", this.subSentences[0].toString(), this.subSentences[1].toString());
            default:
                throw new RuntimeException("Type is not recognized");
        }
    }
}
