package im.mace.android.bounce.game.standard;

import im.mace.android.bounce.common.Constants;
import im.mace.android.bounce.io.GameState;
import im.mace.android.bounce.io.LevelManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class StandardMode extends Activity {
	
	private String levelSet;
	private int currentLevel = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        levelSet = this.getIntent().getStringExtra(Constants.LEVEL_SET_KEY);
        if (levelSet==null) {
        	this.finish();
        } else if (savedInstanceState==null) {
        	// First call, start from scratch
        	this.startLevel(levelSet, currentLevel);
        } else {
        	// Otherwise, pull out the saved current value, and do nothing, 
        	// because onActivityResult will do the work for us
        	this.currentLevel = savedInstanceState.getInt(Constants.LEVEL_KEY);
        }
    }
    
    @Override
    public void onSaveInstanceState(Bundle state) {
    	state.putInt(Constants.LEVEL_KEY, currentLevel);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_CANCELED) {
            cancelled();
        } else {
        	this.currentLevel++;
        	if (LevelManager.exists(levelSet, currentLevel)) {
        		this.startLevel(this.levelSet, currentLevel);
        	} else {
        		success();
        	}
        }
    }
    
    private void success() {
    	GameState state = new GameState(this);
    	state.unlockMode(Constants.MODE_PRACTICE);
    	state.unlockNextLevelSet(Constants.MODE_STANDARD, levelSet);
    	if (levelSet.equals(Constants.LEVELSETS[5])) {
    		state.unlockMode(Constants.MODE_MIN);
    		state.unlockLevelSet(Constants.MODE_MIN, Constants.LEVELSETS[0]);
    	} else if (levelSet.equals(Constants.LEVELSETS[8])) {
    		state.unlockMode(Constants.MODE_MAX);
    		state.unlockLevelSet(Constants.MODE_MAX, Constants.LEVELSETS[0]);
    	}
    	finish();
    }
    
    private void cancelled() {
    	finish();
    }

    private void startLevel(String levelSet, int levelNumber) {
        Intent intent = new Intent(getBaseContext(), StandardModeGame.class);
        intent.putExtra(Constants.LEVEL_KEY, currentLevel);
        intent.putExtra(Constants.LEVEL_SET_KEY, levelSet);
        startActivityForResult(intent, 0);
    }

}
