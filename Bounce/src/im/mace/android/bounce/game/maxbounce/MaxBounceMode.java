package im.mace.android.bounce.game.maxbounce;
import java.util.List;

import im.mace.android.bounce.common.Constants;
import im.mace.android.bounce.common.Level;
import im.mace.android.bounce.game.BaseGame;
import im.mace.android.bounce.game.GameScene;
import im.mace.android.bounce.io.GameState;
import im.mace.android.bounce.io.LevelManager;

import org.anddev.andengine.engine.camera.hud.HUD;

public class MaxBounceMode extends BaseGame {

	private MaxBounceHUD maxBounceHUD;

	@Override
	protected GameScene constructGameScene() {
		return new GameScene(this.level.getSpec(), this.textures) {
            public void onGameSuccess() { MaxBounceMode.this.onGameSuccess(); }
            public void onBounce(long bounces) { 
            	if (bounces < 100L) {
            		MaxBounceMode.this.maxBounceHUD.setCurrent(bounces);
            	}
        	}
        };
	}


	@Override
	protected HUD constructHUD() {
		this.maxBounceHUD = new MaxBounceHUD(this.scene, this.textures, this.font) {
            public void onReset() { MaxBounceMode.this.onReset(); }
            public void onQuit() { MaxBounceMode.this.onQuit(); }
        };
        if (this.level.beatenMin()) {
            Long currentBest = this.level.getMaxBounces();
            if (currentBest==null) {
            	currentBest = 1L;
            }
            this.maxBounceHUD.setBest(currentBest);    	
        } else {
        	this.maxBounceHUD.setTarget(this.level.getSpec().max);
        }
    	this.maxBounceHUD.setCurrent(0L);
        return this.maxBounceHUD;
	}


	@Override
	protected void onGameSuccess() {
		if (this.level.setMaxBounces(this.scene.bounces)) {
			this.maxBounceHUD.setBest(this.scene.bounces);
			this.evaluateUnlocks();
		}
	}
    
    private void onReset() {
    	this.maxBounceHUD.setCurrent(0L);
        this.scene.postRunnable(new Runnable() {
			@Override
			public void run() {
				MaxBounceMode.this.scene.resetGame();
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
    	if (!state.isNextLevelSetUnlocked(Constants.MODE_MAX, levelSet)) {
    		List<Level> allLevels = LevelManager.getLevels(this, this.levelSet);
    		for (Level level : allLevels) {
    			if (!level.beatenMax()) {
    				return;
    			}
    		}
    		state.unlockNextLevelSet(Constants.MODE_MAX, this.levelSet);
    		if (this.levelSet.equals(Constants.LEVELSETS[5]) && state.isLevelSetUnlocked(Constants.MODE_MIN, Constants.LEVELSETS[6])) {
    			state.unlockMode(Constants.MODE_TIME);
    			state.unlockLevelSet(Constants.MODE_TIME, Constants.LEVELSETS[0]);
    		}
    	}
	}
}
