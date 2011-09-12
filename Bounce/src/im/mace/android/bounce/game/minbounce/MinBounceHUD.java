package im.mace.android.bounce.game.minbounce;

import im.mace.android.bounce.common.TextureBucket;
import im.mace.android.bounce.game.BaseHUD;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.opengl.font.Font;

public abstract class MinBounceHUD extends BaseHUD {
	
	protected final String TARGET_TEXT = "To beat: ";	
	protected final String BEST_TEXT = "Your Best: ";
	protected final float BEST_TEXT_X = 425;
	protected final float BEST_TEXT_Y = 10;

	protected final String CURRENT_TEXT = "Bounces: ";
	protected final float CURRENT_TEXT_X = 175;
	protected final float CURRENT_TEXT_Y = 10;
    
    protected ChangeableText bestScore;
    protected ChangeableText currentBounces;
    
    /**
     * Make sure textures have been loaded in the TextureUtils before instantiating a GameHUD
     */
    public MinBounceHUD(Scene scene, TextureBucket textures, Font font) {
    	super(scene, textures);
        
    	this.attach(this.bestScore = this.constructBestScoreText(font));
    	this.attach(this.currentBounces = this.constructCurrentBouncesText(font));
    }
    
    protected ChangeableText constructBestScoreText(Font font) {
        ChangeableText text = new ChangeableText(BEST_TEXT_X, BEST_TEXT_Y, font, BEST_TEXT, BEST_TEXT.length()+5);
        return text;
    }
    
    protected ChangeableText constructCurrentBouncesText(Font font) {
        ChangeableText text = new ChangeableText(CURRENT_TEXT_X, CURRENT_TEXT_Y, font, CURRENT_TEXT+"0123456789", CURRENT_TEXT.length()+11);
        return text;    	
    }
    
    public void setBest(Long best) {
    	bestScore.setText(BEST_TEXT+best);
    }
    
    public void setCurrent(Long current) {
    	currentBounces.setText(CURRENT_TEXT+current);
    }
    
    public void setTarget(Long target) {
    	bestScore.setText(TARGET_TEXT+target);
    }

}
