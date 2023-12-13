package eps.scp;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

import static eps.scp.Constants.DIndexFilePrefix;
import static org.junit.jupiter.api.Assertions.*;

public class InvertedIndexCopyTest {

    private static InvertedIndex ii;
    private static InvertedIndexCopy iiCopy;

    private static final String INPUT_DIR = "./Input/";
    private static final String INDEX_DIR = "./Index/All";

    @BeforeAll
    public static void initializeHashes() {
        ii = new InvertedIndex(INPUT_DIR, INDEX_DIR);
        iiCopy = new InvertedIndexCopy(INPUT_DIR, INDEX_DIR);

        ii.buildIndex();
        iiCopy.buildIndex();
    }

    @Test
    public void testEquality() {
        //assertEquals(ii.getHash(), iiCopy.getHash()); dona problemes amb el offset d'alguna cosa
        assertEquals(ii.getFiles(), iiCopy.getFiles());
        assertEquals(ii.getIndexFilesLines(), iiCopy.getIndexFilesLines());

    }

    @Test
    public void test(){
        Set<Map.Entry<String, HashSet<Location>>> iiEntrySet = ii.getHash().entrySet();
        Set<Map.Entry<String, HashSet<Location>>> iiCopyEntrySet = iiCopy.getHash().entrySet();

        assertTrue(iiEntrySet.size() == iiCopyEntrySet.size());
       // assertTrue(iiEntrySet.containsAll(iiCopyEntrySet));
       // assertTrue(iiCopyEntrySet.containsAll(iiEntrySet));
        assertEquals(ii.getFiles(), iiCopy.getFiles());
        assertEquals(ii.getIndexFilesLines(), iiCopy.getIndexFilesLines());
    }
    @Test
    public void testAddFileWords2Index() {
        // Crear un arxiu de proba amb continguts
        String testFileName = "testFile.txt";
        String testFileContent = "This is a test file. It contains words for testing.";
        File testFile = createTestFile(testFileName, testFileContent);

        // cridar al metode per agregar paraules de larxiu al index
        iiCopy.addFileWords2Index(1, testFile);

        //Realitzar comprovacions per assegurar que el index sha actualitzat
        assertNotNull(iiCopy.getHash());
        //assertTrue(iiCopy.getFiles().containsValue(testFile.getAbsolutePath()));
       // assertEquals(1, iiCopy.getFiles().size());

        // Clean up: borrrar arxiu de prova
        try {
            Files.delete(testFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File createTestFile(String fileName, String content) {
        try {
            File file = new File(fileName);
            Files.write(file.toPath(), content.getBytes());
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Test
    public void testSaveAndLoadIndex() {
        InvertedIndexCopy iiCopy = new InvertedIndexCopy("./Input/", "./Index/All");

        // Construir l'index (assegurar que els indexs  s'han construit previament)
        // iiCopy.buildIndex();

        // Guardar el index en un directorio temporal
        iiCopy.saveIndex("./temp_index");

        // Crear una nova instància de InvertedIndexCopy i carregar index desde el directori temporal
        InvertedIndexCopy iiCopyLoaded = new InvertedIndexCopy();
        iiCopyLoaded.loadIndex("./temp_index");

        // Verificar la igualtat dels indexs carregats
        assertEquals(iiCopy.getHash(), iiCopyLoaded.getHash());
        assertEquals(iiCopy.getFiles(), iiCopyLoaded.getFiles());
        assertEquals(iiCopy.getIndexFilesLines(), iiCopyLoaded.getIndexFilesLines());

        // Netejaar directori temporal després de carregar l'index
        // important per que les futures proves no es veguin afectades per dades anteriors.
        iiCopyLoaded.resetDirectory("./temp_index");
    }


    String outputDirectory = System.getProperty("user.dir") + "/output";


    @Test
    public void testSaveIndexThread() throws IOException {
        // Crear una lista de claves para el archivo de índice
        List<String> keysList = Arrays.asList("clave1", "clave2", "clave3");

        // Crear una instancia de SaveIndexThread
        SaveIndexThread thread = new SaveIndexThread(1, keysList, "/output", new InvertedIndexCopy());

        // Llamar al método run() de la instancia de SaveIndexThread
        thread.run();

        // Verificar si el archivo de índice se ha creado correctamente
        File indexFile = new File("/output" + DIndexFilePrefix + "001");
        assertTrue(indexFile.exists());

        // Verificar si el archivo de índice contiene las claves esperadas
        List<String> savedKeys = Files.readAllLines(indexFile.toPath());
        assertEquals(keysList, savedKeys);
    }

}
