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
import lk.ijse.carHire.business.custom.RentBo;
import lk.ijse.carHire.db.DBConnection;
import lk.ijse.carHire.dto.CarDto;
import lk.ijse.carHire.dto.CustomerDto;
import lk.ijse.carHire.dto.RentDto;
import lk.ijse.carHire.dto.tm.RentTm;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RentFormController {
    public AnchorPane rootNode;
    public TextField txtId;
    public TableView<RentTm> tableRent;
    public TableColumn<?, ?> colId;
    public TableColumn<?, ?> colCustId;
    public TableColumn<?, ?> colCarId;
    public TableColumn<?, ?> colPickupDate;
    public TableColumn<?, ?> colReturnDate;
    public ComboBox<String> cmbCar;
    public ComboBox<String> cmbCustomer;
    public DatePicker dpPickupDate;
    public DatePicker dpReturnDate;

    public void initialize(){

        List<CarDto> carDtos  = loadCarIds();
        setCarIds(carDtos);

        List<CustomerDto> customerDtos = loadCustomerIds();
        setCustomerIds(customerDtos);

        setCellValueFactory();
        loadAllRents();


    }

    private RentBo rentBoImpl = BoFactory.getBo(BoType.RENT);

    private void loadAllRents() {

        try {
            List<RentDto> rentDtos= rentBoImpl.getAllRents();
            ObservableList<RentTm> tableData = FXCollections.observableArrayList();

            for(RentDto dto : rentDtos){
                tableData.add(new RentTm(
                        dto.getId(),

                        dto.getCarId(),
                        dto.getCustId(),
                        dto.getPickupDate(),
                        dto.getReturnDate()
                ));
                tableRent.setItems(tableData);
            }
        } catch (Exception e) {

            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    private void setCellValueFactory() {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCustId.setCellValueFactory(new PropertyValueFactory<>("custId"));
        colCarId.setCellValueFactory(new PropertyValueFactory<>("carId"));
        colPickupDate.setCellValueFactory(new PropertyValueFactory<>("pickupDate"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

    }



    private void setCustomerIds(List<CustomerDto> customerDtos) {
        ObservableList<String> obList = FXCollections.observableArrayList();

        for(CustomerDto customerDto : customerDtos){
            obList.add(customerDto.getId());
        }
        cmbCustomer.setItems(obList);
    }

    private List<CustomerDto> loadCustomerIds() {

        List<CustomerDto> customerDtos = new ArrayList<>();

        try {
            Connection connection = DBConnection.getInstance().getConnection();

            String sql ="SELECT * FROM customers";

            PreparedStatement pstm = connection.prepareStatement(sql);

            ResultSet rst = pstm.executeQuery();

            while (rst.next()){
                customerDtos.add(new CustomerDto(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getString(4),
                        rst.getString(5),
                        rst.getString(6),
                        rst.getString(7),
                        rst.getString(8),
                        rst.getString(9)
                ));
            }
            return customerDtos;

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
        return null;

    }

    private void setCarIds(List<CarDto> carDtos) {
        ObservableList<String> obList = FXCollections.observableArrayList();

        for(CarDto carDto : carDtos){
            obList.add(carDto.getCarId());
        }
        cmbCar.setItems(obList);
    }

    private List<CarDto> loadCarIds() {
        List<CarDto> carDtos = new ArrayList<>();

        try {
            Connection connection = DBConnection.getInstance().getConnection();

            String sql = "SELECT * FROM car WHERE IsAvailable='Yes' ";
            PreparedStatement pstm = connection.prepareStatement(sql);

            ResultSet rst = pstm.executeQuery();

            while(rst.next()){
                carDtos.add(new CarDto(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getString(4),
                        rst.getInt(5),
                        rst.getString(6),
                        rst.getDouble(7),
                        rst.getString(8)
                ));


            }
            return carDtos;

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

        return null;

    }

    public void txtIdOnAction(ActionEvent actionEvent) {
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {

        String id = txtId.getText();
        String carId =cmbCar.getValue();
        String custId = cmbCustomer.getValue();
        String pickupDate = getFormattedDateFromDatePicker(dpPickupDate.getValue());
        String returnDate = getFormattedDateFromDatePicker(dpReturnDate.getValue());

        var rentDto = new RentDto(id, carId, custId, pickupDate, returnDate);

        try {
            boolean isSaved = rentBoImpl.saveRent(rentDto);
            if(isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"Rental Created").show();
                clearFields();
                loadAllRents();

            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void clearFields() {
        txtId.setText("");
        cmbCar.getSelectionModel().clearSelection();
        cmbCustomer.getSelectionModel().clearSelection();
        dpPickupDate.getEditor().clear();
        dpReturnDate.getEditor().clear();
    }

    private String getFormattedDateFromDatePicker(LocalDate value) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return  value.format(formatter);
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    public void btnOnMouseCllicked(MouseEvent mouseEvent) {
    }

    public void cmbSuppllierIdOnAction(ActionEvent actionEvent) {
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage primaryStage = (Stage) this.rootNode.getScene().getWindow();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Dashboard");
    }
}
