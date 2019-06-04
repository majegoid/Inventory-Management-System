/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
public class ProductAddController implements Initializable {

    ObservableList<Part> newProductParts = FXCollections.observableArrayList();

    @FXML
    private TextField idTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField stockTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    private TextField maxTextField;

    @FXML
    private TextField minTextField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchTextField;

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
    private Button addButton;

    @FXML
    private TableView<Part> productPartTableView;

    @FXML
    private TableColumn<Part, Integer> productPartIdCol;

    @FXML
    private TableColumn<Part, String> productPartNameCol;

    @FXML
    private TableColumn<Part, Integer> productPartStockCol;

    @FXML
    private TableColumn<Part, Double> productPartPriceCol;

    @FXML
    private Button deleteButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    void addPartToProduct(ActionEvent event) {
        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            for (Part part : newProductParts) {
                if (part.getId() == selectedPart.getId()) {
                    return;
                }
            }
            newProductParts.add(selectedPart);
            productPartTableView.setItems(newProductParts);
        }
    }

    @FXML
    void cancelProduct(ActionEvent event) {
        try {
            switchScene(event, "/view/MainView.fxml", "Inventory Management System - Main Menu");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void deletePartFromProduct(ActionEvent event) {
        Part selectedPart = productPartTableView.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            newProductParts.remove(productPartTableView.getSelectionModel().getSelectedIndex());
        }
    }

    @FXML
    void saveProduct(ActionEvent event) {
        boolean switchScene = false;
        try {
            //Catch any format exception first
            Integer id = Inventory.nextProductIndex;
            String name = nameTextField.getText();
            Integer max = Integer.parseInt(maxTextField.getText());
            Integer min = Integer.parseInt(minTextField.getText());
            Integer stock = Integer.parseInt(stockTextField.getText());
            Double price = Double.parseDouble(priceTextField.getText());
            //Validate correctly formatted fields
            String errorMessage = validateProductTextFields(price, stock, max, min);
            if (errorMessage != null && errorMessage.isEmpty() == false) {
                errorLabel.setText(errorMessage);
            } else {
                Product newProduct = new Product(id, name, price, stock, min, max);
                for (Part part : newProductParts) {
                    if (part != null) {
                        newProduct.addAssociatedPart(part);
                    }
                }
                Inventory.addProduct(newProduct);
                switchScene = true;
            }

            if (switchScene) {
                //Switch scene.
                try {
                    switchScene(event, "/view/MainView.fxml", "Inventory Management System - Main Menu");
                } catch (IOException e) {
                    System.out.println("controller.PartAddController.savePart() switchScene catch(IOException e)");
                    System.out.println(e.toString());
                } catch (Exception e) {
                    System.out.println("controller.PartAddController.savePart() switchScene catch(Exception e)");
                    System.out.println(e.toString());
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("controller.ProductAddController.saveProduct() outer catch(NumberFormatException e)");
            System.out.println(e.toString());
            String errorMessage = "One or more fields are invalid or empty.";
            errorLabel.setText(errorMessage);
        }
    }

    @FXML
    void searchParts(ActionEvent event) {
        ObservableList<Part> searchResults = FXCollections.observableArrayList();
        String searchString = searchTextField.getText().toLowerCase();
        for (Part part : Inventory.getAllParts()) {
            if (part.getName().toLowerCase().contains(searchString)) {
                searchResults.add(part);
            }
        }
        partTableView.setItems(searchResults);
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

    public String validateProductTextFields(Double price, Integer stock, Integer max, Integer min) {
        StringBuilder errorMessageBuilder = new StringBuilder();
        if (max <= 0) {
            errorMessageBuilder.append("\"Max\" must be a positive whole number.\n");
        }
        if (min < 0) {
            errorMessageBuilder.append("\"Min\" must be a positive whole number or zero.\n");
        }
        if (min > stock) {
            errorMessageBuilder.append("\"Stock\" must be greater than or equal to \"Min\".\n");
        }
        if (max < stock) {
            errorMessageBuilder.append("\"Stock\" must be less than or equal to \"Max\".\n");
        }
        if (price < 0) {
            errorMessageBuilder.append("\"Price\" must be greater than or equal to $0.00.\n");
        }
        return errorMessageBuilder.toString();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partTableView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productPartTableView.setItems(newProductParts);
        productPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPartStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
