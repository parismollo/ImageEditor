import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ImageEditView extends JFrame{
    JButton cutButton, undoButton, redoButton;
    ImagePane imagePane;
    ImageEditModel model;

    private class ImagePane extends JPanel {
        Selection selection = new Selection();

        public ImagePane() {
            this.setPreferredSize(new Dimension(model.getImage().getHeight(), model.getImage().getWidth()));
            this.addMouseListener(selection);
            this.addMouseMotionListener(selection);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(model.getImage(), 0, 0, this);
            ((Graphics2D) g).draw(selection.getRectangle());
        }

        public class Selection extends MouseAdapter implements MouseMotionListener {
            int a, b, c, d;
            
            public Rectangle getRectangle() {
                return new Rectangle(a, b, c-a, d-b);
            }

            public void mousePressed(MouseEvent event) {
                a = event.getX();
                b = event.getY();

                ImageEditView.this.cutButton.setEnabled(false);
                ImageEditView.ImagePane.this.repaint();
            }

            public void mouseDragged(MouseEvent event) {
                c = event.getX();
                d = event.getY();

                if(c != this.a && d == this.b) {
                    ImageEditView.this.cutButton.setEnabled(true);
                    ImageEditView.ImagePane.this.repaint();
                }
            }
            public void mouseMoved(MouseEvent event) {

            }
        }
    }
}