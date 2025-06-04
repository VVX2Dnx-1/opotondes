package Model.Debt;
import Model.People.ModelPeople;


public class ModelDebt {
    private int id, nominal;
    private ModelPeople pemberi;
    private ModelPeople penerima;
    private String date, note;
    
    public int getId(){
        return id;
    }
    
    public void setId(Integer id){
        this.id = id;
    }
    
    public int getNominal(){
        return nominal;
    }
    
    public void setNominal(Integer nominal){
        this.nominal = nominal;
    }

    public ModelPeople getPemberi(){
        return pemberi;
    }
    
    public void setPemberi(ModelPeople pemberi){
        this.pemberi = pemberi;
    }
    
    public ModelPeople getPenerima(){
        return penerima;
    }
    
    public void setPenerima(ModelPeople penerima){
        this.penerima = penerima;
    }
    public String getDate(){
        return date;
    }
    
    public void setDate(String date){
        this.date = date;
    }
    public String getNote(){
        return note;
    }
    
    public void setNote(String note){
        this.note = note;
    }


}
