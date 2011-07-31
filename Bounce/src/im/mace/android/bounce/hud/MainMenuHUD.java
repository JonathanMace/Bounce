package im.mace.android.bounce.hud;

import im.mace.android.bounce.common.TextureBucket;
import im.mace.android.bounce.objects.Button;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.util.Log;

public abstract class MainMenuHUD extends AnimatedHUD {

    private final Scene scene;
    private final TextureBucket textures;
    
    protected float buttonX = 275;
    protected float buttonWidth = 250;
    protected float buttonHeight = 100;
    
    protected float playgameButtonY = 150;    
    protected float settingsButtonY = 260;    
    protected float quitButtonY = 370;
    
    protected Button playgameButton;
    protected Button settingsButton;   
    protected Button quitButton;
    
    protected final float preHideLocation = 0;
    
    /**
     * Make sure textures have been loaded in the TextureUtils before instantiating a GameHUD
     */
    public MainMenuHUD(Scene scene, TextureBucket textures) {
        this.scene = scene;
        this.textures = textures;

        this.attach(this.playgameButton = this.constructPlayGameButton());
        this.attach(this.settingsButton = this.constructSettingsButton());
        this.attach(this.quitButton = this.constructQuitButton());
    }
    
    protected void attach(Shape shape) {
        if (shape!=null) {
            this.registerTouchArea(shape);
            this.attachChild(shape);
        }
    }
    
    protected void detach(Shape shape) {
        if (shape!=null) {
            this.unregisterTouchArea(shape);
            this.detachChild(shape);
        }
    }
    
    protected Button constructPlayGameButton() {
        Button playGameButton = null;
        
        TiledTextureRegion buttonTexture = textures.getTiledTexture("button_playgame");
        if (buttonTexture != null) {
            playGameButton = new Button(scene, buttonX, playgameButtonY, buttonWidth, buttonHeight, buttonTexture){
                public void onClick() {
                    MainMenuHUD.this.onPlayGame();
                }
            };
        } else {
            Log.e("hud", "No texture found for the playgame button button");
        }
        
        return playGameButton;
    }
    
    protected Button constructSettingsButton() {
        Button nextButton = null;
        
        TiledTextureRegion resetTexture = textures.getTiledTexture("button_settings");
        if (resetTexture != null) {
            nextButton = new Button(scene, buttonX, settingsButtonY, buttonWidth, buttonHeight, resetTexture){
                public void onClick() {
                    MainMenuHUD.this.onSettings();
                }
            };
        } else {
            Log.e("hud", "No texture found for the next button");
        }
        
        return nextButton;
    }
    
    protected Button constructQuitButton() {
        Button quitButton = null;

        TiledTextureRegion quitTexture = textures.getTiledTexture("button_quit");
        if (quitTexture != null) {
            quitButton = new Button(scene, buttonX, quitButtonY, buttonWidth, buttonHeight, quitTexture){
                public void onClick() {
                    MainMenuHUD.this.onQuit();
                }
            };
        } else {
            Log.e("hud", "No texture found for the quit button");
        }     
        
        return quitButton;
    }
    
    public abstract void onPlayGame();
    public abstract void onSettings();
    public abstract void onQuit();

}
