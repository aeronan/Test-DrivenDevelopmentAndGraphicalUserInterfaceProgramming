/*
* This file forms part of the CargoSystem Project
* Assignment Two �C CAB302 2015
*
*/

package asgn2GUI;

import java.awt.BorderLayout;
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
 * The main window for the Cargo Manifest Text application.
 *
 * @author ZHAO ZHEN TANG(09262555)
 */
public class CargoTextFrame extends JFrame {

   
	private static final int WIDTH = 600;
    private static final int HEIGHT = 400;

    private JButton btnLoad;
    private JButton btnUnload;
    private JButton btnFind;
    private JButton btnNewManifest;

    private CargoTextArea canvas;

    private JPanel pnlControls;
    private JPanel pnlDisplay;

    private CargoManifest cargo;

    

    /**
     * Constructs the GUI.
     *
     * @param title The frame title to use.
     * @throws HeadlessException from JFrame.
     */
    public CargoTextFrame(String frameTitle) throws HeadlessException {
        super(frameTitle);
        constructorHelper();
        this.setLayout(new BorderLayout());
        pnlControls=createControlPanel();
        
        pnlDisplay=new JPanel(new GridLayout());   
        
        //Use JScrollPane to make pnlDisplay can Autosize
        JScrollPane jsp = new JScrollPane(pnlDisplay);
        jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        canvas=new CargoTextArea(cargo);
        
        pnlDisplay.add(canvas);
        canvas.updateDisplay();        
        this.add(pnlDisplay,BorderLayout.CENTER);
        this.add(pnlControls,BorderLayout.SOUTH);
        disableButtons();        
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
            canvas = new CargoTextArea(cargo);
            //implementation here 
            pnlDisplay.add(canvas);
            canvas.updateDisplay();  
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
                        CargoTextFrame.this.resetCanvas();
                        CargoTextFrame.this.doLoad();
                    }
                };
                SwingUtilities.invokeLater(doRun);
            }
        });
        btnUnload = createButton("Unload", new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                doUnload();
                
            }
        	//implementation here 
        });
        btnFind = createButton("Find", new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {               
            	doFind();
            }
        	//implementation here 
        });
        btnNewManifest = createButton("New Manifest", new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	Runnable doRun = new Runnable() {
            		public void run() {
            			setNewManifest();
            			if(cargo != null){
            				enableButtons();
            			}
            		}
            	};
            	 SwingUtilities.invokeLater(doRun);
            }         
        });       
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
    	cargo = ManifestDialog.showDialog(this);
    	setCanvas(cargo);
    	redraw();
    }

    /**
     * Turns off container highlighting when an action other than Find is initiated.
     */
    private void resetCanvas() {    	 
    	 canvas=new CargoTextArea(cargo);
         canvas.updateDisplay();     
         redraw();
         
    }

    /**
     * Initiates the Load Container dialog.
     */
    private void doLoad() {    	 
    	try {
    	    FreightContainer container=LoadContainerDialog.showDialog(this);
            if(container!=null){
            cargo.loadContainer(container);
            }
		
		} catch (ManifestException e) {
		    JOptionPane.showMessageDialog(this,"An Error Occured:" +e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}
    	setCanvas(cargo);      
    	
    	redraw();
    	
    }

    /**
     * Initiates the Unload Container dialog.
     */
    private void doUnload() {    		
    	try {
    	    ContainerCode code=ContainerCodeDialog.showDialog(this);
    	    // if cancel button has been clicked, null will be returned 
    	    if(code!=null){
    		cargo.unloadContainer(code);
    	    }
		} catch (ManifestException e) {			
		    JOptionPane.showMessageDialog(this,"An Error Occured:" +e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}
    	
    	setCanvas(cargo);
    	redraw();
    }

    /**
     * Initiates the Find Container dialog.
     */
    private void doFind() {
        ContainerCode code=ContainerCodeDialog.showDialog(this);
        if(code==null){
            JOptionPane.showMessageDialog(this,"Cannot find the container","Infomation",JOptionPane.INFORMATION_MESSAGE);
        }else{
    	canvas.setToFind(code);
    	redraw();
        }
    }

    /**
     * 
     * Updates the display area.
     *
     */
    private void redraw() {
    	pnlDisplay.removeAll();
    	pnlDisplay.updateUI();
    	pnlDisplay.add(canvas);   	
    	
    }
}
