package com.ebook.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ebook.entity.Roles;
import com.ebook.service.RoleService;

@Controller
@RequestMapping("/admin/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/getroles")
    public String getAllRoles(Model model) {
        List<Roles> roles = roleService.getAllRoless();
        model.addAttribute("roles", roles);
        return "Admine/roles";
    }

    @GetMapping("/add")
    public String showRoleForm(Model model) {
        model.addAttribute("role", new Roles());
        model.addAttribute("editMode", false);
        return "Admine/role-form";
    }

    @PostMapping("/save")
    public String saveRole(@ModelAttribute("role") Roles role) {
        roleService.createRoles(role);
        return "redirect:/admin/roles/getroles";
    }

    @GetMapping("/edit/{id}")
    public String showEditRoleForm(@PathVariable Long id, Model model) {
        Roles role = roleService.getRolesById(id);
        model.addAttribute("role", role);
        model.addAttribute("editMode", true);
        return "Admine/role-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteRole(@PathVariable Long id) {
        roleService.deleteRoles(id);
        return "redirect:/admin/roles/getroles";
    }
}
