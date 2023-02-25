package domtoapp;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class SimpleTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private final String[] columnNames;
    private List<String[]> data; 
    private String name;
    private String prjName;
    
    public SimpleTableModel(String name, String prjName, String[] pColumnNames, List<String[]> pData) {
    	this.name = name;
    	this.prjName = prjName;
    	this.columnNames = pColumnNames;
        this.data = pData;
        this.fireTableDataChanged();
    }
       

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        return data.get(row)[col];
    }
    
	public List<String[]> getData() {
		return data;
	}

	public void setData(List<String[]> data) {
		this.data = data;
	}

	public String[] getColumnNames() {
		return columnNames;
	}

	public String getName() {
		return this.name;
	}

	public String getPrjName() {
		return this.prjName;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("SimpleTableModel: name=" + this.name + ", prjName=" + this.prjName + ", columnNames=");
		for (String s : this.columnNames) {
			sb.append(s + ", ");
		}
		sb.append("data=");
		for (String[] s : this.data) {
			sb.append("[");
			for (String s2 : s) {
				sb.append(s2 + ", ");
			}
			sb.append("], ");
		}
		return sb.toString();
	}

}//end class

