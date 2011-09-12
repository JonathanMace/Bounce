package im.mace.android.bounce.game;

import im.mace.android.bounce.game.mainmenu.MainMenu;

import java.util.Collection;

import org.anddev.andengine.entity.shape.Shape;

public abstract class MenuPage {
	
	private Collection<Shape> currentObjects;
	
	public void attach(float offsetX, float offsetY, MainMenu menu) {
		this.detach(menu);
		this.currentObjects = this.constructMenuObjects(menu);
		for (Shape object : this.currentObjects) {
			object.setPosition(object.getX()+offsetX, object.getY()+offsetY);
			menu.attachChild(object);
			menu.registerTouchArea(object);
		}
	}
	
	public void detach(MainMenu menu) {
		if (this.currentObjects!=null) {
			for (Shape object : this.currentObjects) {
				menu.detachChild(object);
				menu.unregisterTouchArea(object);
			}
		}
	}
	
	/**
	 * Construct the objects for this menu and return them.  It is not necessary to attach them to the menu
	 * @param menu
	 * @param textures
	 * @return
	 */
	public abstract Collection<Shape> constructMenuObjects(MainMenu menu); 

}
