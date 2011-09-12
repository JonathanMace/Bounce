package im.mace.android.bounce.game.minbounce;

import im.mace.android.bounce.common.Constants;
import im.mace.android.bounce.common.Level;
import im.mace.android.bounce.game.mainmenu.MainMenu;
import im.mace.android.bounce.game.mainmenu.SelectLevelMenu;
import im.mace.android.bounce.game.mainmenu.SelectLevelSetMenu;

public class MinModeMenu extends SelectLevelSetMenu {
	
	public MinModeMenu() {
		super(Constants.MODE_MIN);
	}

	@Override
	protected void onLevelSetSelected(MainMenu menu, String levelSet) {
        menu.nextMenu(new SelectLevelMinMenu(levelSet));
	}
	
	public class SelectLevelMinMenu extends SelectLevelMenu {

		public SelectLevelMinMenu(String levelSet) {
			super(levelSet, 0);
		}

		@Override
		protected void onLevelSelected(MainMenu menu, int levelNumber) {
			menu.startGame(MinBounceMode.class, levelSet, levelNumber);
		}

		@Override
		protected boolean displayTick(Level level) {
			return level.beatenMin();
		}
		
	}
	
}
