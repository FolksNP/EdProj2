import java.util.ArrayList;
import java.util.List;

public class ArvoreAVL {
    private No raiz;
    private final StatusRotacoes statusRotacoes; // TODO: Coloco um getter diretamente ou repasso os resultados dos métodos?
    private static final double GRAU_TOLERANCIA_IGUALDADE = 0.000001;  // Comparar double sem sofrer com imprecisões

    public ArvoreAVL() {
        this.statusRotacoes = new StatusRotacoes();
    }

    public void adicionarParDocumento(ParDocumento novoPar) {
        adicionarParDocumento(this.raiz, novoPar);
    }
    // TODO: adicionar lógica de balanceamento
    private No adicionarParDocumento(No raiz, ParDocumento novoPar) {
        if(raiz == null) {
            return new No(novoPar, 0); // Inserção na folha. FB = 0
        }

        if(Math.abs(novoPar.getGrauSimilaridade() - raiz.grauSimilaridade) < GRAU_TOLERANCIA_IGUALDADE) {
            raiz.adicionarPar(novoPar);
        } else if(novoPar.getGrauSimilaridade() < raiz.pares.getFirst().getGrauSimilaridade()) {
            raiz.esquerda = adicionarParDocumento(raiz.esquerda, novoPar);
        } else {
            raiz.direita = adicionarParDocumento(raiz.direita, novoPar);
        }

        return raiz;
    }

    // TODO
    private void rotacaoEsquerda(No raiz) {}
    private void rotacaoDireita(No raiz) {}

    public List<ParDocumento> obterParesEmOrdem() {
        ArrayList<ParDocumento> pares = new ArrayList<>(); // Podemos trocar para LinkedList, se for mais eficiente
        obterParesEmOrdem(raiz, pares);

        return pares;
    }

    private void obterParesEmOrdem(No raiz, List<ParDocumento> lista) {
        if(raiz == null) return;
        obterParesEmOrdem(raiz.esquerda, lista);

        for(ParDocumento par : raiz.pares) {
            lista.addLast(par);
        }

        obterParesEmOrdem(raiz.direita, lista);
    }


    private static class No {
        List<ParDocumento> pares;
        // Sei que todos ParDocumento tem o próprio atributo e todos os pares da lista terão o mesmo grau e,
        // por isso, esse atributo é redundante. Mas mantive, pois acredito que ele deixe o código menos confuso.
        double grauSimilaridade;

        No esquerda;
        No direita;
        int altura;

        public No(ParDocumento par, int altura) {
            this.altura = altura;
            this.grauSimilaridade = par.getGrauSimilaridade();
            pares = new ArrayList<>();
            pares.addLast(par);
        }

        public void adicionarPar(ParDocumento par) {
            pares.addLast(par);
        }

        public void adicionarPar(Documento docA, Documento docB, double grauSimilaridade) {
            pares.addLast(new ParDocumento(docA, docB, grauSimilaridade));
        }

        public void atualizarAltura() {
            // * TODO *
        }
    }

}

/*
Requisitos para a árvore:
 - A árvore ArvoreAVL deve armazenar a similaridade como chave. Atenção: é comum que
   múltiplos pares de documentos resultem no mesmo valor de similaridade.
   Para tratar corretamente este caso, cada n´o da ´arvore deve ser capaz de armazenar
   uma lista de pares de documentos associada `aquela chave, em vez de um ´unico par.

 - A implementação do método de inserção na Árvore AVLTree deve ser modificada para
   retornar um registro ou estrutura de dados que quantifique as rotações
   (Simples Esquerda/Direita, Dupla Esquerda/Direita) realizadas durante a operação.

 TODO - Dúvida: devemos separar a contagem para cada tipo de rotação? Ou apenas um contador serve?
         Na dúvida, criei uma classe própria pra lidar com essa lógica.
 */