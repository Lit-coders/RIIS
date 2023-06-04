package com.riis.controller.PageControllerFactory;

public interface PageControllerFactoryProducer {
    public static AbstractPageControllerFactory getFactory(String actor) {
        if(actor.equals("FinanceOfficer")) {
            return new FinancePageControllerFactory();
        } else if(actor.equals("KebeleManager")) {
            return new KebelePageControllerFactory();
        } else if(actor.equals("InformationOfficer")) {
            return new InfoPageControllerFactory();
        } else {
            return null;
        }
    } 
}

