import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class TicTacToeSingle extends JFrame implements ActionListener {
    private JButton[][] buttons;
    private boolean playerTurn;
    private static boolean isWinning;
    public TicTacToeSingle() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        setTitle("Tic Tac Toe Singleplayer");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 3));

        buttons = new JButton[3][3];
        playerTurn = true;
        Random random = new Random();
        int winNum = random.nextInt(2);
        isWinning = winNum >= 1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
                buttons[i][j].addActionListener(this);
                buttons[i][j].setFocusable(false);
                buttons[i][j].setFocusPainted(false);
                add(buttons[i][j]);
            }
        }
        JMenu modeMenu = new JMenu("Mode");
        JMenuItem singlePlayerMenuItem = new JMenuItem("MultiPlayer");
        singlePlayerMenuItem.addActionListener((ActionListener) new ActionListener(){
            public void actionPerformed(ActionEvent e){
                dispose();
                new TicTacToeMulti();
            }
        });
        modeMenu.add(singlePlayerMenuItem);
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(modeMenu);
        setJMenuBar(menuBar);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        JButton buttonClicked = (JButton) e.getSource();
        if (playerTurn) {
            buttonClicked.setText("X");
        } else {
            buttonClicked.setText("O");
        }
        buttonClicked.setEnabled(false);
        playerTurn = !playerTurn;

        if (checkForWin()) {
            JOptionPane.showMessageDialog(this, (playerTurn ? "AI" : "You ") + (playerTurn ? "wins" : "win") + '!');
            resetGame();
        } else if (isBoardFull()) {
            JOptionPane.showMessageDialog(this, "It's a draw!");
            resetGame();
        } else if (!playerTurn) {
            computerMove();
        }
    }

    private boolean checkForWin() {
        String[][] board = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = buttons[i][j].getText();
            }
        }

        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if (board[i][0].equals(board[i][1]) && board[i][1].equals(board[i][2]) && !board[i][0].equals("")) {
                return true;
            }
            if (board[0][i].equals(board[1][i]) && board[1][i].equals(board[2][i]) && !board[0][i].equals("")) {
                return true;
            }
        }

        // Check diagonals
        if (board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2]) && !board[0][0].equals("")) {
            return true;
        }
        if (board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0]) && !board[0][2].equals("")) {
            return true;
        }

        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    private void resetGame() {
        dispose();
        new TicTacToeSingle();
    }

    private void computerMove() {
        int[] move = minimax();
        buttons[move[0]][move[1]].setText("O");
        buttons[move[0]][move[1]].setEnabled(false);
        if (checkForWin()) {
            JOptionPane.showMessageDialog(this, "AI Wins!");
            resetGame();
        } else if (isBoardFull()) {
            JOptionPane.showMessageDialog(this, "It's a draw!");
            resetGame();
        } else {
            playerTurn = true;
        }
    }

    private int[] minimax() {
        int bestScore = Integer.MIN_VALUE;
        int[] move = new int[]{-1, -1};

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().equals("")) {
                    buttons[i][j].setText("O");
                    int score = minimaxHelper(0, false);
                    buttons[i][j].setText("");
                    if (score > bestScore) {
                        bestScore = score;
                        move[0] = i;
                        move[1] = j;
                    }
                }
            }
        }

        return move;
    }

    private int minimaxHelper(int depth, boolean isMaximizing) {
        if (checkForWin()) {
            return isMaximizing ? (isWinning ? 0 : -10 + depth) : (10 - depth);
        }
        if (isBoardFull()) {
            return 0;
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (buttons[i][j].getText().equals("")) {
                        buttons[i][j].setText("O");
                        int score = minimaxHelper(depth + 1, false);
                        buttons[i][j].setText("");
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (buttons[i][j].getText().equals("")) {
                        buttons[i][j].setText("X");
                        int score = minimaxHelper(depth + 1, true);
                        buttons[i][j].setText("");
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }

    public static void main(String[] args) {
        new TicTacToeSingle();
    }
}
