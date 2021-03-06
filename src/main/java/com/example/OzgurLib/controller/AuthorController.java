package com.example.OzgurLib.controller;

import com.example.OzgurLib.entities.Author;
import com.example.OzgurLib.repositories.AuthorRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Controller
@RequestMapping("/author")
public class AuthorController {

    private final AuthorRepo authorRepo;

    //AutoWired
    public AuthorController(AuthorRepo authorRepo) {
        this.authorRepo = authorRepo;
    }


    @GetMapping("/list")
    public String showList(Model model) {

        var authors = authorRepo.findAll();

        model.addAttribute("authors", authors);

        return "lists/author-list";
    }

    @GetMapping("/showFormForAdd")
    public String showForAddForm(Model model) {

        Author author = new Author();

        model.addAttribute("author", author);

        return "forms/author-form";

    }


    @PostMapping("/save")
    public String save(@RequestParam("picture") MultipartFile file, @ModelAttribute("author") Author author) {

        String encodedPicture = null;

        try {
            encodedPicture = Base64.getEncoder().encodeToString(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }


        author.setPhoto(encodedPicture);


        authorRepo.save(author);

        return "redirect:/author/list";

    }


    @GetMapping("/showDetail")
    public String showDetail(@RequestParam long id , Model model){

        Optional<Author> optionalAuthor = authorRepo.findById(id);

        Author author;

        if(optionalAuthor.isPresent()) { //if there is record

            author = optionalAuthor.get();
            model.addAttribute(author);

        }
        return "details/author-detail";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") long id){

        authorRepo.deleteById(id);

        return "redirect:/author/list";
    }



    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("id") long id,Model model){

        Optional<Author> optionalAuthor = authorRepo.findById(id);

        if(optionalAuthor.isPresent()){
            Author author = optionalAuthor.get();
            model.addAttribute(author);
        }

        return "forms/author-form";
    }


}
