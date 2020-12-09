package com.khodataev.my.app.controller;

import com.khodataev.my.app.model.Role;
import com.khodataev.my.app.model.User;
import com.khodataev.my.app.service.RoleService;
import com.khodataev.my.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.HashSet;
import java.util.Set;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String allUsers(ModelMap model, Principal principal) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("userThis", userService.loadUserByUsername(principal.getName()));
        return "adminPage";
    }

    @GetMapping(value = "add")
    public String addUser(Model model, Principal principal) {
        User user = new User();
        model.addAttribute("userNew", user);
        model.addAttribute("userThis", userService.loadUserByUsername(principal.getName()));
        return "addUser";
    }

    @PostMapping(value = "add")
    public String postAddUser(@ModelAttribute("user") User user,
                              @RequestParam(required = false) String roleAdmin,
                              @RequestParam(required = false) String roleVIP) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getRoleByName("USER"));
        if (roleAdmin != null && roleAdmin.equals("ADMIN")) {
            roles.add(roleService.getRoleByName("ADMIN"));
        }
        if (roleVIP != null && roleVIP.equals("VIP")) {
            roles.add(roleService.getRoleByName("VIP"));
        }
        user.setRoles(roles);
        userService.addUser(user);

        return "redirect:/admin";
    }


    @GetMapping(value = "edit/{id}")
    public String editUser(ModelMap model, @PathVariable("id") Long id, Principal principal) {
        User user = userService.getUserById(id);
        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            if (role.equals(roleService.getRoleByName("ADMIN"))) {
                model.addAttribute("roleAdmin", true);
            }
            if (role.equals(roleService.getRoleByName("VIP"))) {
                model.addAttribute("roleVIP", true);
            }
        }
        model.addAttribute("userDB", user);
        model.addAttribute("userThis", userService.loadUserByUsername(principal.getName()));
        return "editUser";
    }

    @PostMapping(value = "edit")
    public String postEditUser(@ModelAttribute("user") User user,
                               @RequestParam(required = false) String roleAdmin,
                               @RequestParam(required = false) String roleVIP) {

        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getRoleByName("USER"));
        if (roleAdmin != null && roleAdmin.equals("ADMIN")) {
            roles.add(roleService.getRoleByName("ADMIN"));
        }
        if (roleVIP != null && roleVIP.equals("VIP")) {
            roles.add(roleService.getRoleByName("VIP"));
        }
        user.setRoles(roles);
        userService.editUser(user);
        return "redirect:/admin";
    }

    @GetMapping("delete/{id}")
    public String deleteUserById(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

}
