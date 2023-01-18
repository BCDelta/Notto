import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class waifuBackground extends JTextArea {
    
    Image image;
    JTextArea textArea;
    private int x, y, width, height;
    private float alpha;

    public waifuBackground() {
        super();
        alpha = 0.6f;
    }

    public waifuBackground(String text) {
        super(text);
    }

    public void setImageBackground(ImageIcon icon) {
        this.image = icon.getImage();

        setOpaque(false);

        repaint();
    }
    public void paint(Graphics g) {
        
        Graphics2D g2D = (Graphics2D) g;
        Composite old = g2D.getComposite();

        AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        g2D.setComposite(composite);
        
        g2D.drawImage(image, x, y, width, height, null);

        g2D.setComposite(old);
        super.paint(g2D);
    }

    public void setImageBackground(ImageIcon icon, int x, int y, int width, int height) {
        this.image = icon.getImage();

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        setOpaque(false);

        repaint();
    }
}
