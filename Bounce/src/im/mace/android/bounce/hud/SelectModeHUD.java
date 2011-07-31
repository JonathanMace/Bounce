package im.mace.android.bounce.hud;

import im.mace.android.bounce.common.TextureBucket;
import im.mace.android.bounce.objects.DisableButton;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.util.Log;

public abstract class SelectModeHUD extends AnimatedHUD {

    private final Scene scene;
    private final TextureBucket textures;
    
    protected float col1 = 125;
    protected float col2 = 425;
    
    protected float row1 = 150;    
    protected float row2 = 260;    
    protected float row3 = 370;
    
    protected float buttonWidth = 250;
    protected float buttonHeight = 100;
    
    protected DisableButton minMaxButton;
    protected DisableButton practiceButton;
    protected DisableButton maxBounceButton;
    protected DisableButton minBounceButton;
    protected DisableButton standardModeButton;
    protected DisableButton timeAttackButton;
    

    
    /**
     * Make sure textures have been loaded in the TextureUtils before instantiating a GameHUD
     */
    public SelectModeHUD(Scene scene, TextureBucket textures) {
        this.scene = scene;
        this.textures = textures;

        this.attach(this.maxBounceButton = this.constructMaxBounceButton());
        this.attach(this.minBounceButton = this.constructMinBounceButton());
        this.attach(this.minMaxButton = this.constructMinMaxButton());
        this.attach(this.practiceButton = this.constructPracticeButton());
        this.attach(this.standardModeButton = this.constructStandardModeButton());
        this.attach(this.timeAttackButton = this.constructTimeAttackButton());
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
    
    protected DisableButton constructPracticeButton() {
        DisableButton button = null;
        
        TiledTextureRegion buttonTexture = textures.getTiledTexture("button_practice");
        if (buttonTexture != null) {
            button = new DisableButton(scene, col1, row1, buttonWidth, buttonHeight, buttonTexture){
                public void onClick() {
                    SelectModeHUD.this.onPractice();
                }
            };
        } else {
            Log.e("hud", "No texture found for the playgame button button");
        }
        
        return button;
    }
    
    protected DisableButton constructStandardModeButton() {
        DisableButton button = null;
        
        TiledTextureRegion buttonTexture = textures.getTiledTexture("button_standardmode");
        if (buttonTexture != null) {
            button = new DisableButton(scene, col1, row2, buttonWidth, buttonHeight, buttonTexture){
                public void onClick() {
                    SelectModeHUD.this.onStandardMode();
                }
            };
        } else {
            Log.e("hud", "No texture found for the playgame button button");
        }
        
        return button;
    }
    
    protected DisableButton constructTimeAttackButton() {
        DisableButton button = null;
        
        TiledTextureRegion buttonTexture = textures.getTiledTexture("button_timeattack");
        if (buttonTexture != null) {
            button = new DisableButton(scene, col1, row3, buttonWidth, buttonHeight, false, buttonTexture){
                public void onClick() {
                    SelectModeHUD.this.onTimeAttack();
                }
            };
        } else {
            Log.e("hud", "No texture found for the playgame button button");
        }
        
        return button;
    }
    
    protected DisableButton constructMinBounceButton() {
        DisableButton button = null;
        
        TiledTextureRegion buttonTexture = textures.getTiledTexture("button_minbounce");
        if (buttonTexture != null) {
            button = new DisableButton(scene, col2, row1, buttonWidth, buttonHeight, false, buttonTexture){
                public void onClick() {
                    SelectModeHUD.this.onMinBounce();
                }
            };
        } else {
            Log.e("hud", "No texture found for the playgame button button");
        }
        
        return button;
    }
    
    protected DisableButton constructMaxBounceButton() {
        DisableButton button = null;
        
        TiledTextureRegion buttonTexture = textures.getTiledTexture("button_maxbounce");
        if (buttonTexture != null) {
            button = new DisableButton(scene, col2, row2, buttonWidth, buttonHeight, false, buttonTexture){
                public void onClick() {
                    SelectModeHUD.this.onMaxBounce();
                }
            };
        } else {
            Log.e("hud", "No texture found for the playgame button button");
        }
        
        return button;
    }
    
    protected DisableButton constructMinMaxButton() {
        DisableButton button = null;
        
        TiledTextureRegion buttonTexture = textures.getTiledTexture("button_minmax");
        if (buttonTexture != null) {
            button = new DisableButton(scene, col2, row3, buttonWidth, buttonHeight, false, buttonTexture){
                public void onClick() {
                    SelectModeHUD.this.onMinMax();
                }
            };
        } else {
            Log.e("hud", "No texture found for the playgame button button");
        }
        
        return button;
    }

    public abstract void onPractice();
    public abstract void onStandardMode();
    public abstract void onTimeAttack();
    public abstract void onMinBounce();
    public abstract void onMaxBounce();
    public abstract void onMinMax();

}
