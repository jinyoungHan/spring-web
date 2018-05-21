package kr.ac.cnu.web.controller.api;

import kr.ac.cnu.web.model.Meal;
import kr.ac.cnu.web.model.Menu;
import kr.ac.cnu.web.model.TodayMenu;
import kr.ac.cnu.web.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rokim on 2018. 5. 21..
 */
@RestController
public class MenuApiController {
    @Autowired
    private MenuRepository menuRepository;

    @PostMapping("/api/menus")
    public Menu insetAction(@RequestBody Menu menu) {

        menuRepository.save(menu);

        return menu;
    }

    @GetMapping("/api/menus/{today}")
    public TodayMenu menu(@PathVariable String today) {
        List<Menu> menuList = menuRepository.findAllByToday(today);
        List<String> breakfastList = new ArrayList<>();
        List<String> launchList = new ArrayList<>();
        List<String> dinnerList = new ArrayList<>();
        for (Menu menu : menuList) {
            if (menu.getMeal() == Meal.BREAKFAST) {
                breakfastList.add(menu.getMenuName());
            } else if (menu.getMeal() == Meal.LAUNCH) {
                launchList.add(menu.getMenuName());
            } else if (menu.getMeal() == Meal.DINNER) {
                dinnerList.add(menu.getMenuName());
            }
        }
        TodayMenu todayMenu = new TodayMenu();
        todayMenu.setToday(today);
        todayMenu.setBreakfastList(breakfastList);
        todayMenu.setLaunchList(launchList);
        todayMenu.setDinnerList(dinnerList);

        return todayMenu;

    }
}
