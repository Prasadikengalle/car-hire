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
import lk.ijse.carHire.business.custom.CarBo;
import lk.ijse.carHire.db.DBConnection;
import lk.ijse.carHire.dto.CarDto;
import lk.ijse.carHire.dto.CategoryDto;
import lk.ijse.carHire.dto.tm.CarTm;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarFormController {
    public AnchorPane rootNode;
    public TextField txtId;
    public TableView<CarTm> tableCar;
    public TableColumn<?, ?> colId;
    public TableColumn<?, ?> colMake;
    public TableColumn<?, ?> colModel;
    public TableColumn<?, ?> colYear;
    public TableColumn<?, ?> colRental;
    public TableColumn<?, ?> colAvailable;
    public TextField txtPlate;
    public ComboBox<String> cmbCategoryId;
    public TextField txtMake;
    public TextField txtModel;
    public TextField txtYear;
    public TextField txtRental;
    public ComboBox<String> comboAvialablity;
    public Label labelCategoryName;

    public  void initialize(){
        List<CategoryDto> categoryDtos = loadCategoryIds();
        setCategoryIds(categoryDtos);

        comboAvialablity.setItems(FXCollections.observableArrayList("Yes","No"));

        setCellValueFactory();
        loadAllCars();
    }

    private CarBo carBoImpl = BoFactory.getBo(BoType.CAR);

    private void loadAllCars() {

        try {
            List<CarDto> carDtos = carBoImpl.getAllCars();
            ObservableList<CarTm> tableData = FXCollections.observableArrayList();

            for(CarDto dto :  carDtos){
                tableData.add(new CarTm(
                        dto.getCarId(),
                        dto.getMake(),
                        dto.getModel(),
                        dto.getYear(),
                        dto.getDailyRentalRate(),
                        dto.getAvailable()


                ));
              //  System.out.println(dto);

            }
            tableCar.setItems(tableData);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colMake.setCellValueFactory(new PropertyValueFactory<>("make"));
        colModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        colRental .setCellValueFactory(new PropertyValueFactory<>("dailyRental"));
        colAvailable.setCellValueFactory(new PropertyValueFactory<>("Availability"));

    }

    private void setCategoryIds(List<CategoryDto> categoryDtos) {

        ObservableList<String> obList = FXCollections.observableArrayList();

        for(CategoryDto categoryDto : categoryDtos){
            obList.add(categoryDto.getId());
        }

        cmbCategoryId.setItems(obList);
    }

    private List<CategoryDto> loadCategoryIds() {

        List<CategoryDto> categoryDtos = new ArrayList<>();

        try {
            Connection con = DBConnection.getInstance().getConnection();

            String sql ="SELECT * FROM carcategories";

            PreparedStatement pstm = con.prepareStatement(sql);

            ResultSet rst = pstm.executeQuery();

            while (rst.next()){
                categoryDtos.add(new CategoryDto(
                        rst.getString(1),
                        rst.getString(2)
                ));
            }

            return  categoryDtos;
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

        return null;


    }

    public void txtIdOnAction(ActionEvent actionEvent) {

        String id = txtId.getText();

        CarDto dto = null;
        try {
            dto = carBoImpl.searchCar(id);
            if(dto!= null) {
                this.txtId.setText(dto.getCarId());
                this.cmbCategoryId.setValue(dto.getCategoryId());
                this.txtPlate.setText(dto.getLicensePlate());
                this.txtYear.setText(String.valueOf(dto.getYear()));
                this.txtMake.setText(dto.getMake());
                this.txtModel.setText(dto.getModel());
                this.txtRental.setText(String.valueOf(dto.getDailyRentalRate()));
                this.comboAvialablity.setValue(dto.getAvailable());
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    public void btnSaveOnAction(ActionEvent actionEvent) {

        String carId = txtId.getText();
        String licensePlate = txtPlate.getText();
        String make = txtMake.getText();
        String model = txtModel.getText();
        Integer year = Integer.parseInt(txtYear.getText());
        String categoryId = String.valueOf(cmbCategoryId.getValue());
        Double dailyRental = Double.parseDouble(txtRental.getText());
        String  available = comboAvialablity.getValue();

        var carDto = new CarDto(carId,
                licensePlate, make, model, year, categoryId, dailyRental, available );

        try {
            boolean isSaved = carBoImpl.saveCar(carDto);

            if(isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"Car Saved").show();
                clearFields();
                loadAllCars();
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    private void clearFields() {
        txtId.setText("");
        txtPlate.setText("");
        txtMake.setText("");
        txtModel.setText("");
        txtYear.setText("");
        cmbCategoryId.getSelectionModel().clearSelection();
        cmbCategoryId.setPromptText("Category Id");
        txtRental.setText("");
        comboAvialablity.getSelectionModel().clearSelection();
        comboAvialablity.setPromptText("Availability");
        labelCategoryName.setText("");
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String carId = txtId.getText();
        String licensePlate = txtPlate.getText();
        String make = txtMake.getText();
        String model = txtModel.getText();
        Integer year = Integer.parseInt(txtYear.getText());
        String categoryId = String.valueOf(cmbCategoryId.getValue());
        Double dailyRental = Double.parseDouble(txtRental.getText());
        String  available = comboAvialablity.getValue();

        var carDto = new CarDto(carId, licensePlate, make, model,
                year, categoryId, dailyRental, available );

        try {
            boolean isUpdated = carBoImpl.updateCar(carDto);
            if(isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION,"Car Updated").show();
                clearFields();
                loadAllCars();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.CONFIRMATION,e.getMessage()).show();
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {

        String id = txtId.getText();

        try {
            boolean isDeleted = carBoImpl.deleteCar(id);
            if(isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"Car Deleted").show();
                clearFields();
                loadAllCars();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void btnOnMouseCllicked(MouseEvent mouseEvent) {
        CarTm selectedCar = tableCar.getSelectionModel().getSelectedItem();

        String id = selectedCar.getId();

        try {
            CarDto dto = carBoImpl.searchCar(id);

            if(dto != null){
                this.txtId.setText(dto.getCarId());
                this.cmbCategoryId.setValue(dto.getCategoryId());
                this.txtPlate.setText(dto.getLicensePlate());
                this.txtYear.setText(String.valueOf(dto.getYear()));
                this.txtMake.setText(dto.getMake());
                this.txtModel.setText(dto.getModel());
                this.txtRental.setText(String.valueOf(dto.getDailyRentalRate()));
                this.comboAvialablity.setValue(dto.getAvailable());
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }


    }

    public void cmbSuppllierIdOnAction(ActionEvent actionEvent) {

        String categoryId = String.valueOf(cmbCategoryId.getValue());

        try {
            Connection connection = DBConnection.getInstance().getConnection();

            String sql = "SELECT * FROM carcategories WHERE CategoryID=?";

            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1,categoryId);

            ResultSet rst = pstm.executeQuery();

            if(rst.next()){
                var categoryDto =  new CategoryDto(
                        rst.getString(1),
                        rst.getString(2)
                );

                fillCategoryLabel(categoryDto);

            }


        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void fillCategoryLabel(CategoryDto categoryDto) {
        labelCategoryName.setText(categoryDto.getCategoryName());
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage primaryStage = (Stage) this.rootNode.getScene().getWindow();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Dashboard");
    }
}
