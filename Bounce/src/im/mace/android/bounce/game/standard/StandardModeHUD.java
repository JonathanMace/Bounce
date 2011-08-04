package im.mace.android.bounce.game.standard;

import im.mace.android.bounce.common.TextureBucket;
import im.mace.android.bounce.game.BaseHUD;
import im.mace.android.bounce.ui.Button;

import org.anddev.andengine.entity.modifier.MoveModifier;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.util.HorizontalAlign;
import org.anddev.andengine.util.modifier.ease.EaseElasticOut;

public abstract class StandardModeHUD extends BaseHUD {
    
    protected Button nextButton;
    
    /**
     * Make sure textures have been loaded in the TextureUtils before instantiating a GameHUD
     */
    public StandardModeHUD(Scene scene, TextureBucket textures, Font font, String levelName) {
    	super(scene, textures);
        
        this.attach(this.constructLevelName(font, levelName));
        this.nextButton = this.constructNextButton();
    }
    
    protected Button constructNextButton() {
        Button nextButton = null;
        TiledTextureRegion resetTexture = textures.getTiledTexture("next");
        if (resetTexture != null) {
            nextButton = new Button(resetButtonX, resetButtonY, resetTexture){
                public void onClick() {
                    StandardModeHUD.this.onNext();
                }
            };
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
    
    public abstract void onNext();

}
