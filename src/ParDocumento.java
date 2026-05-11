public class ParDocumento {
    private final Documento docA;
    private final Documento docB;
    private final double grauSimilaridade;

    public ParDocumento(Documento docA, Documento docB, double grauSimilaridade) {
        this.docA = docA;
        this.docB = docB;
        this.grauSimilaridade = grauSimilaridade;
    }

    public Documento getDocA() {
        return docA;
    }

    public Documento getDocB() {
        return docB;
    }

    public double getGrauSimilaridade() {
        return grauSimilaridade;
    }
}