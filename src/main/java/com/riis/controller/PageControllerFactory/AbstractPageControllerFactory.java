package com.riis.controller.PageControllerFactory;

import com.riis.controller.Controller;

public abstract class AbstractPageControllerFactory {
    public abstract Controller getController(String controllerType) throws Exception;
}

