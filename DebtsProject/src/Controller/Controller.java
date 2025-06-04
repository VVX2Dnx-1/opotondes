package Controller;

import View.Viewer;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import Model.Debt.*;
import Model.People.*;
import Model.ModelTabel;

public class Controller {
    private Viewer view;
    private DAOPeople daoPip;
    private DAODebt daoDeb;

    public Controller(Viewer view){
        this.view = view;
        this.daoPip = new DAOPeople();
        this.daoDeb = new DAODebt();
      
        loadPeopleData();
        loadDebtTable();
         
        view.getSimpelBtn().addActionListener((ActionEvent e) -> {
            simplifyDebts();
            loadDebtTable(); 
        });
    }

    private void loadPeopleData(){
        List<ModelPeople> peopleList = daoPip.getAll();
        view.getPemberiComboBox().removeAllItems();
        view.getPenerimaComboBox().removeAllItems();

        for(ModelPeople people : peopleList){
            view.getPemberiComboBox().addItem(people);
            view.getPenerimaComboBox().addItem(people);
        }
    }

    private void loadDebtTable() {
        List<ModelDebt> debts = daoDeb.getAll();
        view.getDebtTable().setModel(new ModelTabel(debts));
    }

    private void simplifyDebts() {
    List<ModelDebt> allDebts = daoDeb.getAll();
    Map<Integer, Integer> balanceMap = new HashMap<>();
    Map<Integer, String> idToName = new HashMap<>();

    // Ambil nama orang
    for (ModelPeople person : daoPip.getAll()) {
        idToName.put(person.getId(), person.getNama().trim());
    }

    // Hitung saldo bersih
    for (ModelDebt debt : allDebts) {
        int giver = debt.getPemberiId();
        int receiver = debt.getPenerimaId();
        int amount = debt.getNominal();

        balanceMap.put(giver, balanceMap.getOrDefault(giver, 0) + amount);
        balanceMap.put(receiver, balanceMap.getOrDefault(receiver, 0) - amount);
    }

    // Proses preview simplifikasi
    StringBuilder hasil = new StringBuilder("Hasil Simplifikasi Utang:\n");

    while (true) {
        int maxCredit = 0, maxCreditId = -1;
        int maxDebt = 0, maxDebtId = -1;

        for (Map.Entry<Integer, Integer> entry : balanceMap.entrySet()) {
            int id = entry.getKey();
            int saldo = entry.getValue();
            if (saldo > maxCredit) {
                maxCredit = saldo;
                maxCreditId = id;
            }
            if (saldo < maxDebt) {
                maxDebt = saldo;
                maxDebtId = id;
            }
        }

        if (maxCredit == 0 || maxDebt == 0) break;

        int settled = Math.min(maxCredit, -maxDebt);

        hasil.append("- ")
            .append(idToName.get(maxDebtId))
            .append(" membayar ")
            .append(idToName.get(maxCreditId))
            .append(" sebesar ")
            .append(settled)
            .append("\n");

        balanceMap.put(maxCreditId, balanceMap.get(maxCreditId) - settled);
        balanceMap.put(maxDebtId, balanceMap.get(maxDebtId) + settled);
    }

    if (hasil.toString().equals("Hasil Simplifikasi Utang:\n")) {
        hasil.append("Tidak ada utang yang perlu disimplifikasi.");
    }

    JOptionPane.showMessageDialog(view, hasil.toString());
}

}
