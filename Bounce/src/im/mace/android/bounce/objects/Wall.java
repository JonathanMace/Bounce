package im.mace.android.bounce.objects;

import im.mace.android.bounce.common.Constants;

import org.anddev.andengine.entity.primitive.Line;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.extension.physics.box2d.PhysicsFactory;
import org.anddev.andengine.extension.physics.box2d.PhysicsWorld;

public class Wall {
    
    public float width;
    public float y2;
    public float y1;
    public float x2;
    public float x1;

    public Wall(float x1, float y1, float x2, float y2) {
        this(x1, y1, x2, y2, Constants.WALL_WIDTH_DEFAULT);
    }
    
    public Wall(float x1, float y1, float x2, float y2, float width) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.width = width;
    }
    
    /**
     * Only call this once the engine has been initialised
     * @return
     */
    public Line asLine() {
        return new Line(x1, y1, x2, y2, width);
    }
    
    public void attach(Scene scene, PhysicsWorld world) {
        Line line = this.asLine();
        
        // Attach the line to the scene
        scene.attachChild(line);
        
        // Register physics
        PhysicsFactory.createLineBody(world, line, Constants.WALL_FIXTURE_DEF);
    }

}
