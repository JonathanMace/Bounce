package im.mace.android.bounce.game;

import im.mace.android.bounce.common.Constants;
import im.mace.android.bounce.common.TextureBucket;
import im.mace.android.bounce.ui.Button;

import org.anddev.andengine.engine.camera.hud.HUD;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

public abstract class BaseHUD extends HUD {
	
    protected final TextureBucket textures;
    
    protected float resetButtonX = 5;
    protected float resetButtonY = -11;
    
    protected float quitButtonX = Constants.CAMERA_WIDTH-128;
    protected float quitButtonY = -11;
    
    protected Button resetButton;
    protected Button quitButton;
    
    /**
     * Make sure textures have been loaded in the TextureUtils before instantiating a GameHUD
     */
    public BaseHUD(Scene scene, TextureBucket textures) {
        this.textures = textures;
        
        this.attach(this.resetButton = this.constructResetButton());
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
    
    protected Button constructResetButton() {
        Button resetButton = null;
        TiledTextureRegion resetTexture = textures.getTiledTexture("reset");
        if (resetTexture != null) {
            resetButton = new Button(resetButtonX, resetButtonY, resetTexture){
                public void onClick() {
                    BaseHUD.this.onReset();
                }
            };
        }
        return resetButton;
    }
    
    protected Button constructQuitButton() {
        Button quitButton = null;
        TiledTextureRegion quitTexture = textures.getTiledTexture("quit");
        if (quitTexture != null) {
            quitButton = new Button(quitButtonX, quitButtonY, quitTexture){
                public void onClick() {
                    onQuit();
                }
            };
        }
        return quitButton;
    }
    
    public abstract void onReset();
    public abstract void onQuit();

}
