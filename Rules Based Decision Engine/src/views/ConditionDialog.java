package views;

import controllers.EntityController;
import controllers.RuleController;
import models.Operator;
import models.*;
import services.EntityCollectionService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static models.Operator.*;

/**
 * This Dialog object allows for the creation of the body of the Condition/Constraints
 * At the moment, it allows for a single Constraint in a single Condition, just as proof
 * of concept.
 *
 * @author Michael Crinite and Ian Markind
 * @version 1.0 11/27/16
 */
public class ConditionDialog extends JDialog
{
    //Components
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox entityBox;
    private JComboBox keyBox;
    private JComboBox operatorBox;
    private JTextField valueTxt;

    //Fields
    private EntityController entityController = EntityController.getINSTANCE();
    private RuleController rc = RuleController.getInstance();

    /**
     * Creates a new ConditionDialog instance
     */
    public ConditionDialog()
    {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        //Populate entityBox
        entityBox.addItem("<Select Entity>");
        for (String s : EntityCollectionService.getInstance().getMap().keySet())
        {
            entityBox.addItem(s);
        }

        //Populate entityBox with Entities which may be created duringn the execution of other ruless
        for (String s : EntityCollectionService.getInstance().getMapFromActions().keySet())
        {
            entityBox.addItem(s);
        }

        //Populate keyBox
        keyBox.addItem("No Data Imported");

        //Populate OperatorBox
        //operatorBox = new JComboBox(Operator.values()); <- For some reason this was not working
        for (Operator o : Operator.values())
        {
            // Convert from operator to unicode
            switch (o)
            {
                case GREATER_EQUAL:
                    operatorBox.addItem("\u2265");
                    break;
                case LESS_EQUAL:
                    operatorBox.addItem("\u2264");
                    break;
                case NOT_EQUAL:
                    operatorBox.addItem("\u2260");
                    break;
                case EQUAL_TO:
                    operatorBox.addItem("=");
                    break;
                default:
                    operatorBox.addItem(o);
            }
        }//Todo: improve if possible

        //valueBox will be user-input

        // ActionListeners
        buttonOK.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                onCancel();
            }
        });

        entityBox.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent itemEvent)
            {
                keyBox.removeAllItems();
                if (entityBox.getSelectedItem().equals("<Select Entity>"))
                {
                    keyBox.addItem("<Select Entity>");
                }
                else
                {
                    String s = (String) entityBox.getSelectedItem();
                    for (String str : entityController.retrieveFields(s))
                    {
                        keyBox.addItem(str);
                    }

                }
            }
        });

        //call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /**
     * To be called when the OK button is clicked.
     * Creates the fields of the rule from user input/selections
     */
    private void onOK()
    {
        if (entityBox.getSelectedItem().equals("<Select Entity>") || keyBox.getSelectedItem().equals("<Select Entity>") || keyBox.getSelectedItem().equals("No Data Imported"))
        {
            JOptionPane.showMessageDialog(null, "Please select or import an Entity", "Incomplete Fields", JOptionPane.ERROR_MESSAGE);
        }
        else if (valueTxt.getText().replaceAll("\\s+", "").equals("") // If it's only spaces
            || valueTxt.getText().equals("")) // If it's an empty String
        {
            JOptionPane.showMessageDialog(null, "Please input a value to compare to.", "Incomplete Fields", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            String entity = (String) entityBox.getSelectedItem();
            String field = (String) keyBox.getSelectedItem();
            Operator o;
            String s;
            //Convert from unicode to operator
            if (operatorBox.getSelectedItem() instanceof String)
            {
                s = (String) operatorBox.getSelectedItem();
                switch (s)
                {
                    case "\u2265":
                        o = GREATER_EQUAL;
                        break;
                    case "\u2264":
                        o = LESS_EQUAL;
                        break;
                    case "\u2260":
                        o = NOT_EQUAL;
                        break;
                    case "=":
                        o = EQUAL_TO;
                        break;
                    default:
                        o = (Operator) operatorBox.getSelectedItem();
                }
            }
            else
            {
                o = (Operator) operatorBox.getSelectedItem();
            }

            String value = valueTxt.getText().trim().replaceAll("\\s+", "_");

            ConditionalElement ce = rc.addConditionalElement(rc.addConstraintList(rc.addConstraint(entity + "." + field, o, value)));

            // PlusNewRuleDialog.cel = new ConditionalElementList();
            PlusNewRuleDialog.cel.add(ce);
            PlusNewRuleDialog.entitylist.add(entity);
            dispose();
        }
    }

    /**
     * Disposes of the window and forgets data input
     */
    private void onCancel()
    {
        dispose();
    }

    {
        // GUI initializer generated by IntelliJ IDEA GUI Designer
        // >>> IMPORTANT!! <<<
        // DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$()
    {
        contentPane = new JPanel();
        contentPane.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1, true, false));
        panel1.add(panel2, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonOK = new JButton();
        buttonOK.setText("OK");
        panel2.add(buttonOK, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonCancel = new JButton();
        buttonCancel.setText("Cancel");
        panel2.add(buttonCancel, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 4, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel3, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        entityBox = new JComboBox();
        panel3.add(entityBox, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        keyBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        keyBox.setModel(defaultComboBoxModel1);
        panel3.add(keyBox, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        operatorBox = new JComboBox();
        panel3.add(operatorBox, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Entity");
        panel3.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Field");
        panel3.add(label2, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Value");
        panel3.add(label3, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        valueTxt = new JTextField();
        panel3.add(valueTxt, new com.intellij.uiDesigner.core.GridConstraints(1, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$()
    {
        return contentPane;
    }
}
