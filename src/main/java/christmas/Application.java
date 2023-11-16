package christmas;

import christmas.controller.PromotionController;

public class Application {
    public static void main(String[] args) {
        PromotionController promotionController = new PromotionController();
        promotionController.run();
    }
}
