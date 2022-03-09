package ca.cgjennings.apps.arkham.plugins.catalog;

import ca.cgjennings.apps.arkham.StrangeEons;
import ca.cgjennings.apps.arkham.plugins.BundleInstaller;
import ca.cgjennings.apps.arkham.plugins.InstalledBundleObject;
import ca.cgjennings.apps.arkham.plugins.PluginBundle;
import ca.cgjennings.apps.arkham.plugins.PluginRoot;
import ca.cgjennings.ui.dnd.FileDrop;
import ca.cgjennings.ui.dnd.ScrapBook;
import ca.cgjennings.ui.table.IconRenderer;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;
import javax.swing.Icon;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import static resources.Language.string;
import resources.ResourceKit;

/**
 * A table control that is used to build a list of Catalog IDs. Only one ID with
 * a given UUID is allowed (the newest). To observe changes to the list, listen
 * for changes to the {@link #LIST_MODIFIED_PROPERTY} property.
 *
 * @author Chris Jennings <https://cgjennings.ca/contact>
 * @since 3.0
 */
@SuppressWarnings("serial")
public class CatalogIDListPanel extends javax.swing.JPanel {

    /**
     * A property that changes when entries are added to or removed from the
     * list. When an ID is removed, the new value will be {@code null}. When an
     * ID is added or replaced, the new value will be the the new ID.
     */
    public static final String LIST_MODIFIED_PROPERTY = "cid-list-modified";

    /**
     * Creates new form CatalogIDListPanel
     */
    public CatalogIDListPanel() {
        initComponents();

        TableColumnModel tcm = idTable.getColumnModel();
        final TableColumn STATUS = tcm.getColumn(COL_STATUS);
        STATUS.setCellRenderer(new IconRenderer());
        STATUS.setResizable(false);
        STATUS.setMinWidth(20);
        STATUS.setMaxWidth(20);
        STATUS.setPreferredWidth(20);
        STATUS.setWidth(20);
        tcm.getColumn(COL_DATE).setResizable(false);

        ListSelectionListener li = (ListSelectionEvent e) -> {
            int[] rows = idTable.getSelectedRows();
            boolean enable = rows.length > 0;
            cutBtn.setEnabled(enable);
            copyBtn.setEnabled(enable);
        };
        idTable.getSelectionModel().addListSelectionListener(li);
        li.valueChanged(null);

        // listen for root/bundle files dropped on the control
        new FileDrop(this, tableScroll, true, (File[] files) -> {
            if (files == null) {
                return;
            }
            for (File f : files) {
                try {
                    PluginRoot root = null;
                    if (f.getName().equals("eons-plugin")) {
                        root = new PluginRoot(f);
                    } else if (PluginBundle.getBundleType(f) != PluginBundle.TYPE_UNKNOWN) {
                        PluginBundle pb = new PluginBundle(f);
                        root = pb.getPluginRoot();
                    }

                    if (root != null) {
                        CatalogID id = root.getCatalogID();
                        if (id != null) {
                            addID(id);
                            continue;
                        }
                    }
                    // Never reach here if a valid ID was obtained
                } catch (IOException e) {
                    StrangeEons.log.log(Level.WARNING, "exception while parsing ID: ", e);
                }
                UIManager.getLookAndFeel().provideErrorFeedback(idTable);
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tableScroll = new javax.swing.JScrollPane();
        idTable = new ca.cgjennings.ui.table.JHeadlessTable();
        jPanel1 = new javax.swing.JPanel();
        cutBtn = new javax.swing.JButton();
        copyBtn = new javax.swing.JButton();
        pasteBtn = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        idTable.setModel( model );
        idTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        idTable.setColumnResizable(true);
        idTable.setFont(idTable.getFont().deriveFont(idTable.getFont().getSize()-1f));
        tableScroll.setViewportView(idTable);

        add(tableScroll, java.awt.BorderLayout.CENTER);

        cutBtn.setIcon(ResourceKit.getIcon("cut").medium());
        cutBtn.setToolTipText(string("prj-re-cut-cat")); // NOI18N
        cutBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cutBtnActionPerformed(evt);
            }
        });

        copyBtn.setIcon(ResourceKit.getIcon("copy").medium());
        copyBtn.setToolTipText(string("prj-re-copy-cat")); // NOI18N
        copyBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyBtnActionPerformed(evt);
            }
        });

        pasteBtn.setIcon(ResourceKit.getIcon("paste").medium());
        pasteBtn.setToolTipText(string("prj-re-paste-cat")); // NOI18N
        pasteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pasteBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(cutBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(copyBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pasteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cutBtn)
                .addGap(2, 2, 2)
                .addComponent(copyBtn)
                .addGap(2, 2, 2)
                .addComponent(pasteBtn)
                .addContainerGap(111, Short.MAX_VALUE))
        );

        add(jPanel1, java.awt.BorderLayout.EAST);
    }// </editor-fold>//GEN-END:initComponents

	private void cutBtnActionPerformed( java.awt.event.ActionEvent evt ) {//GEN-FIRST:event_cutBtnActionPerformed
            copyBtnActionPerformed(null);
            int[] rows = idTable.getSelectedRows();
            Arrays.sort(rows);
            for (int i = rows.length - 1; i >= 0; --i) {
                removeRow(i);
            }
	}//GEN-LAST:event_cutBtnActionPerformed

	private void copyBtnActionPerformed( java.awt.event.ActionEvent evt ) {//GEN-FIRST:event_copyBtnActionPerformed
            int[] rows = idTable.getSelectedRows();
            Arrays.sort(rows);
            StringBuilder b = new StringBuilder();
            for (int i = 0; i < rows.length; ++i) {
                CatalogID id = ids.get(i).id;
                if (b.length() > 0) {
                    b.append(", ");
                }
                b.append(id);
            }
            ScrapBook.setText(b.toString());
	}//GEN-LAST:event_copyBtnActionPerformed

	private void pasteBtnActionPerformed( java.awt.event.ActionEvent evt ) {//GEN-FIRST:event_pasteBtnActionPerformed
            String t = ScrapBook.getText();
            if (t == null) {
                return;
            }
            addFromString(t);
	}//GEN-LAST:event_pasteBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton copyBtn;
    private javax.swing.JButton cutBtn;
    private ca.cgjennings.ui.table.JHeadlessTable idTable;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton pasteBtn;
    private javax.swing.JScrollPane tableScroll;
    // End of variables declaration//GEN-END:variables

    private AbstractTableModel model = new AbstractTableModel() {
        @Override
        public int getRowCount() {
            return ids.size();
        }

        @Override
        public int getColumnCount() {
            return 3;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            CatalogIDLine id = ids.get(rowIndex);
            switch (columnIndex) {
                case COL_ID:
                    return id.toString();
                case COL_DATE:
                    return id.toDate();
                case COL_STATUS:
                    return id.getStatus();
                default:
                    throw new AssertionError();
            }
        }
    };
    private static final int COL_ID = 0, COL_DATE = 1, COL_STATUS = 2;
    private ArrayList<CatalogIDLine> ids = new ArrayList<>();
    private HashMap<UUID, InstalledBundleObject> descCache = new HashMap<>();
    private CatalogID forbidden = null;

    private class CatalogIDLine {

        private CatalogID id;
        private int mode;
        private final InstalledBundleObject ibo;

        public CatalogIDLine(CatalogID id) {
            this.id = id;
            ibo = getIBO(id.getUUID());
            if (forbidden != null && id.sameUUID(forbidden)) {
                mode = 1;
            }
        }

        public Icon getStatus() {
            if (ibo == null) {
                return NOT_INSTALLED;
            }
            CatalogID iid = ibo.getCatalogID();
            if (iid == null || iid.isOlderThan(id)) {
                return ROOT_NEWER;
            }
            if (id.isOlderThan(iid)) {
                return INSTALLED_NEWER;
            }
            return MATCHES_INSTALLED;
        }

        public String toDate() {
            String s = id.getFormattedDate();
            return colorize(s);
        }

        @Override
        public String toString() {
            String s = ibo != null ? ibo.toString() : id.getUUID().toString();
            return colorize(s);
        }

        private String colorize(String s) {
            if (mode == 1) {
                s = "<html><font color='red'>" + s;
            } else if (mode == 2) {
                s = "<html><font color='blue'>" + s;
            } else if (mode == 3) {
                s = "<html><font color='grey'>" + s;
            }
            return s;
        }
    }

    private InstalledBundleObject getIBO(UUID uuid) {
        if (descCache.containsKey(uuid)) {
            return descCache.get(uuid);
        }
        InstalledBundleObject[] matches = BundleInstaller.getInstalledBundleObjectsForUUID(uuid);
        InstalledBundleObject ibo = matches.length == 0 ? null : matches[0];
        descCache.put(uuid, ibo);
        return ibo;
    }

    /**
     * Sets an ID whose UUID cannot be added to the list. For example, if you
     * were editing a root file's "requires" list, you'd forbid the bundle's own
     * ID from being required.
     *
     * @param forbid the ID to forbid
     */
    public void setForbiddenID(CatalogID forbid) {
        forbidden = forbid;
        for (int i = 0; i < ids.size(); ++i) {
            if (ids.get(i).id.sameUUID(forbid)) {
                removeRow(i);
                break;
            }
        }
    }

    private void removeRow(int i) {
        CatalogIDLine line = ids.get(i);
        ids.remove(i);
        model.fireTableRowsDeleted(i, i);
        firePropertyChange(LIST_MODIFIED_PROPERTY, line.id, null);
    }

    /**
     * Adds this ID to the table, or if an ID with the same UUID is already
     * present, sets the date to the newer of this ID and the existing one.
     *
     * @param id the ID to add
     * @return the index at which the replace/add took place
     */
    public int addID(CatalogID id) {
        if (id == null) {
            throw new NullPointerException("id");
        }
        CatalogIDLine newLine = new CatalogIDLine(id);
        if (newLine.mode == 0) {
            for (int i = 0; i < ids.size(); ++i) {
                CatalogIDLine line = ids.get(i);
                CatalogID x = line.id;
                if (!x.sameUUID(id)) {
                    continue;
                }
                int cmp = x.compareDates(id);
                if (cmp < 0) {
                    newLine.mode = 3;
                } else if (cmp > 0) {
                    newLine.mode = 2;
                }
                ids.set(i, newLine);
                model.fireTableRowsUpdated(i, i);
                firePropertyChange(LIST_MODIFIED_PROPERTY, x, id);
                return i;
            }
        }
        int newRow = ids.size();
        ids.add(newLine);
        model.fireTableRowsInserted(newRow, newRow);
        firePropertyChange(LIST_MODIFIED_PROPERTY, null, id);
        return newRow;
    }

    /**
     * Clear all IDs in the panel.
     */
    public void clearIDs() {
        int size = ids.size();
        if (size == 0) {
            return;
        }

        // remove IDs one row at a time so that the
        // appropriate property change events fire
        for (int i = size - 1; i >= 0; --i) {
            removeRow(i);
        }
    }

    /**
     * Adds IDs from a comma-separated list of IDs (catalog listing format).
     *
     * @param s the list of IDs to add
     */
    public void addFromString(String s) {
        if (s == null) {
            return; // empty catalog value
        }
        for (String sid : s.split("\\s*,\\s*")) {
            CatalogID id = CatalogID.extractCatalogID(sid);
            if (id == null) {
                try {
                    UUID uuid = UUID.fromString(sid);
                    id = new CatalogID(uuid);
                } catch (IllegalArgumentException e) {
                }
            }
            if (id != null) {
                addID(id);
            }
        }
    }

    /**
     * Sets the panel's IDs to the values of a list.
     *
     * @param s
     */
    public void initFromString(String s) {
        clearIDs();
        addFromString(s);
    }

    /**
     * Returns the IDs in the panel as a string in catalog listing format.
     *
     * @return the catalog IDs
     */
    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < ids.size(); ++i) {
            if (i > 0) {
                b.append(", ");
            }
            b.append(ids.get(i).id);
        }
        return b.toString();
    }

    /**
     * Identical to {@link #toString()}, but returns {@code null} if the list is
     * empty.
     *
     * @return a string suitable for setting in a catalog listing
     */
    public String toCatalogString() {
        if (ids.isEmpty()) {
            return null;
        }
        String s = toString();
        return s.isEmpty() ? null : s; // defensive, should not be empty
    }

    private Icon NOT_INSTALLED = ResourceKit.getIcon("catalog/not-installed.png");
    private Icon MATCHES_INSTALLED = ResourceKit.getIcon("catalog/up-to-date.png");
    private Icon INSTALLED_NEWER = ResourceKit.getIcon("catalog/update-available.png");
    private Icon ROOT_NEWER = ResourceKit.getIcon("catalog/installed-is-newer.png");
}
