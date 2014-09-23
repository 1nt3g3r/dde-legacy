package ua.com.integer.dde.extension.localize.editor;

import javax.swing.table.AbstractTableModel;

import com.badlogic.gdx.utils.OrderedMap;

@SuppressWarnings("serial")
public class TranslationTableModel extends AbstractTableModel {
	private OrderedMap<String, String> translations;
	
	public TranslationTableModel(OrderedMap<String, String> translations) {
		this.translations = translations;
	}
	
	@Override
	public String getColumnName(int column) {
		if (column == 0) {
			return "Tag";
		} else {
			return "Value";
		}
	}
	
	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public int getRowCount() {
		return translations.size;
	}

	@Override
	public Object getValueAt(int row, int column) {
		if (column == 0) {
			return translations.orderedKeys().get(row);
		} else {
			return translations.get(translations.orderedKeys().get(row), translations.orderedKeys().get(row));
		} 
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex == 1;
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (columnIndex == 1) {
			translations.put(translations.orderedKeys().get(rowIndex), aValue.toString());
		}
	}
	
	
	public int getIndexOfTag(String tag) {
		return translations.orderedKeys().indexOf(tag, false);
	}
}
