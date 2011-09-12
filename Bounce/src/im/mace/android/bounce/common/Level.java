package im.mace.android.bounce.common;

import im.mace.android.bounce.io.HighScoreDatabase;
import android.content.Context;


public class Level {

	private HighScoreDatabase db;
    private LevelSpec spec;

    public Level(Context context, LevelSpec spec) {
    	this.db = new HighScoreDatabase(context);
        this.spec = spec;
    }
    
    public LevelSpec getSpec() {
    	return spec;
    }
    
    /**
     * Set the user's best time for the time attack mode
     * @param newBest the user's time
     * @return true if set successfully, false if the user's current saved value is better
     */
    public boolean setBestTime(long newBest) {
    	boolean updated = false;
    	if (newBest < spec.time) {
    		Long currentBest = db.getScore(Constants.MODE_TIME, spec.id);
    		if (currentBest==null || newBest < currentBest) {
    			db.setScore(Constants.MODE_TIME, spec.id, newBest);
        		updated = true;
    		}
    	}
    	return updated;
    }
    
    public boolean setMinBounces(long newMin) {
    	boolean updated = false;
    	if (newMin < spec.min) {
    		Long currentBest = db.getScore(Constants.MODE_MIN, spec.id);
    		if (currentBest==null || newMin < currentBest) {
    			db.setScore(Constants.MODE_MIN, spec.id, newMin);
        		updated = true;
    		}
    	}
    	return updated;
    }
    
    public boolean setMaxBounces(long newMax) {
    	boolean updated = false;
    	if (newMax > spec.max) {
    		Long currentBest = db.getScore(Constants.MODE_MAX, spec.id);
    		if (currentBest==null || newMax > currentBest) {
    			db.setScore(Constants.MODE_MAX, spec.id, newMax);
        		updated = true;
    		}
    	}
    	return updated;
    }
    
    public boolean setMinMax(long newMinMax) {
    	boolean updated = false;
    	if (newMinMax > spec.minmax) {
    		Long currentBest = db.getScore(Constants.MODE_MINMAX, spec.id);
    		if (currentBest==null || newMinMax > currentBest) {
    			db.setScore(Constants.MODE_MINMAX, spec.id, newMinMax);
        		updated = true;
    		}
    	}
    	return updated; 
    }
    
    /**
     * Gets the best time that the user has completed this level in in 'time attack' mode
     * @return the best time if the user has one saved, or null otherwise
     */
    public Long getBestTime() {
        return db.getScore(Constants.MODE_TIME, spec.id);
    }
    
    /**
     * Gets the fewest number of bounces that the user has completed this level in in 'min bounce' mode
     * @return the fewest bounces if the user has a value saved, or null otherwise
     */
    public Long getMinBounces() {
        return db.getScore(Constants.MODE_MIN, spec.id);
    }
    
    /**
     * Gets the most number of bounces that the user has completed this level in in 'max bounce' mode
     * @return the most bounces if the user has a value saved, or null otherwise
     */
    public Long getMaxBounces() {
        return db.getScore(Constants.MODE_MAX, spec.id);
    }
    
    /**
     * Gets the best difference between min bounces and max bounces achieved by the user in min/max mode
     * @return the best difference if the user has a value saved, or null otherwise
     */
    public Long getMinMax() {
        return db.getScore(Constants.MODE_MINMAX, spec.id);
    }
    
    public boolean beatenTime() {
    	Long time = this.getBestTime();
    	return time!=null && time < spec.time;
    }
    
    public boolean beatenMin() {
    	Long min = this.getMinBounces();
    	return min!=null && min < spec.min;
    }
    
    public boolean beatenMax() {
    	Long max = this.getMaxBounces();
    	return max!=null && max > spec.max;
    }
    
    public boolean beatenMinMax() {
    	Long minmax = this.getMinMax();
    	return minmax!=null && minmax > spec.minmax;
    }

}
