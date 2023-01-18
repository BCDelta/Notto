import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class textPreviewPanel extends JPanel {

    private JTextField textPreview;
    private fontDisplay fontPanel;
    private fontSize sizePanel;
    private fontStyle fontTypes;

    public textPreviewPanel() {

        fontPanel = new fontDisplay();
        sizePanel = new fontSize();
        fontTypes = new fontStyle();

       textPreview = new JTextField(10);
       textPreview.setBackground(Color.WHITE);
       textPreview.setEditable(false);
       textPreview.setText("Hello World");
       textPreview.setHorizontalAlignment(JTextField.CENTER);
    }

    public void setTextPreview() {

        textPreview.setFont(new Font(fontPanel.getSelectedFont(), 
            fontTypes.getFontType(), sizePanel.getSelectedFontSize()));
    }
    
}
