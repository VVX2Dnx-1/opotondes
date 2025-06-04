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
        
    public ModelPeople getByName(String name) {
        ModelPeople people = null;
        try {
            Connection conn = Connector.connect();
            String query = "SELECT * FROM people WHERE nama = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                people = new ModelPeople();
                people.setId(rs.getInt("id"));
                people.setNama(rs.getString("nama"));
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("GetByName failed: " + e.getMessage());
        }
        return people;
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
