package im.mace.android.bounce.objects;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

public abstract class DisableButton extends Button {
    
    private boolean enabled;

    public DisableButton(Scene scene, float pX, float pY, TiledTextureRegion pTiledTextureRegion) {
        this(scene, pX, pY, true, pTiledTextureRegion);
    }

    public DisableButton(Scene scene, float pX, float pY, boolean enabled, TiledTextureRegion pTiledTextureRegion) {
        super(scene, pX, pY, pTiledTextureRegion);
        this.setCurrentTileIndex(0);
        this.setEnabled(enabled);
    }
    
    public DisableButton(Scene scene, float pX, float pY, float width, float height, TiledTextureRegion pTiledTextureRegion) {
        this(scene, pX, pY, width, height, true, pTiledTextureRegion);
    }
    
    public DisableButton(Scene scene, float pX, float pY, float width, float height, boolean enabled, TiledTextureRegion pTiledTextureRegion) {
        super(scene, pX, pY, width, height, pTiledTextureRegion);
        this.setCurrentTileIndex(0);
        this.setEnabled(enabled);
    }

    @Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        if(pSceneTouchEvent.isActionDown() && !down) {
            down = true;
            if (this.enabled) {
                this.setCurrentTileIndex(1);
            }
        } else if (pSceneTouchEvent.isActionUp() && down) {
            down = false;
            if (this.enabled) {
                this.setCurrentTileIndex(0);
                // Make sure the 'up' action is still over the button, otherwise treat it as an 'abort' type action.
                if (pTouchAreaLocalX >= 0 && pTouchAreaLocalX < this.getWidth() && pTouchAreaLocalY >= 0 && pTouchAreaLocalY < this.getHeight()) {
                    this.onClick();
                }
            }
            return true;
        }                
        return down;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (enabled) {
            this.setCurrentTileIndex(0);
        } else {
            this.setCurrentTileIndex(2);
        }
    }

}
