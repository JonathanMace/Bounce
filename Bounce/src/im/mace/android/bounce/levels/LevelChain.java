package im.mace.android.bounce.levels;

import im.mace.android.bounce.levels2.LevelDef;

import java.util.Iterator;

public abstract class LevelChain implements Iterator<LevelDef> {

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Removing from an iterator?  Are you for real???");
    }

}
