package views;

import controllers.Driver;
import controllers.EntityController;
import controllers.RuleController;
import models.ConditionalElementList;
import services.EntityCollectionService;
import services.ActionCollectionService;
import services.RuleCollectionService;
import services.SerializationService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

/**
 * Main GUI Menu for Rules Based Decision Engine
 * Currently supported:
 *  - Creating new Entities from the Import Data screen
 *  - Creating new Actions from the PlusNewActionDialog screen
 *  - Firing Rules using the Fire Rules button
 *  - Creating new Rules using the new Rule button
 *  - NOTE: THE SELECT RULESET FUNCTIONALITY IS NOT CURRENTLY SUPPORTED
 *
 * @author Michael Crinite
 * @author Wolf Team
 */
public class MainView
{
    //Components
    private JTable ruleTable;
    private JButton importDataButton;
    private JButton newActionButton;
    private JButton newRuleButton;
    private JButton selectRuleSetButton;
    private JButton fireRulesButton;
    private JPanel MainPanel;

    //Fields
    private DefaultTableModel ruleModel;
    private static boolean windows = false; //User OS
    private String rulesPath;


    /**
     * Creates an instance of MainView
     *
     * @param args Command line arguments (Empty String array can be passed)
     */
    public static void main(String[] args)
    {
        System.out.println(new File("").getAbsolutePath());
        JFrame frame = new JFrame("Rules-Based Decision Engine");
        frame.setContentPane(new MainView().MainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Set look and feel to system
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        frame.addWindowListener(new WindowListener()
        {
            @Override
            public void windowOpened(WindowEvent e)
            {
                boolean fixed = false; //TODO: fix serialization
                if(fixed)
                {
                    RuleCollectionService ruleService = RuleCollectionService.getInstance();
                    EntityCollectionService entityService = EntityCollectionService.getInstance();
                    entityService.toString();
                    ruleService.toString();
                    SerializationService serService = SerializationService.getInstance();
                    ruleService = (RuleCollectionService) serService.deserialize(new File("").getAbsolutePath() + "\\res\\Serialization\\Rules");
                    entityService = (EntityCollectionService) serService.deserialize((new File("").getAbsolutePath() + "\\res\\Serialization\\Entities"));
                }
            }

            @Override
            public void windowClosing(WindowEvent e)
            {
                boolean fixed = false; //TODO: fix serialization
                if(fixed) {
                    RuleCollectionService ruleService = RuleCollectionService.getInstance();
                    EntityCollectionService entityService = EntityCollectionService.getInstance();
                    entityService.toString();
                    ruleService.toString();
                    SerializationService serService = SerializationService.getInstance();
                    serService.serialize(ruleService, new File("").getAbsolutePath() + "\\res\\Serialization\\Rules");
                    serService.serialize(entityService, new File("").getAbsolutePath() + "\\res\\Serialization\\Entities");
                }
            }

            @Override
            public void windowClosed(WindowEvent e)
            {

            }

            @Override
            public void windowIconified(WindowEvent e)
            {

            }

            @Override
            public void windowDeiconified(WindowEvent e)
            {

            }

            @Override
            public void windowActivated(WindowEvent e)
            {

            }

            @Override
            public void windowDeactivated(WindowEvent e)
            {

            }
        });
    }

    /**
     * Creates an instance of the MainView window
     */
    public MainView()
    {
        // Determine user's OS
        if (System.getProperty("os.name").startsWith("Windows"))
        {
            windows = true;
            rulesPath = "./src/rules";
        }
        else
        {
            rulesPath = "./Rules Based Decision Engine/src/rules";
        }

        // Import Data button
        $$$setupUI$$$();
        importDataButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // The ImportDataDialog should handle the creation of the Entity object
                new ImportDataDialog();
            }
        });

        // + New Action button
        newActionButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // The PlusNewActionDialog handles the creation of the Action object
                new PlusNewActionDialog();
            }
        });

        // Select Rule Set Button
        selectRuleSetButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                //TODO: Rule sets
                //In the meantime, this button will just print the toStrings of everything stored at the moment
                System.out.println(EntityCollectionService.getInstance().toString());
                System.out.println(ActionCollectionService.getInstance().toString());
            }
        });

        //selectRuleSetButton.setVisible(false);

        // + Fire Rules button
        fireRulesButton.addActionListener(new ActionListener()
        {
            //TODO: Fire all drl files
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                RuleController.getInstance().fireAllRules(PlusNewRuleDialog.entitylist);

            }
        });


        // + New Rule button
        newRuleButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                PlusNewRuleDialog.cel = new ConditionalElementList();
                new PlusNewRuleDialog();
                DefaultTableModel model = (DefaultTableModel) ruleTable.getModel();
                model.setRowCount(0);
                for (Object o : RuleController.getInstance().getAllRules())
                {
                    String s = (String) o;
                    model.addRow(new String[]{s});
                }
            }
        });
    }

    /**
     * Rule table must be custom created
     */
    private void createUIComponents()
    {
        //Properties for ruleTable
        ruleModel = new DefaultTableModel(1, 1)
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
        ruleModel.setColumnIdentifiers(new String[]{"Rule Title"});

        ruleTable = new JTable(ruleModel);

        ruleTable.addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent me)
            {
                JTable table = (JTable) me.getSource();
                Point p = me.getPoint();
                int row = table.rowAtPoint(p);
                if (me.getClickCount() == 2 && row != -1)
                {
                    String title = (String) ruleTable.getValueAt(row, 0);
                    String rule = RuleController.getInstance().getRuleTextFromKey(title);
                    JOptionPane.showMessageDialog(null, rule, title, JOptionPane.PLAIN_MESSAGE);
                }
            }
        });
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
        createUIComponents();
        MainPanel = new JPanel();
        MainPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        MainPanel.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel1.add(scrollPane1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane1.setViewportView(ruleTable);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        MainPanel.add(panel2, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(panel3, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        newRuleButton = new JButton();
        newRuleButton.setText("+ New Rule");
        panel3.add(newRuleButton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        selectRuleSetButton = new JButton();
        selectRuleSetButton.setText("Select Rule Set");
        panel3.add(selectRuleSetButton, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(panel4, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        importDataButton = new JButton();
        importDataButton.setText("Import Data");
        panel4.add(importDataButton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        newActionButton = new JButton();
        newActionButton.setText("+ New Action");
        panel4.add(newActionButton, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(panel5, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        fireRulesButton = new JButton();
        fireRulesButton.setText("Fire Rules");
        panel5.add(fireRulesButton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$()
    {
        return MainPanel;
    }
}
