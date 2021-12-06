import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ImageEditView extends JFrame{
    JButton cutButton, undoButton, redoButton;
    ImagePane imagePane;
    ImageEditModel model;

    public ImageEditView(ImageEditModel model) {
        this.model = model;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JMenuBar menuBar = new JMenuBar();

        cutButton = new JButton("cut");
        undoButton = new JButton("undo");
        redoButton = new JButton("redo");

        undoButton.setEnabled(false);
        redoButton.setEnabled(false);

        cutButton.addActionListener(
            (ActionEvent e) -> {
                model.saveCut(imagePane.selection.getRectangle());
                imagePane.repaint();
                cutButton.setEnabled(false);
                undoButton.setEnabled(true);
                // redoButton.setEnabled(false);
            }
        );

        undoButton.addActionListener(
            (ActionEvent e) -> {
                if (model.undoManager.canUndo()) {
                    model.undoManager.undo();
                    
                    if (!model.undoManager.canUndo()) {
                        undoButton.setEnabled(false);
                    }
                    redoButton.setEnabled(true);
                    imagePane.repaint();
                    // TODO: add setEnable true to cut button?
                }
                
            }
        );

        redoButton.addActionListener(
            (ActionEvent e) -> {
                if (model.undoManager.canRedo()) { // FIXME: this is not working....
                    model.undoManager.redo();
                    undoButton.setEnabled(true);
                   
                    if(!model.undoManager.canRedo()) {
                        redoButton.setEnabled(false);
                    }
                    imagePane.repaint();
                }
                
            }
        );
        // cutButton.setEnabled(false);
        menuBar.add(cutButton);
        menuBar.add(undoButton);
        menuBar.add(redoButton);
        setJMenuBar(menuBar);
        this.imagePane = new ImagePane();
        this.setContentPane(this.imagePane);
    }

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
                return new Rectangle(a, b, Math.abs(c-a), Math.abs(d-b));
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

                if(c != this.a && d != this.b) {
                    ImageEditView.this.cutButton.setEnabled(true);
                    ImageEditView.ImagePane.this.repaint();
                }
            }
            public void mouseMoved(MouseEvent event) {

            }
        }
    }
}
