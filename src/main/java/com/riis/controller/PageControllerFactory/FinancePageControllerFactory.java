package com.riis.controller.PageControllerFactory;

import com.riis.controller.Controller;
import com.riis.controller.FinController.FinOverviewController;
import com.riis.controller.FinController.FinRequestsController;

public class FinancePageControllerFactory extends AbstractPageControllerFactory {
    public Controller getController(String controllerType) {
        if(controllerType.equalsIgnoreCase("overview")) {
            return new FinOverviewController();
        } 
        else if(controllerType.equalsIgnoreCase("requests")) {
            return new FinRequestsController();
        }
        return null;
    }
}