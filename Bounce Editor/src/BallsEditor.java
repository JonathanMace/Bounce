import im.mace.android.bounce.common.LevelSpec;
import im.mace.android.bounce.io.LevelSpecHandler;

import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;


public class BallsEditor implements WindowListener {
    
    private JFrame controlPanel;
    private JFrame mainDisplay;
    private BallsEditorCanvas canvas;

    public void show() {
        createAndShowDisplay();
        createAndShowButtons();
    }
    
    private void createAndShowDisplay() {
        mainDisplay = new JFrame();
        mainDisplay.addWindowListener(this);
        
        canvas = new BallsEditorCanvas();
        mainDisplay.add(canvas);

        mainDisplay.getContentPane().setPreferredSize(canvas.getPreferredSize());
        mainDisplay.setLocation(300, 300);
        mainDisplay.pack();
        mainDisplay.setVisible(true);
        
    }
    
    private void createAndShowButtons() {
        controlPanel = new JFrame();
        controlPanel.addWindowListener(this);
        
        JPanel buttons = new JPanel();
        controlPanel.add(buttons);

        JButton save = new JButton("Save");
        JButton load = new JButton("Load");
        JButton ballMode = new JButton("Ball");
        JButton bucketMode = new JButton("Bucket");
        JButton lineMode = new JButton("Wall");
        JButton undo = new JButton("Undo");
        JButton reset = new JButton("Reset");

        buttons.add(ballMode);
        buttons.add(bucketMode);
        buttons.add(lineMode);
        buttons.add(undo);
        buttons.add(reset);
        buttons.add(save);
        buttons.add(load);
        
        save.addMouseListener(new MouseClickListener() {
            public void mouseClicked(MouseEvent arg0) {
                saveCurrent(LevelDefXMLWriter.writeXML(canvas.getCurrent()));
            }
        });
        
        load.addMouseListener(new MouseClickListener() {
            public void mouseClicked(MouseEvent arg0) {
                canvas.setDef(loadCurrent());
            }
        });
        
        ballMode.addMouseListener(new MouseClickListener() {
            public void mouseClicked(MouseEvent arg0) {
                canvas.setBallPlaceMode();
            }
        });

        bucketMode.addMouseListener(new MouseClickListener() {
            public void mouseClicked(MouseEvent arg0) {
                canvas.setBucketPlaceMode();
            }
        });
        
        lineMode.addMouseListener(new MouseClickListener() {
            public void mouseClicked(MouseEvent arg0) {
                canvas.setLineDrawMode();
            }
        });
        
        undo.addMouseListener(new MouseClickListener() {
            public void mouseClicked(MouseEvent arg0) {
                canvas.undo();
            }
        });
        
        reset.addMouseListener(new MouseClickListener() {
            public void mouseClicked(MouseEvent arg0) {
                canvas.reset();
            }
        });
        
        
        
        controlPanel.setLocation(1200, 300);
        controlPanel.pack();
        controlPanel.setVisible(true);
    }
    
    private LevelSpec loadCurrent() {
        LevelSpec def = new LevelSpec();
        try {
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            File file = new File("E:\\Git Repositories\\Bounce\\Bounce\\assets\\levels\\simple\\0.xml");
            LevelSpecHandler handler = new LevelSpecHandler();
            parser.parse(file, handler);
            return handler.getLevelDef();      
        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return def;
    }
    
    private void saveCurrent(String xml) {
        try{
            // Create file 
            FileWriter fstream = new FileWriter("E:\\Git Repositories\\Bounce\\Bounce\\assets\\levels\\simple\\0.xml");
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(xml);
            //Close the output stream
            out.close();
        }catch (Exception e){//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }

    }
    
    private void quit() {
        System.exit(0);
    }
    
    public static void main(String[] args) {
        new BallsEditor().show();
    }
    
    @Override
    public void windowClosing(WindowEvent arg0) {
        quit();
    }

    @Override
    public void windowActivated(WindowEvent arg0) {}
    public void windowClosed(WindowEvent arg0) {}
    public void windowDeactivated(WindowEvent arg0) {}
    public void windowDeiconified(WindowEvent arg0) {}
    public void windowIconified(WindowEvent arg0) {}
    public void windowOpened(WindowEvent arg0) {}

}
