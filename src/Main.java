import java.io.File;
import java.io.FileDescriptor;
import java.util.ArrayList;

public class Main{

    // TODO: Implementar funcao que procura os documentos que vao ser manipulados
    public static ArrayList<File> buscarArquivosNoDiretorio(String caminho){

        File pasta = new File(caminho); // busca a pasta

        // verificar se a pasta e valida
        if(!pasta.exists() || !pasta.isDirectory()){
            System.out.println("Caminho ou pasta invalida");
            return null;
        }

        File[] arquivos = pasta.listFiles();

        if (arquivos == null){
            System.out.println("Nenhum arquivo encontrado");
            return null;
        }

        ArrayList<File> retorno = new ArrayList<File>();

        for (File arquivo: arquivos){ // adiciona somente os arquivos validos
            if (arquivo.isFile() && arquivo.getName().endsWith(".txt")){
                retorno.add(arquivo);
            }
        }

        return retorno;

    }

    // compilar javac Main.java e javac TabelaHash.java
    // entrada: java Main <diretorio_documentos> <limiar> <modo> [argumentos_opcionais]
    public static void main(String[] args){

        // pega as entradas via main no terminal
        String diretorio = args[0];
        Double limiar = Double.valueOf(args[1]);
        String modo = args[2];
        // outros argumentos podem ser capturados posteriormente, conforme a necessidade.

        ArrayList<File> arquivos = buscarArquivosNoDiretorio(diretorio);

        if (arquivos == null){
            System.out.println("Lista nula");
            return;
        }

        System.out.println("Quantidade: " + arquivos.size());

        for (int i = 0; i < arquivos.size(); i++){
            System.out.println(arquivos.get(i));
        }

        // atencao: capacidade nao funciona, esta sendo definida no codigo, para testar
        //TabelaHash tabelaHash = new TabelaHash(10);


        //testes
//        tabelaHash.inserir("computacao", "computacao");
//        System.out.println(tabelaHash.get("computacao"));

    }

}