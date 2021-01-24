package lk.ijse.pos.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.pos.bo.BOFactory;
import lk.ijse.pos.bo.BOType;
import lk.ijse.pos.bo.custom.CustomerBO;
import lk.ijse.pos.bo.custom.impl.CustomerBOImpl;
import lk.ijse.pos.dao.DAOFactory;
import lk.ijse.pos.dto.CustomerDTO;
import lk.ijse.pos.view.tm.CustomerTM;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class CustomerFormController {
    public TextField txtId;
    public AnchorPane root;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtSalary;
    public TextField txtSearch;
    public TableView<CustomerTM> tableCustomer;
    public TableColumn colCustomerId;
    public TableColumn colCustomerName;
    public TableColumn colAddress;
    public TableColumn colSalary;
    public TableColumn colOperate;
    CustomerBOImpl customerBOImpl = BOFactory.getInstance().getBO(BOType.CUSTOMER);

    public void initialize() throws Exception {
        findAll();
        tableListener();
        setCellValueFactory();
    }
    private void tableListener(){
        tableCustomer.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    setData(newValue);
                });
    }

    private void setData(CustomerTM tm) {
        try {
            txtId.setText(tm.getId());
            txtName.setText(tm.getName());
            txtAddress.setText(tm.getAddress());
            txtSalary.setText(String.valueOf(tm.getSalary()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void setCellValueFactory(){
        colCustomerId.setCellValueFactory(new PropertyValueFactory("Id"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory("Name"));
        colAddress.setCellValueFactory(new PropertyValueFactory("Address"));
        colSalary.setCellValueFactory(new PropertyValueFactory("Salary"));
        colOperate.setCellValueFactory(new PropertyValueFactory("btn"));
    }
    public void findAll(){
        try {
            ObservableList<CustomerTM> tmList = FXCollections.observableArrayList();
            List<CustomerDTO> all = customerBOImpl.findAll();
            for(CustomerDTO dto : all){
                Button btn = new Button("Delete");
                CustomerTM tm = new CustomerTM(
                        dto.getId(),
                        dto.getName(),
                        dto.getAddress(),
                        dto.getSalary(),
                        btn
                );
                tmList.add(tm);
                btn.setOnAction((e) -> {
                    try {
                        ButtonType ok = new ButtonType("OK",
                                ButtonBar.ButtonData.OK_DONE);
                        ButtonType no = new ButtonType("NO",
                                ButtonBar.ButtonData.CANCEL_CLOSE);
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                                "Are You Sure ?", ok, no);
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.orElse(no) == ok) {
                            if (customerBOImpl.delete(tm.getId())) {
                                new Alert(Alert.AlertType.CONFIRMATION,
                                        "Deleted", ButtonType.OK).show();
                                findAll();
                                return;
                            }
                            new Alert(Alert.AlertType.WARNING,
                                    "Sonthing Went Wrong!Try Again", ButtonType.OK).show();
                        } else {
                            System.out.println("1");
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                });
            }
            tableCustomer.setItems(tmList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.WARNING,"Something went wrong !").show();
        }
    }

    public void backToHomeOnAction(MouseEvent mouseEvent) {
        this.root.getChildren().clear();
        try {
            this.root.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/MainForm.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getDataOnAction(ActionEvent actionEvent) {
    }

    public void saveOnAction(ActionEvent actionEvent){
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        double salary = Double.parseDouble(txtSalary.getText());

        try {
            if (customerBOImpl.add(new CustomerDTO(
                    id,
                    name,
                    address,
                    salary
            )));{
                new Alert(Alert.AlertType.CONFIRMATION, "Sure ?").showAndWait();
                txtId.setText(null);
                txtName.setText(null);
                txtSalary.setText(null);
                txtAddress.setText(null);
               findAll();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Try again!").showAndWait();
        }
    }
}
