/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dwinventorymanagementsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Product;

/**
 *
 * @author Drew
 */
public class DWInventoryManagementSystem extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainView.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Inventory Management System - Main Menu");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //seed parts
        //inhouse parts
        Inventory.addPart(new InHouse(1, "Part A", 25.00, 60, 50, 999, 126));
        Inventory.addPart(new InHouse(2, "Part B", 20.00, 55, 50, 999, 125));
        Inventory.addPart(new InHouse(3, "Part C", 15.00, 34,  5, 999, 123));
        Inventory.addPart(new InHouse(4, "Part D", 10.00, 26,  0,  99, 127));
        Inventory.addPart(new InHouse(5, "Part E",  5.00,  8,  0,  99, 124));
        //outsourced parts
        Inventory.addPart(new Outsourced( 6, "Part 1",  5.00,  5, 0,  99, "Company 1"));
        Inventory.addPart(new Outsourced( 7, "Part 2", 10.00, 10, 0,  99, "Company 1"));
        Inventory.addPart(new Outsourced( 8, "Part 3", 15.00, 12, 0,  99, "Company 1"));
        Inventory.addPart(new Outsourced( 9, "Part 4", 20.00, 11, 0,  99, "Company 2"));
        Inventory.addPart(new Outsourced(10, "Part 5", 25.00, 60, 0, 999, "Company 2"));
        //seed products
        Inventory.addProduct(new Product(1, "Product A", 25.00, 45, 0, 99));
        Inventory.addProduct(new Product(2, "Product B", 20.00, 45, 0, 99));
        Inventory.addProduct(new Product(3, "Product C", 15.00, 45, 0, 99));
        Inventory.addProduct(new Product(4, "Product D", 10.00, 45, 0, 99));
        Inventory.addProduct(new Product(5, "Product E",  5.00, 45, 0, 99));
        launch(args);
    }
    
}
