import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Janela principal do Trabalho Prático 5 (LP2 - Threads).
 * Contém o método main, já que o projeto não possui uma classe principal separada.
 */
public class TrabalhoPratico5 extends JFrame {

    private final JTextField txtMaximo = new JTextField("1000", 12);
    private final JTextField txtPrioridade1 = new JTextField("1", 12);
    private final JTextField txtPrioridade2 = new JTextField("10", 12);
    private final JButton btnIniciar = new JButton("Iniciar");
    private final JButton btnLimpar = new JButton("Limpar");
    private final PainelContagem painel1 = new PainelContagem();
    private final PainelContagem painel2 = new PainelContagem();

    public TrabalhoPratico5() {
        super("Trabalho Prático 5");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 8, 5, 8);
        c.anchor = GridBagConstraints.WEST;

        // Linha 0: Máximo
        c.gridx = 0; c.gridy = 0;
        add(new JLabel("Máximo:"), c);
        c.gridx = 1;
        add(txtMaximo, c);

        // Linha 1: Prioridade 1
        c.gridx = 0; c.gridy = 1;
        add(new JLabel("Prioridade 1:"), c);
        c.gridx = 1;
        add(txtPrioridade1, c);

        // Linha 2: Prioridade 2
        c.gridx = 0; c.gridy = 2;
        add(new JLabel("Prioridade 2:"), c);
        c.gridx = 1;
        add(txtPrioridade2, c);

        // Chave "}" e observação à direita das prioridades
        JLabel chave = new JLabel("}");
        chave.setFont(new Font("SansSerif", Font.PLAIN, 54));
        c.gridx = 2; c.gridy = 1; c.gridheight = 2;
        add(chave, c);
        c.gridx = 3; c.gridheight = 2;
        add(new JLabel("Número entre 1 e 10"), c);
        c.gridheight = 1;

        // Botão Iniciar
        c.gridx = 0; c.gridy = 3; c.gridwidth = 4;
        c.anchor = GridBagConstraints.CENTER;
        add(btnIniciar, c);

        // Listas (painéis Runnable)
        c.gridy = 4; c.gridwidth = 2; c.gridx = 0;
        add(painel1, c);
        c.gridx = 2;
        add(painel2, c);

        // Botão Limpar
        c.gridx = 0; c.gridy = 5; c.gridwidth = 4;
        add(btnLimpar, c);

        btnIniciar.addActionListener(e -> iniciar());
        btnLimpar.addActionListener(e -> {
            painel1.limpar();
            painel2.limpar();
        });

        pack();
        setMinimumSize(getSize());
        setLocationRelativeTo(null);
    }

    private void iniciar() {
        try {
            int maximo = Integer.parseInt(txtMaximo.getText().trim());
            int p1 = Integer.parseInt(txtPrioridade1.getText().trim());
            int p2 = Integer.parseInt(txtPrioridade2.getText().trim());

            if (maximo < 1) {
                throw new IllegalArgumentException("O valor máximo deve ser maior que zero.");
            }
            if (p1 < Thread.MIN_PRIORITY || p1 > Thread.MAX_PRIORITY
                    || p2 < Thread.MIN_PRIORITY || p2 > Thread.MAX_PRIORITY) {
                throw new IllegalArgumentException("As prioridades devem estar entre 1 e 10.");
            }

            painel1.iniciar(maximo, p1);
            painel2.iniciar(maximo, p2);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Informe apenas números inteiros.",
                    "Valor inválido", JOptionPane.WARNING_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Valor inválido", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
        SwingUtilities.invokeLater(() -> new TrabalhoPratico5().setVisible(true));
    }
}
