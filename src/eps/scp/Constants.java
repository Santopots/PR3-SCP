package eps.scp;

public class Constants {

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_GREEN_YELLOW_UNDER = "\u001B[32;40;4m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final int DIndexMaxNumberOfFiles = 200;   // Número máximo de ficheros para salvar el índice invertido.
    public static final int DIndexMinNumberOfFiles = 2;     // Número mínimo de ficheros para salvar el índice invertido.
    public static final int DKeysByFileIndex = 1000;
    public static final String DIndexFilePrefix = "IndexFile";   // Prefijo de los ficheros de Índice Invertido.
    public static final String DFileLinesName = "FilesLinesContent";  // Nombre fichero donde guardar las lineas de los ficheros indexados
    public static final String DFilesIdsName = "FilesIds";  // Nombre fichero donde guardar las identificadores de los ficheros indexados
    public static final String DDefaultIndexDir = "./Index/";   // Directorio por defecto donde se guarda el indice invertido.
    public static final float DMatchingPercentage = 0.80f;  // Porcentaje mínimo de matching entre el texto original y la consulta (80%)
    public static final float DNearlyMatchingPercentage = 0.60f;  // Porcentaje mínimo de matching entre el texto original y la consulta (80%)
}
