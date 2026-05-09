public class TabelaHash{

    // para testes, vamos usar uma estrutura de dados mais primitiva (array simples)

    private Integer capacidade = 1;
    private Integer elementosInseridos = 0;
    private No [] vetor = new No[capacidade];

    private class No{
        String chave;
        String valor;

        public No(String chave, String valor) {
            this.chave = chave;
            this.valor = valor;
        }

        public String getChave() {
            return chave;
        }

        public void setChave(String chave) {
            this.chave = chave;
        }

        public String getValor() {
            return valor;
        }

        public void setValor(String valor) {
            this.valor = valor;
        }
    }

    public TabelaHash(){

    }

    // mapear a posicao da chave.
    /* Funcionamento:
        Pegamos cada caractere, e pegamos o valor inteiro (ascii)
        Fazemos a soma de todos os valores e fazemos mod capacidade.
     */

    private Integer funcaoHash(String chave){

        Integer [] caracteres = new Integer[chave.length()];
        for (int i = 0; i < chave.length(); i++){
            caracteres[i] = (int) chave.charAt(i); //pega ascii code
        }
        Integer soma = 0;
        for (int i = 0; i < caracteres.length; i++){
            soma+=caracteres[i];
        }

        return soma;

    }

    private Integer compressao(Integer hash){
        return hash % capacidade; // capacidade global
    }

    public void inserir(String chave, String valor){
        if (((double) elementosInseridos / capacidade) > 0.85) resize();

        Integer hash = funcaoHash(chave);
        Integer indice = compressao(hash);
        No novo = new No(chave, valor);

        if (vetor[indice] == null){
            vetor[indice] = novo;
            elementosInseridos++;
        }else{
            // para testes, depois implementar uma solucao mais robusta
            vetor[indice + 1] = novo;
            elementosInseridos++;
        }

    }

    // TODO: Fazer o tratamento de colisoes, podemos usar AVL.

    public String get(String chave){
        Integer hash = funcaoHash(chave);
        Integer indice = compressao(hash);
        return vetor[indice].getValor();
    }

    public void remove(String chave){
        Integer hash = funcaoHash(chave);
        Integer indice = compressao(hash);
        elementosInseridos--;
        vetor[indice] = null;
    }

    public void resize() {

        No[] vetorAntigo = this.vetor;
        Integer capacidadeAntiga = this.capacidade;

        this.capacidade = capacidadeAntiga * 2;

        this.vetor = new No[this.capacidade];
        this.elementosInseridos = 0;

        for (int i = 0; i < capacidadeAntiga; i++) {
            if (vetorAntigo[i] != null) {
                No atual = vetorAntigo[i];
                reinstalarAposResize(atual);
            }
        }
    }

    private void reinstalarAposResize(No no) {
        Integer novoIndice = compressao(funcaoHash(no.getChave()));

        if (vetor[novoIndice] == null) {
            vetor[novoIndice] = no;
        } else {
            // Busca o próximo espaço livre de forma circular
            int tentativa = (novoIndice + 1) % capacidade;
            while (vetor[tentativa] != null) {
                tentativa = (tentativa + 1) % capacidade;
            }
            vetor[tentativa] = no;
        }
        elementosInseridos++;
    }

}
