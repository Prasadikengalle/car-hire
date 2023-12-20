package lk.ijse.carHire.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.carHire.business.BoFactory;
import lk.ijse.carHire.business.BoType;
import lk.ijse.carHire.business.custom.CategoryBo;
import lk.ijse.carHire.dto.CategoryDto;
import lk.ijse.carHire.dto.tm.CategoryTm;

import java.io.IOException;
import java.util.List;

public class CategoryFormController {
    public AnchorPane rootNode;
    public TextField txtId;
    public TextField txtName;
    public TableView<CategoryTm> tableCategory;
    public TableColumn<?,?> colId;
    public TableColumn<?,?> colName;

    public void initialize(){

        setCellValueFactory();
        loadAllCategories();


    }

    private CategoryBo categoryBoImpl = BoFactory.getBo(BoType.CATEGORY);

    private void setCellValueFactory() {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
    }

    private void loadAllCategories() {

        try {
            List<CategoryDto> categoryList = categoryBoImpl.getAllCategories();
            ObservableList<CategoryTm> tableData = FXCollections.observableArrayList();

            for(CategoryDto dto : categoryList){
                tableData.add(new CategoryTm(dto.getId(),dto.getCategoryName()));
            }
            tableCategory.setItems(tableData);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }


    }

    public void txtIdOnAction(ActionEvent actionEvent) {

        String id = txtId.getText();

        try {
            CategoryDto dto = categoryBoImpl.searchCategory(id);
            if(dto!= null){
                this.txtName.setText(dto.getCategoryName());
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String categoryName = txtName.getText();

        CategoryDto dto = new CategoryDto(id,categoryName);

        try {
            boolean isSaved = categoryBoImpl.saveCategory(dto);
            if(isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"Category Saved").show();
                clearFields();
                loadAllCategories();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String categoryName = txtName.getText();

        CategoryDto dto = new CategoryDto(id,categoryName);

        try {
            boolean isUpdated = categoryBoImpl.updateCategory(dto);
            if(isUpdated){
                new Alert(Alert.AlertType.INFORMATION,"Category Updated").show();
                clearFields();
                loadAllCategories();
            }
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
            boolean isDeleted = categoryBoImpl.deleteCategory(id);
            if(isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"Category Deleted").show();
                clearFields();
                loadAllCategories();
            }else {
                new Alert(Alert.AlertType.CONFIRMATION,"Category Not Found").show();
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void btnOnMouseCllicked(MouseEvent mouseEvent) {

        CategoryTm selectedCategory = tableCategory.getSelectionModel().getSelectedItem();
        String id = selectedCategory.getId();

        try {
            CategoryDto dto = categoryBoImpl.searchCategory(id);
            if(dto != null){
                this.txtId.setText(dto.getId());
                this.txtName.setText(dto.getCategoryName());
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
