import javax.swing.*;   //Needed for JPanel
import java.awt.event.*;//Needed for event handling
import java.io.*;       //Needed for the file object
import java.util.*;     //Needed for the scanner object
import java.awt.*; 

public class fontDisplay extends JPanel {

    private JLabel fontLabel;
    private JComboBox fonts;
    private String selectedFont;

    public fontDisplay() {
        
        setLayout(new FlowLayout());


        fontLabel = new JLabel("Font: ");
        fonts = new JComboBox<>(getFonts());
        
        fonts.addActionListener(new ComboBoxListener());

        add(fontLabel);
        add(fonts);

    }

    private class ComboBoxListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            selectedFont = (String) fonts.getSelectedItem();
            System.out.println(selectedFont);
        }
    }

    public String[] getFonts() {

        File fontFile = new File("fonts.txt");

        ArrayList<String> fontList = new ArrayList<String>();
        int numberOfFonts = 0;
            try {

                Scanner input = new Scanner(fontFile);
                while(input.hasNext()) {
                    fontList.add(input.nextLine());
                    numberOfFonts++;
                }
            }
            catch (FileNotFoundException exception) {
                System.out.println("FILE WAS NOT FOUND!");
            }

        String[] fontArray = new String[numberOfFonts];
            for(int i = 0; i < fontArray.length; i++) {
                fontArray[i] = (fontList.get(i));

            }
            selectedFont = fontArray[0];

        return fontArray;
    }

    public String getSelectedFont() {
        return selectedFont;
    }
}
