package Controller;

import View.Viewer;
import java.util.List;
import Model.Debt.*;
import Model.People.*;
import Model.*;
import java.awt.HeadlessException;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;

public class Controller {
    private Viewer view;
    private InterfaceDebt intrD;
    private InterfacePep intrP;
    private DAOPeople daoPip;
    private DAODebt daoDeb;
    private List<ModelDebt> daftarDebt;

    public Controller(Viewer view){
        this.view = view;
        this.daoPip = new DAOPeople();
        this.intrD = new DAODebt();
        loadTabel();
      
        view.getDelBtn().addActionListener((ActionEvent e) -> {
            deleteDebt();
        });      
    
    }
    
    
    private void loadTabel(){
        daftarDebt = intrD.getAll();
        ModelTabel modelTabel = new ModelTabel(daftarDebt);
        view.getTable().setModel(modelTabel);
    }
    
    private void deleteDebt() {
    int row = view.getTable().getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(view, 
            "Please select a row to delete", 
            "No Selection", 
            JOptionPane.WARNING_MESSAGE);
        return;
    }

    int confirm = JOptionPane.showConfirmDialog(
        view,
        "Are you sure you want to delete this record?",
        "Confirm Deletion",
        JOptionPane.YES_NO_OPTION
    );

    if (confirm == JOptionPane.YES_OPTION) {
        try {
            int id = (int) view.getTable().getValueAt(row, 0);
            intrD.delete(id);
            loadTabel(); // Refresh the table
            JOptionPane.showMessageDialog(view, 
                "Record deleted successfully", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(view, 
                "Failed to delete record: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    }
    
    
    
   private void addDebt(){
       try {
        String pemberiNama = (String) view.getPemberiComboBox().getSelectedItem();
        String penerimaNama = (String) view.getPenerimaComboBox().getSelectedItem();
        int nominal = Integer.parseInt(view.getNominalField());
        String date = view.getDateField();
        String note = view.getNoteArea();
        
        // Validasi input
        if (pemberiNama.equals(penerimaNama)) {
            JOptionPane.showMessageDialog(view, "Pemberi dan Penerima tidak boleh sama", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        ModelDebt debt = new ModelDebt();
        debt.setPemberi(daoPip.getNama(pemberiNama));
        debt.setPenerima(daoPip.getNama(penerimaNama));
        debt.setNominal(nominal);
        debt.setDate(date);
        debt.setNote(note);
        
        intrD.insert(debt);
        loadTabel();
        
        JOptionPane.showMessageDialog(view, "Data berhasil ditambahkan");
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(view, "Nominal harus angka", "Error", JOptionPane.ERROR_MESSAGE);
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(view, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
   }
}
    
 //  public void handleNewPersonInput(String nama){
 //      if (!isPersonExists(nama)) {
  //      ModelPeople newPerson = new ModelPeople();
 //       newPerson.setNama(nama);
        
 //       // Simpan ke database
 //       if (intrP.insert(newPerson)) {
//            // Update JComboBox
 //           refreshPeopleComboBox();
//        }
//    }
//       
 //  }
//    
//   private boolean isPersonExists(String nama) {
//    return intrP.getAll().stream().anyMatch(p -> p.getNama().equalsIgnoreCase(nama));
//    }
//    

