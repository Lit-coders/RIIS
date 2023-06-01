package com.riis.controller.InfoController;

import com.riis.controller.Controller;
import com.riis.utils.Sidebar;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class InfoReplaceController implements Controller{

    @FXML
    private AnchorPane bocy_anchor;

    @FXML
    private VBox detail_box;

    @FXML
    private HBox detail_hbox;

    @FXML
    private HBox detail_hbox1;

    @FXML
    private VBox detail_vbox;

    @FXML
    private Button re_approve_btn;

    @FXML
    private Button re_clear_btn;

    @FXML
    private Button re_search_btn;

    @FXML
    private TextField re_search_field;

    @FXML
    private ListView<?> replace_list;

    @FXML
    private HBox search_box;

    @Override
    public void getView() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/riis/fxml/Info_fxml/InfoReplace.fxml"));

        AnchorPane anchorPane = (AnchorPane) root;
        Sidebar.borderPane.setCenter(anchorPane);
    }
}