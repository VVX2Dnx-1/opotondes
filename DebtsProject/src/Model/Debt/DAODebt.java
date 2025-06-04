/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Debt;
import Model.Connector;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author nx
 */
public class DAODebt implements InterfaceDebt {

    @Override
    public void insert(ModelDebt debt) {
        try{
            String query = "INSERT INTO debt ()";
            PreparedStatement statement;
            statement = Connector.connect().prepareStatement(query);
            
            
        } catch(SQLException e){
            System.out.println("Gagal input: " + e.getLocalizedMessage());
        }
    }

    @Override
    public void delete(ModelDebt debt) {

    }

    @Override
    public List<ModelDebt> getAll() {

    }
    
}
