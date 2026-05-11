public class StatusRotacoes {
    private int simplesEsquerda;
    private int simplesDireita;
    private int duplaEsquerda;
    private int duplaDireita;

    public StatusRotacoes() {
        this.simplesEsquerda = 0;
        this.simplesDireita = 0;
        this.duplaEsquerda = 0;
        this.duplaDireita = 0;
    }

    public void adicionarSimplesEsquerda() {
        simplesEsquerda++;
    }

    public void adicionarSimplesDireita() {
        simplesDireita++;
    }

    public void adicionarDuplaEsquerda() {
        duplaEsquerda++;
    }

    public void adicionarDuplaDireita() {
        duplaDireita++;
    }

    public int total() {
        return simplesDireita + simplesDireita + duplaEsquerda + duplaDireita;
    }

    public int simples() {
        return simplesDireita + simplesDireita;
    }

    public int duplas() {
        return duplaEsquerda + duplaDireita;
    }

    public int getSimplesEsquerda() {
        return simplesEsquerda;
    }

    public int getSimplesDireita() {
        return simplesDireita;
    }

    public int getDuplaEsquerda() {
        return duplaEsquerda;
    }

    public int getDuplaDireita() {
        return duplaDireita;
    }
}
