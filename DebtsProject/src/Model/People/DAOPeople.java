/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.People;
import Model.Connector;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;



public class DAOPeople implements InterfacePep{

    @Override
    public void insert(ModelPeople people) {
        try{
            String query = "INSERT INTO people (nama) VALUES (?)";
            
            PreparedStatement statement;
            statement = Connector.connect().prepareStatement(query);
            statement.setString(1, people.getNama());
            statement.executeUpdate();
            statement.close();
            
        } catch(SQLException e){
            System.out.println("Input Failed: " + e.getLocalizedMessage());
        }
       
    }
    
    @Override
    public void delete(int id) {
    String query = "DELETE FROM people WHERE id = ?";
    try (Connection conn = Connector.connect();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, id);
        stmt.executeUpdate();
    } catch (SQLException e) {
        System.err.println("Delete failed: " + e.getMessage());
        }
    }    

    @Override
    public List<ModelPeople> getAll() {
        List<ModelPeople> listPipel = null;
        try{
            listPipel = new ArrayList<>();
            try(Statement statement = Connector.connect().createStatement()){
                String query = "SELECT * FROM people";
                ResultSet resultSet = statement.executeQuery(query);
                
                while(resultSet.next()){
                    ModelPeople pipel = new ModelPeople();
                    pipel.setId(resultSet.getInt("id"));
                    pipel.setNama(resultSet.getString("nama"));
                    
                    listPipel.add(pipel);
                }
            }
        }
        catch(SQLException e){
            System.out.println("Gagal mengeluarkan data" + e.getLocalizedMessage());
        }
        return listPipel;
        
    }



}
