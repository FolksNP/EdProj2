import javax.print.Doc;
import java.io.File;
import java.util.ArrayList;

public class Documento {

    private File arquivo;
    private File arquivoTratado = null;
    private TabelaHash hashT = new TabelaHash();

    public Documento(File arquivo){
        this.arquivo = arquivo;
    }

    public void removerStopWords(){

        ArrayList<String> caracteres = new ArrayList<>();

        String [] stopWords = {
                "a", "o", "e",
                "de", "em", "para", "que"
        };



    }

    public void processarArquivo(){

    }


}
