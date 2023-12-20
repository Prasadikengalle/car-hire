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
import lk.ijse.carHire.business.custom.CustomerBo;
import lk.ijse.carHire.business.custom.impl.CustomerBoImpl;
import lk.ijse.carHire.dto.CustomerDto;
import lk.ijse.carHire.dto.tm.CustomerTm;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CustomerFormController {
    public AnchorPane rootNode;
    public TextField txtId;
    public TextField txtAddress;
    public TextField txtPhone;
    public TextField txtCity;
    public TextField txtTitle;
    public TextField txtName;
    public TextField txtProvince;
    public TextField txtZip;
    public DatePicker dpDob;
    public TableView<CustomerTm> tableCustomer;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colZip;
    public TableColumn colPhone;

    private CustomerBo customerBoImpl = BoFactory.getBo(BoType.CUSTOMER);

    public void initialize(){
        System.out.println("Customer Form Just Loaded");

        setCellValueFactory();
        loadAllCustomers();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colZip.setCellValueFactory(new PropertyValueFactory<>("zip"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
    }

    public void txtIdOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();

        try {
            CustomerDto dto = customerBoImpl.searchCustomer(id);

            if(dto != null) {
                this.txtTitle.setText(dto.getTitle());
                this.txtName.setText(dto.getName());
                this.dpDob.setValue(LocalDate.parse(dto.getDob()));
                this.txtPhone.setText(dto.getPhone());
                this.txtAddress.setText(dto.getAddress());
                this.txtCity.setText(dto.getCity());
                this.txtProvince.setText(dto.getProvince());
                this.txtZip.setText(dto.getZip());
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String title = txtTitle.getText();
        String name = txtName.getText();
        String dob = getFormattedDateFromDatePicker(dpDob.getValue());
        String phone = txtPhone.getText();
        String address = txtAddress.getText();
        String city = txtCity.getText();
        String province = txtProvince.getText();
        String zip = txtZip.getText();

        var customerDto = new CustomerDto(id,title,name,dob, phone, address,city, province,zip);

        try {
            boolean isSaved = customerBoImpl.saveCustomer(customerDto);
            if(isSaved){
                new  Alert(Alert.AlertType.CONFIRMATION,"Customer Saved").show();
                clearFields();
                loadAllCustomers();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void clearFields() {

        txtId.setText("");
        txtTitle.setText("");
        txtName.setText("");
        txtName.setText("");
        dpDob.getEditor().clear();
        txtPhone.setText("");
        txtAddress.setText("");
        txtCity.setText("");
        txtProvince.setText("");
        txtZip.setText("");
    }

    private String getFormattedDateFromDatePicker(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return date.format(formatter);
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String title = txtTitle.getText();
        String name = txtName.getText();
        String dob = getFormattedDateFromDatePicker(dpDob.getValue());
        String phone = txtPhone.getText();
        String address = txtAddress.getText();
        String city = txtCity.getText();
        String province = txtProvince.getText();
        String zip = txtZip.getText();

        var customerDto = new CustomerDto(id,title,name,dob,phone, address,city,province,zip);

        try {
            boolean isUpdated = customerBoImpl.updateCustomer(customerDto);
            if(isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION,"Customer Updated").show();
                clearFields();
                loadAllCustomers();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void loadAllCustomers() {

        try {
            List<CustomerDto> customerDtos = customerBoImpl.getAllCustomers();

            ObservableList<CustomerTm> tableData = FXCollections.observableArrayList();

            for(CustomerDto dto : customerDtos){
                tableData.add(new CustomerTm(
                        dto.getId(),
                        dto.getTitle()+ " " + dto.getName(),
                        dto.getAddress()+", "+dto.getCity()+", "+dto.getProvince(),
                        dto.getPhone(),
                        dto.getZip()));
            }
            tableCustomer.setItems(tableData);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }


    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {

        String id = txtId.getText();

        try {
            boolean isDeleted = customerBoImpl.deleteCustomer(id);

            if(isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"Customer Deleted").show();
                clearFields();
                loadAllCustomers();
            }else {
                new Alert(Alert.AlertType.CONFIRMATION,"Customer Not Deleted").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void btnOnMouseCllicked(MouseEvent mouseEvent) {

        CustomerTm selectedCustomer = tableCustomer.getSelectionModel().getSelectedItem();

        String id = selectedCustomer.getId();

        try {
            CustomerDto dto = customerBoImpl.searchCustomer(id);
            if(dto != null) {
                this.txtId.setText(dto.getId());
                this.txtTitle.setText(dto.getTitle());
                this.txtName.setText(dto.getName());
                this.dpDob.setValue(LocalDate.parse(dto.getDob()));
                this.txtPhone.setText(dto.getPhone());
                this.txtAddress.setText(dto.getAddress());
                this.txtCity.setText(dto.getCity());
                this.txtProvince.setText(dto.getProvince());
                this.txtZip.setText(dto.getZip());
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }


    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {

        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage primaryStage = (Stage) this.rootNode.getScene().getWindow();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Dashboard");


    }
}
