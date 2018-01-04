// License: GPL. For details, see LICENSE file.
package buildings_tools;

import static org.openstreetmap.josm.tools.I18n.tr;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JRadioButton;

import org.openstreetmap.josm.tools.GBC;

@SuppressWarnings("serial")
public class BuildingSizeDialog extends MyDialog {
    private final JFormattedTextField twidth = new JFormattedTextField(NumberFormat.getInstance());
    private final JFormattedTextField tlenstep = new JFormattedTextField(NumberFormat.getInstance());
    private final JCheckBox caddr = new JCheckBox(tr("Use Address dialog"));
    private final JCheckBox cAutoSelect = new JCheckBox(tr("Auto-select building"));
    private final JCheckBox cAddrNode = new JCheckBox(tr("Use address nodes under buildings"));
    private final JRadioButton circleRadio = new JRadioButton("Circle");
    private final JRadioButton rectangleRadio = new JRadioButton("Rectangle");

    public BuildingSizeDialog() {
        super(tr("Set buildings size and shape"));

        ButtonGroup shapeGroup = new ButtonGroup();
        shapeGroup.add(circleRadio);
        shapeGroup.add(rectangleRadio);
        if (ToolSettings.Shape.CIRCLE.equals(ToolSettings.getShape())) {
            circleRadio.setSelected(true);
        } else {
            rectangleRadio.setSelected(true);
        }
        panel.add(rectangleRadio, GBC.eol().fill(GBC.HORIZONTAL));
        panel.add(circleRadio, GBC.eol().fill(GBC.HORIZONTAL));

        addLabelled(tr("Buildings width/diameter:"), twidth);
        addLabelled(tr("Length step:"), tlenstep);
        panel.add(caddr, GBC.eol().fill(GBC.HORIZONTAL));
        panel.add(cAutoSelect, GBC.eol().fill(GBC.HORIZONTAL));
        panel.add(cAddrNode, GBC.eol().fill(GBC.HORIZONTAL));

        twidth.setValue(ToolSettings.getWidth());
        tlenstep.setValue(ToolSettings.getLenStep());
        caddr.setSelected(ToolSettings.isUsingAddr());
        cAutoSelect.setSelected(ToolSettings.isAutoSelect());
        cAddrNode.setSelected(ToolSettings.PROP_USE_ADDR_NODE.get());

        JButton bAdv = new JButton(tr("Advanced..."));
        bAdv.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                AdvancedSettingsDialog dlg = new AdvancedSettingsDialog();
                if (dlg.getValue() == 1) {
                    dlg.saveSettings();
                }
            }
        });
        panel.add(bAdv, GBC.eol().insets(0, 5, 0, 0).anchor(GBC.EAST));

        setupDialog();
        showDialog();
    }

    public final double width() {
        try {
            return NumberFormat.getInstance().parse(twidth.getText()).doubleValue();
        } catch (ParseException e) {
            return 0;
        }
    }

    public double lenstep() {
        try {
            return NumberFormat.getInstance().parse(tlenstep.getText()).doubleValue();
        } catch (ParseException e) {
            return 0;
        }
    }

    public final boolean useAddr() {
        return caddr.isSelected();
    }

    public final void saveSettings() {
        if (circleRadio.isSelected()) {
            ToolSettings.setShape(ToolSettings.Shape.CIRCLE);
            ToolSettings.saveShape("CIRCLE");
        } else if (rectangleRadio.isSelected()) {
            ToolSettings.setShape(ToolSettings.Shape.RECTANGLE);
            ToolSettings.saveShape("RECTANGLE");
        }
        ToolSettings.setSizes(width(), lenstep());
        ToolSettings.setAddrDialog(useAddr());
        ToolSettings.setAutoSelect(cAutoSelect.isSelected());
        ToolSettings.PROP_USE_ADDR_NODE.put(cAddrNode.isSelected());
    }
}
