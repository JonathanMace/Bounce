package im.mace.android.bounce.objects;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.TiledSprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

public abstract class Button extends TiledSprite {
    
    boolean down = false;
    private Scene scene;

    /**
     * Deals with touch events and calls the overridable onClick method
     * when the button is clicked.
     * @param pX the x co-ordinate of the top-left corner of the button
     * @param pY the y co-ordinate of the top-left corner of the button
     * @param pTiledTextureRegion a texture region with 2 (or more) tiles.
     *      The first tile is used as the default image, the second tile is
     *      used as the 'mousedown' image.
     */
    public Button(Scene scene, float pX, float pY, TiledTextureRegion pTiledTextureRegion) {
        super(pX, pY, pTiledTextureRegion);
        this.scene = scene;
        this.setCurrentTileIndex(0);
    }
    
    public Button(Scene scene, float pX, float pY, float width, float height, TiledTextureRegion pTiledTextureRegion) {
        super(pX, pY, width, height, pTiledTextureRegion);
        this.scene = scene;
        this.setCurrentTileIndex(0);
    }

    @Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        if(pSceneTouchEvent.isActionDown() && !down) {
            down = true;
            this.setCurrentTileIndex(1);
        } else if (pSceneTouchEvent.isActionUp() && down) {
            down = false;
            this.setCurrentTileIndex(0);
            // Make sure the 'up' action is still over the button, otherwise treat it as an 'abort' type action.
            if (pTouchAreaLocalX >= 0 && pTouchAreaLocalX < this.getWidth() && pTouchAreaLocalY >= 0 && pTouchAreaLocalY < this.getHeight()) {
                this.scene.postRunnable(new Runnable() {
                    public void run() {
                        Button.this.onClick();
                    }
                });
            }
            return true;
        }                
        return down;
    }
    
    public abstract void onClick();

}
