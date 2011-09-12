package im.mace.android.bounce.game.maxbounce;

import im.mace.android.bounce.common.Constants;
import im.mace.android.bounce.common.Level;
import im.mace.android.bounce.game.mainmenu.MainMenu;
import im.mace.android.bounce.game.mainmenu.SelectLevelMenu;
import im.mace.android.bounce.game.mainmenu.SelectLevelSetMenu;

public class MaxModeMenu extends SelectLevelSetMenu {
	
	public MaxModeMenu() {
		super(Constants.MODE_MAX);
	}

	@Override
	protected void onLevelSetSelected(MainMenu menu, String levelSet) {
        menu.nextMenu(new SelectLevelMaxMenu(levelSet));
	}
	
	public class SelectLevelMaxMenu extends SelectLevelMenu {

		public SelectLevelMaxMenu(String levelSet) {
			super(levelSet, 0);
		}

		@Override
		protected void onLevelSelected(MainMenu menu, int levelNumber) {
			menu.startGame(MaxBounceMode.class, levelSet, levelNumber);
		}

		@Override
		protected boolean displayTick(Level level) {
			return level.beatenMax();
		}
		
	}

}
