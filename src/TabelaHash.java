public class TabelaHash {

    private static final int CAPACIDADE_INICIAL = 16;
    private No[] vetor;
    private int capacidade;
    private int elementosInseridos;

    // Classe interna para representar cada entrada (encadeamento para colisões)
    private class No {
        String chave;
        int valor; // frequência da palavra
        No proximo; // próximo nó na mesma posição (encadeamento)

        public No(String chave, int valor) {
            this.chave = chave;
            this.valor = valor;
            this.proximo = null;
        }
    }

    public TabelaHash() {
        this.capacidade = CAPACIDADE_INICIAL;
        this.vetor = new No[capacidade];
        this.elementosInseridos = 0;
    }

    // Função hash 1 — hash polinomial (djb2-like)
    // Multiplica o hash acumulado por 31 e soma o caractere atual.
    // Usar 31 evita colisões entre palavras que são anagramas (ex: "casa" e "saca").
    public int hashPolinomial(String chave) {
        long hash = 0;
        for (int i = 0; i < chave.length(); i++) {
            hash = (hash * 31 + chave.charAt(i)) % capacidade;
        }
        return (int) hash;
    }

    // Função hash 2 — hash multiplicativo (método de Knuth)
    // Usa a constante A = (sqrt(5) - 1) / 2 ≈ 0.618 para espalhar bem as chaves.
    public int hashMultiplicativo(String chave) {
        final double A = 0.6180339887; // constante de Knuth
        long codigoChave = 0;
        for (char c : chave.toCharArray()) {
            codigoChave = codigoChave * 31 + c;
        }
        double parteDecimal = (Math.abs(codigoChave) * A) % 1.0;
        return (int) (parteDecimal * capacidade);
    }

    private int calcularIndice(String chave) {
        return hashPolinomial(chave); // troque por hashMultiplicativo para comparação
    }

    // Insere a palavra e incrementa sua frequência (ou adiciona com frequência 1)
    public void inserir(String chave) {
        if ((double) elementosInseridos / capacidade > 0.75) {
            resize();
        }

        int indice = calcularIndice(chave);
        No atual = vetor[indice];

        // Percorre a lista encadeada buscando a chave
        while (atual != null) {
            if (atual.chave.equals(chave)) {
                atual.valor++; // já existe: incrementa frequência
                return;
            }
            atual = atual.proximo;
        }

        // Chave não encontrada: insere no início da lista do bucket
        No novo = new No(chave, 1);
        novo.proximo = vetor[indice];
        vetor[indice] = novo;
        elementosInseridos++;
    }

    // Retorna a frequência da palavra, ou 0 se não encontrada
    public int get(String chave) {
        int indice = calcularIndice(chave);
        No atual = vetor[indice];

        while (atual != null) {
            if (atual.chave.equals(chave)) {
                return atual.valor;
            }
            atual = atual.proximo;
        }

        return 0;
    }

    // Retorna todas as palavras armazenadas (necessário para calcular similaridade)
    public String[] obterChaves() {
        String[] chaves = new String[elementosInseridos];
        int idx = 0;
        for (int i = 0; i < capacidade; i++) {
            No atual = vetor[i];
            while (atual != null) {
                chaves[idx++] = atual.chave;
                atual = atual.proximo;
            }
        }
        return chaves;
    }

    // Retorna o número de elementos em cada bucket (para análise de colisões no relatório)
    public int[] distribuicaoColisoes() {
        int[] distribuicao = new int[capacidade];
        for (int i = 0; i < capacidade; i++) {
            No atual = vetor[i];
            while (atual != null) {
                distribuicao[i]++;
                atual = atual.proximo;
            }
        }
        return distribuicao;
    }

    public int getElementosInseridos() {
        return elementosInseridos;
    }

    public int getCapacidade() {
        return capacidade;
    }

    // Dobra a capacidade e reinsere todos os elementos
    private void resize() {
        No[] vetorAntigo = this.vetor;
        int capacidadeAntiga = this.capacidade;

        this.capacidade = capacidadeAntiga * 2;
        this.vetor = new No[this.capacidade];
        this.elementosInseridos = 0;

        for (int i = 0; i < capacidadeAntiga; i++) {
            No atual = vetorAntigo[i];
            while (atual != null) {
                reinserirAposResize(atual.chave, atual.valor);
                atual = atual.proximo;
            }
        }
    }

    private void reinserirAposResize(String chave, int valor) {
        int indice = calcularIndice(chave);
        No novo = new No(chave, valor);
        novo.proximo = vetor[indice];
        vetor[indice] = novo;
        elementosInseridos++;
    }

}
