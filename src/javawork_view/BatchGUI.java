package javawork_view;

import javawork_model.Item;
import javawork_service.CategoryService;
import javawork_service.CompanyService;
import javawork_service.ItemService;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Operation GUI for adding, deleting, searching
 * @author Jiawei Huang
 */
public class BatchGUI extends JFrame {
    private JPanel contentPane;
    private JScrollPane scrollPane;
    private JTable table;
    private JButton saveButton;
    private JButton searchButton;
    private JButton removeButton;

    /**
     * Constructor.
     */
    public BatchGUI() {
        setTitle("Item Maintenance Batch");
        setBounds(220,100,1000, 688); //set Frame's position and size
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);
        String[] head = new String[]{"UPC-code", "Item Description", "Category", "Unit Price", "Unit Size",
                "Monthly Sales", "Company"};


        DefaultTableModel tableModel = new DefaultTableModel(null, head);
        tableModel.addRow(new Object[]{""});
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
        TableCellEditor tce =  table.getDefaultEditor(JTable.class);
        tce.addCellEditorListener(new CellEditorListener() {
            @Override
            public void editingStopped(ChangeEvent e) {
                if(table.getSelectedColumn() == 0) { //check if it is the first column
                    int row = table.getSelectedRow();
                    String upc = table.getValueAt(row, 0).toString();
                    if(upc.equals("")) {
                        JOptionPane.showMessageDialog(null, "UPC code can not be NONE", "Error",
                                JOptionPane.ERROR_MESSAGE, new ImageIcon("src/image/cancel-48.png"));
                        table.editCellAt(row,0);
                    } else {
                        //fill the data rows by upc
                        Item item = ItemService.getDataByUpc(upc);
                        if(item != null) {
                            table.setValueAt(item.getDescription(), row, 1);
                            String categoryName = item.getCategory_id() + "-" + CategoryService.getCategoryMap().
                                    get(item.getCategory_id()).getCategory_name();
                            table.setValueAt(categoryName, row, 2);
                            table.setValueAt(item.getUnit_price(), row, 3);
                            table.setValueAt(item.getUnit_size(), row, 4);
                            table.setValueAt(item.getMonthly_sales(), row, 5);
                            String companyName = item.getCompany_id() + "-" + CompanyService.getCompanyMap().
                                    get(item.getCompany_id()).getCompany_name();
                            table.setValueAt(companyName, row, 6);
                        }
                    }
                }
            }

            @Override
            public void editingCanceled(ChangeEvent e) {

            }
        });
        //set category drop-down cell
        String[] category = {"1-GROCERIES", "2-SALT", "3-SUGAR", "4-MEAL DELI",
        "5-PRODUCE", "6-SODA", "7-BEER", "8-WINE", "9-OIL"};
        JComboBox categoryBox = new JComboBox(category);
        DefaultCellEditor editor = new DefaultCellEditor(categoryBox);
        table.getColumnModel().getColumn(2).setCellEditor(editor);

        //set company drop-down cell
        CompanyService companyService = new CompanyService();
        String[] companyNameArray = companyService.getCompanyName();
        JComboBox companyBox = new JComboBox(companyNameArray);
        DefaultCellEditor companyEditor = new DefaultCellEditor(companyBox);
        table.getColumnModel().getColumn(6).setCellEditor(companyEditor);

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
                        List<Item> itemList = new ArrayList<Item>();
                        for(int i = 0; i < row; i++) {
                            Item item = new Item();
                            item.setUpc_code(table.getValueAt(i,0).toString());
                            item.setDescription(table.getValueAt(i,1).toString());
                            item.setCategory_id(Character.getNumericValue(table.getValueAt(i,2).toString().charAt(0)));
                            item.setUnit_price(Float.parseFloat(table.getValueAt(i,3).toString()));
                            item.setUnit_size(Float.parseFloat(table.getValueAt(i,4).toString()));
                            item.setMonthly_sales(Integer.parseInt(table.getValueAt(i,5).toString()));
                            item.setCompany_id(Character.getNumericValue(table.getValueAt(i,6).toString().charAt(0)));
                            itemList.add(item);
                        }
                        if(ItemService.insertData(itemList)) { //if insert successfully
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

        //create searchButton
        searchButton = new JButton();
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(0,2,2,2));
                JTextField contain = new JTextField();
                JTextField upcStart = new JTextField();
                JTextField upcEnd = new JTextField();

                panel.add(new JLabel("Information Contained"));
                panel.add(contain);
                panel.add(new JLabel("UPC-CODE"));
                panel.add(upcStart);
                panel.add(new JLabel(" to "));
                panel.add(upcEnd);
                panel.add(new JLabel("Category"));
                categoryBox.setSelectedIndex(-1); //not select the first item
                panel.add(categoryBox);
                panel.add(new JLabel("Company"));
                companyBox.setSelectedIndex(-1);
                panel.add(companyBox);

                int result = JOptionPane.showConfirmDialog(null, panel, "Search Information",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE,
                        new ImageIcon("src/image/search-48.png"));
                if(result == JOptionPane.OK_OPTION) {
                    EventQueue.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            String containText = contain.getText();
                            String upcStartText = upcStart.getText();
                            String upcEndText = upcEnd.getText();
                            String category_id = null;
                            String company_id = null;
                            if(categoryBox.getSelectedIndex() != -1) {
                                category_id = String.valueOf(categoryBox.getSelectedItem().toString().charAt(0));
                            }
                            if(companyBox.getSelectedIndex() != -1) {
                                company_id = String.valueOf(companyBox.getSelectedItem().toString().charAt(0));
                            }
                            Object[][] data = ItemService.searchData(containText, upcStartText,
                                    upcEndText, category_id, company_id);
                            tableModel.setDataVector(data, head);
                            table.getColumnModel().getColumn(2).setCellEditor(editor);
                            table.getColumnModel().getColumn(6).setCellEditor(companyEditor);
                        }
                    });
                }
            }
        });
        searchButton.setBounds(70,5,40,50);
        searchButton.setIcon(new ImageIcon("src/image/search-48.png"));
        searchButton.setBorderPainted(false);
        searchButton.setToolTipText("search");
        contentPane.add(searchButton);

        //create removeButton
        removeButton = new JButton();
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        int[] selectRows = table.getSelectedRows();
                        List<String> upcList = new ArrayList<>();
                        for(int i : selectRows) {
                            String upc = table.getValueAt(i,0).toString();
                            upcList.add(upc);
                        }
                        if(ItemService.deleteData(upcList)) {
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
