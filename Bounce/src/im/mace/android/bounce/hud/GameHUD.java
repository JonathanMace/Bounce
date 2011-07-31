package im.mace.android.bounce.hud;

import im.mace.android.bounce.common.Constants;
import im.mace.android.bounce.common.TextureBucket;
import im.mace.android.bounce.objects.Button;

import org.anddev.andengine.engine.camera.hud.HUD;
import org.anddev.andengine.entity.modifier.MoveModifier;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.util.HorizontalAlign;
import org.anddev.andengine.util.modifier.ease.EaseElasticOut;

import android.util.Log;

public abstract class GameHUD extends HUD {
    
    private final Scene scene;
    private final TextureBucket textures;
    
    protected float resetButtonX = 5;
    protected float resetButtonY = -11;
    
    protected float quitButtonX = Constants.CAMERA_WIDTH-128;
    protected float quitButtonY = -11;
    
    protected Button resetButton;
    protected Button nextButton;   
    protected Button quitButton;
    
    /**
     * Make sure textures have been loaded in the TextureUtils before instantiating a GameHUD
     */
    public GameHUD(Scene scene, TextureBucket textures, Font font, String levelName) {
        this.scene = scene;
        this.textures = textures;
        
        this.attach(this.resetButton = this.constructResetButton());
        this.attach(this.quitButton = this.constructQuitButton());
        this.attach(this.constructLevelName(font, levelName));
        this.nextButton = this.constructNextButton();
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
            resetButton = new Button(scene, resetButtonX, resetButtonY, resetTexture){
                public void onClick() {
                    GameHUD.this.onReset();
                }
            };
        } else {
            Log.e("hud", "No texture found for the reset button");
        }
        
        return resetButton;
    }
    
    protected Button constructQuitButton() {
        Button quitButton = null;

        TiledTextureRegion quitTexture = textures.getTiledTexture("quit");
        if (quitTexture != null) {
            quitButton = new Button(scene, quitButtonX, quitButtonY, quitTexture){
                public void onClick() {
                    onQuit();
                }
            };
        } else {
            Log.e("hud", "No texture found for the quit button");
        }     
        
        return quitButton;
    }
    
    protected Button constructNextButton() {
        Button nextButton = null;
        
        TiledTextureRegion resetTexture = textures.getTiledTexture("next");
        if (resetTexture != null) {
            nextButton = new Button(scene, resetButtonX, resetButtonY, resetTexture){
                public void onClick() {
                    GameHUD.this.onNext();
                }
            };
        } else {
            Log.e("hud", "No texture found for the next button");
        }
        
        return nextButton;
    }
    
    protected Shape constructLevelName(Font font, String levelName) {
        float starty = -200;
        Text textCenter = new Text(0, starty, font, levelName, HorizontalAlign.CENTER);
        float x = 400 - textCenter.getWidth()/2;
        float y = 5;
        textCenter.registerEntityModifier(new MoveModifier(4, x, x, starty, y, EaseElasticOut.getInstance()));
        return textCenter;
    }
    
    public void enableNextButton() {
        // Detach the reset button
        this.detach(this.resetButton);

        // Attach the next button
        this.attach(this.nextButton);
    }
    
    public abstract void onReset();
    public abstract void onNext();
    public abstract void onQuit();

}
