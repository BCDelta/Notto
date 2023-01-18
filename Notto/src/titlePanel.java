import javax.swing.*;   //Needed for the swing class
import java.awt.*;      //Needed for the border layout
/**
 * Title panel that has the title for the titleGUI
 */
public class titlePanel extends JPanel {

    //Variables to be used to fill the title screen
    private JLabel title, subtitle, imageLabel; 

    /**
     * Constructor for the class. Builds the title panel
     */
    public titlePanel() {

        //Set the panel to be a border layout
        setLayout(new BorderLayout());


        //Setting the values for the title
        title = new JLabel("ノート", SwingConstants.CENTER);
        title.setFont(new Font("Helvetica", Font.BOLD, 55));

        //Setting the values for the subtitle
        subtitle = new JLabel("An enhanced version of the Notepad program", SwingConstants.CENTER);
        subtitle.setFont(new Font("Helvetica", Font.ITALIC, 15));

        //Uses an image to be displayed in the title screen 
        ImageIcon image = new ImageIcon("Update_Notto.png");
        imageLabel = new JLabel(image);

        //Add all contents to the panel
        add(title, BorderLayout.NORTH);
        add(imageLabel, BorderLayout.CENTER);
        add(subtitle, BorderLayout.SOUTH);

    }
}
