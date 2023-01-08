import javax.swing.JFrame;

//This class is responsible for creating a window inside of which the game is displayed.
@SuppressWarnings("serial")
public class PacmanFrame extends JFrame{
	
	PacmanFrame() 
	{
		this.add(new PacmanPanel()); //Add the JPanel PacmanPanel which displays the game
		this.setTitle("Pacman"); //Title of the window
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Exit the application when the window is closed
		this.setResizable(false); //Window is not resizable. Simulation relies on JPanel dimensions being divisible by particle size
		this.pack(); //JFrame will encompass all the components that are added to the frame
		this.setVisible(true); //Make the window visible
		this.setLocationRelativeTo(null); //Place JFrame in the centre of the screen	
	}

}
