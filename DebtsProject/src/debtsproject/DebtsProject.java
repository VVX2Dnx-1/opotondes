package debtsproject;
import Controller.Controller;
//import Model.Connector;

import View.Viewer;

public class DebtsProject {
    public static void main(String[] args) {
//        Connector.connect();
        Viewer view = new Viewer();
        Controller control = new Controller(view);
        view.setVisible(true);
    }
    
}
