/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

/**
 *
 * @author Drew
 */
public class MainController implements Initializable {

    @FXML
    private Button partSearchButton;

    @FXML
    private TextField partSearchTextField;

    @FXML
    private TableView<Part> partTableView;

    @FXML
    private TableColumn<Part, Integer> partIdCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Integer> partStockCol;

    @FXML
    private TableColumn<Part, Double> partPriceCol;

    @FXML
    private Button partAddButton;

    @FXML
    private Button partModifyButton;

    @FXML
    private Button partDeleteButton;

    @FXML
    private Button productSearchButton;

    @FXML
    private TextField productSearchTextField;

    @FXML
    private TableView<Product> productTableView;

    @FXML
    private TableColumn<Product, Integer> productIdCol;

    @FXML
    private TableColumn<Product, String> productNameCol;

    @FXML
    private TableColumn<Product, Integer> productStockCol;

    @FXML
    private TableColumn<Product, Double> productPriceCol;

    @FXML
    private Button productAddButton;

    @FXML
    private Button productModifyButton;

    @FXML
    private Button productDeleteButton;

    @FXML
    private Button exitButton;

    @FXML
    void exitApplication(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void partAdd(ActionEvent event) {
        try {
            switchScene(event, "/view/PartAddView.fxml", "Inventory Management System - Add Part");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void partDelete(ActionEvent event) {
        if (partTableView.getSelectionModel().getSelectedItem() != null) {
            int index = -1;
            int id = partTableView.getSelectionModel().getSelectedItem().getId();
            for (Part part : Inventory.getAllParts()) {
                index++;
                if (part.getId() == id) {
                    Inventory.deletePart(part);
                    break;
                }
            }
            partTableView.setItems(Inventory.getAllParts());
        }
    }

    @FXML
    void partModify(ActionEvent event) throws IOException {
        if (partTableView.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/PartModifyView.fxml"));
            loader.load();
            PartModifyController PMController = loader.getController();
            PMController.receiveSelectedPart(partTableView.getSelectionModel().getSelectedIndex(), partTableView.getSelectionModel().getSelectedItem());
            try {
                switchSceneWithCustomRoot(event, "/view/PartModifyView.fxml", loader.getRoot(), "Inventory Management System - Modify Part");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    void partSearch(ActionEvent event) {
        ObservableList<Part> searchResults = FXCollections.observableArrayList();
        String searchString = partSearchTextField.getText().toLowerCase();
        for (Part part : Inventory.getAllParts()) {
            if (part.getName().toLowerCase().contains(searchString)) {
                searchResults.add(part);
            }
        }
        partTableView.setItems(searchResults);
    }

    @FXML
    void productAdd(ActionEvent event) {
        try {
            switchScene(event, "/view/ProductAddView.fxml", "Inventory Management System - Add Product");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void productDelete(ActionEvent event) {
        int index = -1;
        int id = productTableView.getSelectionModel().getSelectedItem().getId();
        for (Product product : Inventory.getAllProducts()) {
            index++;
            if (product.getId() == id) {
                Inventory.deleteProduct(product);
                break;
            }
        }
        productTableView.setItems(Inventory.getAllProducts());
    }

    @FXML
    void productModify(ActionEvent event) throws IOException {
        if (productTableView.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ProductModifyView.fxml"));
            loader.load();
            ProductModifyController PMController = loader.getController();
            PMController.receiveSelectedProduct(productTableView.getSelectionModel().getSelectedIndex(), productTableView.getSelectionModel().getSelectedItem());
            try {
                switchSceneWithCustomRoot(event, "/view/ProductModifyView.fxml", loader.getRoot(), "Inventory Management System - Modify Product");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    void productSearch(ActionEvent event) {
        ObservableList<Product> searchResults = FXCollections.observableArrayList();
        String searchString = productSearchTextField.getText().toLowerCase();
        for (Product product : Inventory.getAllProducts()) {
            if (product.getName().toLowerCase().contains(searchString)) {
                searchResults.add(product);
            }
        }
        productTableView.setItems(searchResults);
    }

    public void switchScene(ActionEvent event, String url, String windowTitle) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource(url));
        stage.setScene(new Scene(root));
        stage.setTitle(windowTitle);
        stage.show();
    }

    public void switchSceneWithCustomRoot(ActionEvent event, String url, Parent root, String windowTitle) throws IOException {
        Stage stage;
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle(windowTitle);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partTableView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productTableView.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
