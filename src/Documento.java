import javax.print.Doc;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;

public class Documento {

    private String nomeArquivo;
    private Path caminho;
    private TabelaHash frequenciaTermos;

    public Documento(String nomeDocumento){
        nomeArquivo = nomeDocumento;
        frequenciaTermos = new TabelaHash();
    }





}
