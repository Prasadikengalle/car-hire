package lk.ijse.carHire.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashBoardController {

    public AnchorPane rootNode;

    public void btnCustomerOnAction(ActionEvent actionEvent) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/customer_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage primaryStage = (Stage) this.rootNode.getScene().getWindow();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Customer Manage");
    }

    public void btnCategoriesOnAction(ActionEvent actionEvent) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/category_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage primaryStage = (Stage) this.rootNode.getScene().getWindow();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Category Manage");
    }

    public void btnOnAction(ActionEvent actionEvent) {
    }

    public void btnRentOnAction(ActionEvent actionEvent) {
    }

    public void btnReturnOnAction(ActionEvent actionEvent) {
    }
}
