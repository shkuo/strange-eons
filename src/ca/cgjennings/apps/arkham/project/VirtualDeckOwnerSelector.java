package ca.cgjennings.apps.arkham.project;

import ca.cgjennings.apps.arkham.project.VirtualDeckDialog.Card;
import java.awt.event.KeyEvent;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import static resources.Language.string;

/**
 * Sets owner of a card selection in a virtual deck.
 *
 * @author Chris Jennings <https://cgjennings.ca/contact>
 */
@SuppressWarnings("serial")
class VirtualDeckOwnerSelector extends javax.swing.JDialog {

    private final VirtualDeckDialog parent;

    /**
     * Creates new form VirtualDeckOwnerSelector
     */
    public VirtualDeckOwnerSelector(VirtualDeckDialog parent) {
        super(parent, true);
        setUndecorated(true);
        initComponents();
        this.parent = parent;
        pack();
    }

    private Card[] cards;

    public boolean setOwner(Card[] cards, int x, int y) {
        isEditing = false;

        final JRadioButton[] btns = {
            b0, b1, b2, b3, b4, b5, b6, b7, b8, b9
        };
        for (int i = 0; i < btns.length; ++i) {
            btns[i].setSelected(false);
        }
        final JTextField[] fields = {f1, f2, f3, f4, f5, f6, f7, f8};
        for (int i = 0; i < fields.length; ++i) {
            fields[i].setText(parent.owners[i + 1]);
        }
        cardIcon.setIcon(cards[0].thumb);
        pack();
        this.cards = cards;
        setLocation(x, y - getHeight() / 2);

        isEditing = true;
        cancelled = false;
        setVisible(true);
        if (cancelled) {
            return false;
        }

        for (int i = 0; i < fields.length; ++i) {
            parent.owners[i + 1] = fields[i].getText();
        }
        for (int i = 0; i < btns.length; ++i) {
            if (btns[i].isSelected()) {
                for (int c = 0; c < cards.length; ++c) {
                    cards[c].owner = i;
                }
            }
        }
        return true;
    }
    private boolean cancelled;

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ownerGroup = new javax.swing.ButtonGroup();
        borderPanel = new javax.swing.JPanel();
        b5 = new javax.swing.JRadioButton();
        b8 = new javax.swing.JRadioButton();
        f8 = new javax.swing.JTextField();
        b9 = new javax.swing.JRadioButton();
        b1 = new javax.swing.JRadioButton();
        f1 = new javax.swing.JTextField();
        b7 = new javax.swing.JRadioButton();
        b0 = new javax.swing.JRadioButton();
        f7 = new javax.swing.JTextField();
        f4 = new javax.swing.JTextField();
        f6 = new javax.swing.JTextField();
        cardIcon = new javax.swing.JLabel();
        f2 = new javax.swing.JTextField();
        f5 = new javax.swing.JTextField();
        b6 = new javax.swing.JRadioButton();
        b4 = new javax.swing.JRadioButton();
        f3 = new javax.swing.JTextField();
        b2 = new javax.swing.JRadioButton();
        b3 = new javax.swing.JRadioButton();
        cancelBtn = new javax.swing.JButton();

        borderPanel.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.darkGray));
        borderPanel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                borderPanelKeyPressed(evt);
            }
        });

        ownerGroup.add(b5);
        b5.setText("&5");
        b5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ownerChanged(evt);
            }
        });

        ownerGroup.add(b8);
        b8.setText("&8");
        b8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ownerChanged(evt);
            }
        });

        f8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldActionPerformed(evt);
            }
        });

        ownerGroup.add(b9);
        b9.setText(string( "vdeck-b-other" )); // NOI18N
        b9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ownerChanged(evt);
            }
        });

        ownerGroup.add(b1);
        b1.setText("&1");
        b1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ownerChanged(evt);
            }
        });

        f1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldActionPerformed(evt);
            }
        });

        ownerGroup.add(b7);
        b7.setText("&7");
        b7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ownerChanged(evt);
            }
        });

        ownerGroup.add(b0);
        b0.setText(string( "vdeck-b-ownerless" )); // NOI18N
        b0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ownerChanged(evt);
            }
        });

        f7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldActionPerformed(evt);
            }
        });

        f4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldActionPerformed(evt);
            }
        });

        f6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldActionPerformed(evt);
            }
        });

        cardIcon.setText(" ");

        f2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldActionPerformed(evt);
            }
        });

        f5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldActionPerformed(evt);
            }
        });

        ownerGroup.add(b6);
        b6.setText("&6");
        b6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ownerChanged(evt);
            }
        });

        ownerGroup.add(b4);
        b4.setText("&4");
        b4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ownerChanged(evt);
            }
        });

        f3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldActionPerformed(evt);
            }
        });

        ownerGroup.add(b2);
        b2.setText("&2");
        b2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ownerChanged(evt);
            }
        });

        ownerGroup.add(b3);
        b3.setText("&3");
        b3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ownerChanged(evt);
            }
        });

        cancelBtn.setText(string("cancel")); // NOI18N
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout borderPanelLayout = new javax.swing.GroupLayout(borderPanel);
        borderPanel.setLayout(borderPanelLayout);
        borderPanelLayout.setHorizontalGroup(
            borderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(borderPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(borderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(b0)
                    .addComponent(b9)
                    .addComponent(cardIcon))
                .addGap(18, 18, 18)
                .addGroup(borderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(b4)
                    .addComponent(b3)
                    .addComponent(b2)
                    .addComponent(b1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(borderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(f1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(f2, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(f3, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(f4, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(borderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(b5)
                    .addComponent(b6)
                    .addComponent(b7)
                    .addComponent(b8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(borderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(f5, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(f6, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(f7, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(f8, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, borderPanelLayout.createSequentialGroup()
                .addContainerGap(318, Short.MAX_VALUE)
                .addComponent(cancelBtn)
                .addContainerGap())
        );
        borderPanelLayout.setVerticalGroup(
            borderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(borderPanelLayout.createSequentialGroup()
                .addGroup(borderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(borderPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(borderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(b0)
                            .addComponent(f1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(b5)
                            .addComponent(f5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(b1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(borderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(b9)
                            .addComponent(b2)
                            .addComponent(f2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(b6)
                            .addComponent(f6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(borderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(b3)
                            .addComponent(f3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(b7)
                            .addComponent(f7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(borderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(b4)
                            .addComponent(f4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(b8)
                            .addComponent(f8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(borderPanelLayout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addComponent(cardIcon)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, Short.MAX_VALUE)
                .addComponent(cancelBtn)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(borderPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(borderPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

	private void ownerChanged(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ownerChanged
            if (!isEditing) {
                return;
            }

            final JRadioButton[] btns = {
                b0, b1, b2, b3, b4, b5, b6, b7, b8, b9
            };
            for (int i = 0; i < btns.length; ++i) {
                if (btns[i].equals(evt.getSource())) {
                    for (int c = 0; c < cards.length; ++c) {
                        cards[c].owner = i;
                        setVisible(false);
                    }
                }
            }
	}//GEN-LAST:event_ownerChanged

	private void borderPanelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_borderPanelKeyPressed
            if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
                dispose();
            }
	}//GEN-LAST:event_borderPanelKeyPressed

	private void fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldActionPerformed
            setVisible(false);
	}//GEN-LAST:event_fieldActionPerformed

	private void cancelBtnActionPerformed( java.awt.event.ActionEvent evt ) {//GEN-FIRST:event_cancelBtnActionPerformed
            cancelled = true;
            setVisible(false);
	}//GEN-LAST:event_cancelBtnActionPerformed

    private boolean isEditing;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton b0;
    private javax.swing.JRadioButton b1;
    private javax.swing.JRadioButton b2;
    private javax.swing.JRadioButton b3;
    private javax.swing.JRadioButton b4;
    private javax.swing.JRadioButton b5;
    private javax.swing.JRadioButton b6;
    private javax.swing.JRadioButton b7;
    private javax.swing.JRadioButton b8;
    private javax.swing.JRadioButton b9;
    private javax.swing.JPanel borderPanel;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JLabel cardIcon;
    private javax.swing.JTextField f1;
    private javax.swing.JTextField f2;
    private javax.swing.JTextField f3;
    private javax.swing.JTextField f4;
    private javax.swing.JTextField f5;
    private javax.swing.JTextField f6;
    private javax.swing.JTextField f7;
    private javax.swing.JTextField f8;
    private javax.swing.ButtonGroup ownerGroup;
    // End of variables declaration//GEN-END:variables

}
