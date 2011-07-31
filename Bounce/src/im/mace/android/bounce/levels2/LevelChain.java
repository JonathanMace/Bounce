package im.mace.android.bounce.levels2;

import java.util.Iterator;
import java.util.List;


public class LevelChain {
    
    /**
     * Due to the uniqueness of folder names, the folder name acts as the ID for a level chain
     */
    private final String folder;

    /**
     * Constructs a new levelChain using the specified folder
     * as the root directory for the levels.
     * @param folder String name of the folder within the assets/levels
     * directory, eg the folder name for "/levels/awkward" is "awkward"
     */
    public LevelChain(String folder) {
        this.folder = folder;
        
    }
    
    public String getID() {
        return this.folder;
    }
    
    public LevelDef getLevel(int levelNumber) {
        return null;
    }
    
    public int getLevelCount() {
        return 0;
    }
    
    public List<String> getLevelNames() {
        return null;        
    }
    
    public Iterator<LevelDef> iterator() {
        return null;
    }
    
    

}
