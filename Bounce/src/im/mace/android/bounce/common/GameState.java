package im.mace.android.bounce.common;

public class GameState {
    
    public static boolean isLevelSetUnlocked(String levelSet) {
        boolean isUnlocked = false;
        
        if (levelSet.equals("simple") || levelSet.equals("elegant") || levelSet.equals("tricky") || levelSet.equals("awkward") ||  levelSet.equals("impossible")) {
            isUnlocked = true;
        }
        
        return isUnlocked;
    }

}
