import javax.swing.*;   //Needed for JPanel
import java.awt.event.*;//Needed for event handling
import java.awt.*;      //Needed for flow layout

public class fontStyle extends JPanel {

    private JComboBox fontStyles;
    private int selectedFontType;
    private JLabel fontStylesLabel;

    public fontStyle() {

        setLayout(new FlowLayout());

        String[] fontStylesArray = {"Regular", "Bold", "Italics"};

        fontStylesLabel = new JLabel("Font Type: ");
        fontStyles = new JComboBox<>(fontStylesArray);

        //Set initial value to font size 10;
        fontStyles.setSelectedItem(fontStylesArray[0]);
        selectedFontType = 0;

        
        fontStyles.addActionListener(new ComboBoxListener());

        add(fontStylesLabel);
        add(fontStyles);
    }

    private class ComboBoxListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            selectedFontType = fontStyles.getSelectedIndex();
        }
    }

    public int getFontType() {
        return selectedFontType;
    }
}
