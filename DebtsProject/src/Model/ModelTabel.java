package Model;
import java.util.List;
import  Model.Debt.*;
import javax.swing.table.AbstractTableModel;

public class ModelTabel extends AbstractTableModel{
    private final List<ModelDebt> data;
    String kolom[] = {"ID", "Penghoetang", "Pemberi", "Nominal", "Waktu", "Keterangan"};
    
    public ModelTabel(List<ModelDebt> data){
        this.data = data;
    }
    
    @Override
    public int getRowCount() {
       return data.size();
    }
    
    @Override
    public int getColumnCount(){
        return kolom.length;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex){
        return switch(columnIndex){
            case 0 -> data.get(rowIndex).getId();
            case 1 -> data.get(rowIndex).getPenerima().getNama();
            case 2 -> data.get(rowIndex).getPemberi().getNama();
            case 3 -> data.get(rowIndex).getNominal();
            case 4 -> data.get(rowIndex).getDate();
            case 5 -> data.get(rowIndex).getNote();
            default -> null;
        };
                
    }
    @Override
    public String getColumnName(int columnIndex){
        return kolom[columnIndex];
    }
}
