package window;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ModelTableMenu extends AbstractTableModel {
    private final String[] columnNames = {"Identifiant", "Description", "Prix", "Poids", "Temps de préparation"};
    private final Class<?>[] columnClasses = {int.class, String.class, Float.class, Float.class, Integer.class};
    private final ArrayList<Menu> menus;

    public ModelTableMenu(ArrayList<Menu> menus) {
        this.menus = menus;
    }

    @Override
    public int getRowCount() {
        return menus.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClasses[columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false; // No cells are editable
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Menu menu = menus.get(rowIndex);
        switch (columnIndex) {
            case 0: return menu.getId();
            case 1: return menu.getDescription();
            case 2: return menu.getPrix();
            case 3: return menu.getPoids();
            case 4: return menu.getTempPrepa();
            default: throw new IndexOutOfBoundsException("Invalid column index");
        }
    }
}