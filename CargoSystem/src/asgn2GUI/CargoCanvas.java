package asgn2GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import asgn2Codes.ContainerCode;
import asgn2Containers.DangerousGoodsContainer;
import asgn2Containers.FreightContainer;
import asgn2Containers.GeneralGoodsContainer;
import asgn2Containers.RefrigeratedContainer;
import asgn2Exceptions.ManifestException;
import asgn2Manifests.CargoManifest;

/**
 * Creates a JPanel in which graphical components are laid out to represent the
 * cargo manifest.
 * 
 * @author ZHAO ZHEN TANG(09262555)
 */
public class CargoCanvas extends JPanel {

    private static final int WIDTH = 120;
    private static final int HEIGHT = 50;
    private static final int HSPACE = 10;
    private static final int VSPACE = 20;

    public static final int EMPTY = -1;
    public static final int GENERAL = 0;
    public static final int DANGEROUS = 1;
    public static final int REFRIEGERATED = 2;

    public int figure = 0;

    private final CargoManifest cargo;

    private FreightContainer[] container,Findcontainer;
  
    private Integer x, y,noOfStack;
    private ContainerCode toFind;

    /**
     * Constructor
     * 
     * @param cargo
     *            The <code>CargoManifest</code> on which the graphics is based
     *            so that the number of stacks and height can be adhered to.
     */
    public CargoCanvas(CargoManifest cargo) {
        this.cargo = cargo;
        setName("Canvas");
        setSize(WIDTH, HEIGHT);
        this.setPreferredSize(new Dimension(HEIGHT, WIDTH));
        
       
       
    }

    /**
     * Highlights a container.
     * 
     * @param code
     *            ContainerCode to highlight.
     */
    public void setToFind(ContainerCode code) {

       
    	
    	try {
    		 // implementation here - don't forget to repaint
        	int locatedStack = cargo.whichStack(code);
        	int locatedHigh = cargo.howHigh(code);
        	
			Findcontainer = cargo.toArray(locatedStack);
			
			toFind = Findcontainer[locatedHigh].getCode();
			
	        if(toFind==null){
	        JOptionPane.showMessageDialog(this,"Cannot find the container","Infomation",JOptionPane.INFORMATION_MESSAGE);
	        }else{
	            repaint();    
	        }
		} catch (ManifestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
        
    }

    /**
     * Draws the containers in the cargo manifest on the Graphics context of the
     * Canvas.
     * 
     * @param g
     *            The Graphics context to draw on.
     */
    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);      
        try {
        	noOfStack=0;
        	do{
				container = cargo.toArray(noOfStack);
				noOfStack++;
        	 }while(container.toString()!=null);
        } catch (ManifestException e) {}    
        try {
        	
            
        	
            x=0;
            y=0;
            for (int i = 0; i < noOfStack; i++) {
            	
                x = 0;
                container = cargo.toArray(i);
                drawContainer(g, null, x, y);
                if (container.length >= 1) {
                    for (int j = 0; j < container.length; j++) {

                        if (toFind == container[j].getCode()) {
                            g.setColor(Color.GREEN);
                            g.drawRect(20 + x, 20 + y, WIDTH, HEIGHT);
                            g.fillRect(20 + x, 20 + y, WIDTH, HEIGHT);
                            g.setColor(Color.WHITE);
                            g.drawString(container[j].getCode().toString(),
                                    40 + x, 50 + y);
                        } else {
                            drawContainer(g, container[j], x, y);

                        }
                        x += HSPACE + WIDTH;

                    }

                }
                y += VSPACE + HEIGHT;
            }

        } catch (ManifestException e) {       
            JOptionPane.showMessageDialog(this,"An Error Occured:" +e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     * Draws a container at the given location.
     * 
     * @param g
     *            The Graphics context to draw on.
     * @param container
     *            The container to draw - the type determines the colour and
     *            ContainerCode is used to identify the drawn Rectangle.
     * @param x
     *            The x location for the Rectangle.
     * @param y
     *            The y location for the Rectangle.
     */
    private void drawContainer(Graphics g, FreightContainer container, int x,
            int y) {
        if (container != null) {
            if (container.getClass() == GeneralGoodsContainer.class) {
                g.setColor(Color.GRAY);
                g.drawRect(20 + x, 20 + y, WIDTH, HEIGHT);
                g.fillRect(20 + x, 20 + y, WIDTH, HEIGHT);
                g.setColor(Color.WHITE);
                g.drawString(container.getCode().toString(), 40 + x, 50 + y);
            } else if (container.getClass() == DangerousGoodsContainer.class) {
                g.setColor(Color.RED);
                g.drawRect(20 + x, 20 + y, WIDTH, HEIGHT);
                g.fillRect(20 + x, 20 + y, WIDTH, HEIGHT);
                g.setColor(Color.WHITE);
                g.drawString(container.getCode().toString(), 40 + x, 50 + y);
            } else if (container.getClass() == RefrigeratedContainer.class) {

                g.setColor(Color.BLUE);
                g.drawRect(20 + x, 20 + y, WIDTH, HEIGHT);
                g.fillRect(20 + x, 20 + y, WIDTH, HEIGHT);
                g.setColor(Color.WHITE);
                g.drawString(container.getCode().toString(), 40 + x, 50 + y);
            } else {
                g.setColor(Color.BLACK);
                g.drawLine(10, 20 + y, 10, 70 + y);
            }
        } else {
            g.setColor(Color.BLACK);
            g.drawLine(10, 20 + y, 10, 70 + y);
        }

    }
}
