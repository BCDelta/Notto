import javax.swing.*;   //Needed for JPanel
import java.awt.*;      //Needed for Font object

public class fontPreview extends JPanel {
    
    private JTextField textPreview;
    private fontDisplay fontPanel;
    private fontSize sizePanel;
    private fontStyle fontTypes;

    public fontPreview() {

        textPreview = new JTextField(20);
        textPreview.setEditable(false);
        textPreview.setText("Hello World");
        fontPanel = new fontDisplay();
        sizePanel = new fontSize();
        fontTypes = new fontStyle();

        setTextPreview();

        add(textPreview);
    }

    public void setTextPreview() {
        textPreview.setFont(new Font(fontPanel.getSelectedFont(), 
            fontTypes.getFontType(), sizePanel.getSelectedFontSize()));
    }
}
