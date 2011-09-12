package im.mace.android.bounce.common;

import org.anddev.andengine.extension.physics.box2d.PhysicsFactory;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Constants {
    
    public static final int CAMERA_WIDTH = 800;
    public static final int CAMERA_HEIGHT = 480;

    public static final FixtureDef WALL_FIXTURE_DEF = PhysicsFactory.createFixtureDef(0, 0.8f, 0.01f);
    public static final FixtureDef BUCKET_FIXTURE_DEF = PhysicsFactory.createFixtureDef(0, 0.01f, 0.9f);
    public static final FixtureDef BALL_FIXTURE_DEF = PhysicsFactory.createFixtureDef(1, 0.5f, 0.5f);

    public static final Vector2 GRAVITY = new Vector2(0, 30);
    
    public static final Vector2 ZERO_VECTOR = new Vector2(0, 0);
    
    public static final float WALL_WIDTH_DEFAULT = 2;

    public static final float BUCKET_WIDTH_DEFAULT = 86;
    public static final float BUCKET_HEIGHT_DEFAULT = 70;
    public static final float BUCKET_WALL_WIDTH_DEFAULT = 3;
    
    public static final float BALL_RADIUS = 32;
    public static final float RECTICLE_RADIUS = 64;
    
    public static final String LEVEL_SET_KEY = "levelset";
    public static final String LEVEL_KEY = "level";
    
    public static final Long MAX_BOUNCES_CAP = 0L;
    public static final Long MIN_BOUNCES_FLOOR = 100L;
    public static final Long TIME_ATTACK_CAP = 60000000000L;

    public static final String[] LEVELSETS = new String[] {
             "starter", "bouncy", "trickshot",
             "intermediate", "delicate", "fluke",
             "advanced", "painful", "impossible" 
    };
    

    public static final String MODE_TIME = "besttime";
    public static final String MODE_MIN = "minbounce";
    public static final String MODE_MAX = "maxbounce";
    public static final String MODE_MINMAX = "minmax";
    public static final String MODE_PRACTICE = "practice";
    public static final String MODE_STANDARD = "standard";
    
    public static final String PREFERENCES_NAME = "im.mace.android.bounce";
    
    public static final long SECOND = 1000000000L;

}
