import java.util.ArrayList;
import java.util.List;

public class ArvoreAVL {
    private No raiz;
    private final StatusRotacoes statusRotacoes;
    private static final double GRAU_TOLERANCIA_IGUALDADE = 0.000001; // evita problemas de precisão com double

    public ArvoreAVL() {
        this.statusRotacoes = new StatusRotacoes();
    }

    public void adicionarParDocumento(ParDocumento novoPar) {
        this.raiz = adicionarParDocumento(this.raiz, novoPar); // atribui o retorno para atualizar a raiz
    }

    private No adicionarParDocumento(No raiz, ParDocumento novoPar) {
        // Inserção padrão de BST
        if (raiz == null) {
            return new No(novoPar);
        }

        if (Math.abs(novoPar.getGrauSimilaridade() - raiz.grauSimilaridade) < GRAU_TOLERANCIA_IGUALDADE) {
            raiz.adicionarPar(novoPar); // mesmo grau de similaridade: adiciona na lista do nó
        } else if (novoPar.getGrauSimilaridade() < raiz.grauSimilaridade) {
            raiz.esquerda = adicionarParDocumento(raiz.esquerda, novoPar);
        } else {
            raiz.direita = adicionarParDocumento(raiz.direita, novoPar);
        }

        // Atualiza altura e aplica balanceamento se necessário
        raiz.atualizarAltura();
        return balancear(raiz);
    }

    // Verifica o fator de balanceamento e aplica a rotação correta
    private No balancear(No no) {
        int fb = fatorBalanceamento(no);

        // Caso LL: subárvore esquerda pesada, filho esquerdo também pende para esquerda
        if (fb > 1 && fatorBalanceamento(no.esquerda) >= 0) {
            statusRotacoes.adicionarSimplesDireita();
            return rotacaoDireita(no);
        }

        // Caso RR: subárvore direita pesada, filho direito também pende para direita
        if (fb < -1 && fatorBalanceamento(no.direita) <= 0) {
            statusRotacoes.adicionarSimplesEsquerda();
            return rotacaoEsquerda(no);
        }

        // Caso LR: subárvore esquerda pesada, mas filho esquerdo pende para direita
        if (fb > 1 && fatorBalanceamento(no.esquerda) < 0) {
            statusRotacoes.adicionarDuplaEsquerda();
            no.esquerda = rotacaoEsquerda(no.esquerda);
            return rotacaoDireita(no);
        }

        // Caso RL: subárvore direita pesada, mas filho direito pende para esquerda
        if (fb < -1 && fatorBalanceamento(no.direita) > 0) {
            statusRotacoes.adicionarDuplaDireita();
            no.direita = rotacaoDireita(no.direita);
            return rotacaoEsquerda(no);
        }

        return no; // já está balanceado
    }

    // Rotação simples à esquerda (nó y sobe, nó x desce)
    private No rotacaoEsquerda(No x) {
        No y = x.direita;
        No t2 = y.esquerda;

        y.esquerda = x;
        x.direita = t2;

        x.atualizarAltura();
        y.atualizarAltura();

        return y;
    }

    // Rotação simples à direita (nó y sobe, nó x desce)
    private No rotacaoDireita(No x) {
        No y = x.esquerda;
        No t2 = y.direita;

        y.direita = x;
        x.esquerda = t2;

        x.atualizarAltura();
        y.atualizarAltura();

        return y;
    }

    private int altura(No no) {
        if (no == null) return -1;
        return no.altura;
    }

    private int fatorBalanceamento(No no) {
        if (no == null) return 0;
        return altura(no.esquerda) - altura(no.direita);
    }

    // Percurso em ordem (retorna pares ordenados do menor para o maior grau de similaridade)
    public List<ParDocumento> obterParesEmOrdem() {
        ArrayList<ParDocumento> pares = new ArrayList<>();
        obterParesEmOrdem(raiz, pares);
        return pares;
    }

    private void obterParesEmOrdem(No raiz, List<ParDocumento> lista) {
        if (raiz == null) return;
        obterParesEmOrdem(raiz.esquerda, lista);
        for (ParDocumento par : raiz.pares) {
            lista.addLast(par);
        }
        obterParesEmOrdem(raiz.direita, lista);
    }

    public StatusRotacoes getStatusRotacoes() {
        return statusRotacoes;
    }


    private static class No {
        List<ParDocumento> pares;
        double grauSimilaridade; // chave do nó (redundante com pares.get(0), mas deixa o código mais claro)
        No esquerda;
        No direita;
        int altura;

        public No(ParDocumento par) {
            this.grauSimilaridade = par.getGrauSimilaridade();
            this.pares = new ArrayList<>();
            this.pares.addLast(par);
            this.altura = 0;
        }

        public void adicionarPar(ParDocumento par) {
            pares.addLast(par);
        }

        public void atualizarAltura() {
            int alturaEsquerda = (esquerda == null) ? -1 : esquerda.altura;
            int alturaDireita  = (direita  == null) ? -1 : direita.altura;
            this.altura = 1 + Math.max(alturaEsquerda, alturaDireita);
        }
    }

}
