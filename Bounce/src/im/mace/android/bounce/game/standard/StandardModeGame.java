package im.mace.android.bounce.game.standard;
import im.mace.android.bounce.game.BaseGame;
import im.mace.android.bounce.game.GameScene;

import org.anddev.andengine.engine.camera.hud.HUD;

public class StandardModeGame extends BaseGame {

	private StandardModeHUD standardModeHUD;

	@Override
	protected GameScene constructGameScene() {
		return new GameScene(this.level.getSpec(), this.textures) {
            public void onGameSuccess() { StandardModeGame.this.onGameSuccess(); }
        };
	}


	@Override
	protected HUD constructHUD() {
		this.standardModeHUD = new StandardModeHUD(this.scene, this.textures, this.font, this.level.getSpec().name) {
            public void onReset() { StandardModeGame.this.onReset(); }
            public void onNext() { StandardModeGame.this.onNext(); }
            public void onQuit() { StandardModeGame.this.onQuit(); }
        };
        return this.standardModeHUD;
	}


	@Override
	protected void onGameSuccess() {
		this.scene.postRunnable(new Runnable() {
			@Override
			public void run() {
		        StandardModeGame.this.standardModeHUD.enableNextButton();				
			}
		});		
	}
    
    private void onReset() {
        this.scene.postRunnable(new Runnable() {
			@Override
			public void run() {
				StandardModeGame.this.scene.resetGame();
			}
		});
    }

    
    private void onNext() {
        this.setResult(RESULT_OK);
        this.finish();
    }


	@Override
	protected void onQuit() {
        this.setResult(RESULT_CANCELED);
        this.finish();      
	}
}
