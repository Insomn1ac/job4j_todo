package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Account;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.service.ItemService;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class ItemController {
    private final ItemService service;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public ItemController(ItemService service) {
        this.service = service;
    }

    @GetMapping("/tasks")
    public String index(Model model, HttpSession session) {
        model.addAttribute("tasks", service.findAll());
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            account = new Account();
            account.setName("Гость");
        }
        model.addAttribute("account", account);
        return "tasks";
    }

    @GetMapping("/completedTasks")
    public String completedTasks(Model model, HttpSession session) {
        model.addAttribute("completed", service.findCompletedTasks());
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            account = new Account();
            account.setName("Гость");
        }
        model.addAttribute("account", account);
        return "completedTasks";
    }

    @GetMapping("/newTasks")
    public String newTasks(Model model, HttpSession session) {
        model.addAttribute("newTasks", service.findNewTasks());
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            account = new Account();
            account.setName("Гость");
        }
        model.addAttribute("account", account);
        return "newTasks";
    }

    @GetMapping("/addTask")
    public String addTask(Model model, HttpSession session) {
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            account = new Account();
            account.setName("Гость");
        }
        model.addAttribute("account", account);
        return "addTask";
    }

    @PostMapping("/createTask")
    public String createTask(@ModelAttribute Item item) {
        item.setCreated(LocalDateTime.now().format(formatter));
        service.add(item);
        return "redirect:/tasks";
    }

    @GetMapping("/task/{taskId}")
    public String taskInfo(Model model, @PathVariable("taskId") int id, HttpSession session) {
        model.addAttribute("task", service.findById(id));
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            account = new Account();
            account.setName("Гость");
        }
        model.addAttribute("account", account);
        return "task";
    }

    @GetMapping("/formUpdateTask/{taskId}")
    public String formUpdateTask(Model model, @PathVariable("taskId") int id, HttpSession session) {
        model.addAttribute("task", service.findById(id));
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            account = new Account();
            account.setName("Гость");
        }
        model.addAttribute("account", account);
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
