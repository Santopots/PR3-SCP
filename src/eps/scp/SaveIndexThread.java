package eps.scp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static eps.scp.Constants.DIndexFilePrefix;

public class SaveIndexThread extends Thread{
    private final int fileNumber;
    private final List<String> keysList;
    private final String outputDirectory;
    private final InvertedIndexCopy invertedIndexCopy;

    public SaveIndexThread(int fileNumber, List<String> keysList, String outputDirectory, InvertedIndexCopy invertedIndexCopy){
        this.fileNumber = fileNumber;
        this.keysList = keysList;
        this.outputDirectory = outputDirectory;
        this.invertedIndexCopy = invertedIndexCopy;
    }

    @Override
    public void run(){
        try {
            File KeyFile = new File(outputDirectory + "/" + DIndexFilePrefix + String.format("%03d", fileNumber));
            FileWriter fw = new FileWriter(KeyFile);
            BufferedWriter bw = new BufferedWriter(fw);

            // Recorremos las claves correspondientes a este fichero.
            for (String key : keysList) {
                invertedIndexCopy.saveIndexKey(key, bw);  // Salvamos la clave al fichero.
            }
            bw.close(); // Cerramos el fichero.
        } catch (IOException e) {
            System.err.println("Error creating Index file " + outputDirectory + "/IndexFile" + fileNumber);
            e.printStackTrace();
            System.exit(-1);
        }
    }

}
