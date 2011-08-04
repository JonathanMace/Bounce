
import im.mace.android.bounce.common.Constants;
import im.mace.android.bounce.common.LevelSpec;
import im.mace.android.bounce.ui.Wall;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.EmptyStackException;
import java.util.Stack;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public class BallsEditorCanvas extends JComponent {
    
    private static final int BORDER = 100;
    
    LevelSpec def = new LevelSpec();
    Stack<Undo> undoStack = new Stack<Undo>();
    
    public BallsEditorCanvas() {
        this.setPreferredSize(new Dimension(Constants.CAMERA_WIDTH+2*BORDER, Constants.CAMERA_HEIGHT+2*BORDER));
    }
    
    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.blue);
        g.fillRect(0, 0, Constants.CAMERA_WIDTH+2*BORDER, BORDER);
        g.fillRect(0, 0, BORDER, Constants.CAMERA_HEIGHT+2*BORDER);
        g.fillRect(0, Constants.CAMERA_HEIGHT+BORDER, Constants.CAMERA_WIDTH+2*BORDER, BORDER);
        g.fillRect(Constants.CAMERA_WIDTH+BORDER, 0, BORDER, Constants.CAMERA_HEIGHT+2*BORDER);
        g.setColor(Color.black);
        g.fillRect(BORDER, BORDER, Constants.CAMERA_WIDTH, Constants.CAMERA_HEIGHT);
        g.setColor(Color.white);
        drawBall(g);
        drawBucket(g);
        drawWalls(g);
    }
    
    private void drawBall(Graphics g) {
        g.drawOval((int) (def.ballX-Constants.BALL_RADIUS)+BORDER, (int) (def.ballY-Constants.BALL_RADIUS)+BORDER, (int) (Constants.BALL_RADIUS*2), (int) (Constants.BALL_RADIUS*2));
    }
    
    private void drawBucket(Graphics g) {
        g.drawLine((int) (def.bucketX - Constants.BUCKET_WIDTH_DEFAULT/2+BORDER), 
                (int) (def.bucketY - Constants.BUCKET_HEIGHT_DEFAULT/2)+BORDER, 
                (int) (def.bucketX - Constants.BUCKET_WIDTH_DEFAULT/2)+BORDER, 
                (int) (def.bucketY + Constants.BUCKET_HEIGHT_DEFAULT/2)+BORDER);

        g.drawLine((int) (def.bucketX + Constants.BUCKET_WIDTH_DEFAULT/2)+BORDER, 
                (int) (def.bucketY - Constants.BUCKET_HEIGHT_DEFAULT/2)+BORDER, 
                (int) (def.bucketX + Constants.BUCKET_WIDTH_DEFAULT/2)+BORDER, 
                (int) (def.bucketY + Constants.BUCKET_HEIGHT_DEFAULT/2)+BORDER);

        g.drawLine((int) (def.bucketX - Constants.BUCKET_WIDTH_DEFAULT/2)+BORDER, 
                (int) (def.bucketY + Constants.BUCKET_HEIGHT_DEFAULT/2)+BORDER, 
                (int) (def.bucketX + Constants.BUCKET_WIDTH_DEFAULT/2)+BORDER, 
                (int) (def.bucketY + Constants.BUCKET_HEIGHT_DEFAULT/2)+BORDER);
        
    }
    
    private void drawWalls(Graphics g) {
        for (Wall wall : def.walls) {
            g.drawLine((int) wall.x1+BORDER, (int) wall.y1+BORDER, (int) wall.x2+BORDER, (int) wall.y2+BORDER); 
        }
    }
    
    private void removeMouseListeners() {
        for (MouseListener listener : this.getMouseListeners()) {
            this.removeMouseListener(listener);
        }
    }
    
    public void setBallPlaceMode() {
        removeMouseListeners();
        this.addMouseListener(new BallPlaceListener());
    }
    
    public void setBucketPlaceMode() {
        removeMouseListeners();
        this.addMouseListener(new BucketPlaceListener());        
    }
    
    public void setLineDrawMode() {
        removeMouseListeners();
        this.addMouseListener(new LineDrawBeginListener());   
    }
    
    public void undo() {
        try {
            Undo undo = undoStack.pop();
            undo.undo();
            BallsEditorCanvas.this.repaint();
        } catch (EmptyStackException e) {
            // Do nothing;
        }
    }
    
    public void reset() {
        setDef(new LevelSpec());
    }
    
    public void setDef(LevelSpec newDef) {
        final LevelSpec oldDef = def;
        undoStack.push(new Undo() {
            public void undo() {   
                def = oldDef;
            }
        });
        def = newDef;
        BallsEditorCanvas.this.repaint();
    }
    
    public LevelSpec getCurrent() {
        return def;
    }
    
    private class BallPlaceListener extends MouseClickListener {
        public void mouseClicked(MouseEvent arg0) {
            final float prevX = def.ballX;
            final float prevY = def.ballY;
            undoStack.add(new Undo() {
                public void undo() {
                    def.ballX = prevX;
                    def.ballY = prevY;
                }
            });
            float x = arg0.getX()-BORDER;
            float y = arg0.getY()-BORDER;
            if (x > Constants.CAMERA_WIDTH - Constants.BALL_RADIUS-2) {
                x = Constants.CAMERA_WIDTH - Constants.BALL_RADIUS-2;
            }
            if (y > Constants.CAMERA_HEIGHT - Constants.BALL_RADIUS-2) {
                y = Constants.CAMERA_HEIGHT - Constants.BALL_RADIUS-2;
            }
            if (x < Constants.BALL_RADIUS) {
                x = Constants.BALL_RADIUS;
            }
            if (y < Constants.BALL_RADIUS) {
                y = Constants.BALL_RADIUS;
            }
            def.ballX = x;
            def.ballY = y;
            BallsEditorCanvas.this.repaint();
        }     
    }
    
    private class BucketPlaceListener extends MouseClickListener {
        public void mouseClicked(MouseEvent arg0) {
            final float prevX = def.bucketX;
            final float prevY = def.bucketY;
            undoStack.add(new Undo() {
                public void undo() {
                    def.bucketX = prevX;
                    def.bucketY = prevY;
                }
            });
            float x = arg0.getX()-BORDER;
            float y = arg0.getY()-BORDER;
            if (x > Constants.CAMERA_WIDTH-2 - Constants.BUCKET_WIDTH_DEFAULT/2) {
                x = Constants.CAMERA_WIDTH-2 - Constants.BUCKET_WIDTH_DEFAULT/2;
            }
            if (y > Constants.CAMERA_HEIGHT-2 - Constants.BUCKET_HEIGHT_DEFAULT/2) {
                y = Constants.CAMERA_HEIGHT-2 - Constants.BUCKET_HEIGHT_DEFAULT/2;
            }
            if (x < Constants.BUCKET_WIDTH_DEFAULT/2) {
                x = Constants.BUCKET_WIDTH_DEFAULT/2;
            }
            if (y < Constants.BUCKET_HEIGHT_DEFAULT/2) {
                y = Constants.BUCKET_HEIGHT_DEFAULT/2;
            }
            def.bucketX = x;
            def.bucketY = y;
            BallsEditorCanvas.this.repaint();
        }  
    }
    
    private class LineDrawBeginListener extends MouseClickListener {
        public void mouseClicked(MouseEvent arg0) {
            float x = arg0.getX() - BORDER;
            float y = arg0.getY() - BORDER;
            if (x > 0 && x <= Constants.CAMERA_WIDTH && y > 0 && y < Constants.CAMERA_HEIGHT) {
                removeMouseListener(this);
                LineDrawListener nextListener =new LineDrawListener(arg0.getX()-BORDER, arg0.getY()-BORDER);
                addMouseListener(nextListener);
                addMouseMotionListener(nextListener);
                BallsEditorCanvas.this.repaint();
            }
        }   
    }
    
    private class LineDrawListener extends MouseClickListener implements MouseMotionListener {
        
        private Wall wall;
        
        public LineDrawListener(float x, float y) {
            wall = new Wall(x, y, x, y);
            def.walls.add(wall);
        }
        
        public void mouseClicked(MouseEvent arg0) {
            removeMouseListener(this);
            removeMouseMotionListener(this);
            undoStack.push(new Undo() {
                public void undo() {
                    def.walls.remove(wall);
                }
            });
            addMouseListener(new LineDrawBeginListener());
            BallsEditorCanvas.this.repaint();
        }
        
        public void mouseMoved(MouseEvent arg0) {
            float x = arg0.getX()-BORDER;
            float y = arg0.getY()-BORDER;
            if (x < 0) {
                x = 0;
            }
            if (y < 0) {
                y = 0;
            }
            if (x > Constants.CAMERA_WIDTH) {
                x = Constants.CAMERA_WIDTH;
            }
            if (y > Constants.CAMERA_HEIGHT){
                y = Constants.CAMERA_HEIGHT;
            }
            wall.x2 = x;
            wall.y2 = y;
            BallsEditorCanvas.this.repaint();
        }
        
        public void mouseDragged(MouseEvent arg0) {}        
        public void mouseEntered(MouseEvent arg0) {}
        public void mouseExited(MouseEvent arg0) {}
        public void mousePressed(MouseEvent arg0) {}
        public void mouseReleased(MouseEvent arg0) {}        
    }
    
}