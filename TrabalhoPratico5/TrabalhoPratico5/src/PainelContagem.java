import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * Painel com uma lista que é preenchida pelo laço de contagem de uma Thread.
 * Estende JPanel e implementa Runnable.
 */
public class PainelContagem extends JPanel implements Runnable {

    private final DefaultListModel<Integer> modelo = new DefaultListModel<>();
    private final JList<Integer> lista = new JList<>(modelo);
    private final JLabel lblFim = new JLabel("Final", SwingConstants.CENTER);

    private Thread thread;
    private int maximo;

    public PainelContagem() {
        setLayout(new BorderLayout(0, 8));

        JScrollPane scroll = new JScrollPane(lista);
        scroll.setPreferredSize(new Dimension(120, 130));

        lblFim.setVisible(false); // só aparece ao final da contagem

        add(scroll, BorderLayout.CENTER);
        add(lblFim, BorderLayout.SOUTH);
    }

    /** Cria a Thread, define a prioridade e inicia a contagem. */
    public void iniciar(int maximo, int prioridade) {
        limpar();
        this.maximo = maximo;
        thread = new Thread(this);
        thread.setPriority(prioridade);
        thread.start();
    }

    /** Esvazia a lista e esconde o label "Final". */
    public void limpar() {
        if (thread != null && thread.isAlive()) {
            thread.interrupt();
        }
        modelo.clear();
        lblFim.setVisible(false);
    }

    @Override
    public void run() {
        for (int i = 0; i < maximo; i++) {
            if (Thread.currentThread().isInterrupted()) {
                return;
            }
            final int valor = i;
            // Componentes Swing só devem ser alterados na Event Dispatch Thread
            SwingUtilities.invokeLater(() -> {
                modelo.addElement(valor);
                lista.ensureIndexIsVisible(modelo.size() - 1);
            });
        }
        SwingUtilities.invokeLater(() -> lblFim.setVisible(true));
    }
}
