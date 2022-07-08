package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.service.ItemService;

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
    public String index(Model model) {
        model.addAttribute("tasks", service.findAll());
        return "tasks";
    }

    @GetMapping("/completedTasks")
    public String completedTasks(Model model) {
        model.addAttribute("completed", service.findCompletedTasks());
        return "completedTasks";
    }

    @GetMapping("/newTasks")
    public String newTasks(Model model) {
        model.addAttribute("newTasks", service.findNewTasks());
        return "newTasks";
    }

    @GetMapping("/addTask")
    public String addTask() {
        return "addTask";
    }

    @PostMapping("/createTask")
    public String createTask(@ModelAttribute Item item) {
        item.setCreated(LocalDateTime.now().format(formatter));
        service.add(item);
        return "redirect:/tasks";
    }

    @GetMapping("/task/{taskId}")
    public String taskInfo(Model model, @PathVariable("taskId") int id) {
        model.addAttribute("task", service.findById(id));
        return "task";
    }

    @GetMapping("/formUpdateTask/{taskId}")
    public String formUpdateTask(Model model, @PathVariable("taskId") int id) {
        model.addAttribute("task", service.findById(id));
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
