package im.mace.android.bounce.io;

import im.mace.android.bounce.common.Constants;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class GameState {

	private static final String MODE_UNLOCK_PREFIX = "mode_unlocked_";
	private static final String LEVELSET_UNLOCK_PREFIX = "levelset_unlocked_";

    public SharedPreferences preferences;

	public GameState(Activity activity) {
		this.preferences = PreferenceManager.getDefaultSharedPreferences(activity.getBaseContext());
		unlockDefaults();
	}
    
    private void unlockDefaults() {
    	unlockMode(Constants.MODE_STANDARD);
    	unlockMode(Constants.MODE_MIN);
    	unlockMode(Constants.MODE_MAX);
    	unlockMode(Constants.MODE_TIME);
    	for (String levelSet : Constants.LEVELSETS) {
        	unlockLevelSet(Constants.MODE_STANDARD, levelSet); 
        	unlockLevelSet(Constants.MODE_MIN, levelSet); 
        	unlockLevelSet(Constants.MODE_MAX, levelSet); 
        	unlockLevelSet(Constants.MODE_TIME, levelSet);    		
    	}
    	
//    	unlockMode(Constants.MODE_STANDARD);
//    	unlockLevelSet(Constants.MODE_STANDARD, Constants.LEVELSETS[0]);
    }
    
    public boolean isLevelSetUnlocked(String mode, String levelSet) {
    	return this.preferences.getBoolean(LEVELSET_UNLOCK_PREFIX+mode+"_"+levelSet, false);
    }
    
    public boolean isNextLevelSetUnlocked(String mode, String curLevelSet) {
    	String nextLevelSet = getNext(curLevelSet);
    	boolean isUnlocked = false;
    	if (nextLevelSet!=null) {
    		isUnlocked = isLevelSetUnlocked(mode, nextLevelSet);
    	}
    	return isUnlocked;    	
    }
    
    public boolean isModeUnlocked(String mode) {
    	return this.preferences.getBoolean(MODE_UNLOCK_PREFIX+mode, false);
    }
    
    public void unlockMode(String mode) {
    	Editor editor = this.preferences.edit();
    	editor.putBoolean(MODE_UNLOCK_PREFIX+mode, true);
    	editor.commit();
    }
    
    public void unlockLevelSet(String mode, String levelSet) {
    	Editor editor = this.preferences.edit();
    	editor.putBoolean(LEVELSET_UNLOCK_PREFIX+mode+"_"+levelSet, true);
    	editor.commit();
    }
    
    public void unlockNextLevelSet(String mode, String curLevelSet) {
    	String nextLevelSet = getNext(curLevelSet);
    	if (nextLevelSet!=null) {
    		unlockLevelSet(mode, nextLevelSet);
    		unlockLevelSet(Constants.MODE_PRACTICE, curLevelSet);
    	}
    }
    
    private String getNext(String current) {
    	String nextLevelSet = null;
    	for (int i = 0; i < Constants.LEVELSETS.length-1; i++) {
    		if (Constants.LEVELSETS[i].equals(current)) {
    			nextLevelSet = Constants.LEVELSETS[i+1];
    		}
    	}
    	return nextLevelSet;
    }

}
