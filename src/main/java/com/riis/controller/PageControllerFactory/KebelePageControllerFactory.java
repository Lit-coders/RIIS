package com.riis.controller.PageControllerFactory;

import com.riis.controller.Controller;
import com.riis.controller.KebeleController.KebeleOverviewController;
import com.riis.controller.KebeleController.KebeleRequestsController;

public class KebelePageControllerFactory extends AbstractPageControllerFactory {
    public Controller getController(String controllerType) {
        if (controllerType.equalsIgnoreCase("overview")) {
            return new KebeleOverviewController();
        } else if (controllerType.equalsIgnoreCase("requests")) {
            return new KebeleRequestsController();
        }

        return null;
    }
}
