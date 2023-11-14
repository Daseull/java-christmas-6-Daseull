package christmas.domain;

import christmas.Constant;
import christmas.domain.menu.Category;
import christmas.domain.menu.Menu;
import christmas.exception.ErrorMessage;
import christmas.exception.PlannerException;
import java.util.Map;
import java.util.Map.Entry;

public class Order {
    private final Menus menus;

    public Order(Menus menus) {
        validateCategory(menus);
        this.menus = menus;
    }

    // view 를 위한 getter
    public Map<Menu, Integer> getOrderMenu() {
        return menus.getMenus();
    }

    private void validateCategory(Menus menus) {
        int numBeverage = menus.countByCategory(Category.BEVERAGE);
        int totalCount = menus.totalCount();

        if(numBeverage == totalCount){
            throw new PlannerException(ErrorMessage.INVALID_ORDER_MESSAGE);
        }
    }

    public int totalPrice(){
        return menus.totalPrice();
    }

    public int countByCategory(Category category){
        return menus.countByCategory(category);
    }




}
