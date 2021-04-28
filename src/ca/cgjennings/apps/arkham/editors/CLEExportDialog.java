package ca.cgjennings.apps.arkham.editors;

import ca.cgjennings.ui.textedit.tokenizers.JavaScriptTokenizer;
import ca.cgjennings.ui.textedit.tokenizers.PropertyTokenizer;
import static resources.Language.string;
import resources.Settings;

/**
 * Dialog that displays export code for a {@link CardLayoutEditor}.
 *
 * @author Chris Jennings <https://cgjennings.ca/contact>
 */
@SuppressWarnings("serial")
class CLEExportDialog extends javax.swing.JDialog {

    private final CardLayoutEditor ed;

    /**
     * Creates new form CLEExportDialog
     */
    public CLEExportDialog(java.awt.Frame parent, CardLayoutEditor ed) {
        super(parent, true);
        this.ed = ed;
        initComponents();
        paintCode.setTokenizer(new JavaScriptTokenizer());
        settingsCode.setTokenizer(new PropertyTokenizer());
        Settings.getUser().getYesNo("card-layout-export-back");
        frontBtn.setSelected(true);
        updateCode(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        faceGroup = new javax.swing.ButtonGroup();
        closeBtn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        frontBtn = new javax.swing.JRadioButton();
        backBtn = new javax.swing.JRadioButton();
        tab = new javax.swing.JTabbedPane();
        paintCode = new ca.cgjennings.ui.textedit.JSourceCodeEditor();
        settingsCode = new ca.cgjennings.ui.textedit.JSourceCodeEditor();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(string( "cle-export-title" )); // NOI18N

        closeBtn.setText(string( "close" )); // NOI18N
        closeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeBtnActionPerformed(evt);
            }
        });

        jLabel2.setText(string( "cle-export-face-info" )); // NOI18N

        faceGroup.add(frontBtn);
        frontBtn.setText(string( "cle-export-front" )); // NOI18N
        frontBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                frontBtnActionPerformed(evt);
            }
        });

        faceGroup.add(backBtn);
        backBtn.setText(string( "cle-export-back" )); // NOI18N
        backBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBtnActionPerformed(evt);
            }
        });

        tab.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, java.awt.Color.gray));

        paintCode.setEditable(false);
        tab.addTab(string( "cle-export-paint-code" ), paintCode); // NOI18N

        settingsCode.setEditable(false);
        tab.addTab(string( "cle-export-settings" ), settingsCode); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(553, Short.MAX_VALUE)
                .addComponent(closeBtn)
                .addContainerGap())
            .addComponent(tab, javax.swing.GroupLayout.DEFAULT_SIZE, 620, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(backBtn)
                            .addComponent(frontBtn))))
                .addContainerGap(524, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tab, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(frontBtn)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(closeBtn)
                    .addComponent(backBtn))
                .addContainerGap())
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-636)/2, (screenSize.height-538)/2, 636, 538);
    }// </editor-fold>//GEN-END:initComponents

	private void closeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeBtnActionPerformed
            dispose();
	}//GEN-LAST:event_closeBtnActionPerformed

	private void frontBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_frontBtnActionPerformed
            updateCode(true);
	}//GEN-LAST:event_frontBtnActionPerformed

	private void backBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBtnActionPerformed
            updateCode(false);
	}//GEN-LAST:event_backBtnActionPerformed

    private void updateCode(boolean front) {
        paintCode.setText(ed.createExportText(CardLayoutEditor.ExportType.PAINTING_CODE, front, false));
        paintCode.select(0, 0);
        settingsCode.setText(ed.createExportText(CardLayoutEditor.ExportType.SETTINGS, front, false));
        settingsCode.select(0, 0);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton backBtn;
    private javax.swing.JButton closeBtn;
    private javax.swing.ButtonGroup faceGroup;
    private javax.swing.JRadioButton frontBtn;
    private javax.swing.JLabel jLabel2;
    private ca.cgjennings.ui.textedit.JSourceCodeEditor paintCode;
    private ca.cgjennings.ui.textedit.JSourceCodeEditor settingsCode;
    private javax.swing.JTabbedPane tab;
    // End of variables declaration//GEN-END:variables

}
