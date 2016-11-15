package asgn2GUI;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

import asgn2Codes.ContainerCode;
import asgn2Containers.FreightContainer;
import asgn2Exceptions.ManifestException;
import asgn2Manifests.CargoManifest;

/**
 * The main window for the Cargo Manifest graphics application.
 *
 * @author ZHAO ZHEN TANG(09262555)
 * 
 */
public class CargoFrame extends JFrame {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    private JButton btnLoad;
    private JButton btnUnload;
    private JButton btnFind;
    private JButton btnNewManifest;

    private CargoCanvas canvas;

    private JPanel pnlControls;
    private JPanel pnlDisplay;

    private CargoManifest cargo;
    private FreightContainer[] stack;
    

    /**
     * Constructs the GUI.
     *
     * @param title The frame title to use.
     * @throws HeadlessException from JFrame.
     */
    public CargoFrame(String title) throws HeadlessException {
        super(title);

        constructorHelper();
        this.setLayout(new BorderLayout());
        
        pnlControls=createControlPanel();
        
        pnlDisplay=new JPanel(new GridLayout());   
        
        
        
        this.add(pnlDisplay,BorderLayout.CENTER);
        this.add(pnlControls,BorderLayout.SOUTH);
        disableButtons();
        redraw();
        setResizable(true);
        setVisible(true);
    }

    /**
     * Initialises the container display area.
     *
     * @param cargo The <code>CargoManifest</code> instance containing necessary state for display.
     */
    private void setCanvas(CargoManifest cargo) {
        if (canvas != null) {
            pnlDisplay.remove(canvas);
        }
        if (cargo == null) {
            disableButtons();
        } else {
        	
        	 canvas = new CargoCanvas(cargo);
        	
        	 pnlDisplay.add(canvas);   
        	 
        }
        redraw();
    }

    /**
     * Enables buttons for user interaction.
     */
    private void enableButtons() {
    	//implementation here  
    	btnLoad.setEnabled(true);
    	btnUnload.setEnabled(true);
    	btnFind.setEnabled(true);
    	btnNewManifest.setEnabled(true);
    	
    }

    /**
     * Disables buttons from user interaction.
     */
    private void disableButtons() {
    	//implementation here    
    	btnLoad.setEnabled(false);
    	btnUnload.setEnabled(false);
    	btnFind.setEnabled(false);
    }

    /**
     * Initialises and lays out GUI components.
     */
    private void constructorHelper() {
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        btnLoad = createButton("Load", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Runnable doRun = new Runnable() {
                    @Override
                    public void run() {
                        CargoFrame.this.resetCanvas();
                        CargoFrame.this.doLoad();
                    }
                };
                SwingUtilities.invokeLater(doRun);
            }
        });
        btnUnload = createButton("Unload", new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
            	
            	doUnload();
                
            }
        	//implementation here    
        });
        btnFind = createButton("Find", new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
            	doFind();
            	
            }
        	//implementation here    
        });
        btnNewManifest = createButton("New Manifest", new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	setNewManifest();
               
                if(cargo != null){
    				enableButtons();
    			}
            }
        	//implementation here    
        });

      //implementation here    
        repaint();
    }

    /**
     * Creates a JPanel containing user controls (buttons).
     *
     * @return User control panel.
     */
    private JPanel createControlPanel() {
    	JPanel panel=new JPanel();    	
    	panel.add(btnNewManifest);
    	panel.add(btnLoad);
    	panel.add(btnUnload);
    	panel.add(btnFind);
        return panel;
    }

    /**
     * Factory method to create a JButton and add its ActionListener.
     *
     * @param name The text to display and use as the component's name.
     * @param btnListener The ActionListener to add.
     * @return A named JButton with ActionListener added.
     */
    private JButton createButton(String name, ActionListener btnListener) {
        JButton btn = new JButton(name);
        btn.setName(name);
        btn.addActionListener(btnListener);
        return btn;
    }

    /**
     * Initiate the New Manifest dialog which sets the instance of CargoManifest to work with.
     */
    private void setNewManifest() {
    	//implementation here   
    	cargo = ManifestDialog.showDialog(this);
    	setCanvas(cargo);
    	redraw();
    }

    /**
     * Turns off container highlighting when an action other than Find is initiated.
     */
    private void resetCanvas() {
    	//implementation here   
    	
    }

    /**
     * Initiates the Load Container dialog.
     */
    private void doLoad() {
    	//implementation here 
    	try {
    	    FreightContainer container=LoadContainerDialog.showDialog(this);
    	    if(container!=null){
			cargo.loadContainer(container);
    	    }
			
		} catch (ManifestException e) {			 
		    JOptionPane.showMessageDialog(this,"An Error Occured:" +e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}
    	setCanvas(cargo);
       
    	//Don't forget to redraw
    	redraw();
    	
    }

    private void doUnload() {    	 
    	Integer check =0;    	
    	try {
    	    ContainerCode code=ContainerCodeDialog.showDialog(this);
            // if cancel button has been clicked, null will be returned 
            if(code!=null){
            cargo.unloadContainer(code);
            }            
			check =cargo.whichStack(code);			
			
		} catch (ManifestException e) {
		    JOptionPane.showMessageDialog(this,"An Error Occured:" +e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);			
		}    	
    	setCanvas(cargo);
    	redraw();
    	
    }

    private void doFind() {    	 
        ContainerCode code=ContainerCodeDialog.showDialog(this);
        if(code==null){
            JOptionPane.showMessageDialog(this,"Cannot find the container","Infomation",JOptionPane.INFORMATION_MESSAGE);
        }else{
        canvas.setToFind(code);
        redraw();
        }
    }

    private void redraw() {
        invalidate();
        validate();
        repaint();
    }
}