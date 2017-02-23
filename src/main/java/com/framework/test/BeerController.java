package com.framework.test;

import com.framework.annotations.controller.Contoller;
import com.framework.annotations.parameters.PathVariable;
import com.framework.annotations.parameters.RequestVariable;
import com.framework.annotations.requests.GetMapping;
import com.framework.annotations.requests.PostMapping;
import com.framework.model.ModelImpl;

@Contoller
public class BeerController {

    @GetMapping("/beer")
    public String getMapping(ModelImpl model) {
        model.addAttribute("key", "testValue");
        return "beer";
    }

    @PostMapping("/beer")
    public String submitBeer(@RequestVariable("beerBrand") String beerBrand){
        System.out.println(beerBrand);
        return "beer";
    }

    @GetMapping("/beer/edit/{id}")
    public String getId(@PathVariable("id") String id) {
        System.out.println(id);
        return "beer";
    }
}
