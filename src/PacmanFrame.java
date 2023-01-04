import java.awt.Dimension;

import javax.swing.JFrame;

public class PacmanFrame extends JFrame{
	
	PacmanFrame() {

		//this.setPreferredSize(new Dimension(1280, 720));
		this.add(new PacmanPanel());
		this.setTitle("Pacman");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack(); //JFrame will encompass all the components that are added to the frame.
		this.setVisible(true);
		this.setLocationRelativeTo(null); //Place JFrame in the centre of the screen.
		
	}

}
