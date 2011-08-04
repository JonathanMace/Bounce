package im.mace.android.bounce.game.timeattack;
import im.mace.android.bounce.common.Constants;
import im.mace.android.bounce.game.BaseGame;
import im.mace.android.bounce.game.GameScene;

import org.anddev.andengine.engine.camera.hud.HUD;

public class TimeAttackMode extends BaseGame {

	private TimeAttackHUD timeAttackHUD;
	private long currentTime;

	@Override
	protected GameScene constructGameScene() {
		return new GameScene(this.level.getSpec(), this.textures) {
            public void onGameSuccess() { TimeAttackMode.this.onGameSuccess(); }
            @Override
            protected void onTimeElapsed(long elapsed) {
            	currentTime = elapsed;
            	TimeAttackMode.this.timeAttackHUD.setCurrent(elapsed);
            }
        };
	}


	@Override
	protected HUD constructHUD() {
		this.timeAttackHUD = new TimeAttackHUD(this.scene, this.textures, this.font) {
            public void onReset() { TimeAttackMode.this.onReset(); }
            public void onQuit() { TimeAttackMode.this.onQuit(); }
        };
        if (this.level.beatenTime()) {
            Long currentBest = this.level.getBestTime();
            if (currentBest==null) {
            	currentBest = Constants.TIME_ATTACK_CAP;
            }
            this.timeAttackHUD.setBest(currentBest);        	
        } else {
        	this.timeAttackHUD.setTarget(this.level.getSpec().time);
        }
    	this.timeAttackHUD.setCurrent(0L);
        return this.timeAttackHUD;
	}


	@Override
	protected void onGameSuccess() {
		if (this.level.setBestTime(currentTime)) {
			this.timeAttackHUD.setBest(currentTime);
		}
	}
    
    private void onReset() {
    	this.timeAttackHUD.setCurrent(0L);
        this.scene.postRunnable(new Runnable() {
			@Override
			public void run() {
				TimeAttackMode.this.scene.resetGame();
			}
		});
    }


	@Override
	protected void onQuit() {
        this.setResult(RESULT_CANCELED);
        this.finish();      
	}
}
