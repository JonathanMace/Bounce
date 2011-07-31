package im.mace.android.bounce.levels;

import im.mace.android.bounce.levels2.LevelDef;


public class LevelManager {
    
    private static LevelChain chain;
    private static LevelDef current;
    
    public static void begin(String chainName) {
        begin(chainName, 0);
    }
    
    public static void begin(String chainName, int levelNumber) {
        chain = LevelIO.getLevels(chainName, levelNumber);
        progress();
    }
    
    public static void progress() {
        current = null;
        if (chain.hasNext()) {
            try {
                current = chain.next();
            } catch (Exception e) {
                // Do nothing
            }
        }
    }
    
    public static boolean hasCurrent() {
        return current!=null;
    }
    
    public static LevelDef getCurrent() {
        return current;
    }
    
    public static void done() {
        chain = null;
        current = null;
    }

}
