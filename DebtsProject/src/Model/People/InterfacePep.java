
package Model.People;
import java.util.List;

public interface InterfacePep {
    public void insert(ModelPeople people);
    public void delete(int id);
    public List<ModelPeople> getAll();
    
    public ModelPeople getByName(String name);
}
