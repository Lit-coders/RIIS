package com.riis.controller.FinController;

import com.riis.controller.BaseController.BaseSidebarController;
import com.riis.controller.PageControllerFactory.AbstractPageControllerFactory;
import com.riis.controller.PageControllerFactory.PageControllerFactoryProducer;
import com.riis.model.viewmodel.SidebarModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class FinSidebarController extends BaseSidebarController {

    public FinSidebarController(Stage stage) {
        super(stage);
    }

    public FinSidebarController() {
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
        root = FXMLLoader.load(getClass().getResource("/com/riis/fxml/Fin_fxml/FinSidebar.fxml"));
        BorderPane borderPane = (BorderPane) root;
        SidebarModel.borderPane = borderPane;
        showView();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void showView() throws Exception {
        AbstractPageControllerFactory FinFactory = PageControllerFactoryProducer.getFactory("FinanceOfficer");
        FinFactory.getController(SidebarModel.clickedHbox.getId()).getView();
        setTitlebar(SidebarModel.clickedHbox.getId());
    }

}