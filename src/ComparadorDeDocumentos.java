public class ComparadorDeDocumentos {

    // Calcula a similaridade de cosseno entre dois documentos.
    // Fórmula: cos(θ) = (A · B) / (|A| × |B|)
    // Onde A e B são vetores de frequência de termos de cada documento.
    public static double calcularSimilaridade(Documento docA, Documento docB) {
        TabelaHash freqA = docA.getFrequenciaTermos();
        TabelaHash freqB = docB.getFrequenciaTermos();

        String[] chavesA = freqA.obterChaves();

        // Produto escalar: soma de freqA[termo] * freqB[termo] para cada termo de A
        double produtoEscalar = 0;
        for (String chave : chavesA) {
            produtoEscalar += freqA.get(chave) * freqB.get(chave);
        }

        // Magnitude do vetor A: raiz da soma dos quadrados das frequências
        double magnitudeA = 0;
        for (String chave : chavesA) {
            magnitudeA += Math.pow(freqA.get(chave), 2);
        }
        magnitudeA = Math.sqrt(magnitudeA);

        // Magnitude do vetor B
        double magnitudeB = 0;
        for (String chave : freqB.obterChaves()) {
            magnitudeB += Math.pow(freqB.get(chave), 2);
        }
        magnitudeB = Math.sqrt(magnitudeB);

        // Evita divisão por zero (documento vazio após normalização)
        if (magnitudeA == 0 || magnitudeB == 0) return 0;

        return produtoEscalar / (magnitudeA * magnitudeB);
    }

}
