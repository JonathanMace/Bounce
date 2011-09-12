package im.mace.android.bounce.game;
import im.mace.android.bounce.common.Constants;
import im.mace.android.bounce.common.Level;
import im.mace.android.bounce.common.TextureBucket;
import im.mace.android.bounce.common.TextureUtils;
import im.mace.android.bounce.io.LevelManager;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.camera.hud.HUD;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.os.Bundle;

public abstract class BaseGame extends BaseGameActivity {

    protected Camera camera;
    protected HUD hud;
    protected GameScene scene;
    
    protected Level level;
    protected String levelSet;
    protected int levelNumber;
    
    protected TextureBucket textures;
    protected Font font;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.levelNumber = this.getIntent().getIntExtra(Constants.LEVEL_KEY, -1);
        this.levelSet = this.getIntent().getStringExtra(Constants.LEVEL_SET_KEY);
        
        this.level = LevelManager.getLevel(this, levelSet, levelNumber);
        if (this.level==null) {
        	this.onQuit();
        }
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
        font = TextureUtils.loadGameFont(this.mEngine);
    }

    @Override
    public Scene onLoadScene() {
        // Create the game scene
        this.scene = this.constructGameScene();
        
        // Create the HUD
        this.hud = this.constructHUD();
        this.hud.setTouchAreaBindingEnabled(true);
        this.camera.setHUD(this.hud);
        
        return this.scene;
    }

    public void onLoadComplete() { /* Do nothing */ }
    
    protected abstract GameScene constructGameScene();
    
    protected abstract HUD constructHUD();

    protected abstract void onGameSuccess();
    
    protected abstract void onQuit();
}
