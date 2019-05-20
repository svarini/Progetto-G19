package it.unipv.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ManagerHome extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/manager/ManagerHome.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Area Manager");
        stage.setResizable(false);
        stage.show();
    }
    
    public static void main(String[] args) { launch(args); }
    
}