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
    	unlockLevelSet(Constants.LEVELSETS[0][0]);
    	unlockMode(Constants.MODE_PRACTICE);
    	unlockMode(Constants.MODE_STANDARD);
    	unlockMode(Constants.MODE_MIN);
    	unlockMode(Constants.MODE_MAX);
    	unlockMode(Constants.MODE_TIME);
    }
    
    public boolean isLevelSetUnlocked(String levelSet) {
    	return this.preferences.getBoolean(LEVELSET_UNLOCK_PREFIX+levelSet, false);
    }
    
    public boolean isModeUnlocked(String mode) {
    	return this.preferences.getBoolean(MODE_UNLOCK_PREFIX+mode, false);
    }
    
    public void unlockMode(String mode) {
    	Editor editor = this.preferences.edit();
    	editor.putBoolean(MODE_UNLOCK_PREFIX+mode, true);
    	editor.commit();
    }
    
    public void unlockLevelSet(String levelSet) {
    	Editor editor = this.preferences.edit();
    	editor.putBoolean(LEVELSET_UNLOCK_PREFIX+levelSet, true);
    	editor.commit();
    }

}
