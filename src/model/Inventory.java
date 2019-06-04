/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Drew
 */
public class Inventory {

    //fields
    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();
    public static int nextPartIndex = 1;
    public static int nextProductIndex = 1;

    //constructor (default constructor is created automatically)
    //methods
    //add (create)
    public static void addPart(Part part) {
        allParts.add(part);
        nextPartIndex++;
    }

    public static void addProduct(Product product) {
        allProducts.add(product);
        nextProductIndex++;
    }

    //lookup (read)
    public static Part lookupPart(int partId) {
        return allParts.get(partId);
    }

    public static Product lookupProduct(int productId) {
        return allProducts.get(productId);
    }

    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> returnList = FXCollections.observableArrayList();
        for (int i = 0; i < allParts.size(); i++) {
            if (allParts.get(i).getName().contains(partName)) {
                returnList.add(allParts.get(i));
            }
        }
        return returnList;
    }

    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> returnList = FXCollections.observableArrayList();
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getName().contains(productName)) {
                returnList.add(allProducts.get(i));
            }
        }
        return returnList;
    }

    //update (update)
    public static void updatePart(int index, Part part) {
        allParts.set(index, part);
    }

    public static void updateProduct(int index, Product product) {
        allProducts.set(index, product);
    }

    //delete (delete)
    public static void deletePart(Part part) {
        allParts.remove(part);
    }

    public static void deleteProduct(Product product) {
        allProducts.remove(product);
    }

    //getAll (read?)
    public static ObservableList<Part> getAllParts() {
        ObservableList<Part> defensiveCopy = FXCollections.observableArrayList(allParts);
        return defensiveCopy;
    }

    public static ObservableList<Product> getAllProducts() {
        ObservableList<Product> defensiveCopy = FXCollections.observableArrayList(allProducts);
        return defensiveCopy;
    }
}
