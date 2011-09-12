package im.mace.android.bounce.game;

import static org.anddev.andengine.extension.physics.box2d.util.constants.PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
import im.mace.android.bounce.common.Constants;
import im.mace.android.bounce.common.LevelSpec;
import im.mace.android.bounce.common.TextureBucket;
import im.mace.android.bounce.ui.Bucket;
import im.mace.android.bounce.ui.GameBorder;
import im.mace.android.bounce.ui.TargetingLine;
import im.mace.android.bounce.ui.Wall;

import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.anddev.andengine.extension.physics.box2d.PhysicsConnector;
import org.anddev.andengine.extension.physics.box2d.PhysicsFactory;
import org.anddev.andengine.extension.physics.box2d.PhysicsWorld;
import org.anddev.andengine.input.touch.TouchEvent;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public abstract class GameScene extends Scene {
    
    // Important variables
    private final LevelSpec def;
    private final PhysicsWorld world;
    private final TextureBucket textures;
    
    // Geometry 
    private Shape ball;
	private Body ballbody;
    private Bucket bucket;
    
    // Other variables
    private Vector2 previousFirePoint;    
    private volatile boolean levelComplete = false;
    private volatile boolean processedFinish = false;
    
    // Score counts
    public volatile long gameDuration;
    public volatile long bounces;
	private IUpdateHandler currentTimer;
	private FireBallTouchListener fireBallListener;
    
    public GameScene(LevelSpec def, TextureBucket textures) {
        this.def = def;
        this.textures = textures;
        
        // Default black background
        this.setBackground(new ColorBackground(0, 0, 0));
        
        // Fixed step physics world
        this.world = new FixedStepPhysicsWorld(60, Constants.GRAVITY, false);
        
        // Set up geometry
        this.constructGeometry();
        
        // Connect the world to the scene
        this.registerUpdateHandler(this.world);
        
        // Check for goal state & finished flag
        this.world.setContactListener(new GameBounceListener());
        this.registerUpdateHandler(new LevelFinishedHandler());
        
        // Initialise
        this.resetGame();
    }
    
    /**
     * Resets the game back to its starting state
     */
    public void resetGame() {
        this.levelComplete = false;
        this.processedFinish = false;
        
        this.bounces = 0;
        
        if (this.currentTimer!=null) {
        	this.unregisterUpdateHandler(this.currentTimer);
        	this.currentTimer=null;
        }

        this.world.setGravity(Constants.ZERO_VECTOR);
        this.resetBall();

        if (this.fireBallListener!=null) {
        	this.fireBallListener.detachGhost();
        }
        
        this.fireBallListener = new FireBallTouchListener(def.ballX, def.ballY, previousFirePoint);
        this.setOnSceneTouchListener(this.fireBallListener);
        
        System.gc();
    }
    
    /**
     * Override if you wish to receive callbacks when the ball bounces
     */
    protected void onBounce(long total) {
    }
    
    /**
     * Override if you wish to receive callbacks when time elapses while the ball is moving
     */
    protected void onTimeElapsed(long elapsed) {
    }
    
    private void gameComplete() {
    	if (this.currentTimer!=null) {
    		this.unregisterUpdateHandler(this.currentTimer);
    	}
    	onGameSuccess();
    }
    
    /**
     * Callback when the game successfully finishes
     */
    public abstract void onGameSuccess();

    /**
     * Sets up all the physical objects in the game scene
     */
    private void constructGeometry() {
        // Create the level border
        GameBorder border = new GameBorder();
        border.attach(this, world);
        
        // Create the bucket
        this.bucket = new Bucket(def.bucketX, def.bucketY);
        this.bucket.attach(this, world);
        
        // Add walls from def
        for (Wall wall : def.walls) {
            wall.attach(this, world);
        }
    }
    
    private void resetBall() {
        destroyBall(this.ball);
        this.ball = this.createBall(def.ballX - Constants.BALL_RADIUS, def.ballY - Constants.BALL_RADIUS);
        this.ballbody = this.world.getPhysicsConnectorManager().findBodyByShape(this.ball);
    }
    
    private Shape createBall(float x, float y) {
        Shape ball = new Sprite(x, y, textures.getTexture("ball"));
        Body body = PhysicsFactory.createCircleBody(this.world, ball, BodyType.DynamicBody, Constants.BALL_FIXTURE_DEF);
        this.world.registerPhysicsConnector(new PhysicsConnector(ball, body, true, true));
        this.attachChild(ball);
        return ball;
    }
    
    private void destroyBall(Shape ball) {
        if (ball!=null) {
            PhysicsConnector ballPhysicsConnector = this.world.getPhysicsConnectorManager().findPhysicsConnectorByShape(ball);
            if (ballPhysicsConnector!=null) {
                this.world.unregisterPhysicsConnector(ballPhysicsConnector);
                this.world.destroyBody(ballPhysicsConnector.getBody());
            }
            this.detachChild(ball);
        }
    }

    private void fire(Vector2 velocity) {
    	onBounce(bounces);
        if (ballbody!=null) {
            ballbody.setLinearVelocity(velocity);
        }
        this.world.setGravity(Constants.GRAVITY);
        this.currentTimer = new Timer();
        this.registerUpdateHandler(this.currentTimer);
    }
    
    private boolean goalAchieved() {
        return isBallInBucket() && isBallAtRest();
    }
    
    private boolean isBallInBucket() {
        boolean ret = false;
        if (ballbody!=null) {
            Vector2 position = ballbody.getPosition();
            ret = this.bucket.contains(position.x * PIXEL_TO_METER_RATIO_DEFAULT, position.y * PIXEL_TO_METER_RATIO_DEFAULT);
        }
        return ret;
    }
    
    private boolean isBallAtRest() {
        boolean atRest = false;
        if (ballbody!=null) {
            Vector2 vel = ballbody.getLinearVelocity();
            float mod = vel.x*vel.x+vel.y*vel.y;
            return mod < 1.5f;
        }
        return atRest;
    }
    
    private boolean checkIfBounce(Contact contact) {
    	if (testCollision(contact.getFixtureA().getBody(), contact.getFixtureB().getBody())) {
    		this.bounces++;
        	onBounce(bounces);
        	return true;
    	}    	
    	return false;
    }
    
    /**
     * Tests whether the two bodies are legitimate (eg. ball and wall), not part of the bucket
     */
    private boolean testCollision(Body a, Body b) {
    	Body wall;
    	if (a==this.ballbody) {
    		if (b==this.ballbody) {
    			return false;
    		} else {
    			wall = b;
    		}
    	} else {
    		if (b==this.ballbody) {
    			wall = a;
    		} else {
    			return false;
    		}
    	}
    	return !this.bucket.isPartOfBucket(wall, this.world);
    }
    
    // Checks goal condition, and also increments bounce count
    private class GameBounceListener implements ContactListener {
    	long previous = System.nanoTime();
        public void beginContact(Contact contact) {
            if (!GameScene.this.levelComplete && GameScene.this.goalAchieved()) {
                GameScene.this.levelComplete = true;
            }
        }
        public void endContact(Contact contact) {
        	if ((System.nanoTime() - previous) > 10000000L) {
        		previous = checkIfBounce(contact) ? System.nanoTime() : previous;
        	}
    	}
        public void preSolve(Contact contact, Manifold oldManifold) { /* Do nothing */ }
        public void postSolve(Contact contact, ContactImpulse impulse) { /* Do nothing */ }
    }
    
    // For threading reasons, have to have an update loop checking for finished
    private class LevelFinishedHandler implements IUpdateHandler {
        public void onUpdate(float pSecondsElapsed) {
            if (GameScene.this.levelComplete && !GameScene.this.processedFinish) {
                GameScene.this.gameComplete();
                GameScene.this.processedFinish = true;
            }
        }
        public void reset() { /* Do nothing */ }
    }
    
    private class Timer implements IUpdateHandler {
    	float begin = 0;
		public void onUpdate(float pSecondsElapsed) {
			// Use the elapsed time, rather than System time, because
			// if slowdown occurs in the game engine, the scaled time
			// takes this into account as well.
			begin += pSecondsElapsed;
			long longElapsed = (long) (begin * 1000000000);
			GameScene.this.onTimeElapsed(longElapsed);
		}
		public void reset() {}
    }
    
    protected class FireBallTouchListener implements IOnSceneTouchListener {

        private final float startX;
        private final float startY;

        private TargetingLine activeLine;
        private final Shape ghostRecticle;

        public FireBallTouchListener(float x, float y) {
            this(x, y, null);
        }
        
        public FireBallTouchListener(float x, float y, Vector2 previous) {
            this.startX = x;
            this.startY = y;
            this.ghostRecticle = constructGhostRecticle(previous);
        }
        
        protected Shape constructGhostRecticle(Vector2 previous) {
            Shape ghostRecticle = null;
            if (previous!=null) {
                ghostRecticle = new Sprite(
                    previous.x-Constants.RECTICLE_RADIUS, 
                    previous.y-Constants.RECTICLE_RADIUS, 
                    Constants.RECTICLE_RADIUS*2, 
                    Constants.RECTICLE_RADIUS*2, 
                    textures.getTexture("ghostrecticle")
                );
                GameScene.this.attachChild(ghostRecticle);
            }
            return ghostRecticle;            
        }
        
        // Detaches this listener from the scene and clears up any mess left behind
        public void detach() {
            this.detachActiveLine();
            GameScene.this.setOnSceneTouchListener(null);    
            GameScene.this.fireBallListener=null;
            if (this.ghostRecticle!=null) {
                GameScene.this.postRunnable(new Runnable() {
                    public void run() {
                    	detachGhost();
                    }
                });
            }
        }
        
        public void detachGhost() {
        	GameScene.this.detachChild(this.ghostRecticle);
        	
        }

        @Override
        public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
            if(pSceneTouchEvent.isActionMove() || pSceneTouchEvent.isActionDown()) {
                updateTarget(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
                return true;
            } else if(pSceneTouchEvent.isActionUp()) {
                fire(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
                return true;
            }
            return false;
        }

        private void fire(float releaseX, float releaseY) {
            // Snap to whole numbers
            releaseX = (float) Math.floor(releaseX);
            releaseY = (float) Math.floor(releaseY);
            
            if (releaseX > Constants.CAMERA_WIDTH) {
                releaseX = Constants.CAMERA_WIDTH;
            } else if (releaseX < 0) {
                releaseX = 0;
            }
            if (releaseY > Constants.CAMERA_HEIGHT) {
                releaseY = Constants.CAMERA_HEIGHT;
            } else if (releaseY < 0) {
                releaseY = 0;
            }

            // Calculate velocity of ball
            float x = (startX - releaseX) / 4;
            float y = (startY - releaseY) / 4;
            float length = (float) Math.sqrt(x * x + y * y);
            x = x * length / 75;
            y = y * length / 75;
            Vector2 velocity = new Vector2(x, y);
            
            // Fire the ball
            GameScene.this.fire(velocity);
            
            // Save this fire point
            GameScene.this.previousFirePoint = new Vector2(releaseX, releaseY);

            // Detach self from the scene
            this.detach();
        }

        private void updateTarget(float newX, float newY) {
            newX = (float) Math.floor(newX);
            newY = (float) Math.floor(newY);
            
            if (newX > Constants.CAMERA_WIDTH) {
                newX = Constants.CAMERA_WIDTH;
            } else if (newX < 0) {
                newX = 0;
            }
            if (newY > Constants.CAMERA_HEIGHT) {
                newY = Constants.CAMERA_HEIGHT;
            } else if (newY < 0) {
                newY = 0;
            }
            
            this.detachActiveLine();
            TargetingLine newLine = new TargetingLine(textures, startX, startY, newX, newY);
            this.attachActiveLine(newLine);     
        }
        
        private void detachActiveLine() {
            if (this.activeLine!=null) {
                final TargetingLine previousLine = this.activeLine;
                GameScene.this.postRunnable(new Runnable() {
                    public void run() {
                        previousLine.detach(GameScene.this);
                        if (FireBallTouchListener.this.activeLine==previousLine) {
                            FireBallTouchListener.this.activeLine=null;
                        }
                    }
                });
            }
        }
        
        private void attachActiveLine(final TargetingLine nextLine) {
            if (nextLine!=null) {
                this.activeLine = nextLine;
                GameScene.this.postRunnable(new Runnable() {
                    public void run() {
                        nextLine.attach(GameScene.this);
                    }
                });
            }
        }
    }

}
