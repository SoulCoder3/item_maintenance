package javawork_view;

import javawork_model.Company;
import javawork_service.CompanyService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Operation GUI for Company adding, deleting
 * @author Jiawei Huang
 */
public class CompanyGUI extends JFrame {
    private JPanel contentPane;
    private JScrollPane scrollPane;
    private JTable table;
    private JButton saveButton;
    private JButton removeButton;

    /**
     * Constructor.
     */
    public CompanyGUI() {
        setTitle("Company Information");
        setBounds(220,100,1000, 600); //set Frame's position and size
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);

        String[] head = new String[]{"company name", "company address", "phone number"};
        Object[][] data = CompanyService.getCompanyData(head);

        DefaultTableModel tableModel = new DefaultTableModel(data, head);
        table = new JTable(tableModel);
        table.getTableHeader().setReorderingAllowed(false); //column cannot move
        table.setBorder(BorderFactory.createLineBorder(Color.black)); //set Border color
        table.setGridColor(Color.black); //set Gird Cell color
        table.setCellSelectionEnabled(true);
        table.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(KeyEvent.VK_ENTER == e.getKeyCode()) {
                    if((table.getSelectedRow() + 1) == table.getRowCount()) { //check if it is the last cell
                        tableModel.addRow(new Object[]{""});
                        table.setModel(tableModel);
                        table.editCellAt(table.getSelectedRow()+1, 0);
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(14,55,960,386);
        contentPane.add(scrollPane);
        contentPane.setVisible(true);

        //create saveButton
        saveButton = new JButton();
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        int row = table.getRowCount();
                        List<Company> companyList = new ArrayList<Company>();
                        for(int i = 0; i < row; i++) {
                            Company company = new Company();
                            company.setCompany_name(table.getValueAt(i,0).toString());
                            company.setAddress(table.getValueAt(i,1).toString());
                            company.setPhone_number(table.getValueAt(i,2).toString());
                            companyList.add(company);
                        }
                        if(CompanyService.insertCompanyData(companyList)) {
                            JOptionPane.showMessageDialog(null, "Save Successfully", "Message",
                                    JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src/image/ok-48.png"));
                        } else {
                            JOptionPane.showMessageDialog(null, "Save Failed, " +
                                            "Please check if the data is complete", "Error",
                                    JOptionPane.ERROR_MESSAGE, new ImageIcon("src/image/cancel-48.png"));
                        }
                    }
                });
            }
        });
        saveButton.setBounds(15,5,35,50);
        saveButton.setIcon(new ImageIcon("src/image/save-all-48.png"));
        saveButton.setBorderPainted(false);
        saveButton.setToolTipText("save");
        contentPane.add(saveButton);

        //create removeButton
        removeButton = new JButton();
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        int[] selectRows = table.getSelectedRows();
                        List<String> companyList = new ArrayList<>();
                        for(int i : selectRows) {
                            String companyName = table.getValueAt(i,0).toString();
                            companyList.add(companyName);
                        }
                        if(CompanyService.deleteCompanyData(companyList)) {
                            JOptionPane.showMessageDialog(null, "Delete Successfully", "Message",
                                    JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src/image/ok-48.png"));
                            tableModel.removeRow(table.getSelectedRow());
                        } else {
                            JOptionPane.showMessageDialog(null, "Delete Failed", "Error",
                                    JOptionPane.ERROR_MESSAGE, new ImageIcon("src/image/cancel-48.png"));
                        }
                    }
                });
            }
        });
        removeButton.setBounds(120,5,40,50);
        removeButton.setIcon(new ImageIcon("src/image/trash-48.png"));
        removeButton.setBorderPainted(false);
        removeButton.setToolTipText("delete");
        contentPane.add(removeButton);
    }
}
