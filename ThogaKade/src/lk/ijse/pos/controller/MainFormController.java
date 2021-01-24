package lk.ijse.pos.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFormController {
    public AnchorPane root;

    public void openCustomerForm(MouseEvent mouseEvent) throws IOException {
        this.root.getChildren().clear();
        this.root.getChildren().add(FXMLLoader.load(this.getClass().getResource("../view/CustomerForm.fxml")));
    }

    public void openItemForm(MouseEvent mouseEvent) throws IOException {
        this.root.getChildren().clear();
        this.root.getChildren().add(FXMLLoader.load(this.getClass().getResource("../view/ItemForm.fxml")));
    }

    public void openOrderForm(MouseEvent mouseEvent) {
    }

    public void openPlaceOrderForm(MouseEvent mouseEvent) {
    }

    /*private void setUI(String location) throws IOException {
        this.root.getChildren().clear();
        this.root.getChildren()
                .add(FXMLLoader.
                        load(this.getClass().getResource("/view/" +
                                location)));
    }*/

}
