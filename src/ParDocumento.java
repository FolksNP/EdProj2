public class ParDocumento {
    private final Documento docA;
    private final Documento docB;

    // Acredito que o cálculo do grau de similaridade deve ter uma lógica própria, em uma classe separada.
    // Podemos criar um método estático nessa nova classe e chamar aqui no construtor (encapsula melhor) ou só receber
    // diretamente como parâmetro mesmo (menos acoplado). Não sei de qual forma fica melhor.
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