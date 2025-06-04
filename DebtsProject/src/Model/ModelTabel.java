package Model;
import java.util.List;
import  Model.Debt.*;

public class ModelTabel {
    private final List<ModelDebt> data;
    String kolom[] = {"ID", "Penerima", "Pemberi", "Nominal", "Waktu", "Keterangan"};
    
    public ModelTabel(List<ModelDebt> data){
        this.data = data;
    }
    
    public int getRowCount() {
       return data.size();
    }
    
    public int getColumnCount(){
        return kolom.length;
    }
    
    public Object getValueAt(int rowIndex, int columnIndex){
        return switch(columnIndex){
            case 0 -> data.get(rowIndex).getId();
            case 1 -> data.get(rowIndex).getPenerima();
            case 2 -> data.get(rowIndex).getPemberi();
            case 3 -> data.get(rowIndex).getNominal();
            case 4 -> data.get(rowIndex).getDate();
            case 5 -> data.get(rowIndex).getNote();
            default -> null;
        };
                
    }
    public String getColumnName(int columnIndex){
        return kolom[columnIndex];
    }
}
