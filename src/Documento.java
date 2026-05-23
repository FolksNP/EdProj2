import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Documento {

    private String nomeArquivo;
    private File arquivo;
    private TabelaHash frequenciaTermos;

    // Lista de stop words em português para ignorar durante o processamento
    private static final Set<String> STOP_WORDS = new HashSet<>(Arrays.asList(
        "a", "o", "e", "é", "de", "do", "da", "dos", "das", "em", "no", "na",
        "nos", "nas", "para", "por", "com", "sem", "que", "se", "um", "uma",
        "uns", "umas", "ao", "aos", "ou", "mas", "nem", "pois", "como",
        "mais", "menos", "muito", "ja", "ainda", "tambem", "nao", "sim",
        "este", "esta", "esse", "essa", "aquele", "aquela", "seu", "sua",
        "meu", "minha", "nosso", "nossa", "pelo", "pela", "pelos", "pelas",
        "num", "numa", "ele", "ela", "eles", "elas", "eu", "tu", "nos", "vos",
        "me", "te", "lhe", "isto", "isso", "aquilo", "quando", "onde", "quem",
        "qual", "quais", "tudo", "nada", "cada", "entre", "ate", "sobre",
        "depois", "antes", "ja", "so", "bem", "mal", "neste", "nesta",
        "nesse", "nessa", "deste", "desta", "desse", "dessa", "daquele",
        "daquela", "foi", "ser", "ter", "ha", "sao", "eram"
    ));

    public Documento(String nomeArquivo, File arquivo) {
        this.nomeArquivo = nomeArquivo;
        this.arquivo = arquivo;
        this.frequenciaTermos = new TabelaHash();
    }

    // Lê o arquivo, normaliza o texto e popula a tabela hash com as frequências
    public void processarDocumento() {
        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                // Converte para minúsculo
                linha = linha.toLowerCase();

                // Remove caracteres não-alfanuméricos (mantém letras unicode e espaços)
                linha = linha.replaceAll("[^\\p{L}0-9\\s]", "");

                // Tokenização: separa as palavras por espaço
                String[] tokens = linha.split("\\s+");

                for (String token : tokens) {
                    if (token.isEmpty()) continue;
                    if (STOP_WORDS.contains(token)) continue; // ignora stop words
                    frequenciaTermos.inserir(token);
                }

            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + nomeArquivo);
        }
    }

    // Getters e setters
    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public File getArquivo() {
        return arquivo;
    }

    public TabelaHash getFrequenciaTermos() {
        return frequenciaTermos;
    }

    public void setFrequenciaTermos(TabelaHash frequenciaTermos) {
        this.frequenciaTermos = frequenciaTermos;
    }

}
