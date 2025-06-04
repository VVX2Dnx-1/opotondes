package Controller;

import Model.*;
import Model.Debt.*;
import Model.People.*;
import View.Viewer;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.JOptionPane;

public class Controller {
    private Viewer view;
    private InterfaceDebt intrD;
    private InterfacePep intrP;
    private DAOPeople daoPip;
    private DAODebt daoDeb;
    private List<ModelDebt> daftarDebt;

    public Controller(Viewer view){
        this.view = view;
        this.intrP = new DAOPeople();
        this.intrD = new DAODebt();
        
        //loadPeopleData();
        loadTabel();
      
        view.getDelBtn().addActionListener((ActionEvent e) -> {
            deleteDebt();
        });      
        
        view.getAddBtn().addActionListener((ActionEvent e) -> {
            addDebt();
        });
        
        
    }
    
 //   private void loadPeopleData() {
 //   try {
 //       // Bersihkan combobox terlebih dahulu
 //       view.getPemberiComboBox().removeAllItems();
  //      view.getPenerimaComboBox().removeAllItems();
 //       
 //       // Ambil semua data people dari database
 //       List<ModelPeople> peopleList = daoPip.getAll();
 //       
//        // Tambahkan ke combobox
 //       for (ModelPeople person : peopleList) {
 //           view.getPemberiComboBox().addItem(person.getNama());
 //           view.getPenerimaComboBox().addItem(person.getNama());
 //       }
 //   } catch (Exception e) {
 //       JOptionPane.showMessageDialog(view, 
 //           "Gagal memuat data orang: " + e.getMessage(), 
 //           "Error", 
 //           JOptionPane.ERROR_MESSAGE);
 //   }
//    }
    
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
    
    
private void addDebt() {
    try {
        String penerimaName = view.getPenerimaName().trim();
        String pemberiName = view.getPemberiName().trim();
        String nominalText = view.getNominal().trim();
        String date = view.getDate().trim();
        String note = view.getNote().trim();

        // Validasi wajib isi
        if (penerimaName.isEmpty() || pemberiName.isEmpty() || nominalText.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Penerima, Pemberi, dan Nominal harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int nominal;
        try {
            nominal = Integer.parseInt(nominalText);
            if (nominal <= 0) {
                JOptionPane.showMessageDialog(view, "Nominal harus lebih dari 0!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(view, "Nominal harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Cek atau insert pemberi
        ModelPeople pemberi = intrP.getByName(pemberiName);
        if (pemberi == null) {
            pemberi = new ModelPeople();
            pemberi.setNama(pemberiName);
            intrP.insert(pemberi);
            pemberi = intrP.getByName(pemberiName); // Ambil ulang untuk mendapatkan ID
        }

        // Cek atau insert penerima
        ModelPeople penerima = intrP.getByName(penerimaName);
        if (penerima == null) {
            penerima = new ModelPeople();
            penerima.setNama(penerimaName);
            intrP.insert(penerima);
            penerima = intrP.getByName(penerimaName); // Ambil ulang untuk mendapatkan ID
        }

        // Buat dan isi data utang
        ModelDebt debt = new ModelDebt();
        debt.setPenerima(penerima);
        debt.setPemberi(pemberi);
        debt.setNominal(nominal);
        debt.setDate(date);
        debt.setNote(note);

        // Simpan ke tabel debt
        intrD.insert(debt);

        JOptionPane.showMessageDialog(view, "Data utang berhasil ditambahkan.");
        loadTabel();       // Refresh tabel
        view.clearForm();  // Bersihkan input

    } catch (Exception e) {
        JOptionPane.showMessageDialog(view, "Terjadi kesalahan: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}


}
        
    

