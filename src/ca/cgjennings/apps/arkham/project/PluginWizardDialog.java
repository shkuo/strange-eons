package ca.cgjennings.apps.arkham.project;

import ca.cgjennings.apps.arkham.dialog.ErrorDialog;
import ca.cgjennings.platform.PlatformSupport;
import ca.cgjennings.ui.EditorPane;
import ca.cgjennings.ui.JUtilities;
import ca.cgjennings.ui.wizard.WizardAdapter;
import ca.cgjennings.ui.wizard.WizardController;
import ca.cgjennings.ui.wizard.WizardEvent;
import ca.cgjennings.ui.wizard.WizardListener;
import ca.cgjennings.ui.wizard.WizardModel;
import ca.cgjennings.ui.wizard.WizardPanel;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import resources.Language;
import static resources.Language.string;
import resources.ResourceKit;
import resources.projects.pluginwizard.SkeletonKit;

/**
 * A wizard dialog that is displayed when the user creates a new plug-in task.
 * Plug-in authors can add new kinds of plug-in projects to the dialog by
 * registering a {@link WizardKit} with this class.
 *
 * @author Chris Jennings <https://cgjennings.ca/contact>
 * @since 3.0
 */
@SuppressWarnings("serial")
public class PluginWizardDialog extends javax.swing.JDialog {

    private final Task task;
    private Controller controller;
    private WizardModel model;

    /**
     * Creates a new wizard dialog that will fill in the provided new, empty
     * task. The task may be {@code null} to assist in testing of new wizard
     * kits.
     *
     * @param owner
     * @param task
     */
    public PluginWizardDialog(Frame owner, Task task) {
        super(owner, true);
        initComponents();
        this.task = task;

        backBtn.setIcon(ResourceKit.getIcon("ui/go-back.png"));
        nextBtn.setIcon(ResourceKit.getIcon("ui/continue.png"));

        if (PlatformSupport.PLATFORM_IS_MAC) {
            cancelBtn.setVisible(false);
        } else {
            cancelBtn2.setVisible(false);
        }

        populateKitList();
        showKitSelectionPage();
    }

    private void showKitSelectionPage() {
        if (controller != null) {
            controller.dispose();
            controller = null;
        }
        pages.removeAll();
        pages.add(kitSelectPanel, "kit");
        backBtn.setEnabled(false);
        nextBtn.setEnabled(kitList.getSelectedValue() != null);
        okBtn.setEnabled(false);
        ((CardLayout) (pages.getLayout())).show(pages, "kit");
        model = null;
        pages.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        kitSelectPanel = new javax.swing.JPanel();
        javax.swing.JScrollPane kitListScroll = new javax.swing.JScrollPane();
        kitList = new javax.swing.JList<>();
        javax.swing.JScrollPane kitDescScroll = new javax.swing.JScrollPane();
        kitDesc = new EditorPane();
        javax.swing.JLabel typeLabel = new javax.swing.JLabel();
        javax.swing.JPanel rhsPanel = new javax.swing.JPanel();
        pages =  new WizardPanel() ;
        cancelBtn = new javax.swing.JButton();
        okBtn = new javax.swing.JButton();
        nextBtn = new javax.swing.JButton();
        backBtn = new javax.swing.JButton();
        cancelBtn2 = new javax.swing.JButton();
        pageLabel = new ca.cgjennings.ui.JHeading();
        banner = new javax.swing.JLabel();

        kitList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        kitList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                kitListValueChanged(evt);
            }
        });
        kitListScroll.setViewportView(kitList);

        kitDesc.setEditable(false);
        kitDesc.setContentType("text/html"); // NOI18N
        kitDescScroll.setViewportView(kitDesc);

        typeLabel.setText(string("prj-l-plugin-wiz-kit")); // NOI18N

        javax.swing.GroupLayout kitSelectPanelLayout = new javax.swing.GroupLayout(kitSelectPanel);
        kitSelectPanel.setLayout(kitSelectPanelLayout);
        kitSelectPanelLayout.setHorizontalGroup(
            kitSelectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kitSelectPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(kitSelectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kitSelectPanelLayout.createSequentialGroup()
                        .addComponent(kitListScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(kitDescScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE))
                    .addComponent(typeLabel))
                .addContainerGap())
        );
        kitSelectPanelLayout.setVerticalGroup(
            kitSelectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kitSelectPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(typeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(kitSelectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(kitListScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                    .addComponent(kitDescScroll))
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(string( "prj-l-plugin-wiz" )); // NOI18N

        pages.setLayout(null);

        cancelBtn.setText(string("cancel")); // NOI18N
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });

        okBtn.setText(string("finish")); // NOI18N
        okBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okBtnActionPerformed(evt);
            }
        });

        nextBtn.setText(string("nextpage")); // NOI18N
        nextBtn.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        nextBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextBtnPressedOnKitPage(evt);
            }
        });

        backBtn.setText(string("backpage")); // NOI18N

        cancelBtn2.setText(string("cancel")); // NOI18N
        cancelBtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("resources/text/interface/eons-text"); // NOI18N
        pageLabel.setText(bundle.getString("prj-l-plugin-wiz")); // NOI18N

        javax.swing.GroupLayout rhsPanelLayout = new javax.swing.GroupLayout(rhsPanel);
        rhsPanel.setLayout(rhsPanelLayout);
        rhsPanelLayout.setHorizontalGroup(
            rhsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rhsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(rhsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pages, javax.swing.GroupLayout.DEFAULT_SIZE, 587, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rhsPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(cancelBtn2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(backBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nextBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(okBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelBtn))
                    .addComponent(pageLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        rhsPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {backBtn, nextBtn});

        rhsPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancelBtn, okBtn});

        rhsPanelLayout.setVerticalGroup(
            rhsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rhsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pages, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                .addGap(22, 22, 22)
                .addGroup(rhsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelBtn)
                    .addComponent(okBtn)
                    .addComponent(nextBtn)
                    .addComponent(backBtn)
                    .addComponent(cancelBtn2))
                .addContainerGap())
        );

        getContentPane().add(rhsPanel, java.awt.BorderLayout.CENTER);

        banner.setIcon(resources.ResourceKit.createBleedBanner("banner.jpg")
        );
        banner.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        banner.setIconTextGap(0);
        getContentPane().add(banner, java.awt.BorderLayout.WEST);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Handles pressing of the Finish button. Note that this is a listener
     * directly on the Finish button, not a WizardListener handler. In order to
     * allow any such listeners to complete before we try to create the task, we
     * use an invokeLater. (The Finish button is disabled when the list of kits
     * is shown, so this can only be called when there is an actual kit being
     * shown.)
     *
     * @param evt the press event
     */
	private void okBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okBtnActionPerformed
            EventQueue.invokeLater(() -> {
                WizardKit kit = ((KitListItem) kitList.getSelectedValue()).kit;
                JUtilities.showWaitCursor(PluginWizardDialog.this);
                try {
                    kit.createTask(model, task);
                    ok = true;
                    dispose();
                } catch (Throwable t) {
                    ErrorDialog.displayError(string("prj-err-task"), t);
                } finally {
                    JUtilities.hideWaitCursor(PluginWizardDialog.this);
                }
            });
	}//GEN-LAST:event_okBtnActionPerformed

	private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
            dispose();
	}//GEN-LAST:event_cancelBtnActionPerformed

    private void kitListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_kitListValueChanged
        Object sel = kitList.getSelectedValue();
        if (sel == null) {
            nextBtn.setEnabled(false);
            kitDesc.setText("");
        } else {
            nextBtn.setEnabled(true);
            kitDesc.setText(
                    ((KitListItem) sel).kit.getDescription()
            );
        }
        kitDesc.select(0, 0);
    }//GEN-LAST:event_kitListValueChanged

    private void nextBtnPressedOnKitPage(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextBtnPressedOnKitPage
        // will be called when next is pressed on other pages, but won't do anything
        if (pages.getComponentCount() != 1 || pages.getComponent(0) != kitSelectPanel) {
            return;
        }

        KitListItem k = (KitListItem) kitList.getSelectedValue();
        model = k.getModel();
        WizardPanel panel = (WizardPanel) pages;
        panel.setModel(null);
        panel.setModel(model);
        controller = new Controller(nextBtn, backBtn, okBtn, panel, model);
        fixDialogSize();
    }//GEN-LAST:event_nextBtnPressedOnKitPage

    private BufferedImage plainBanner;

    public boolean showDialog() {
        ok = false;
        setVisible(true);
        return ok;
    }

    private boolean ok;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backBtn;
    private javax.swing.JLabel banner;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JButton cancelBtn2;
    private javax.swing.JEditorPane kitDesc;
    private javax.swing.JList<KitListItem> kitList;
    private javax.swing.JPanel kitSelectPanel;
    private javax.swing.JButton nextBtn;
    private javax.swing.JButton okBtn;
    private ca.cgjennings.ui.JHeading pageLabel;
    private javax.swing.JPanel pages;
    // End of variables declaration//GEN-END:variables

    /**
     * A simple wrapper for wizard kits that appear in the JList of selectable
     * kits.
     */
    private class KitListItem {

        private WizardKit kit;
        private WizardModel model;

        public KitListItem(WizardKit kit) {
            this.kit = kit;
        }

        /**
         * Returns a model for the kit, lazily creating it on demand.
         *
         * @return a model suitable for the encapsulated kit
         */
        public WizardModel getModel() {
            if (model == null) {
                model = kit.createModel(task);
            }
            return model;
        }

        /**
         * Returns the kit name (for display in the JList).
         */
        @Override
        public String toString() {
            return kit.getName();
        }
    }

    /**
     * Fills in the list of plug-in types using the registered kits.
     */
    private void populateKitList() {
        WizardKit[] sorted = kits.toArray(new WizardKit[0]);
        Arrays.sort(sorted, sorter);

        DefaultListModel<KitListItem> m = new DefaultListModel<>();
        for (WizardKit k : sorted) {
            m.addElement(new KitListItem(k));
        }
        kitList.setModel(m);

        // select a kit if possible
        if (m.getSize() > 0) {
            kitList.setSelectedIndex(0);
        }
    }

    /**
     * Simple controller subclass that handles going back from page 0 to the kit
     * selection page.
     */
    private class Controller extends WizardController {

        public Controller(JButton nextBtn, JButton prevBtn, JButton finishBtn, WizardPanel panel, WizardModel model) {
            super(nextBtn, prevBtn, finishBtn, panel, model);
        }

        @Override
        protected void updateButtonStates() {
            super.updateButtonStates();
            // allow returning to kit list page
            if (getModel().getCurrentPage() == 0) {
                getPreviousPageButton().setEnabled(true);
            }
        }

        @Override
        protected void handlePreviousPageButton() {
            // check if we are on page 0 and if so go to kit page
            if (getModel().getCurrentPage() == 0) {
                showKitSelectionPage();
            } else {
                super.handlePreviousPageButton();
            }
        }

        @Override
        protected void install() {
            super.install();
            resizeListener = new WizardAdapter() {
                @Override
                public void wizardPageChanged(WizardEvent e) {
                    // check if this new page is too large for the dialog
                    fixDialogSize();
                }
            };
            getModel().addWizardListener(resizeListener);
        }

        ;
		@Override
        public void dispose() {
            if (resizeListener != null) {
                model.removeWizardListener(resizeListener);
                resizeListener = null;
            }
            super.dispose();
        }

        private WizardListener resizeListener;
    }

    private void fixDialogSize() {
//		EventQueue.invokeLater( new Runnable() {
//			@Override
//			public void run() {
//				validate();
        Dimension ps = getPreferredSize();
        Dimension cs = getSize();
        if (cs.width < ps.width || cs.height < ps.height) {
            final int newWidth = Math.max(cs.width, ps.width);
            final int newHeight = Math.max(cs.height, ps.height);
            final int dx = cs.width - newWidth;
            final int dy = cs.height - newHeight;

            Rectangle bounds = getGraphicsConfiguration().getBounds();
            final int newX = Math.max(getX() + dx / 2, bounds.x);
            final int newY = Math.max(getY() + dy / 2, bounds.y);
            setBounds(newX, newY, newWidth, newHeight);
        }
//			}
//		});
    }

//    public static void main(String args[]) {
//		Language.setInterfaceLocale( Locale.getDefault() );
//        java.awt.EventQueue.invokeLater(new Runnable() {
//			@Override
//            public void run() {
//				for( int i=1; i<5; ++i ) {
//					final int n = i;
//					PluginWizardDialog.registerWizardKit( new WizardKit() {
//						@Override
//						public String getName() {
//							return "Dummy Kit " + n;
//						}
//						@Override
//						public String getDescription() {
//							return "This is a dummy kit for <b>test purposes only</b>.";
//						}
//						@Override
//						public WizardModel createModel( Task t ) {
//							DefaultWizardModel m = new DefaultWizardModel();
//							JComponent[] pages = new JComponent[3];
//							for( int j=0; j<3; ++j ) {
//								pages[j] = new JLabel( String.valueOf(n+j) );
//							}
//							m.setPages( pages );
//							return m;
//						}
//						@Override
//						public void createTask( WizardModel model, Task task ) throws Exception {
//							System.err.println( "Do it!" );
//						}
//					});
//				}
//                PluginWizardDialog dialog = new PluginWizardDialog(new javax.swing.JFrame(), null);
//				dialog.setDefaultCloseOperation( DISPOSE_ON_CLOSE );
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//					@Override
//                    public void windowClosing( java.awt.event.WindowEvent e ) {
//                        System.exit(0);
//                    }
//                });
//                dialog.setVisible(true);
//            }
//        });
//    }
    /**
     * A wizard kit allows you to define new kinds of plug-in wizard projects.
     * All registered projects will be listed on the first page of the dialog,
     * and when the user selects a project the matching kit will be used to
     * provide the following pages. If the user finishes the wizard, the
     * matching kit's {@code createTask} method will be called to fill in the
     * task folder.
     */
    public static interface WizardKit {

        /**
         * Returns the short name of the kit that should be displayed in the
         * list of available kits (plug-in project types).
         *
         * @return the kit name
         */
        String getName();

        /**
         * Returns a longer description of the kind of plug-in project that is
         * created with the kit.
         *
         * @return the kit description (basic HTML allowed)
         */
        String getDescription();

        /**
         * Creates a new {@link WizardModel} that provides the pages needed to
         * complete the steps of this kit.
         *
         * @param task the task folder that will be filled in if the user
         * completes the wizard
         * @return the new model
         */
        WizardModel createModel(Task task);

        /**
         * Fills in an empty task folder using the information filled in by the
         * user in the pages of the provided model. This will be called when the
         * user finishes the wizard while this kit is active.
         *
         * @param model the previously created model that was presented to the
         * user
         * @param task the empty task folder to be filled in
         * @throws Exception if an error occurs while the task is being created
         * that prevents creation of the task
         */
        void createTask(WizardModel model, Task task) throws Exception;
    }

    /**
     * Registers a new kit that can be used to create plug-in tasks.
     *
     * @param kit the kit instance for the new project type
     */
    public static void registerWizardKit(WizardKit kit) {
        if (kit == null) {
            throw new NullPointerException("kit");
        }
        kits.add(kit);
    }

    /**
     * Unregisters a kit that was previously registered.
     *
     * @param kit the kit instance for the new project type
     */
    public static void unregisterWizardKit(WizardKit kit) {
        kits.remove(kit);
    }

    /**
     * A sorted set of the registered kits.
     */
    private static final Set<WizardKit> kits = new HashSet<>();

    private final Comparator<PluginWizardDialog.WizardKit> sorter = (WizardKit o1, WizardKit o2) -> Language.getInterface().getCollator().compare(o1.getName(), o2.getName());

    static {
        registerWizardKit(new SkeletonKit());
    }
}
