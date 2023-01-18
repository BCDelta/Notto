import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;

import java.awt.*;                          //Needed for the layout manager
import java.awt.event.*;                    //Needed for event handling

public class fontGUI extends JFrame {
    
    private JFrame window;
    private fontDisplay fontPanel;
    private fontSize sizePanel;
    private fontStyle fontTypes;
    private JTextField textPreview;

    private final int WINDOW_WIDTH = 250;
    private final int WINDOW_HEIGHT = 200;

    private JPanel fontChooser, buttonPreview, textPreviewPanel;

    public fontGUI(JTextArea blankArea) {


       //Create the JFrame object
       window = new JFrame();
       window.setMinimumSize((new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT)));

       fontChooser = new JPanel();
       buttonPreview = new JPanel();
       textPreviewPanel = new JPanel();

       //Set the layout to Grid layout
       window.setLayout(new BorderLayout());

       //Setting the window's title
       window.setTitle("Font Chooser");

       //What would happen when the "X" button on top right corner is pressed
       window.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

       //Create titlePanel object
       fontPanel = new fontDisplay();
       sizePanel = new fontSize();
       fontTypes = new fontStyle();

       textPreview = new JTextField(10);

       textPreview.setBackground(Color.WHITE);
       textPreview.setEditable(false);
       textPreview.setText("Hello World");
       textPreview.setHorizontalAlignment(JTextField.CENTER);

       JButton confirm = new JButton("OK");
       confirm.addActionListener(new ButtonListener());


        fontChooser.add(fontPanel);
        fontChooser.add(sizePanel);
        fontChooser.add(fontTypes);

        buttonPreview.setLayout(new FlowLayout());
        textPreviewPanel.add(textPreview);
        buttonPreview.add(confirm);

        window.add(fontChooser, BorderLayout.NORTH);
        window.add(textPreviewPanel, BorderLayout.CENTER);
        window.add(buttonPreview, BorderLayout.SOUTH);

        window.pack();

       //Center the window to the center of the screen
       window.setLocationRelativeTo(null);

       //Make window size fixed
       window.setResizable(true);

       //Make window visible for user
       window.setVisible(true);

       window.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
            blankArea.setFont(new Font(fontPanel.getSelectedFont(), 
            fontTypes.getFontType(), sizePanel.getSelectedFontSize()));

        }

    });

    }

    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            textPreviewPanel.repaint();
            textPreviewPanel.revalidate();

            setTextPreview();

        }
    }

    public void setTextPreview() {

        textPreview.setFont(new Font(fontPanel.getSelectedFont(), 
            fontTypes.getFontType(), sizePanel.getSelectedFontSize()));
    }
/* 
    public void setTextboxGUI(JTextArea blankSpace) {
        this.blankSpace = blankSpace;
    }
*/

}
