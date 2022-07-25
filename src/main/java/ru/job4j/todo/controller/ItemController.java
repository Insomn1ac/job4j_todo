package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Account;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.service.CategoryService;
import ru.job4j.todo.service.ItemService;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class ItemController {
    private final ItemService service;
    private final CategoryService categoryService;

    public ItemController(ItemService service, CategoryService categoryService) {
        this.service = service;
        this.categoryService = categoryService;
    }

    private Account setAccountToModel(Model model, HttpSession session) {
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            account = new Account();
            account.setName("Гость");
        }
        model.addAttribute("account", account);
        return account;
    }

    @GetMapping("/tasks")
    public String index(Model model, HttpSession session) {
        model.addAttribute("tasks", service.findAll());
        setAccountToModel(model, session);
        return "tasks";
    }

    @GetMapping("/completedTasks")
    public String completedTasks(Model model, HttpSession session) {
        model.addAttribute("completed", service.findCompletedTasks());
        setAccountToModel(model, session);
        return "completedTasks";
    }

    @GetMapping("/newTasks")
    public String newTasks(Model model, HttpSession session) {
        model.addAttribute("newTasks", service.findNewTasks());
        setAccountToModel(model, session);
        return "newTasks";
    }

    @GetMapping("/userTasks")
    public String userTasks(Model model, HttpSession session) {
        Account account = (Account) session.getAttribute("account");
        model.addAttribute("userTasks", service.findByAccountId(account.getId()));
        setAccountToModel(model, session);
        return "userTasks";
    }

    @GetMapping("/addTask")
    public String addTask(Model model, HttpSession session) {
        setAccountToModel(model, session);
        model.addAttribute("categories", categoryService.findAll());
        return "addTask";
    }

    @PostMapping("/createTask")
    public String createTask(@ModelAttribute Item item,
                             @RequestParam("categoriesId") List<String> categoriesId,
                             HttpSession session) {
        Account account = (Account) session.getAttribute("account");
        item.setAccount(account);
        item.setCreated(new Date(System.currentTimeMillis()));
        service.add(item, categoriesId);
        return "redirect:/tasks";
    }

    @GetMapping("/task/{taskId}")
    public String taskInfo(Model model, @PathVariable("taskId") int id, HttpSession session) {
        model.addAttribute("task", service.findById(id));
        setAccountToModel(model, session);
        return "task";
    }

    @GetMapping("/formUpdateTask/{taskId}")
    public String formUpdateTask(Model model, @PathVariable("taskId") int id, HttpSession session) {
        model.addAttribute("task", service.findById(id));
        setAccountToModel(model, session);
        return "updateTask";
    }

    @PostMapping("/updateTask")
    public String updateTask(@ModelAttribute Item item, int id) {
        service.update(item, id);
        return "redirect:/tasks";
    }

    @GetMapping("/deleteTask/{id}")
    public String deleteTask(Model model, @PathVariable int id) {
        model.addAttribute("task", service.delete(id));
        return "redirect:/tasks";
    }

    @GetMapping("/updateState/{id}")
    public String updateState(Model model, @PathVariable int id) {
        model.addAttribute("task", service.updateState(id));
        return "redirect:/tasks";
    }
}
