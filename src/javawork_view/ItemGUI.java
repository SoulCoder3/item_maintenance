package javawork_view;

import javawork_service.ItemService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Main GUI for displaying items data
 * @author Jiawei Huang
 */
public class ItemGUI extends JFrame{
    private JPanel contentPane;
    private JTable table;
    private JScrollPane scrollPane;
    private JButton batchButton;
    private JButton refreshButton;
    private JButton companyButton;
    private JButton categoryButton;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                ItemGUI frame = new ItemGUI();
                frame.setVisible(true);
            }
        });
    }

    public ItemGUI() {
        setTitle("Item Maintenance");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(200,120,1000, 688); //set Frame's position and size
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);
        String[] head = new String[]{"UPC-code", "Item Description", "Category", "Unit Price", "Unit Size",
                "Monthly Sales", "Company", "Update Date"};
        Object[][] data = ItemService.fillData(head);

        DefaultTableModel tableModel = new DefaultTableModel(data, head);
        table = new JTable(tableModel);
        table.getTableHeader().setReorderingAllowed(false); //column cannot move
        table.setBorder(BorderFactory.createLineBorder(Color.black)); //set Border color
        table.setGridColor(Color.black); //set Gird Cell color
        table.setEnabled(false);


        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(14,55,960,600);
        contentPane.add(scrollPane);
        contentPane.setVisible(true);

        //set batch button
        batchButton = new JButton();
        batchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        BatchGUI batchGUI = new BatchGUI();
                        batchGUI.setVisible(true);
                    }
                });
            }
        });
        batchButton.setBounds(15, 5, 35,50);
        batchButton.setIcon(new ImageIcon("src/image/document-48.png"));
        batchButton.setBorderPainted(false); //no border
        batchButton.setToolTipText("new batch"); //set tip text
        contentPane.add(batchButton);

        //create companyButton
        companyButton = new JButton();
        companyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        CompanyGUI companyGUI = new CompanyGUI();
                        companyGUI.setVisible(true);
                    }
                });
            }
        });
        companyButton.setBounds(70,5,50,50);
        companyButton.setIcon(new ImageIcon("src/image/company-48.png"));
        companyButton.setBorderPainted(false);
        companyButton.setToolTipText("company");
        contentPane.add(companyButton);

        //create categoryButton
        categoryButton = new JButton();
        categoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        });
        categoryButton.setBounds(125,5,50,50);
        categoryButton.setIcon(new ImageIcon("src/image/category-48.png"));
        categoryButton.setBorderPainted(false);
        categoryButton.setToolTipText("category");
        contentPane.add(categoryButton);

        //create refreshButton
        refreshButton = new JButton();
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        Object[][] data = ItemService.fillData(head);
                        DefaultTableModel tableModel = new DefaultTableModel(data,head);
                        table.setModel(tableModel);
                    }
                });
            }
        });
        refreshButton.setBounds(180,5,50,50);
        refreshButton.setIcon(new ImageIcon("src/image/refresh-48.png"));
        refreshButton.setBorderPainted(false);
        refreshButton.setToolTipText("refresh");
        contentPane.add(refreshButton);
    }
}
