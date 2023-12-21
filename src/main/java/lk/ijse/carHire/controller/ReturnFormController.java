package lk.ijse.carHire.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.carHire.business.BoFactory;
import lk.ijse.carHire.business.BoType;
import lk.ijse.carHire.business.custom.ReturnBo;
import lk.ijse.carHire.db.DBConnection;
import lk.ijse.carHire.dto.RentDto;
import lk.ijse.carHire.dto.ReturnDto;
import lk.ijse.carHire.dto.tm.RentTm;
import lk.ijse.carHire.dto.tm.ReturnTm;
import lk.ijse.carHire.entity.RentEntity;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReturnFormController {
    public AnchorPane rootNode;
    public TextField txtCarId;
    public TableView<ReturnTm> tableReturn;
    public TableColumn<?,?> colId;
    public TableColumn<?,?> colRent;
    public TableColumn<?,?> colCar;
    public TableColumn<?,?> colFee;
    public TextField txtFees;
    public TextField txtId;
    public ComboBox<String> cmbRentalId;

    private ReturnBo returnBoImpl = BoFactory.getBo(BoType.RETURN);

    public void initialize(){

        List<RentDto> rentDtos = loadAllRentIds();
        setRentIds(rentDtos);


        setCellValueFactory();
        loadAllReturns();
    }

    private void loadAllReturns() {
        try {
            List<ReturnDto> returnDtos = returnBoImpl.getAllReturns();
            ObservableList<ReturnTm> tableData = FXCollections.observableArrayList();

            for(ReturnDto dto : returnDtos){
                tableData.add(new ReturnTm(
                        dto.getReturnId(),
                        dto.getRentalId(),
                        dto.getCarId(),
                        dto.getFee()
                ));
                tableReturn.setItems(tableData);
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }



    }


    private void setRentIds(List<RentDto> rentDtos) {
        ObservableList<String > obList = FXCollections.observableArrayList();

        for(RentDto rentDto : rentDtos){
            obList.add(rentDto.getId());
        }
        cmbRentalId.setItems(obList);

    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("returnId"));
        colRent.setCellValueFactory(new PropertyValueFactory<>("rentalId"));
        colCar.setCellValueFactory(new PropertyValueFactory<>("carId"));
        colFee.setCellValueFactory(new PropertyValueFactory<>("fee"));

    }

    private List<RentDto> loadAllRentIds() {
        List<RentDto> rentDtos = new ArrayList<>();

        try {
            Connection con = DBConnection.getInstance().getConnection();

            String sql = "SELECT * FROM rentals WHERE IsReturned= 'No' ";

            PreparedStatement pstm = con.prepareStatement(sql);

            ResultSet rst = pstm.executeQuery();

            while (rst.next()){
                rentDtos.add(new RentDto(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getString(4),
                        rst.getString(5)

                ));

            }
            return rentDtos;
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }


        return null;
    }

    public void txtIdOnAction(ActionEvent actionEvent) {
    }

    public void btnReturnOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();

        String rentalId = cmbRentalId.getValue();
        String carId = txtCarId.getText();
        Double fee = Double.valueOf(txtFees.getText());


        var returnDto = new ReturnDto(id,rentalId,carId,fee);
        System.out.println(carId);
        try {
            boolean isReturned = returnBoImpl.saveReturn(returnDto);
            if(isReturned){
                new Alert(Alert.AlertType.CONFIRMATION,"Car Returned").show();
                clearFields();
                loadAllReturns();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtCarId.setText("");
        txtFees.setText("");
        cmbRentalId.getSelectionModel().clearSelection();
        cmbRentalId.setPromptText("Rental Id");
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
    }

    public void btnOnMouseCllicked(MouseEvent mouseEvent) {
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {

        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage primaryStage = (Stage) this.rootNode.getScene().getWindow();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Dashboard");
    }

    public void cmbOnAction(ActionEvent actionEvent) {

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "SELECT * FROM rentals WHERE RentalID=?";

            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1,cmbRentalId.getValue());

            ResultSet rst = pstm.executeQuery();

            if(rst.next()){
                RentEntity re = new RentEntity(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getString(4),
                        rst.getString(5),
                        rst.getString(6)
                );
                txtCarId.setText(re.getCustId());
                double feess = returnBoImpl.calculateFee(cmbRentalId.getValue());
                txtFees.setText(String.valueOf(feess));

            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
