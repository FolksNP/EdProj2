/*
    Grupo:
    Felipe Kanamaru de Oliveira - 10435742
    Felipe Silva Siqueira - 10445036
    Gustavo Henrique de Sousa Santos - 10731355
    João Pedro Honorato - 10726497
    Theo Resende Simoes Esposito - 10721356
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    // Busca todos os arquivos .txt dentro de um diretório
    public static ArrayList<File> buscarArquivosNoDiretorio(String caminho) {
        File pasta = new File(caminho);

        if (!pasta.exists() || !pasta.isDirectory()) {
            System.out.println("Caminho ou pasta inválida");
            return null;
        }

        File[] arquivos = pasta.listFiles();

        if (arquivos == null) {
            System.out.println("Nenhum arquivo encontrado");
            return null;
        }

        ArrayList<File> retorno = new ArrayList<>();
        for (File arquivo : arquivos) {
            if (arquivo.isFile() && arquivo.getName().endsWith(".txt")) {
                retorno.add(arquivo);
            }
        }

        return retorno;
    }

    // Cria e processa todos os documentos do diretório
    private static ArrayList<Documento> processarDocumentos(ArrayList<File> arquivos) {
        ArrayList<Documento> documentos = new ArrayList<>();
        for (File arquivo : arquivos) {
            Documento doc = new Documento(arquivo.getName(), arquivo);
            doc.processarDocumento();
            documentos.add(doc);
        }
        return documentos;
    }

    // Compara todos os pares possíveis e insere os resultados na AVL
    private static ArvoreAVL compararTodosOsPares(ArrayList<Documento> documentos) {
        ArvoreAVL avl = new ArvoreAVL();
        for (int i = 0; i < documentos.size(); i++) {
            for (int j = i + 1; j < documentos.size(); j++) {
                Documento docA = documentos.get(i);
                Documento docB = documentos.get(j);
                double similaridade = ComparadorDeDocumentos.calcularSimilaridade(docA, docB);
                avl.adicionarParDocumento(new ParDocumento(docA, docB, similaridade));
            }
        }
        return avl;
    }

    // Exibe no terminal e salva no resultado.txt ao mesmo tempo
    private static void exibirESalvar(String conteudo, FileWriter escritor) {
        System.out.print(conteudo);
        try {
            escritor.write(conteudo);
        } catch (IOException e) {
            System.out.println("Erro ao escrever no resultado.txt");
        }
    }

    // entrada: java Main <diretorio_documentos> <limiar> <modo> [argumentos_opcionais]
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Uso: java Main <diretorio> <limiar> <modo> [argumentos]");
            return;
        }

        String diretorio = args[0];
        double limiar    = Double.parseDouble(args[1]);
        String modo      = args[2];

        ArrayList<File> arquivos = buscarArquivosNoDiretorio(diretorio);
        if (arquivos == null || arquivos.isEmpty()) {
            System.out.println("Nenhum arquivo .txt encontrado em: " + diretorio);
            return;
        }

        ArrayList<Documento> documentos = processarDocumentos(arquivos);

        try (FileWriter escritor = new FileWriter("resultado.txt", false)) {

            if (modo.equals("busca")) {
                // Modo busca: compara dois documentos específicos
                if (args.length < 5) {
                    System.out.println("Modo busca requer dois nomes de arquivo: busca <doc1> <doc2>");
                    return;
                }
                String nomeA = args[3];
                String nomeB = args[4];

                Documento docA = null, docB = null;
                for (Documento doc : documentos) {
                    if (doc.getNomeArquivo().equals(nomeA)) docA = doc;
                    if (doc.getNomeArquivo().equals(nomeB)) docB = doc;
                }

                if (docA == null || docB == null) {
                    System.out.println("Arquivo(s) não encontrado(s): " + nomeA + " ou " + nomeB);
                    return;
                }

                double similaridade = ComparadorDeDocumentos.calcularSimilaridade(docA, docB);

                StringBuilder sb = new StringBuilder();
                sb.append("=== VERIFICADOR DE SIMILARIDADE DE TEXTOS ===\n");
                sb.append("Comparando: ").append(nomeA).append(" <-> ").append(nomeB).append("\n");
                sb.append(String.format("Similaridade calculada: %.2f%n", similaridade));
                sb.append("Métrica utilizada: Cosseno\n");
                exibirESalvar(sb.toString(), escritor);

            } else {
                // Modos lista e topK: compara todos os pares e usa a AVL
                ArvoreAVL avl = compararTodosOsPares(documentos);
                List<ParDocumento> pares = avl.obterParesEmOrdem(); // ordem crescente de similaridade
                int totalPares = pares.size();

                StringBuilder sb = new StringBuilder();
                sb.append("=== VERIFICADOR DE SIMILARIDADE DE TEXTOS ===\n");
                sb.append("Total de documentos processados: ").append(documentos.size()).append("\n");
                sb.append("Total de pares comparados: ").append(totalPares).append("\n");
                sb.append("Função hash utilizada: hashPolinomial\n");
                sb.append("Métrica de similaridade: Cosseno\n\n");

                if (modo.equals("lista")) {
                    sb.append(String.format("Pares com similaridade >= %.2f:%n", limiar));
                    sb.append("---------------------------------\n");

                    boolean encontrou = false;
                    // Percorre do fim para o início (maior para menor similaridade)
                    for (int i = pares.size() - 1; i >= 0; i--) {
                        ParDocumento par = pares.get(i);
                        if (par.getGrauSimilaridade() >= limiar) {
                            sb.append(String.format("%-20s <-> %-20s = %.2f%n",
                                par.getDocA().getNomeArquivo(),
                                par.getDocB().getNomeArquivo(),
                                par.getGrauSimilaridade()));
                            encontrou = true;
                        }
                    }

                    if (!encontrou) sb.append("Nenhum par encontrado acima do limiar.\n");

                } else if (modo.equals("topK")) {
                    if (args.length < 4) {
                        System.out.println("Modo topK requer o valor de K: topK <K>");
                        return;
                    }
                    int k = Integer.parseInt(args[3]);

                    sb.append("Top ").append(k).append(" pares mais similares:\n");
                    sb.append("---------------------------------\n");

                    int exibidos = 0;
                    for (int i = pares.size() - 1; i >= 0 && exibidos < k; i--) {
                        ParDocumento par = pares.get(i);
                        sb.append(String.format("%-20s <-> %-20s = %.2f%n",
                            par.getDocA().getNomeArquivo(),
                            par.getDocB().getNomeArquivo(),
                            par.getGrauSimilaridade()));
                        exibidos++;
                    }

                } else {
                    System.out.println("Modo inválido: " + modo + ". Use: lista, topK ou busca.");
                    return;
                }

                // Informações sobre as rotações realizadas na AVL
                StatusRotacoes sr = avl.getStatusRotacoes();
                sb.append("\nRotações realizadas na AVL:\n");
                sb.append("  Simples à direita: ").append(sr.getSimplesDireita()).append("\n");
                sb.append("  Simples à esquerda: ").append(sr.getSimplesEsquerda()).append("\n");
                sb.append("  Dupla esquerda-direita: ").append(sr.getDuplaEsquerda()).append("\n");
                sb.append("  Dupla direita-esquerda: ").append(sr.getDuplaDireita()).append("\n");
                sb.append("  Total: ").append(sr.total()).append("\n");

                exibirESalvar(sb.toString(), escritor);
            }

        } catch (IOException e) {
            System.out.println("Erro ao criar/abrir o arquivo resultado.txt");
        }
    }

}
