package eps.scp;

import java.util.Objects;
import java.io.Serializable;

public class Location implements Comparable<Location>
{
    //classe location, per a definir on es troba x paraula

    private static final long serialVersionUID = 1L;

    private int fileId; // ID del fitxer
    private int line; //línea

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public Location(int fileId, int line) {
        this.fileId = fileId;
        this.line = line;
    } // crear instancia de classe Location proporcionant in fileID i una línea

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Location))
            return false;
        Location tuple = (Location) o;
        return fileId == tuple.fileId && line==tuple.line;
    } // mètode sobreescrit, dos objectes Location son iguals

    @Override
    public int hashCode() {
        return Objects.hash(fileId, line);
    } // per a proporcionar un valor hash basat en els valors del fileID i linea

    @Override
    public String toString() {
        return "("+fileId+","+line+')';
    }

    @Override
    public int compareTo(Location o) {
        if (this.fileId == o.fileId && this.line == o.line)
            return 0;

        if (this.fileId < o.fileId || (this.fileId == o.fileId && this.line < o.line))
            return -1;
        else
            return 1;
    } //compara dos objectes Location
}
