package QueryExecutor;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import BasicClassAccessDbase.conectToAccessDB;
import net.coderazzi.filters.gui.AutoChoices;
import net.coderazzi.filters.gui.TableFilterHeader;

public class QueryExecutorWindow extends JFrame {
    private JTextArea queryTextArea;
    private JTable resultsTable;
    private JButton executeButton;

    public QueryExecutorWindow() {
        setTitle("Query Executor");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeComponents();
    }

    private void initializeComponents() {
        queryTextArea = new JTextArea();
        queryTextArea.setLineWrap(true);
        queryTextArea.setWrapStyleWord(true);
        
        resultsTable = new JTable();
        
        executeButton = new JButton("Изпълни");
        executeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeQuery();
            }
        });

        setLayout(new BorderLayout());
        add(new JScrollPane(queryTextArea), BorderLayout.NORTH);
        add(new JScrollPane(resultsTable), BorderLayout.CENTER);
        add(executeButton, BorderLayout.SOUTH);
    }

    private void executeQuery() {
        String query = queryTextArea.getText();
        try (Connection connection = conectToAccessDB.conectionBDtoAccess();) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            updateTable(resultSet);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Грешка: " + ex.getMessage(), "Грешка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTable(ResultSet resultSet) throws SQLException {
        DefaultTableModel model = new DefaultTableModel();
        
        // Получаване на метаданни
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        // Добавяне на колонката за номера на реда
        model.addColumn("№");

        // Добавяне на колонките от ResultSet
        for (int i = 1; i <= columnCount; i++) {
            model.addColumn(metaData.getColumnName(i));
        }
        
        // Добавяне на редовете с поредния номер
        int rowNumber = 1; // Започнем от 1
        while (resultSet.next()) {
            Object[] row = new Object[columnCount + 1]; // +1 за номера на реда
            row[0] = rowNumber++; // задаване на пореден номер
            for (int i = 1; i <= columnCount; i++) {
                row[i] = resultSet.getObject(i);
            }
            model.addRow(row);
        }
        new TableFilterHeader(resultsTable, AutoChoices.ENABLED);
        resultsTable.setModel(model);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            QueryExecutorWindow window = new QueryExecutorWindow();
            window.setVisible(true);
        });
    }
}
