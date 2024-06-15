package com.assignment2.assignment2.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.assignment2.assignment2.models.Rectangle;
import com.assignment2.assignment2.models.RectangleRepository;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class RectanglesController {

    @Autowired
    private RectangleRepository rectangleRepo;
    @GetMapping("/rectangles/view")
    public String getAllRectangles(Model model) {
        System.out.println("Getting all rectangles");

        // Get all rectangles from database
        List<Rectangle> rectangles = rectangleRepo.findAll();
        rectangles = rectangleRepo.findAllByOrderByUidAsc();
        // end of database call

        model.addAttribute("rt", rectangles);
        return "rectangles/showAll";
    }

    @PostMapping("/rectangles/add")
    public String addRectangle(@RequestParam Map<String, String> newrectangle, HttpServletResponse response) {
        System.out.println("ADD user");
        String newName = newrectangle.get("name");
        String newColor = newrectangle.get("color");
        int newWidth = Integer.parseInt(newrectangle.get("width"));
        int newHeight = Integer.parseInt(newrectangle.get("height"));
        rectangleRepo.save(new Rectangle(newName, newColor, newWidth, newHeight));
        response.setStatus(201);
        return "rectangles/addedRectangle";
    }

    @RequestMapping("/rectangles/edit/{uid}")
    public String edit(@PathVariable("uid") Integer uid, Model model) {
        Optional<Rectangle> rectangle = rectangleRepo.findById(uid);
        model.addAttribute("rectangle", rectangle);
        return "rectangles/showRectangle";
    }

    @RequestMapping("/rectangles/update")
    public String edit(Model model, Rectangle rectangle) {
        rectangleRepo.save(rectangle);
        List<Rectangle> rectangles = rectangleRepo.findAll();
        model.addAttribute("rectangle", rectangles);
        return "redirect:/rectangles/view";
    }
    
    @RequestMapping("/rectangles/delete/{uid}")
    public String deleteOneRectangle(@PathVariable("uid") Integer uid, Model model) {
        Optional<Rectangle> rectangle = rectangleRepo.findById(uid);
        rectangleRepo.delete(rectangle.get());
        List<Rectangle> rectangles = rectangleRepo.findAll();
        model.addAttribute("rc", rectangles);
        return "rectangles/showDelete";
    }
    
    
}
