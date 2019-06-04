/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

/**
 *
 * @author Drew
 */
public class PartModifyController implements Initializable {
    
    int selectedPartIndex;
    Part selectedPart;
    
    @FXML
    private RadioButton inhouseRadioButton;

    @FXML
    private ToggleGroup partTypeToggleGroup;

    @FXML
    private RadioButton outsourcedRadioButton;

    @FXML
    private TextField idTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField stockTextField;

    @FXML
    private TextField priceTextField;
    
    @FXML
    private Label companyNameLabel;

    @FXML
    private TextField companyNameTextField;

    @FXML
    private TextField maxTextField;

    @FXML
    private TextField minTextField;
    
    @FXML
    private Label errorLabel;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    void cancelPart(ActionEvent event) {
        try 
        {
            switchScene(event, "/view/MainView.fxml", "Inventory Management System - Main Menu");
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void savePart(ActionEvent event) {
        boolean switchScene = false;
        try
        {
            //Catch any format exception first
            Integer id = selectedPart.getId();
            String name = nameTextField.getText();
            Integer max = Integer.parseInt(maxTextField.getText());
            Integer min = Integer.parseInt(minTextField.getText());
            Integer stock = Integer.parseInt(stockTextField.getText());
            Double price = Double.parseDouble(priceTextField.getText());
            //Validate correctly formatted fields
            String errorMessage = validatePartTextFields(price, stock, max, min);
            if(errorMessage != null && errorMessage.isEmpty() == false)
            {
                errorLabel.setText(errorMessage);
            }
            else
            {
                //Determine item type and create that type of item.
                if(inhouseRadioButton.isSelected())
                {
                    int machineId = Integer.parseInt(companyNameTextField.getText());
                    if(machineId <= 0)
                    {
                        errorMessage += "\"MachineId\" should be a positive whole number.";
                        errorLabel.setText(errorMessage);
                    }
                    else
                    {
                        Inventory.updatePart(selectedPartIndex, new InHouse(id, name, price, stock, max, min, machineId));
                        switchScene = true;
                    }
                }
                else
                {
                    String companyName = companyNameTextField.getText();
                    Inventory.updatePart(selectedPartIndex, new Outsourced(id, name, price, stock, min, max, companyName));
                    switchScene = true;
                }
                if(switchScene)
                {
                    //Switch scene.
                    try 
                    {
                        switchScene(event, "/view/MainView.fxml", "Inventory Management System - Main Menu");
                    }
                    catch(IOException e)
                    {
                        System.out.println("controller.PartAddController.savePart() switchScene catch(IOException e)");
                        System.out.println(e.toString());
                    }
                    catch(Exception e)
                    {
                        System.out.println("controller.PartAddController.savePart() switchScene catch(Exception e)");
                        System.out.println(e.toString());
                    }
                }
            }
        }
        catch(NumberFormatException e)
        {
            System.out.println("controller.PartAddController.savePart() outer catch(NumberFormatException e)");
            System.out.println(e.toString());
            String errorMessage = "One or more fields are invalid or empty.";
            errorLabel.setText(errorMessage);
        }
    }

    @FXML
    void selectPartInhouse(ActionEvent event) {
        companyNameLabel.setText("Machine ID");
        companyNameTextField.setPromptText("Machine ID");
        companyNameTextField.clear();
    }

    @FXML
    void selectPartOutsourced(ActionEvent event) {
        companyNameLabel.setText("Company Name");
        companyNameTextField.setPromptText("Company Name");
        companyNameTextField.clear();
    }
    
    public void switchScene(ActionEvent event, String url, String windowTitle) throws IOException
    {
        Stage stage;
        Parent root;
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource(url));
        stage.setScene(new Scene(root));
        stage.setTitle(windowTitle);
        stage.show();
    }
    
    public String validatePartTextFields(Double price, Integer stock, Integer max, Integer min)
    {
        StringBuilder errorMessageBuilder = new StringBuilder();
        if (max <= 0)
            errorMessageBuilder.append("\"Max\" must be a positive whole number.\n");
        if (min < 0)
            errorMessageBuilder.append("\"Min\" must be a positive whole number or zero.\n");
        if(min > stock)
            errorMessageBuilder.append("\"Stock\" must be greater than or equal to \"Min\".\n");
        if(max < stock)
            errorMessageBuilder.append("\"Stock\" must be less than or equal to \"Max\".\n");
        if (price < 0)
            errorMessageBuilder.append("\"Price\" must be greater than or equal to $0.00.\n");
        return errorMessageBuilder.toString();
    }
    
    public void receiveSelectedPart(int partIndex, Part part)
    {
        selectedPart = part;
        selectedPartIndex = partIndex;
        idTextField.setText(String.valueOf(part.getId()));
        nameTextField.setText(part.getName());
        stockTextField.setText(String.valueOf(part.getStock()));
        priceTextField.setText(String.valueOf(part.getPrice()));
        minTextField.setText(String.valueOf(part.getMin()));
        maxTextField.setText(String.valueOf(part.getMax()));
        if (part instanceof InHouse)
        {
            inhouseRadioButton.setSelected(true);
            companyNameLabel.setText("Machine ID");
            companyNameTextField.setPromptText("Machine ID");
            companyNameTextField.setText(String.valueOf(((InHouse)part).getMachineId()));
        }
        else
        {
            outsourcedRadioButton.setSelected(true);
            companyNameLabel.setText("Company Name");
            companyNameTextField.setPromptText("Company Name");
            companyNameTextField.setText(((Outsourced)part).getCompanyName());
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
