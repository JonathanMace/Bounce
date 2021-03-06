package im.mace.android.bounce.game.minbounce;
import java.util.List;

import im.mace.android.bounce.common.Constants;
import im.mace.android.bounce.common.Level;
import im.mace.android.bounce.game.BaseGame;
import im.mace.android.bounce.game.GameScene;
import im.mace.android.bounce.io.GameState;
import im.mace.android.bounce.io.LevelManager;

import org.anddev.andengine.engine.camera.hud.HUD;

public class MinBounceMode extends BaseGame {

	private MinBounceHUD minBounceHUD;

	@Override
	protected GameScene constructGameScene() {
		return new GameScene(this.level.getSpec(), this.textures) {
            public void onGameSuccess() { MinBounceMode.this.onGameSuccess(); }
            public void onBounce(long bounces) { 
            	if (bounces < 100L) {
            		MinBounceMode.this.minBounceHUD.setCurrent(bounces);
            	}
        	}
        };
	}


	@Override
	protected HUD constructHUD() {
		this.minBounceHUD = new MinBounceHUD(this.scene, this.textures, this.font) {
            public void onReset() { MinBounceMode.this.onReset(); }
            public void onQuit() { MinBounceMode.this.onQuit(); }
        };
        if (this.level.beatenMin()) {
            Long currentBest = this.level.getMinBounces();
            if (currentBest==null) {
            	currentBest = 99L;
            }
            this.minBounceHUD.setBest(currentBest);        	
        } else {
        	this.minBounceHUD.setTarget(this.level.getSpec().min);
        }
    	this.minBounceHUD.setCurrent(0L);
        return this.minBounceHUD;
	}


	@Override
	protected void onGameSuccess() {
		if (this.level.setMinBounces(this.scene.bounces)) {
			this.minBounceHUD.setBest(this.scene.bounces);
			this.evaluateUnlocks();
		}
	}
    
    private void onReset() {
    	this.minBounceHUD.setCurrent(0L);
        this.scene.postRunnable(new Runnable() {
			@Override
			public void run() {
				MinBounceMode.this.scene.resetGame();
			}
		});
    }


	@Override
	protected void onQuit() {
        this.setResult(RESULT_CANCELED);
        this.finish();      
	}
	
	private void evaluateUnlocks() {
    	GameState state = new GameState(this);
    	if (!state.isNextLevelSetUnlocked(Constants.MODE_MIN, levelSet)) {
    		List<Level> allLevels = LevelManager.getLevels(this, this.levelSet);
    		for (Level level : allLevels) {
    			if (!level.beatenMin()) {
    				return;
    			}
    		}
    		state.unlockNextLevelSet(Constants.MODE_MIN, this.levelSet);
    		if (this.levelSet.equals(Constants.LEVELSETS[5]) && state.isLevelSetUnlocked(Constants.MODE_MAX, Constants.LEVELSETS[6])) {
    			state.unlockMode(Constants.MODE_TIME);
    			state.unlockLevelSet(Constants.MODE_TIME, Constants.LEVELSETS[0]);
    		}
    	}
	}
}
