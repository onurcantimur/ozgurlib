package com.example.OzgurLib.controller;

import com.example.OzgurLib.entities.Publisher;
import com.example.OzgurLib.repositories.PublisherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/publisher")
@Controller
public class PublisherController {

    @Autowired
    private PublisherRepo publisherRepo;


    @GetMapping("/list")
    public String showList(Model model){

        var publishers = publisherRepo.findAll();

        model.addAttribute("publisherList",publishers);


        return "lists/publisher-list";
    }


    @GetMapping("/add")
    public String showFormForAdd(Model model){

        Publisher publisher = new Publisher();

        model.addAttribute(publisher);

        return "forms/publisher-form";

    }


}
