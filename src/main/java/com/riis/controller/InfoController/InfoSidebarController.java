package com.riis.controller.InfoController;

import com.riis.controller.BaseController.BaseSidebarController;
import com.riis.controller.PageControllerFactory.AbstractPageControllerFactory;
import com.riis.controller.PageControllerFactory.PageControllerFactoryProducer;
import com.riis.model.viewmodel.SidebarModel;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class InfoSidebarController extends BaseSidebarController {

    public InfoSidebarController(Stage stage) {
        super(stage);
    }

    public InfoSidebarController() {
    }

    @Override
    public void initialize() throws Exception {
        super.initialize();
        clickedHbox = overview;
        SidebarModel.clickedHbox = clickedHbox;
    }

    @Override
    public void getView() throws Exception {
        stage.close();
        Parent root = FXMLLoader.load(getClass().getResource("/com/riis/fxml/Sidebar_fxml/InfoSidebar.fxml"));
        BorderPane borderPane = (BorderPane) root;
        SidebarModel.borderPane = borderPane;
        showView();

        Scene scene = new Scene(root, 1300, 700);
        stage.setScene(scene);
        stage.setTitle("Information officer");
        // stage.setMaximized(true);
// stage.setMaximized(true);
        stage.show();

    }

    @Override
    public void showView() throws Exception {
        AbstractPageControllerFactory InfoFactory = PageControllerFactoryProducer.getFactory("InformationOfficer");
        InfoFactory.getController(SidebarModel.clickedHbox.getId()).getView();
        setTitlebar(SidebarModel.clickedHbox.getId());
    }
}