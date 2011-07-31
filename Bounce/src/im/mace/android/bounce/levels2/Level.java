package im.mace.android.bounce.levels2;

public abstract class Level {
    
    private LevelDef def;

    public Level(LevelDef def) {
        this.def = def;
    }
    
    /**
     * Set the user's best time for the time attack mode
     * @param best the user's time
     * @return true if set successfully, false if the user's current saved value is better
     */
    public boolean setBestTime(Float best) {
        return false;
        
    }
    
    /**
     * Gets the best time that the user has completed this level in in 'time attack' mode
     * @return the best time if the user has one saved, or null otherwise
     */
    public Float getBestTime() {
        return null;
    }
    
    /**
     * Gets the fewest number of bounces that the user has completed this level in in 'min bounce' mode
     * @return the fewest bounces if the user has a value saved, or null otherwise
     */
    public Integer getMinBounces() {
        return null;
    }
    
    /**
     * Gets the most number of bounces that the user has completed this level in in 'max bounce' mode
     * @return the most bounces if the user has a value saved, or null otherwise
     */
    public Integer getMaxBounces() {
        return null;
    }
    
    /**
     * Gets the best difference between min bounces and max bounces achieved by the user in min/max mode
     * @return the best difference if the user has a value saved, or null otherwise
     */
    public Integer getMinMax() {
        return null;
    }
    
    
    /**
     * Should return the next level to play once this one is over, 
     * or null to return to the menu it came from
     * @return a Level, or null if there are no more levels to play
     */
    public abstract Level nextLevel();

}
