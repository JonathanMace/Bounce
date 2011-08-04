package im.mace.android.bounce.game.practice;
import im.mace.android.bounce.game.BaseGame;
import im.mace.android.bounce.game.BaseHUD;
import im.mace.android.bounce.game.GameScene;

import org.anddev.andengine.engine.camera.hud.HUD;

public class PracticeMode extends BaseGame {

	private BaseHUD huuuuud;

	@Override
	protected GameScene constructGameScene() {
		return new GameScene(this.level.getSpec(), this.textures) {
            public void onGameSuccess() { /* Do nothing */ }
        };
	}


	@Override
	protected HUD constructHUD() {
		this.huuuuud = new BaseHUD(this.scene, this.textures) {
            public void onReset() { PracticeMode.this.onReset(); }
            public void onQuit() { PracticeMode.this.onQuit(); }
        };
        return this.huuuuud;
	}
    
    private void onReset() {
        this.scene.postRunnable(new Runnable() {
			@Override
			public void run() {
				PracticeMode.this.scene.resetGame();
			}
		});
    }


	@Override
	protected void onQuit() {
        this.setResult(RESULT_CANCELED);
        this.finish();      
	}


	@Override
	protected void onGameSuccess() { /* Do nothing */ }
}
