package im.mace.android.bounce.game.mainmenu;

import im.mace.android.bounce.common.Constants;
import im.mace.android.bounce.common.Level;
import im.mace.android.bounce.common.TextureBucket;
import im.mace.android.bounce.game.MenuPage;
import im.mace.android.bounce.io.GameState;
import im.mace.android.bounce.io.LevelManager;

import java.util.EmptyStackException;
import java.util.Stack;

import org.anddev.andengine.engine.camera.hud.HUD;
import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.modifier.MoveModifier;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.util.modifier.IModifier;
import org.anddev.andengine.util.modifier.IModifier.IModifierListener;
import org.anddev.andengine.util.modifier.ease.EaseQuadInOut;
import org.anddev.andengine.util.modifier.ease.IEaseFunction;

import android.app.Activity;
import android.content.Intent;

public abstract class MainMenu extends HUD {

	private static final float TRANSITION_DURATION = 1.5f;
    private final IEaseFunction TRANSITION_FUNCTION = EaseQuadInOut.getInstance();

	private Activity activity;
	private GameState gameState;
	private TextureBucket textures;
	private Font font;
	private Stack<MenuPage> menuHistory = new Stack<MenuPage>();
	
	private volatile boolean transitioning = false;
        
    public MainMenu(Activity activity, TextureBucket textures, Font font, Stack<MenuPage> menuHistory) {
    	this.gameState = new GameState(activity);
    	this.activity = activity;
    	this.textures = textures;
    	this.font = font;
    	this.menuHistory = menuHistory;
    	this.menuHistory.peek().attach(0, 0, this);    	
    	this.setTouchAreaBindingEnabled(true);
    }
    
    public Stack<MenuPage> getMenuHistory() {
    	return menuHistory;
    }
    
    public TextureBucket getTextures() {
    	return textures;
    }
    
    public Font getFont() {
    	return font;
    }
    
    public GameState getGameState() {
    	return gameState;
    }
    
    public Level getLevel(String levelSet, int levelNumber) {
    	return LevelManager.getLevel(activity.getBaseContext(), levelSet, levelNumber);
    }
    
    public void nextMenu(MenuPage next) {
    	if (!transitioning) {
    		transitioning = true;
    		
	    	MenuPage currentPage = menuHistory.peek();
	    	menuHistory.push(next);
	    	
	    	float nextX = this.getX() - Constants.CAMERA_WIDTH*1.3f;
	    	float nextY = this.getY();

	    	this.moveTo(nextX, nextY, currentPage, next);
    	}
    }
    
    public void previousMenu() {
    	if (!transitioning) {
    		transitioning = true;
    		if (menuHistory.size() <= 1) {
    			this.quitGame(menuHistory.peek());
    		} else {
    			MenuPage currentPage = menuHistory.pop();
		    	MenuPage priorPage = menuHistory.peek();
	
		    	float nextX = this.getX() + Constants.CAMERA_WIDTH*1.3f;
		    	float nextY = this.getY();

		    	this.moveTo(nextX, nextY, currentPage, priorPage);
    		}
    	}
    }
    
    public void reload() {
    	try {
	    	MenuPage currentPage = menuHistory.peek();
	    	currentPage.detach(MainMenu.this);
	    	currentPage.attach(-MainMenu.this.getX(), -MainMenu.this.getY(), MainMenu.this);
    	} catch (Exception e) {
    		// Do nothing
    	}
    }
    
    private void moveTo(float x, float y, final MenuPage previous, MenuPage next) {
    	next.attach(-x, -y, this);
    	this.transition(x, y, new Runnable() {
			public void run() {
				previous.detach(MainMenu.this);
				transitioning = false;
			}
    	});
    }
        
    public void startGame(Class<?> gameModeActivity, String levelSet) {
        Intent intent = new Intent(activity.getBaseContext(), gameModeActivity);
        intent.putExtra(Constants.LEVEL_SET_KEY, levelSet);
        activity.startActivityForResult(intent, 0);               	
    }
    
    public void startGame(Class<?> gameModeActivity, String levelSet, int levelNumber) {
        Intent intent = new Intent(activity.getBaseContext(), gameModeActivity);
        intent.putExtra(Constants.LEVEL_SET_KEY, levelSet);
        intent.putExtra(Constants.LEVEL_KEY, levelNumber);
        activity.startActivityForResult(intent, 0);    
    }
    
    public void quitGame() {
    	try {
    		this.quitGame(menuHistory.peek());
    	} catch (EmptyStackException e) {
    		this.onQuit();
    	}
    }
    
    private void quitGame(MenuPage currentPage) {
    	float nextX = this.getX();
    	float nextY = Constants.CAMERA_HEIGHT*8 - this.getY();
    	this.transition(nextX, nextY, new Runnable() {
			public void run() {
				onQuit();
			}
    	});
    }
    
    private void transition(float toX, float toY, final Runnable onfinish) {
        MoveModifier modifier = new MoveModifier(TRANSITION_DURATION, this.getX(), toX, this.getY(), toY, TRANSITION_FUNCTION);
        modifier.addModifierListener(new IModifierListener<IEntity>() {
			public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {}
			public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
				onTransitionEnd();
				onfinish.run();
			}
		});
        this.onTransitionBegin((toX - this.getX()) / Constants.CAMERA_WIDTH, (toY - this.getY()) / Constants.CAMERA_HEIGHT);
        this.registerEntityModifier(modifier);
    }
    
    public abstract void onQuit();
    public abstract void onTransitionBegin(float xDirection, float yDirection);
    public abstract void onTransitionEnd();
}
