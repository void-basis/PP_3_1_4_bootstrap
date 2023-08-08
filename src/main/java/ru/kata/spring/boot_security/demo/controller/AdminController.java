package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
//@RequestMapping("/admin")
public class AdminController {

    public final UserService userService;
    public final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/admin/users")
    public String usersPage(ModelMap model, Principal principal) {
        model.addAttribute("usersList", userService.listUsers());
        model.addAttribute("currentUser", userService.loadUserByUsername(principal.getName()));
        return "users";
    }

    @GetMapping(value = "/admin/addUser")
    public String addUser(ModelMap model) {
        model.addAttribute("addUser", new User());
        return "addUser";
    }

    @PostMapping("/admin/users")
    public String create(@ModelAttribute("addUser") User user, @RequestParam String role) {
        userService.add(user, role);
        return "redirect:/admin/users";
    }

    @GetMapping(value = "/admin/userUpdate/{id}")
    public String update(@PathVariable("id") long id, ModelMap model) {
        model.addAttribute("userUpdate", userService.getUser(id));
        return "userUpdate";
    }

    @PostMapping("/admin/userUpdate")
    public String update(@ModelAttribute("userUpdate") User user, @RequestParam String role) {
        userService.updateUser(user, role);
        return "redirect:/admin/users";
    }

    @GetMapping(value = "/admin/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteUserById(id);
        return "redirect:/admin/users";
    }

    @GetMapping(value = "/user/{id}")
    public String userPage(ModelMap model, @PathVariable("id") long id) {
        model.addAttribute("user", userService.getUser(id));
        return "user";
    }
}
