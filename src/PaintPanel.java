import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

public class PaintPanel extends JPanel {
    private  int brushFormWidth = 1;
    private Color brushColor = Color.BLACK;
    private int previousX, previousY;
    private BufferedImage image;
    private Graphics2D graphics;
    public PaintPanel(){
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        addMouseListener(new MouseCoordinateReader());
        addMouseMotionListener(new PaintClass());
        graphics=(Graphics2D)getGraphics();
    }

    public Color getBrushColor() {
        return brushColor;
    }

    public void setBrushFormWidth(int brushFormWidth) {
        this.brushFormWidth = brushFormWidth;
    }

    public void setBrushColor(Color brushColor) {
        this.brushColor = brushColor;
    }

    private class MouseCoordinateReader extends MouseAdapter {

        public void mousePressed(MouseEvent ev) {
            setPreviousCoordinates(ev.getX(), ev.getY());
        }
    }

    private class PaintClass extends MouseMotionAdapter {

        public void mouseDragged(MouseEvent ev) {

            BasicStroke brushForm = new BasicStroke(brushFormWidth, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL);
            graphics.setStroke(brushForm);
            graphics.setColor(brushColor);
            graphics.drawLine(previousX, previousY, ev.getX(), ev.getY());
            setPreviousCoordinates(ev.getX(), ev.getY());
            repaint();
        }
    }

    public void setPreviousCoordinates(int aPrevX, int aPrevY) {
        previousX = aPrevX;
        previousY = aPrevY;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (image == null) {
            // image to draw null ==> we create
            image =(BufferedImage) createImage(getSize().width, getSize().height);
            graphics = (Graphics2D) image.getGraphics();
            // enable antialiasing
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // clear draw area
            clear();
        }
        g.drawImage(image, 0, 0, null);
    }
    // now we create exposed methods
    public void clear() {
        graphics.setPaint(Color.white);
        // draw white on entire draw area to clear
        graphics.fillRect(0, 0, getSize().width, getSize().height);
        graphics.setPaint(Color.black);
        repaint();
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }
}

