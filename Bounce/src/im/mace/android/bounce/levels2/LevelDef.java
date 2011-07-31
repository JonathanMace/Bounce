package im.mace.android.bounce.levels2;


import im.mace.android.bounce.common.Constants;
import im.mace.android.bounce.objects.Wall;

import java.util.ArrayList;
import java.util.Collection;

public class LevelDef {
    
    public String name = "Level";
    
    public Collection<Wall> walls = new ArrayList<Wall>();
    public float bucketX = Constants.CAMERA_WIDTH - Constants.WALL_WIDTH_DEFAULT - Constants.BUCKET_WIDTH_DEFAULT/2;
    public float bucketY = Constants.CAMERA_HEIGHT - Constants.WALL_WIDTH_DEFAULT - Constants.BUCKET_HEIGHT_DEFAULT/2;
    public float ballX = Constants.WALL_WIDTH_DEFAULT + Constants.BALL_RADIUS + 10;
    public float ballY = Constants.CAMERA_HEIGHT - Constants.WALL_WIDTH_DEFAULT - Constants.BALL_RADIUS;

}
