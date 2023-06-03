package com.riis.controller.PageControllerFactory;

import com.riis.controller.Controller;
import com.riis.controller.InfoController.InfoNewResidentController;
import com.riis.controller.InfoController.InfoOverviewController;
import com.riis.controller.InfoController.InfoRenewalController;
import com.riis.controller.InfoController.InfoReplaceController;
import com.riis.controller.InfoController.InfoRequestsController;

public class InfoPageControllerFactory extends AbstractPageControllerFactory{
    public Controller getController(String controllerType) {
        if(controllerType.equalsIgnoreCase("overview")) {
            return new InfoOverviewController();
        } else if(controllerType.equalsIgnoreCase("requests")) {
            return new InfoRequestsController();
        } else if(controllerType.equalsIgnoreCase("new_resident")) {
            return new InfoNewResidentController();
        } else if(controllerType.equalsIgnoreCase("id_renewal")) {
            return new InfoRenewalController();
        } else if(controllerType.equalsIgnoreCase("replace_id")) {
            return new InfoReplaceController();
        }

        return null;
    }
}
