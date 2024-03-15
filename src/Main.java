import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

public class Main {
    JFrame jFrame = new JFrame();

    public Main() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(300, 100);
        jFrame.setLocationRelativeTo(null);
        jFrame.setLayout(new GridLayout(2, 1));
        JLabel jLabel = new JLabel();
        jLabel.setText("Choose player mode: ");
        jLabel.setHorizontalAlignment(0);

        JLabel blank = new JLabel();
        blank.setText(null);

        JButton jButton = new JButton();
        jButton.setText("Single Player");
        jButton.setFocusable(false);
        jButton.setFocusPainted(false);
        jButton.addActionListener(e -> singlePlayerClicked(null));
        
        JButton jButton2 = new JButton();
        jButton2.setText("Multi Player");
        jButton2.setFocusable(false);
        jButton2.setFocusPainted(false);
        jButton2.addActionListener(e -> multiPlayerClicked(null));
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        panel.add(jButton);
        panel.add(jButton2);
        jFrame.add(jLabel, BorderLayout.CENTER);
        jFrame.add(panel, BorderLayout.CENTER);

        jFrame.setVisible(true);
    }

    public void singlePlayerClicked(ActionListener e) {
        jFrame.dispose();
        new TicTacToeSingle();
    }

    public void multiPlayerClicked(ActionListener e) {
        jFrame.dispose();
        new TicTacToeMulti();
    }

    public static void main(String[] args) {
        new Main();
    }

}