package im.mace.android.bounce.hud;

import im.mace.android.bounce.common.Constants;

import java.util.Collection;

import org.anddev.andengine.engine.camera.hud.HUD;
import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.modifier.MoveModifier;
import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.util.modifier.IModifier.IModifierListener;
import org.anddev.andengine.util.modifier.ease.EaseQuadIn;
import org.anddev.andengine.util.modifier.ease.EaseQuintOut;
import org.anddev.andengine.util.modifier.ease.IEaseFunction;

public abstract class AnimatedHUD extends HUD {
    
    protected Collection<Shape> toAnimate;
    protected boolean animating;
    
    private IEaseFunction in = EaseQuintOut.getInstance();
    private IEaseFunction out = EaseQuadIn.getInstance();

    protected final float preHideLocation = Constants.CAMERA_WIDTH;
    protected final float postHideLocation = -Constants.CAMERA_WIDTH;
    
    public AnimatedHUD() {
        // Out of sight, out of mind
        this.setPosition(preHideLocation, 0);
    }
    
    public void enterLeft() {
        modify(0, 0, 1.0f, in);
    }
    
    public void enterRight() {
        modify(0, 0, 1.0f, in);
    }
    
    public void exitLeft(IModifierListener<IEntity> listener) {
        modify(postHideLocation, 0, 0.3f, out, listener);        
    }
    
    public void exitRight(IModifierListener<IEntity> listener) {
        modify(preHideLocation, 0, 0.3f, out, listener);        
    }
    
    public void modify(float toX, float toY, float duration, IEaseFunction func) {
        modify(toX, toY, duration, func, null);
    }
    
    public void modify(float toX, float toY, float duration, IEaseFunction func, IModifierListener<IEntity> listener) {
        this.clearEntityModifiers();
        MoveModifier modifier = new MoveModifier(1, this.getX(), toX, this.getY(), toY, func);
        if (listener!=null) {
            modifier.addModifierListener(listener);
        }
        this.registerEntityModifier(modifier);
    }

}
