package anasazi.applet;

import java.applet.Applet;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import artificialAnasaziReplication.LongHouseValleyWithUI;

public class AnasaziApplet extends Applet {
	//Called when this applet is loaded into the browser.
    public void init() {
        //Execute a job on the event-dispatching thread; creating this applet's GUI.
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                	JButton startButton = new JButton("Start");
                	add(startButton);
                	startButton.setActionCommand("Start simulation");
                	startButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							LongHouseValleyWithUI.main(null);
						}
                    });
                }
            });
        } catch (Exception e) {
            System.err.println("createGUI didn't complete successfully");
        }
    }
}
