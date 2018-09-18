package nl.sjop.controller;

import nl.sjop.drinks.BusinessServiceAutowiredImpl;
import nl.sjop.drinks.BusinessServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class APIController {

    @Autowired
    BusinessServiceAutowiredImpl businessServiceAutowired;

    @RequestMapping("/")
    public String index() {
        //return new BusinessServiceImpl().produkten()
          return businessServiceAutowired.produkten();
    }
}