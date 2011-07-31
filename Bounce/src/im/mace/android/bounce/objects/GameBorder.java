package im.mace.android.bounce.objects;

import im.mace.android.bounce.common.Constants;

import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.extension.physics.box2d.PhysicsFactory;
import org.anddev.andengine.extension.physics.box2d.PhysicsWorld;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class GameBorder {
    
    private final float width;
    private final float height;
    private final float wallWidth;


    public GameBorder() {
        this(Constants.CAMERA_WIDTH, Constants.CAMERA_HEIGHT, Constants.WALL_WIDTH_DEFAULT);
    }
    
    public GameBorder(float width, float height, float wallWidth) {
        this.width = width;
        this.height = height;
        this.wallWidth = wallWidth;
    }

    
    /**
     * Adds walls to the specified scene and world.  Constants defining the bucket's height and width
     * are specified in Constants.java.  Alternatively pass them in to GameBorder's constructor
     * @param scene the scene that the bucket will display in
     * @param world the world that the bucket will belong to
     * @param x the x co-ordinate of the CENTER of the bucket
     * @param y the y co-ordinate of the CENTER of the bucket
     */
    public void attach(Scene scene, PhysicsWorld world) {  
        // Create the objects
        final Shape ground = new Rectangle(0, height - wallWidth, width, wallWidth);
        final Shape roof = new Rectangle(0, 0, width, wallWidth);
        final Shape left = new Rectangle(0, 0, wallWidth, height);
        final Shape right = new Rectangle(width - wallWidth, 0, wallWidth, height);

        // Attach objects to scene
        scene.attachChild(ground);
        scene.attachChild(roof);
        scene.attachChild(left);
        scene.attachChild(right);

        // Register object physics
        PhysicsFactory.createBoxBody(world, ground, BodyType.StaticBody, Constants.WALL_FIXTURE_DEF);
        PhysicsFactory.createBoxBody(world, roof, BodyType.StaticBody, Constants.WALL_FIXTURE_DEF);
        PhysicsFactory.createBoxBody(world, left, BodyType.StaticBody, Constants.WALL_FIXTURE_DEF);
        PhysicsFactory.createBoxBody(world, right, BodyType.StaticBody, Constants.WALL_FIXTURE_DEF);
    }
}
