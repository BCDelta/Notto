import java.awt.event.*;                    //Needed for the event handling
import javax.swing.*;                       //Needed for the swing class
import java.awt.*;                          //Needed for the border layout
/**
 * Title panel for the Notto text-editor program.
 */
public class titleGUI extends JFrame {

    private JFrame window;                            //Needed to create the title screen
    private final int WINDOW_WIDTH = 450;             //Width for title screen
    private final int WINDOW_HEIGHT = 300;            //Height for title screen

    //Buttons needed
    private JButton enterButton, exitButton;

    //Panels needed
    private JPanel buttonPanel;

    private titlePanel title;
    private textboxGUI notepad;


    /**
     * Constructs the title GUI for the program
     */
    public titleGUI() {  
        //Create the JFrame object
        window = new JFrame();

        //Set the layout to border layout
        window.setLayout(new BorderLayout());

        //Setting the window's title
        window.setTitle("Welcome to Notto!");

        //Setting the window's dimensions
        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        //What would happen when the "X" button on top right corner is pressed
        window.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Build button panel
        buildButtonPanel();

        //Create titlePanel object
        title = new titlePanel();

        //Add button panel to the title screen
        window.add(title, BorderLayout.NORTH, SwingConstants.CENTER);
        window.add(buttonPanel, BorderLayout.SOUTH);

        //Center the window to the center of the screen
        window.setLocationRelativeTo(null);

        //Set icon image to the logo
        window.setIconImage(new ImageIcon(ClassLoader.getSystemResource(
            "Update_Notto.png")).getImage());

        //Make window size fixed
        window.setResizable(false);

        //Make window visible for user
        window.setVisible(true);
    }

    /**
     * Method that builds the button panel for use
     */
    public void buildButtonPanel() {
        //Create new panel
        buttonPanel = new JPanel();
        
        //Creating enter button
        enterButton = new JButton("Continue");
        enterButton.setFont(new Font("Arial", Font.BOLD, 15));
        enterButton.addActionListener(new enterButtonListener());

        //Creating exit button
        exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.BOLD, 15));
        exitButton.addActionListener(new exitButtonListener());
        
        //Adding the buttons to panel
        buttonPanel.add(enterButton);
        buttonPanel.add(exitButton);
    }
    /**
     * Inner class that handles the enterButton event
     */
    private class enterButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //Open the textbox window
            notepad = new textboxGUI();

            //Dispose of the title window
            window.dispose();
        }
    }
    /**
     * Inner class that handles the exitButton event
     */
    private class exitButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //End the program
            System.exit(0);
        }
    }
}

