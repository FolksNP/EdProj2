import java.util.List;

public class ArvoreAVL {
    private No raiz;

    public ArvoreAVL() {
    }

    public void adicionarParDocumento() {
        // TODO
    }

    private static class ParDocumento {
        Documento docA;
        Documento docB;
        double grauSimilaridade;

        public ParDocumento(Documento docA, Documento docB, double grauSimilaridade) {
            this.docA = docA;
            this.docB = docB;
            this.grauSimilaridade = grauSimilaridade;
        }
    }

    private static class No {
        List<ParDocumento> pares; // Chave do nó
        No esquerda;
        No direita;
        int fatorBalanceamento;

        public No(ParDocumento par, int fatorBalanceamento) {
            this.fatorBalanceamento = fatorBalanceamento;
        }

        public void adicionarPar(ParDocumento par) {
            pares.addLast(par);
        }

        public void adicionarPar(Documento docA, Documento docB, double grauSimilaridade) {
            pares.addLast(new ParDocumento(docA, docB, grauSimilaridade));
        }
    }

}

/*
Requisitos para a árvore:
 - A árvore ArvoreAVL deve armazenar a similaridade como chave. Atenção: é comum que
   múltiplos pares de documentos resultem no mesmo valor de similaridade.
   Para tratar corretamente este caso, cada n´o da ´arvore deve ser capaz de armazenar
   uma lista de pares de documentos associada `aquela chave, em vez de um ´unico par.

 - A implementa¸c˜ao do m´etodo de inser¸c˜ao na Arvore ArvoreAVL deve ser modificada para ´
   retornar um registro ou estrutura de dados que quantifique as rota¸c˜oes
   (Simples Esquerda/Direita, Dupla Esquerda/Direita) realizadas durante a opera¸c˜ao.

 Dúvida: devemos separar a contagem para cada tipo de rotação? Ou apenas um contador serve?
         Se for pra separar, vale a pena criar uma classe pra isso.
 */