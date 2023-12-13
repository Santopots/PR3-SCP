package eps.scp;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Map;

import static eps.scp.Constants.ANSI_RED;
import static eps.scp.Constants.ANSI_RESET;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Indexing {
    public static final boolean Verbose = false;

    public static void main(String[] args) {
        InvertedIndexCopy hash;

        if (args.length != 2)
            System.err.println("Error in Parameters. Usage: Indexing <SourceDirectory> [<Index_Directory>]");
        if (args.length < 2)
            hash = new InvertedIndexCopy(args[0]);
        else
            hash = new InvertedIndexCopy(args[0], args[1]);

        Instant start = Instant.now();

        hash.buildIndex();
        hash.saveIndex();
        Map<String, HashSet<Location>> old_hash = hash.getHash();
        Map<Location, String> old_indexFilesLines = hash.getIndexFilesLines();
        Map<Integer, String> old_files = hash.getFiles();
        hash.loadIndex();

        // Comprobar que el Indice Invertido cargado sea igual al salvado.
        try {
            assertEquals(old_hash, hash.getHash());
            assertEquals(old_indexFilesLines, hash.getIndexFilesLines());
            assertEquals(old_files, hash.getFiles());
        } catch (AssertionError e) {
            System.out.println(ANSI_RED + e.getMessage() + " " + ANSI_RESET);
        }

        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();  //in millis
        System.out.printf("[All Stages] Total execution time: %.3f secs.\n", timeElapsed / 1000.0);
    }
}
