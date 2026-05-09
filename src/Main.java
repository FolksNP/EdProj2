import java.io.File;
import java.nio.file.Path;

public class Main{

    // TODO: Implementar funcao que procura os documentos que vao ser manipulados

    public static void main(String[] args){

        TabelaHash tabelaHash = new TabelaHash(10);

        tabelaHash.inserir("computacao", "computacao");
        System.out.println(tabelaHash.get("computacao"));

    }

}