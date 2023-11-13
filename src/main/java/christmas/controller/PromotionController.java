package christmas.controller;

import static christmas.Constant.D_DAY_DISCOUNT_NAME;
import static christmas.Constant.GIVEAWAY_EVENT_NAME;
import static christmas.Constant.SPECIAL_DISCOUNT_NAME;
import static christmas.Constant.WEEKDAY_DISCOUNT_NAME;
import static christmas.Constant.WEEKEND_DISCOUNT_NAME;

import christmas.domain.Date;
import christmas.domain.Order;
import christmas.domain.OrderMenu;
import christmas.domain.event.DDayDiscount;
import christmas.domain.event.EventPolicy;
import christmas.domain.event.Giveaway;
import christmas.domain.event.SpecialDiscount;
import christmas.domain.event.WeekdayDiscount;
import christmas.domain.event.WeekendDiscount;
import christmas.exception.PlannerException;
import christmas.view.InputView;
import christmas.view.OutputView;
import christmas.view.Parser;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PromotionController {
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();

    public void run() {
        Date date = askVisitDate();
        Order order = askMenus();
        showPlan(date, order);
    }

    private Date askVisitDate() {
        while (true) {
            try {
                return new Date(inputView.readVisitDate());
            } catch (PlannerException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private Order askMenus() {
        while (true) {
            try {
                return createOrder(inputView.readMenus());
            } catch (PlannerException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private Order createOrder(List<String> menus) {
        OrderMenu orderMenu = new OrderMenu();
        menus.stream()
                .map(menu -> Parser.parseMenu(menu, "-"))
                .forEach(menuCount -> orderMenu.add(menuCount.menu(), menuCount.count()));
        return new Order(orderMenu.getOrderMenu());
    }

    //TODO
    private void showPlan(Date date, Order order) {
        outputView.printPlanHeader();
        showOrder(order);
        outputView.printTotalAmount(order.totalPrice());
        showGiveaway(date, order);
        showBenefitDetails(date, order);
    }

    private void showOrder(Order order) {
        List<MenuCount> menuCounts = toMenuCount(order);
        outputView.printOrder(menuCounts);
    }

    private void showGiveaway(Date date, Order order) {
        Giveaway giveaway = new Giveaway();
        outputView.printGiveaway(giveaway.receiveGiveaway(date, order));
    }

    private void showBenefitDetails(Date date, Order order) {
        Map<String, EventPolicy> policies = new LinkedHashMap<>();
        policies.put(D_DAY_DISCOUNT_NAME, new DDayDiscount());
        policies.put(WEEKDAY_DISCOUNT_NAME, new WeekdayDiscount());
        policies.put(WEEKEND_DISCOUNT_NAME, new WeekendDiscount());
        policies.put(SPECIAL_DISCOUNT_NAME, new SpecialDiscount());
        policies.put(GIVEAWAY_EVENT_NAME, new Giveaway());

        List<DiscountAmount> discountAmounts = policies.entrySet().stream()
                .map(entry -> new DiscountAmount(entry.getKey(), entry.getValue().amount(date, order)))
                .filter(dto -> dto.amount() < 0)
                .toList();
        outputView.printBenefitDetails(discountAmounts);
    }

    private List<MenuCount> toMenuCount(Order order) {
        return order.getOrderMenu().entrySet()
                .stream()
                .map(entry -> new MenuCount(entry.getKey().description(), entry.getValue()))
                .toList();
    }
}
