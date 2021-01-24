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
import lk.ijse.pos.bo.custom.ItemBo;
import lk.ijse.pos.bo.custom.impl.ItemBOImpl;
import lk.ijse.pos.dao.DAOFactory;
import lk.ijse.pos.dto.ItemDTO;
import lk.ijse.pos.view.tm.ItemTM;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ItemFormController {
    public AnchorPane root;
    public TextField txtItemCode;
    public TextField txtDescription;
    public TextField txtQtyOnHand;
    public TextField txtPrice;
    public TableView<ItemTM> tableItem;
    public TableColumn colCode;
    public TableColumn colDescription;
    public TableColumn colqty;
    public TableColumn colPrice;
    public TableColumn colOperate;
    public TextField txtSearch;

    ItemBOImpl itemBO = BOFactory.getInstance().getBO(BOType.ITEM);

    public void initialize() throws Exception {
        findAll();
        tableListener();
        setCellValueFactory();
    }
    private void tableListener(){
        tableItem.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    setData(newValue);
                });
    }
    private void setData(ItemTM tm) {
        try {
            txtItemCode.setText(tm.getCode());
            txtDescription.setText(tm.getDescription());
            txtPrice.setText(String.valueOf(tm.getUnitPrice()));
            txtQtyOnHand.setText(String.valueOf(tm.getQtyOnHand()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void setCellValueFactory(){
        colCode.setCellValueFactory(new PropertyValueFactory("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory("description"));
        colPrice.setCellValueFactory(new PropertyValueFactory("unitPrice"));
        colqty.setCellValueFactory(new PropertyValueFactory("qtyOnHand"));
        colOperate.setCellValueFactory(new PropertyValueFactory("btn"));
    }
    public void findAll(){
        try {
            ObservableList<ItemTM> tmList = FXCollections.observableArrayList();
            List<ItemDTO> all = itemBO.findAll();
            for(ItemDTO dto : all){
                Button btn = new Button("Delete");
                ItemTM tm = new ItemTM(
                        dto.getCode(),
                        dto.getDescription(),
                        dto.getPrice(),
                        dto.getQtyOnHand(),
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
                            if (itemBO.delete(tm.getCode())) {
                                new Alert(Alert.AlertType.CONFIRMATION,
                                        "Deleted", ButtonType.OK).show();
                                txtItemCode.setText(null);
                                txtDescription.setText(null);
                                txtPrice.setText(null);
                                txtQtyOnHand.setText(null);
                                findAll();
                                return;
                            }
                            new Alert(Alert.AlertType.WARNING,
                                    "Try Again", ButtonType.OK).show();
                        } else {
                            System.out.println("1");
                        }


                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                });
            }
            tableItem.setItems(tmList);
        } catch (Exception e) {
             new Alert(Alert.AlertType.WARNING,"Something Went Wrong !").show();
        }
    }

    public void saveOnAction(ActionEvent actionEvent) {
        String code = txtItemCode.getText();
        String description = txtDescription.getText();
        double unitPrice = Double.parseDouble(txtPrice.getText());
        int qty = Integer.parseInt(txtQtyOnHand.getText());

        try {
            if(itemBO.add(new ItemDTO(
                    code,
                    description,
                    unitPrice,
                    qty
            ))){
                txtItemCode.setText(null);
                txtDescription.setText(null);
                txtPrice.setText(null);
                txtQtyOnHand.setText(null);
                new Alert(Alert.AlertType.INFORMATION, "Item Added !").show();
                findAll();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getDataOnAction(ActionEvent actionEvent) {
    }

    public void backToHome(MouseEvent mouseEvent) {
    }
}
