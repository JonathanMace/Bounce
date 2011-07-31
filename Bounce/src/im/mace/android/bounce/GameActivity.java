package im.mace.android.bounce;
import im.mace.android.bounce.common.Constants;
import im.mace.android.bounce.common.TextureBucket;
import im.mace.android.bounce.common.TextureUtils;
import im.mace.android.bounce.hud.GameHUD;
import im.mace.android.bounce.levels.LevelManager;
import im.mace.android.bounce.levels2.LevelDef;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.os.Bundle;
import android.view.KeyEvent;

public class GameActivity extends BaseGameActivity {

    private Camera camera;
    private GameHUD hud;
    private GameScene scene;
    
    private LevelDef levelDef;
    
    private TextureBucket textures;
    private Font font;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get level def from bundle, for now just generate
        this.levelDef = LevelManager.getCurrent();
    }


    @Override
    public Engine onLoadEngine() {
        this.camera = new Camera(0, 0, Constants.CAMERA_WIDTH, Constants.CAMERA_HEIGHT);
        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(Constants.CAMERA_WIDTH, Constants.CAMERA_HEIGHT), camera);
        return new Engine(engineOptions);
    }

    @Override
    public void onLoadResources() {
        // Load up them thar textures
        textures = TextureUtils.loadGameTextures(this, this.mEngine);
        font = TextureUtils.loadFont(this.mEngine);
    }

    @Override
    public Scene onLoadScene() {
        // Create the game scene
        this.scene = new GameScene(levelDef, textures) {
            public void onGameFinished() { GameActivity.this.onGameFinished(); }
        };
        
        // Create the HUD
        this.hud = new GameHUD(this.scene, this.textures, this.font, this.levelDef.name) {
            public void onReset() { GameActivity.this.onReset(); }
            public void onNext() { GameActivity.this.onNext(); }
            public void onQuit() { GameActivity.this.onQuit(); }
        };
        this.hud.setTouchAreaBindingEnabled(true);
        this.camera.setHUD(this.hud);
        
        return this.scene;
    }

    public void onLoadComplete() { /* Do nothing */ }

    private void onGameFinished() {
        this.hud.enableNextButton();
    }
    
    private void onReset() {
        this.scene.resetGame();
    }
    
    private void onNext() {
//        this.setResult(resultCode, data)
        this.setResult(RESULT_OK);
        this.finish();
    }
    
    private void onQuit() {
        this.setResult(RESULT_CANCELED);
        this.finish();        
    }
}
