package Model.Debt;
import java.util.List;

public interface InterfaceDebt {
    public void insert (ModelDebt debt);
    public void delete (int id);
    public List<ModelDebt> getAll();
}
