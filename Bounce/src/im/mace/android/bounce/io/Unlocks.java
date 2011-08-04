package im.mace.android.bounce.io;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;

public class Unlocks {
	
	private static Map<String, String> standardModeUnlockProgression = new HashMap<String, String>();
	private static Map<String, String> modeUnlockProgression = new HashMap<String, String>();
	
	
	static {
		// Progression through the modes
		standardModeUnlockProgression.put("simple", "elegant");
		standardModeUnlockProgression.put("elegant", "tricky");
		standardModeUnlockProgression.put("tricky", "awkward");
		standardModeUnlockProgression.put("awkward", "impossible");
		
		// Unlock new modes after certain points
		modeUnlockProgression.put("awkward", "timeattack");
	}
	
	public static void evaluateUnlocks(GameState gameState, String completedLevelSet) {
		String unlockedLevelSet = standardModeUnlockProgression.get(completedLevelSet);
		if (unlockedLevelSet!=null) {
			gameState.unlockLevelSet(unlockedLevelSet);
		}
	}
	
	public static void evaluateUnlocks(Activity activity, String completedLevelSet) {
		GameState gameState = new GameState(activity);
		evaluateUnlocks(gameState, completedLevelSet);
	}

}
