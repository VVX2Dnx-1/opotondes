package Model.Debt;
import Model.People.*;
import Model.Connector;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class DAODebt implements InterfaceDebt {

    @Override
    public void insert(ModelDebt debt) {
        try{
        String query = "INSERT INTO debt (pemberi_id, penerima_id, nominal, date, note) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = Connector.connect().prepareStatement(query);
        
        // Set parameter values
        statement.setInt(1, debt.getPemberi().getId());    // pemberi_id
        statement.setInt(2, debt.getPenerima().getId());   // penerima_id
        statement.setInt(3, debt.getNominal());            // nominal
        statement.setString(4, debt.getDate());            // date
        statement.setString(5, debt.getNote());            // note
        //statement.setBoolean(6, debt.getStatus());
        // Eksekusi query
        statement.executeUpdate();
        statement.close();
        
    } catch(SQLException e){
        System.out.println("Gagal input: " + e.getLocalizedMessage());
        }
    }

    @Override
    public void delete(int id) {
    String query = "DELETE FROM debt WHERE id = ?";
    
    try (Connection conn = Connector.connect();
         PreparedStatement statement = conn.prepareStatement(query)) {
        
        statement.setInt(1, id);
        int affectedRows = statement.executeUpdate();
        
        if (affectedRows == 0) {
            throw new SQLException("No record found with ID: " + id);
        }
        
    } catch (SQLException e) {
        System.out.println("Failed to delete: " + e.getMessage());
        throw new RuntimeException("Database error while deleting", e);
    }
}

    @Override
    public List<ModelDebt> getAll() {
        List<ModelDebt> listDebt = new ArrayList<>();
        try {
            String query = "SELECT d.*, p1.nama as pemberi_nama, p2.nama as penerima_nama " +
                           "FROM debt d " +
                           "JOIN people p1 ON d.pemberi_id = p1.id " +
                           "JOIN people p2 ON d.penerima_id = p2.id";
            Statement statement = Connector.connect().createStatement();
            ResultSet rs = statement.executeQuery(query);
            
            while(rs.next()) {
                ModelDebt debt = new ModelDebt();
                debt.setId(rs.getInt("id"));
                debt.setNominal(rs.getInt("nominal"));
                debt.setDate(rs.getString("date"));
                debt.setNote(rs.getString("note"));
                //debt.setStatus(rs.getBoolean("status"));
                
                // Set pemberi
                ModelPeople pemberi = new ModelPeople();
                pemberi.setId(rs.getInt("pemberi_id"));
                pemberi.setNama(rs.getString("pemberi_nama"));
                debt.setPemberi(pemberi);
                
                // Set penerima
                ModelPeople penerima = new ModelPeople();
                penerima.setId(rs.getInt("penerima_id"));
                penerima.setNama(rs.getString("penerima_nama"));
                debt.setPenerima(penerima);
                
                listDebt.add(debt);
            }
        } catch(SQLException e) {
            System.out.println("Gagal mengambil data: " + e.getMessage());
        }
        return listDebt;
    }
  }
    

