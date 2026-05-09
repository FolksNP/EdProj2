public class TabelaHash{

    // para testes, vamos usar uma estrutura de dados mais primitiva (array simples)

    private Integer capacidade = 10;
    private Integer elementosInseridos = 0;
    private No [] vetor = new No[capacidade];

    private class No{
        Integer chave;
        String valor;

        public No(Integer chave, String valor) {
            this.chave = chave;
            this.valor = valor;
        }

        public Integer getChave() {
            return chave;
        }

        public void setChave(Integer chave) {
            this.chave = chave;
        }

        public String getValor() {
            return valor;
        }

        public void setValor(String valor) {
            this.valor = valor;
        }
    }

    public TabelaHash(Integer capacidade){
        this.capacidade = capacidade;
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
        No novo = new No(hash, valor);

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
        vetor[indice] = null;
    }

    // TODO: Fazer a funcao de expensao - resize()
    public void resize(){
        this.capacidade = capacidade * 2; // dobra o tamanho
    }

}
