package im.mace.android.bounce.common;


import im.mace.android.bounce.ui.Wall;

import java.util.ArrayList;
import java.util.Collection;

public class LevelSpec {
    
    public String name = "Level";
    public String id = "level";
    
    public long min = 99;
    public long max = 1;
    public long minmax = 0;
    public long time = 10000000000000L; /* 10 seconds in nanotime */
    
    public Collection<Wall> walls = new ArrayList<Wall>();
    public float bucketX = Constants.CAMERA_WIDTH - Constants.WALL_WIDTH_DEFAULT - Constants.BUCKET_WIDTH_DEFAULT/2;
    public float bucketY = Constants.CAMERA_HEIGHT - Constants.WALL_WIDTH_DEFAULT - Constants.BUCKET_HEIGHT_DEFAULT/2;
    public float ballX = Constants.WALL_WIDTH_DEFAULT + Constants.BALL_RADIUS + 10;
    public float ballY = Constants.CAMERA_HEIGHT - Constants.WALL_WIDTH_DEFAULT - Constants.BALL_RADIUS;

}
