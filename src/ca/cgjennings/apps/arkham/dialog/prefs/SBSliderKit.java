package ca.cgjennings.apps.arkham.dialog.prefs;

import ca.cgjennings.apps.arkham.diy.SettingBackedControl;
import javax.swing.event.ChangeEvent;

/**
 * Creates a slider that implements the {@link SettingBackedControl} interface
 * and follows the layout of sliders that appear in the standard preference
 * categories.
 *
 * @author Chris Jennings <https://cgjennings.ca/contact>
 */
@SuppressWarnings("serial")
public class SBSliderKit extends javax.swing.JPanel implements SettingBackedControl {

    /**
     * Creates a setting backed slider for use in a {@link PreferenceCategory}.
     *
     * @param min the minimum slider value
     * @param max the maximum slider value
     * @param stepSize the major tick spacing, or -1 to not show ticks
     * @param minorStepSize the minor tick spacing
     * @param leftSideText the label for the left end, or {@code null}
     * @param rightSideText the label for the right end, or {@code null}
     * @param showSliderValue if {@code true}, a label beneath the slider will
     * be updated as the slider value changes
     */
    public SBSliderKit(int min, int max, int stepSize, int minorStepSize, String leftSideText, String rightSideText, boolean showSliderValue) {
        initComponents();
        FillInPreferenceCategory.style(this);

        slider.setMinimum(min);
        slider.setMaximum(max);
        if (stepSize < 0) {
            slider.setPaintTicks(false);
        } else {
            slider.setMajorTickSpacing(stepSize);
            slider.setMinorTickSpacing(minorStepSize);
        }

        if (leftSideText == null) {
            lhsLabel.setVisible(false);
        } else {
            lhsLabel.setText(leftSideText);
        }
        if (rightSideText == null) {
            rhsLabel.setVisible(false);
        } else {
            rhsLabel.setText(rightSideText);
        }

        if (!showSliderValue) {
            updateLabel.setVisible(false);
        } else {
            slider.addChangeListener((ChangeEvent e) -> {
                updateSliderValue(slider.getValue());
            });
        }

        slider.setSnapToTicks(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        slider = new javax.swing.JSlider();
        updateLabel = new javax.swing.JLabel();
        rhsLabel = new javax.swing.JLabel();
        lhsLabel = new javax.swing.JLabel();

        setBackground(java.awt.Color.white);
        setLayout(new java.awt.BorderLayout());

        slider.setBackground(java.awt.Color.white);
        slider.setFont(slider.getFont().deriveFont(slider.getFont().getSize()-1f));
        slider.setMajorTickSpacing(25);
        slider.setMinorTickSpacing(5);
        slider.setPaintTicks(true);
        slider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderStateChanged(evt);
            }
        });
        add(slider, java.awt.BorderLayout.CENTER);

        updateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        updateLabel.setText("Update Label");
        updateLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 4, 4, 4));
        add(updateLabel, java.awt.BorderLayout.PAGE_END);

        rhsLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        rhsLabel.setText("<html>Right Hand<br>Side");
        rhsLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 8, 0, 4));
        add(rhsLabel, java.awt.BorderLayout.LINE_END);

        lhsLabel.setText("<html>Left Hand<br>Side");
        lhsLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 0, 8));
        add(lhsLabel, java.awt.BorderLayout.LINE_START);
    }// </editor-fold>//GEN-END:initComponents

	private void sliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderStateChanged
            updateLabel.setText(updateSliderValue(slider.getValue()));
	}//GEN-LAST:event_sliderStateChanged

    /**
     * Called to let the subclass update the label beneath the slider that
     * describes its current value.
     *
     * @param value the slider value
     * @return the text to use to describe the slider value
     */
    public String updateSliderValue(int value) {
        return "" + value;
    }

    /**
     * Called to convert a setting value into the initial slider state.
     * Subclasses may need to override this to map the setting's number system
     * to the slider's.
     *
     * @param v the settiing value
     */
    @Override
    public void fromSetting(String v) {
        int val = slider.getMinimum();
        try {
            val = Integer.valueOf(v);
        } catch (NumberFormatException e) {
        }

        val = Math.max(slider.getMinimum(), Math.min(slider.getMaximum(), val));
        slider.setValue(val);
    }

    @Override
    public String toSetting() {
        return "" + slider.getValue();
    }

    public javax.swing.JSlider getSlider() {
        return slider;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel lhsLabel;
    public javax.swing.JLabel rhsLabel;
    public javax.swing.JSlider slider;
    public javax.swing.JLabel updateLabel;
    // End of variables declaration//GEN-END:variables

}
