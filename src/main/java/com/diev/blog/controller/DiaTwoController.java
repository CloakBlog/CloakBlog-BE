package com.diev.blog.controller;

import com.diev.blog.domain.DiaTwoBlog;
import com.diev.blog.dto.BlogDto;
import com.diev.blog.service.DiaTwoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/diatwo")
public class DiaTwoController {

    @Autowired
    private final DiaTwoService diaTwoService;

    public DiaTwoController(DiaTwoService diaTwoService) {
        this.diaTwoService = diaTwoService;
    }

    @GetMapping("/")
    public String diaHome() {
        return "dia-two/home";
    }

    @GetMapping("/posts")
    public List<DiaTwoBlog> getAllBlogs() {
        return diaTwoService.getAllDiaTwoBlog();
    }

    @PostMapping("/post")
    public DiaTwoBlog diaBlogWrite(@RequestBody DiaTwoBlog request) {
        return diaTwoService.saveDiaTwoBlog(request);
    }

    @PutMapping("/post/{id}")
    public DiaTwoBlog diaBlogUpdate(@PathVariable("id") long id, @RequestBody BlogDto request) {
        return diaTwoService.updateDiaTwoBlog(id, request);
    }

    @DeleteMapping("/post/{id}")
    public String diaBlogDelete(@PathVariable("id") long id) {
        diaTwoService.delete(id);
        return "Delete Successfully!";
    }
}
