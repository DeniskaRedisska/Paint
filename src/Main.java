import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                //Image img = new ImageIcon("1.png").getImage();
                mySimplePaint msp = new mySimplePaint();
                msp.setSize(500, 500);
                msp.setTitle("My Simple Paint");
                msp.setLocationByPlatform(true);
                msp.setVisible(true);
                msp.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                //msp.setIconImage(img);

            }
        });

    }

    public static class mySimplePaint extends JFrame {

        //TODO gridlayout

        private PaintPanel paintPanel = new PaintPanel();
        private JTextField setBrushWidthValueField = new JTextField();
        private JFileChooser fileChooser = new JFileChooser("C:");

        public mySimplePaint() {

            Container c = getContentPane();

            JPanel btnPanel = new JPanel();
            c.add(btnPanel, BorderLayout.SOUTH);
            paintPanel.setPreferredSize(new Dimension(3000, 3000));
            JScrollPane scrollPane = new JScrollPane(paintPanel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            scrollPane.setSize(1000, 1000);
            c.add(scrollPane);

            setBrushWidthValueField.setColumns(10);
            setBrushWidthValueField.setHorizontalAlignment(JTextField.CENTER);

            JButton selectBrushColorButton = new JButton("Select Color");
            btnPanel.add(selectBrushColorButton);
            JButton selectBrushWidthButton = new JButton("Select Brush Width");
            btnPanel.add(selectBrushWidthButton);
            JButton saveButton = new JButton("Save");
            btnPanel.add(saveButton);
            JButton openButton = new JButton("Open");
            btnPanel.add(openButton);
            btnPanel.add(setBrushWidthValueField);

            selectBrushColorButton.addActionListener(new colorButtonActionListener());
            selectBrushWidthButton.addActionListener(new widthButtonActionListener());
            saveButton.addActionListener(e -> {
                fileChooser.setDialogTitle("save");
                fileChooser.setFileSelectionMode(JFileChooser.OPEN_DIALOG);
                int result = fileChooser.showSaveDialog(this);
                if (result == JFileChooser.APPROVE_OPTION)
                    try {
                        BufferedImage im = (BufferedImage) paintPanel.getImage();
                        ImageIO.write(im, "png", fileChooser.getSelectedFile());
                        JOptionPane.showMessageDialog(this,
                                "" + fileChooser.getSelectedFile()
                        );

                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(this, ex, "Error!",
                                JOptionPane.PLAIN_MESSAGE);
                    }
            });
            openButton.addActionListener(e -> {
                fileChooser.setDialogTitle("");
                fileChooser.setFileSelectionMode(JFileChooser.OPEN_DIALOG);
                int result = fileChooser.showOpenDialog(this);
                if (result == JFileChooser.APPROVE_OPTION)
                    try {
                        BufferedImage image = ImageIO.read(fileChooser.getSelectedFile());
                        paintPanel.setImage(image);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(this, ex, "Error!", JOptionPane.PLAIN_MESSAGE);
                    }
            });
        }

        private class colorButtonActionListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                paintPanel.setBrushColor(JColorChooser.showDialog(((Component) e.getSource()).getParent(),
                        "Select brush color panel", paintPanel.getBrushColor()));
            }
        }

        private class widthButtonActionListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                String str = setBrushWidthValueField.getText();
                boolean b = str.matches("\\d+");
                if (b && str.length() <= 2) {
                    paintPanel.setBrushFormWidth(Integer.parseInt(setBrushWidthValueField.getText()));
                }
            }
        }
    }
}