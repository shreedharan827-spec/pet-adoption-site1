package com.petadoption.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Redirects root path to API base path for deployment.
 */
@RestController
@RequestMapping("/")
public class RootController {

    @GetMapping
    public RedirectView redirectToApi() {
        return new RedirectView("/api");
    }

    @GetMapping("status")
    public String status() {
        return "Pet Adoption API is running";
    }
}