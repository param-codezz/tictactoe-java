import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class TicTacToeMulti implements ActionListener {
    JFrame frame;
    JButton[][] buttons;
    char currentPlayer = 'X';

    public TicTacToeMulti() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        this.frame = new JFrame("Tic Tac Toe Multiplayer");
        this.frame.setSize(300, 300);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 3));
        
        this.buttons = new JButton[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.buttons[i][j] = new JButton();
                this.buttons[i][j].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
                this.buttons[i][j].addActionListener(this);
                this.buttons[i][j].setFocusPainted(false);
                this.buttons[i][j].setFocusable(false);
                panel.add(this.buttons[i][j]);
            }
        }
        JMenu modeMenu = new JMenu("Mode");
        JMenuItem singlePlayerMenuItem = new JMenuItem("SinglePlayer");
        singlePlayerMenuItem.addActionListener((ActionListener) new ActionListener(){
            public void actionPerformed(ActionEvent e){
                frame.dispose();
                new TicTacToeSingle();
            }
        });
        modeMenu.add(singlePlayerMenuItem);
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(modeMenu);
        this.frame.setJMenuBar(menuBar);
        this.frame.add(panel);
        this.frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        if (button.getText().equals("")) {
            button.setText(Character.toString(this.currentPlayer));
            button.setEnabled(false);
            if (this.checkWin()) {
                JOptionPane.showMessageDialog(this.frame, "Player " + this.currentPlayer + " wins!");
                this.resetGame();
            } else if (this.checkDraw()) {
                JOptionPane.showMessageDialog(this.frame, "It's a draw!");
                this.resetGame();
            } else {
                this.currentPlayer = (this.currentPlayer == 'X') ? 'O' : 'X';
            }
        }
    }

    private boolean checkWin() {
        for (int i = 0; i < 3; i++) {
            if (this.buttons[i][0].getText().equals(Character.toString(this.currentPlayer)) &&
                this.buttons[i][1].getText().equals(Character.toString(this.currentPlayer)) &&
                this.buttons[i][2].getText().equals(Character.toString(this.currentPlayer)))
                return true;
            if (this.buttons[0][i].getText().equals(Character.toString(this.currentPlayer)) &&
                this.buttons[1][i].getText().equals(Character.toString(this.currentPlayer)) &&
                this.buttons[2][i].getText().equals(Character.toString(this.currentPlayer)))
                return true;
        }
        if (this.buttons[0][0].getText().equals(Character.toString(this.currentPlayer)) &&
            this.buttons[1][1].getText().equals(Character.toString(this.currentPlayer)) &&
            this.buttons[2][2].getText().equals(Character.toString(this.currentPlayer)))
            return true;
        if (this.buttons[0][2].getText().equals(Character.toString(this.currentPlayer)) &&
            this.buttons[1][1].getText().equals(Character.toString(this.currentPlayer)) &&
            this.buttons[2][0].getText().equals(Character.toString(this.currentPlayer)))
            return true;
        return false;
    }

    private boolean checkDraw() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (this.buttons[i][j].getText().equals(""))
                    return false;
            }
        }
        return true;
    }

    private void resetGame() {
        this.frame.dispose();
        new TicTacToeMulti();
    }

    public static void main(String[] args) {
        new TicTacToeMulti();
    }
}
