package asgn2GUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


//import asgn2Exceptions.CargoException;
import asgn2Exceptions.ManifestException;
import asgn2Manifests.CargoManifest;

/**
 * Creates a dialog box allowing the user to enter parameters for a new <code>CargoManifest</code>.
 *
 * @author ZHAO ZHEN TANG(09262555)
 */
public class ManifestDialog extends AbstractDialog {

   
	private static final long serialVersionUID = 1L;
	private static final int HEIGHT = 150;
    private static final int WIDTH = 250;

    private JTextField txtNumStacks;
    private JTextField txtMaxHeight;
    private JTextField txtMaxWeight;

    private static CargoManifest manifest;
    
  

    /**
     * Constructs a modal dialog box that gathers information required for creating a cargo
     * manifest.
     *
     * @param parent the frame which created this dialog box.
     */
    ManifestDialog(JFrame parent) {
        super(parent, "Create Manifest", WIDTH, HEIGHT);
        setName("New Manifest");
        setResizable(false);
        manifest = null;
    }

    /**
     * @see AbstractDialog.createContentPanel()
     */
    @Override
    protected JPanel createContentPanel() {

        txtNumStacks = createTextField(8, "Number of Stacks");
        txtMaxHeight = createTextField(8, "Maximum Height");
        txtMaxWeight = createTextField(8, "Maximum Weight");

        JPanel toReturn = new JPanel();
        toReturn.setLayout(new GridBagLayout());

        //Implementation here
        //Create label
        JLabel lblNumStack = new JLabel("NumStacks");
        JLabel lblMaxHeight = new JLabel("MaxHeight");
        JLabel lblMaxWeight = new JLabel("MaxWeight");
        
        //add components to grid
        GridBagConstraints constraints = new GridBagConstraints(); 
	   
        
	   	//Defaults
	    constraints.fill = GridBagConstraints.NONE;
	    constraints.anchor = GridBagConstraints.CENTER;
	    constraints.weightx = 100;
	    constraints.weighty = 100;
	    
	    //Set component location in panel
	    addToPanel(toReturn, lblNumStack,constraints,0,0,2,1); 
	    addToPanel(toReturn, txtNumStacks,constraints,2,0,2,1);
	    
	    addToPanel(toReturn, lblMaxHeight,constraints,0,2,2,1); 
	    addToPanel(toReturn,txtMaxHeight,constraints,2,2,2,1); 
	    
	    addToPanel(toReturn, lblMaxWeight,constraints,0,4,2,1); 
	    addToPanel(toReturn, txtMaxWeight,constraints,2,4,2,1); 
	    
	    //Return Panel
        return toReturn;
    }

    /*
     * Factory method to create a named JTextField
     */
    private JTextField createTextField(int numColumns, String name) {
        JTextField text = new JTextField();
        text.setColumns(numColumns);
        text.setName(name);
        return text;
    }

    @Override
    protected boolean dialogDone() {
        //Implementation here 
    	 Integer numStack =0;
    	 Integer  maxHeight =0;
    	 Integer maxWeight =0;
    	
    	 
    	try{
    		numStack = Integer.parseInt(txtNumStacks.getText());
        	maxHeight=Integer.parseInt(txtMaxHeight.getText());
        	maxWeight=Integer.parseInt(txtMaxWeight.getText());
        	manifest = new CargoManifest(numStack,maxHeight,maxWeight);
        	
    	} catch (ManifestException e) {			
			JOptionPane.showMessageDialog(rootPane,"An Error Occured:" +e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
			return false;
		}catch (NumberFormatException e){
			JOptionPane.showMessageDialog(rootPane,"An Error Occured:" +e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
			return false;
		}

    	return true;
    }

    /**
     * Shows the <code>ManifestDialog</code> for user interaction.
     *
     * @param parent - The parent <code>JFrame</code> which created this dialog box.
     * @return a <code>CargoManifest</code> instance with valid values.
     */
    public static CargoManifest showDialog(JFrame parent) {
    	
    	ManifestDialog test = new ManifestDialog(parent);
    	test.setVisible(true);
    	
    	//Implementation again
        return manifest;
    	
    		
    	
       
        
    }
}
