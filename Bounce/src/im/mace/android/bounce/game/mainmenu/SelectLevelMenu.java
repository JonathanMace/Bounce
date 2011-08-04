package im.mace.android.bounce.game.mainmenu;

import im.mace.android.bounce.common.Level;
import im.mace.android.bounce.game.MenuPage;
import im.mace.android.bounce.ui.Button;
import im.mace.android.bounce.ui.DisableButton;

import java.util.ArrayList;
import java.util.Collection;

import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.util.HorizontalAlign;

public abstract class SelectLevelMenu extends MenuPage {

	protected static final float TICK_SIZE = 60;

	protected static final float BUTTON_X = 175;
	protected static final float BUTTON_START_Y = 135;
	
	protected static final float BUTTON_WIDTH = 450;
    protected static final float BUTTON_HEIGHT = 60;
    protected static final float BUTTON_DIVISION = 8;

    protected static final float NEXT_PAGE_X = 690;    
    protected static final float PREVIOUS_PAGE_X = 10;
    protected static final float NEXT_PREV_Y = 252;
    protected static final float NEXT_PREV_SIZE = 100;
    
    
    protected static final int NUM_PER_PAGE = 5;

	protected final String levelSet;
	protected final int pageOffset;
    
    public SelectLevelMenu(String levelSet, int offset) {
    	this.levelSet = levelSet;
    	this.pageOffset = offset;
    }

	@Override
	public Collection<Shape> constructMenuObjects(final MainMenu menu) {
		Collection<Shape> buttons = new ArrayList<Shape>();
		
		int currentLevel = 0;
		Level level = menu.getLevel(levelSet, currentLevel+pageOffset);
		while (level!=null && currentLevel < NUM_PER_PAGE) {
			buttons.add(constructButton(BUTTON_X, BUTTON_START_Y+(BUTTON_HEIGHT+BUTTON_DIVISION)*currentLevel, level, currentLevel+pageOffset, menu));
			currentLevel++;
			level = menu.getLevel(levelSet, currentLevel+pageOffset);
		}
		
		if (level!=null) {
			// More levels, so add pagination
			buttons.add(constructNextButton(menu));
		}
//		
//		if (pageOffset>0) {
//			// Not the start index, so add back pagination
			buttons.add(constructPreviousButton(menu));
//		}
		
		return buttons;
	}

    protected DisableButton constructButton(float x, float y, Level level, final int levelNumber, final MainMenu menu) {
        DisableButton button = null;
        TiledTextureRegion buttonTexture = menu.getTextures().getTiledTexture("button_level").clone();
        if (buttonTexture != null) {
        	// Create the button
            button = new DisableButton(x, y, BUTTON_WIDTH, BUTTON_HEIGHT, buttonTexture){
                public void onClick() {
                	onLevelSelected(menu, levelNumber);
                }
            };
            
            // Create the label
            String label = level.getSpec().name;
            if (label.length()>20) {
            	label = label.substring(0, 20)+"...";
            }
            label = (levelNumber+1)+". "+label;
            Text text = new Text(20, 11, menu.getFont(),label, HorizontalAlign.LEFT);
            button.attachChild(text);      
            
            // Create the tick
            if (displayTick(level)) {
    			TextureRegion tickTexture = menu.getTextures().getTexture("tick");
    			Sprite tick = new Sprite(BUTTON_WIDTH-45, -2, TICK_SIZE, TICK_SIZE, tickTexture);
    			button.attachChild(tick);            	
            }
        }
        return button;
    }
    
    protected Button constructPreviousButton(final MainMenu menu) {
    	Button button = null;
        TiledTextureRegion buttonTexture = menu.getTextures().getTiledTexture("arrowleft");
        if (buttonTexture!=null) {
			button = new Button(PREVIOUS_PAGE_X, NEXT_PREV_Y, NEXT_PREV_SIZE, NEXT_PREV_SIZE, buttonTexture) {
				public void onClick() {
					menu.previousMenu();
				}
			};
        }
    	return button;
    }
    
    protected Button constructNextButton(final MainMenu menu) {
    	Button button = null;
        TiledTextureRegion buttonTexture = menu.getTextures().getTiledTexture("arrowright");
        if (buttonTexture!=null) {
			button = new Button(NEXT_PAGE_X, NEXT_PREV_Y, NEXT_PREV_SIZE, NEXT_PREV_SIZE, buttonTexture) {
				public void onClick() {
					menu.nextMenu(new Page(SelectLevelMenu.this, pageOffset+NUM_PER_PAGE));
				}
			};
        }
    	return button;
    }
    
    protected abstract boolean displayTick(Level level);
    
    protected abstract void onLevelSelected(MainMenu menu, int levelNumber);
    
    private class Page extends SelectLevelMenu {

		private SelectLevelMenu parent;

		public Page(SelectLevelMenu parent, int index) {
			super(parent.levelSet, index);
			this.parent = parent;
		}

		@Override
		protected boolean displayTick(Level level) {
			return parent.displayTick(level);
		}

		@Override
		protected void onLevelSelected(MainMenu menu, int levelNumber) {
			parent.onLevelSelected(menu, levelNumber);
		}
		
    	
    }

}
