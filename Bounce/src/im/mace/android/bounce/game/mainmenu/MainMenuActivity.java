package im.mace.android.bounce.game.mainmenu;

import im.mace.android.bounce.common.Constants;
import im.mace.android.bounce.common.TextureBucket;
import im.mace.android.bounce.common.TextureUtils;
import im.mace.android.bounce.game.MenuPage;
import im.mace.android.bounce.ui.GameBorder;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.anddev.andengine.extension.physics.box2d.PhysicsConnector;
import org.anddev.andengine.extension.physics.box2d.PhysicsFactory;
import org.anddev.andengine.extension.physics.box2d.util.Vector2Pool;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.sensor.accelerometer.AccelerometerData;
import org.anddev.andengine.sensor.accelerometer.IAccelerometerListener;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class MainMenuActivity extends BaseGameActivity implements IAccelerometerListener {
	
	private static Stack<MenuPage> previousMenus = new Stack<MenuPage>();
	
	static {
		previousMenus.push(new TopMenu());
	}

    /*
     * Importants
     */
    
    private Camera camera;
    private Scene scene;
    private FixedStepPhysicsWorld world;
    private TextureBucket textures;
    private Font font;
    private MainMenu menu;
    
    
    /*
     * Not so importants
     */
    private float logoX = 200;
    private float logoY = 20;
    private final int MAX_BALLS = 30;
    private Queue<Shape> balls = new LinkedList<Shape>();
    
    private boolean menuTransitioning = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Engine onLoadEngine() {
        this.camera = new Camera(0, 0, Constants.CAMERA_WIDTH, Constants.CAMERA_HEIGHT);
        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(Constants.CAMERA_WIDTH, Constants.CAMERA_HEIGHT), camera);
        return new Engine(engineOptions);
    }

    @Override
    public void onLoadResources() {
        // Load up them thar textures
        textures = TextureUtils.loadMenuTextures(this, this.mEngine);
        font = TextureUtils.loadMenuFont(this.mEngine);
    }

    @Override
    public Scene onLoadScene() {
        this.scene = new Scene();
        this.scene.setBackground(new ColorBackground(0, 0, 0));
        
        // Attach logo to scene
        TextureRegion text = this.textures.getTexture("logo");
        Sprite logo = new Sprite(this.logoX, this.logoY, text);
        this.scene.attachChild(logo);
        
        // Fixed step physics world
        this.world = new FixedStepPhysicsWorld(60, Constants.GRAVITY, false);

        // Connect the world to the scene
        this.scene.registerUpdateHandler(this.world);
        
        // Add the menu
        this.menu = new MainMenu(this, textures, font, previousMenus) {
			public void onQuit() {
				MainMenuActivity.this.onQuit();
			}
			public void onTransitionBegin(float xDirection, float yDirection) {
				menuTransitioning = true;
	            final Vector2 gravity = Vector2Pool.obtain(xDirection*30, 0 /* ignore yDirection for now */);
	            world.setGravity(gravity);
	            Vector2Pool.recycle(gravity);
			}
			public void onTransitionEnd() {
				menuTransitioning = false;
			}
        };
        this.camera.setHUD(this.menu);        
        
        // Add some balls
        addRandomBall();
        addRandomBall();
        addRandomBall();
        addRandomBall();
        addRandomBall();
        addRandomBall();
        
        // Set up the 'scene' parts
        this.setUpBackgroundAction();
        
        return this.scene;
    }
    
    @Override
    public void onResumeGame() {
            super.onResumeGame();

            this.enableAccelerometerSensor(this);
    }

    @Override
    public void onPauseGame() {
            super.onPauseGame();

            this.disableAccelerometerSensor();
    }


    @Override
    public void onLoadComplete() {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void onSaveInstanceState(Bundle state) {
    	// Save the current menus
    	if (this.menu!=null) {
    		previousMenus = this.menu.getMenuHistory();
    	}
    }
    
    @Override
    public void onAccelerometerChanged(final AccelerometerData pAccelerometerData) {
        if (!menuTransitioning) {
            final Vector2 gravity = Vector2Pool.obtain(pAccelerometerData.getX()*3, pAccelerometerData.getY()*3);
            this.world.setGravity(gravity);
            Vector2Pool.recycle(gravity);
        }
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            this.menu.previousMenu();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    
    /**
     * Sets up all the nifty background stuff, like ball spawning and the like
     */
    private void setUpBackgroundAction() {
        
        // Add a border
        GameBorder border = new GameBorder();
        border.attach(this.scene, this.world);

        // Add ball spawner
        this.scene.setOnSceneTouchListener(new IOnSceneTouchListener() {
            public boolean onSceneTouchEvent(Scene pScene, final TouchEvent pSceneTouchEvent) {
                if (pSceneTouchEvent.isActionDown()) {
                    MainMenuActivity.this.scene.postRunnable(new Runnable() {
                        public void run() {                        
                            MainMenuActivity.this.addBall(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
                        }
                    });
                    return true;
                }
                return false;
            }
        });
        
    }
    
    private void addRandomBall() {
        float x = (float) (Math.random() * (Constants.CAMERA_WIDTH - 2 * Constants.BALL_RADIUS) + Constants.BALL_RADIUS);
        float y = (float) (Math.random() * (Constants.CAMERA_HEIGHT - 2 * Constants.BALL_RADIUS) + Constants.BALL_RADIUS);
        addBall(x, y);
    }

    private void addBall(float x, float y) {
        if (balls.size() > MAX_BALLS) {
            removeBall(balls.remove());
        }
        Shape ball = new Sprite(x-Constants.BALL_RADIUS, y-Constants.BALL_RADIUS, textures.getTexture("ball"));
        Body body = PhysicsFactory.createCircleBody(this.world, ball, BodyType.DynamicBody, Constants.BALL_FIXTURE_DEF);
        this.world.registerPhysicsConnector(new PhysicsConnector(ball, body, true, true));
        this.scene.attachChild(ball);
        balls.add(ball);
    }
    
    private void removeBall(Shape ball) {
        if (ball!=null) {
            PhysicsConnector ballPhysicsConnector = this.world.getPhysicsConnectorManager().findPhysicsConnectorByShape(ball);
            if (ballPhysicsConnector!=null) {
                this.world.unregisterPhysicsConnector(ballPhysicsConnector);
                this.world.destroyBody(ballPhysicsConnector.getBody());
            }
            this.scene.detachChild(ball);
        }        
    }
    
    private void onQuit() {
    	this.finish();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	this.menu.reload();
    }
}
