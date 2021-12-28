package ca.cgjennings.apps.arkham.deck.item;

import ca.cgjennings.apps.arkham.ContextBar;
import ca.cgjennings.apps.arkham.MarkupTargetFactory;
import ca.cgjennings.apps.arkham.StrangeEons;
import ca.cgjennings.spelling.ui.JSpellingTextArea;
import ca.cgjennings.ui.StyleUtilities;
import ca.cgjennings.ui.theme.ThemeInstaller;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyEvent;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import static resources.Language.string;
import resources.ResourceKit;

/**
 * Edits the text on text boxes in a deck. This class is public in order to
 * cross a package barrier. You should not need to instantiate this class.
 *
 * @author Chris Jennings <https://cgjennings.ca/contact>
 */
@SuppressWarnings("serial")
public final class TextBoxEditor extends javax.swing.JDialog implements java.awt.event.KeyListener {

    private TextBox card;

    /**
     * Creates new form RelabelDialog
     */
    TextBoxEditor(java.awt.Frame parent, TextBox card) {
        super(parent, false);
        initComponents();
        if (ThemeInstaller.isDark()) {
            instructions.setBackground(Color.BLACK);
            instructions.setForeground(Color.WHITE);
        }

        MarkupTargetFactory.enableTargeting(textArea, true);
        textArea.putClientProperty(ContextBar.BAR_LEADING_SIDE_PROPERTY, true);

        textArea.setFont(ResourceKit.getEditorFont());
        this.card = card;
        setText(card.getText());
        StyleUtilities.setWindowOpacity(this, 0.95f);

        SwingUtilities.invokeLater(() -> {
            textArea.requestFocusInWindow();
            textArea.select(0, 0);
            StrangeEons.getApplication().requestNewMarkupTarget(textArea);
        });

        // hack: apply menu accelerators from Markup menu
        JMenuBar bar = StrangeEons.getWindow().getJMenuBar();
        for (int i = 0; i < bar.getMenuCount(); ++i) {
            if (bar.getMenu(i).getName().equals("markupMenu")) {
                JMenu markup = bar.getMenu(i);
                for (int j = 0; j < markup.getMenuComponentCount(); ++j) {
                    Component c = markup.getMenuComponent(j);
                    if (c instanceof JMenuItem) {
                        JMenuItem item = (JMenuItem) c;
                        if (item.getAction() != null) {
                            Action a = item.getAction();
                            if (a.getValue(Action.ACCELERATOR_KEY) != null) {
                                textArea.getInputMap().put((KeyStroke) a.getValue(Action.ACCELERATOR_KEY), a.getValue(Action.ACTION_COMMAND_KEY));
                                textArea.getActionMap().put(a.getValue(Action.ACTION_COMMAND_KEY), a);
                            }
                        }
                    }
                }
                break;
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane();
        textArea = new JSpellingTextArea();
        instructions = new javax.swing.JLabel();

        setUndecorated(true);

        scrollPane.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(128, 128, 128)));

        textArea.setColumns(20);
        textArea.setLineWrap(true);
        textArea.setRows(5);
        textArea.setTabSize(4);
        textArea.setWrapStyleWord(true);
        textArea.addKeyListener(this);
        scrollPane.setViewportView(textArea);

        getContentPane().add(scrollPane, java.awt.BorderLayout.CENTER);

        instructions.setBackground(new java.awt.Color(215, 229, 242));
        instructions.setFont(instructions.getFont().deriveFont(instructions.getFont().getSize()-1f));
        instructions.setText(string("de-text-box-relabel-help")); // NOI18N
        instructions.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 1, new java.awt.Color(128, 128, 128)), javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(128, 128, 128)), javax.swing.BorderFactory.createEmptyBorder(1, 2, 1, 2))));
        instructions.setOpaque(true);
        getContentPane().add(instructions, java.awt.BorderLayout.SOUTH);

        setSize(new java.awt.Dimension(372, 191));
        setLocationRelativeTo(null);
    }

    // Code for dispatching events from components to event handlers.

    public void keyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getSource() == textArea) {
            TextBoxEditor.this.textAreaKeyPressed(evt);
        }
    }

    public void keyReleased(java.awt.event.KeyEvent evt) {
    }

    public void keyTyped(java.awt.event.KeyEvent evt) {
    }// </editor-fold>//GEN-END:initComponents

    private void textAreaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textAreaKeyPressed
        boolean shift = evt.isShiftDown();
        boolean command = evt.isControlDown() || evt.isMetaDown();
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                evt.consume();
                if (shift || command) {
                    textArea.replaceSelection("\n");
                    return;
                }
                acceptChanges();
                break;
            case KeyEvent.VK_ESCAPE:
                evt.consume();
                cancelChanges();
                break;
        }
    }//GEN-LAST:event_textAreaKeyPressed

    private void acceptChanges() {
        final String text = textArea.getText();
        if (!card.getText().equals(text)) {
            card.setText(text);
        }
        cancelChanges();
    }

    private void cancelChanges() {
        TextBox.cancelActiveEditor();
    }

    public void setText(String text) {
        textArea.setText(text);
    }

    public String getText() {
        return textArea.getText();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel instructions;
    private javax.swing.JTextArea textArea;
    // End of variables declaration//GEN-END:variables
}
