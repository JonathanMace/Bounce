package im.mace.android.bounce.hud;

import im.mace.android.bounce.common.GameState;
import im.mace.android.bounce.common.TextureBucket;
import im.mace.android.bounce.objects.DisableButton;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.util.Log;

public abstract class SelectLevelSetHUD extends AnimatedHUD {

    private final Scene scene;
    private final TextureBucket textures;

    protected final float[] cols = new float[] { 13, 275, 537 };
    protected final float[] rows = new float[] { 150, 260, 370 };
    
    protected final String[][] levelSets = new String[][] {
            { "simple", "elegant", "tricky" },
            { "awkward", "frustrating", "phonesmashing" },
            { "confusing", "annoying", "impossible" } 
    };
    
    protected float buttonWidth = 250;
    protected float buttonHeight = 100;
    
    
        
    /**
     * Make sure textures have been loaded in the TextureUtils before instantiating a GameHUD
     */
    public SelectLevelSetHUD(Scene scene, TextureBucket textures) {
        this.scene = scene;
        this.textures = textures;
        
        for (int i = 0; i < levelSets.length; i++) {
            String[] row = levelSets[i];
            for (int j = 0; j < row.length; j++) {
                String levelSetName = row[j];
                this.attach(this.constructButton(cols[j], rows[i], buttonWidth, buttonHeight, levelSetName));
            }
        }
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
    
    protected DisableButton constructButton(float x, float y, float w, float h, final String setName) {
        DisableButton button = null;
        
        TiledTextureRegion buttonTexture = textures.getTiledTexture("button_levels_"+setName);
        if (buttonTexture != null) {
            button = new DisableButton(scene, x, y, w, h, GameState.isLevelSetUnlocked(setName), buttonTexture){
                public void onClick() {
                    SelectLevelSetHUD.this.onLevelSetSelected(setName);
                }
            };
        } else {
            Log.e("hud", "No texture found for set " + setName);
        }
        
        return button;
    }
    
    public abstract void onLevelSetSelected(String levelSet);

}
