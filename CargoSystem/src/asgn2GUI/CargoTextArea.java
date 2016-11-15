package asgn2GUI;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import asgn2Codes.ContainerCode;
import asgn2Manifests.CargoManifest;

/**
 * Creates a JTextArea in which textual components are laid out to represent the
 * cargo manifest.
 * 
 * @author Aeron (08667501)
 */
public class CargoTextArea extends JTextArea {

    private static final long serialVersionUID = 1L;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 370;
   

    private final CargoManifest cargo;

    private ContainerCode toFind;

    /**
     * Constructor initialises the JTextArea.
     * 
     * @param cargo
     *            he <code>CargoManifest</code> on which the text area is based
     * 
     */
    public CargoTextArea(CargoManifest cargo) {
        setFont(new Font("Calibri", Font.PLAIN, 12));
        setName("Cargo Text Area");
        setSize(WIDTH, HEIGHT);
        setEditable(false);

        this.setPreferredSize(new Dimension(HEIGHT, WIDTH));
        this.cargo = cargo;
    }

    /**
     * Highlights a container.
     * 
     * @param code
     *            ContainerCode to highlight.
     */
    public void setToFind(ContainerCode code) {
        // implementation here - don't forget to update the display
        toFind = code;
        String text=cargo.toString(toFind);
        if (text == null|| text.isEmpty()|| text.equals("||  ||\n||  ||\n||  ||\n")) {
            JOptionPane.showMessageDialog(this, "Cannot find the container",
                    "Infomation", JOptionPane.INFORMATION_MESSAGE);
        } else {
            this.setText(text);
        }
        
        

    }

    /**
     * Outputs the container representation from the cargo manifest on the text
     * area.
     * 
     */
    public void updateDisplay() {
        // System.out.println("Update Display Try 1");
        if (cargo != null) {
            System.out.println(cargo.toString());
            this.setText(cargo.toString());
        } else {
            // System.out.println("Still Null");
            this.setText("|| ||\n|| ||\n|| ||\n");
        }
    }
}
