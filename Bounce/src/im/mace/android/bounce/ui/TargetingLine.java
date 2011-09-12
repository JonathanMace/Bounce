package im.mace.android.bounce.ui;

import im.mace.android.bounce.common.Constants;
import im.mace.android.bounce.common.TextureBucket;

import org.anddev.andengine.entity.primitive.Line;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.entity.sprite.Sprite;

public class TargetingLine {
    
    private final TextureBucket textures;

    private final Shape line;
    private final Shape recticle;

    private volatile boolean hasDetached = false;

    /**
     * A TargetingLine can only be attached to a scene once.
     */
    public TargetingLine(TextureBucket textures, float startx, float starty, float endx, float endy) {
        this.textures = textures;
        this.line = constructLine(startx, starty, endx, endy);
        this.recticle = constructRecticle(endx, endy);
    }
    
    protected Shape constructRecticle(float endx, float endy) {
        Sprite recticle = new Sprite(
                endx-Constants.RECTICLE_RADIUS, 
                endy-Constants.RECTICLE_RADIUS, 
                Constants.RECTICLE_RADIUS*2, 
                Constants.RECTICLE_RADIUS*2, 
                textures.getTexture("recticle")
        );
        return recticle;        
    }
    
    protected Shape constructLine(float startx, float starty, float endx, float endy) {
        // Modify start and end of line so that they begin at the edge of the ball / recticle rather than center
        float lengthsq = (float) Math.sqrt((starty - endy) * (starty - endy) + (startx - endx) * (startx - endx));
        float modStartX = startx - ((startx - endx) * (Constants.BALL_RADIUS / lengthsq));
        float modStartY = starty - ((starty - endy) * (Constants.BALL_RADIUS / lengthsq));
        float modEndX = endx + ((startx - endx) * (Constants.RECTICLE_RADIUS / lengthsq));
        float modEndY = endy + ((starty - endy) * (Constants.RECTICLE_RADIUS / lengthsq));

        Shape line = new Line(modStartX, modStartY, modEndX, modEndY);
        line.setColor(0.5f, 0.5f, 0.5f);
        
        return line;
    }

    public void attach(final Scene scene) {
        if (!hasDetached) {
            scene.attachChild(line);
            scene.attachChild(recticle);
        }
    }

    public void detach(Scene scene) {
        hasDetached = true;
        scene.detachChild(line);
        scene.detachChild(recticle);
    }

}
