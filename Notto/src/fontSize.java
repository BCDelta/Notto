import javax.swing.*;   //Needed for JPanel
import java.awt.event.*;//Needed for event handling
import java.awt.*;      //Needed for flow layout


public class fontSize extends JPanel {

    private JLabel fontSize;
    private JComboBox sizes;
    private int selectedFontSize;
    private String selection;


    public fontSize() {

        setLayout(new FlowLayout());

        String[] fontSizes = {"2", "4", "6", "8", "10", "12", "14",
                             "16", "18", "20", "24", "36", "48"};

        fontSize = new JLabel("Size: ");
        sizes = new JComboBox<>(fontSizes);
        sizes.setEditable(true);
        
        //Set initial value to font size 10;
        sizes.setSelectedItem(fontSizes[4]);
        selectedFontSize = Integer.parseInt(fontSizes[4]);

        sizes.addActionListener(new ComboBoxListener());

        add(fontSize);
        add(sizes);
    }

    private class ComboBoxListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            selection = (String) sizes.getSelectedItem();
            selectedFontSize = Integer.parseInt(selection);
        }
    }

    public int getSelectedFontSize() {
        return selectedFontSize;
    }
    
}
