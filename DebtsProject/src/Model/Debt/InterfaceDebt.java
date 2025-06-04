/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Debt;
import java.util.List;
/**
 *
 * @author nx
 */
public interface InterfaceDebt {
    public void insert (ModelDebt debt);
    public void delete (ModelDebt debt);
    
    public List<ModelDebt> getAll();
}
