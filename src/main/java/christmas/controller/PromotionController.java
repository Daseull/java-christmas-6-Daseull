package christmas.controller;

import christmas.domain.Date;
import christmas.exception.PlannerException;
import christmas.view.InputView;

public class PromotionController {
    private final InputView inputView = new InputView();

    public void run(){
        Date date = askVisitDate();
        askMenus();
        showPlan();
    }

    private Date askVisitDate() {
        while(true){
            try {
                return new Date(inputView.readVisitDate());
            }
            catch (PlannerException e){
                System.out.println(e.getMessage());
            }
        }
    }

    //TODO
    private void askMenus() {

    }

    //TODO
    private void showPlan() {

    }
}
