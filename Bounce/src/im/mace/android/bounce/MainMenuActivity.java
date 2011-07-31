package im.mace.android.bounce;

import im.mace.android.bounce.common.Constants;
import im.mace.android.bounce.common.TextureBucket;
import im.mace.android.bounce.common.TextureUtils;
import im.mace.android.bounce.hud.AnimatedHUD;
import im.mace.android.bounce.hud.MainMenuHUD;
import im.mace.android.bounce.hud.SelectLevelSetHUD;
import im.mace.android.bounce.hud.SelectModeHUD;
import im.mace.android.bounce.levels.LevelManager;
import im.mace.android.bounce.objects.GameBorder;

import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.IEntity;
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
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.sensor.accelerometer.AccelerometerData;
import org.anddev.andengine.sensor.accelerometer.IAccelerometerListener;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.modifier.IModifier;
import org.anddev.andengine.util.modifier.IModifier.IModifierListener;
import org.anddev.andengine.util.modifier.ease.EaseCubicIn;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class MainMenuActivity extends BaseGameActivity implements IAccelerometerListener {

    /*
     * Importants
     */
    
    private Camera camera;
    private Scene scene;
    private FixedStepPhysicsWorld world;
    
    private TextureBucket textures;
    private Stack<AnimatedHUD> previousMenus = new Stack<AnimatedHUD>();
    
    
    /*
     * Not so importants
     */
    private float logoX = 200;
    private float logoY = 20;
    private final int MAX_BALLS = 30;
    private Queue<Shape> balls = new LinkedList<Shape>();
    
    private volatile boolean animatingMenu = false;
    

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
        
        // Default HUD is Main Menu
        this.nextMenu(this.createRootMenu());
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
    public void onAccelerometerChanged(final AccelerometerData pAccelerometerData) {
        if (!animatingMenu) {
            final Vector2 gravity = Vector2Pool.obtain(pAccelerometerData.getX()*3, pAccelerometerData.getY()*3);
            this.world.setGravity(gravity);
            Vector2Pool.recycle(gravity);
        }
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            previousMenu();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    
    private void previousMenu() {
        if (!animatingMenu) {
            animatingMenu = true;
            final Vector2 gravity = Vector2Pool.obtain(30, 0);
            this.world.setGravity(gravity);
            Vector2Pool.recycle(gravity);
            AnimatedHUD currentHUD = this.previousMenus.pop();
            try {
                final AnimatedHUD previousHUD = this.previousMenus.peek();
                currentHUD.exitRight(new IModifierListener<IEntity>() {
                    public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {}
                    public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                        previousHUD.enterLeft();
                        MainMenuActivity.this.camera.setHUD(previousHUD);
                        animatingMenu = false;
                    }                
                });
            } catch (EmptyStackException e) {
                quit(currentHUD);
            }
        }
    }
    
    private void nextMenu(final AnimatedHUD nextHUD) {
        if (!animatingMenu) {
            animatingMenu = true;
            final Vector2 gravity = Vector2Pool.obtain(-30, 0);
            this.world.setGravity(gravity);
            Vector2Pool.recycle(gravity);
            IModifierListener<IEntity> listener = new IModifierListener<IEntity>() {
                public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {}
                public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                    nextHUD.enterRight();
                    MainMenuActivity.this.camera.setHUD(nextHUD);
                    MainMenuActivity.this.previousMenus.push(nextHUD);
                    animatingMenu = false;
                }                           
            };
            try {
                AnimatedHUD currentHUD = this.previousMenus.peek();
                currentHUD.exitLeft(listener);
            } catch (EmptyStackException e) {
                listener.onModifierFinished(null, null);               
            }
        }
    }
    
    private AnimatedHUD createRootMenu() {
        // Create the HUD
        MainMenuHUD hud = new MainMenuHUD(this.scene, this.textures) {
            public void onPlayGame() {
                MainMenuActivity.this.nextMenu(MainMenuActivity.this.createSelectModeMenu());
            }
            public void onSettings() {
                
            }
            public void onQuit() {          
                MainMenuActivity.this.quit(this);
            }
        };
        hud.setTouchAreaBindingEnabled(true);
        return hud;
    }
    
    private AnimatedHUD createSelectModeMenu() {
        SelectModeHUD hud = new SelectModeHUD(this.scene, this.textures) {
            public void onPractice() {
            }
            public void onStandardMode() {
                MainMenuActivity.this.nextMenu(MainMenuActivity.this.createSelectLevelSetMenu());
            }
            public void onTimeAttack() {   
            }
            public void onMinBounce() {   
            }
            public void onMaxBounce() {   
            }
            public void onMinMax() {   
            }
        };
        hud.setTouchAreaBindingEnabled(true);
        return hud;
    }
    
    private AnimatedHUD createSelectLevelSetMenu() {
        SelectLevelSetHUD hud = new SelectLevelSetHUD(this.scene, this.textures) {
            public void onLevelSetSelected(String levelSet) {
                Intent intent = new Intent(getBaseContext(), GameControllerActivity.class);
                intent.putExtra(Constants.LEVEL_SET_KEY, levelSet);
                startActivityForResult(intent, 0);                
            }            
        };
        return hud;
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
        Shape ball = new Sprite(x, y, textures.getTexture("ball"));
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
    
    private void quit(AnimatedHUD currentHUD) {
        this.animatingMenu = true;
        IModifierListener<IEntity> listener = new IModifierListener<IEntity>() {
            public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {}
            public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                finish();
            }                           
        };
        if (currentHUD!=null) {
            this.world.setGravity(Constants.ZERO_VECTOR);
            currentHUD.modify(0, Constants.CAMERA_HEIGHT, 1.0f, EaseCubicIn.getInstance(), listener);
        } else {
            listener.onModifierFinished(null, null);
        }        
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Do nuffink
    }
}
