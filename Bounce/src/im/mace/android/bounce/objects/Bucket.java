package im.mace.android.bounce.objects;

import im.mace.android.bounce.common.Constants;

import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.extension.physics.box2d.PhysicsFactory;
import org.anddev.andengine.extension.physics.box2d.PhysicsWorld;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Bucket {
    
    private final float x;
    private final float y;
    private final float width;
    private final float height;
    private final float wallWidth;


    public Bucket(float x, float y) {
        this(x, y, Constants.BUCKET_WIDTH_DEFAULT, Constants.BUCKET_HEIGHT_DEFAULT, Constants.BUCKET_WALL_WIDTH_DEFAULT);
    }
    
    public Bucket(float x, float y, float width, float height, float wallWidth) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.wallWidth = wallWidth;
    }

    
    /**
     * Adds a bucket to the specified scene and world.  Constants defining the bucket's height and width
     * are specified in Constants.java
     * @param scene the scene that the bucket will display in
     * @param world the world that the bucket will belong to
     * @param x the x co-ordinate of the CENTER of the bucket
     * @param y the y co-ordinate of the CENTER of the bucket
     */
    public void attach(Scene scene, PhysicsWorld world) {
        float halfWidth = width / 2;
        float halfHeight = height / 2;
        
        // Create the walls of the bucket
        final Shape ground = new Rectangle(x-halfWidth, y+halfHeight - wallWidth, width, wallWidth);
        final Shape leftWall = new Rectangle(x-halfWidth, y-halfHeight, wallWidth, height);
        final Shape rightWall = new Rectangle(x+halfWidth-wallWidth, y-halfHeight, wallWidth, height);

        ground.setColor(0.2f, 0.8f, 0.2f);
        leftWall.setColor(0.2f, 0.8f, 0.2f);
        rightWall.setColor(0.2f, 0.8f, 0.2f);
        
        // Attach objects to scene
        scene.attachChild(ground);
        scene.attachChild(leftWall);
        scene.attachChild(rightWall);
        
        // Create the physics objects
        PhysicsFactory.createBoxBody(world, ground, BodyType.StaticBody, Constants.BUCKET_FIXTURE_DEF);
        PhysicsFactory.createBoxBody(world, leftWall, BodyType.StaticBody, Constants.BUCKET_FIXTURE_DEF);
        PhysicsFactory.createBoxBody(world, rightWall, BodyType.StaticBody, Constants.BUCKET_FIXTURE_DEF);
    }
    
    public boolean contains(float x, float y) {
        float halfWidth = width / 2;
        float halfHeight = height / 2;
        
        return x < this.x + halfWidth && x > this.x - halfWidth && y < this.y + halfHeight && y > this.y - halfHeight;
    }
}
