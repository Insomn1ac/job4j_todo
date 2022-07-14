package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.todo.model.Account;
import ru.job4j.todo.service.AccountService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class AccountController {
    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @GetMapping("/success")
    public String success(Model model, HttpSession session) {
        Account account = (Account) session.getAttribute("account");
        model.addAttribute("account", account);
        return "success";
    }

    @GetMapping("/formAddAccount")
    public String addAccount(Model model, HttpSession session, @RequestParam(name = "fail", required = false) Boolean fail) {
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            account = new Account();
            account.setName("Гость");
        }
        model.addAttribute("account", account);
        model.addAttribute("fail", fail != null);
        return "addAccount";
    }

    @PostMapping("/createAccount")
    public String createAccount(@ModelAttribute Account account, HttpSession session) {
        Optional<Account> regAccount = service.add(account);
        if (regAccount.isEmpty()) {
            return "redirect:/formAddAccount?fail=true";
        }
        session.setAttribute("account", regAccount.get());
        return "redirect:/success";
    }

    @GetMapping("/loginPage")
    public String loginPage(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute Account account, HttpServletRequest req) {
        Optional<Account> accountDb = service.findAccountByLoginAndPassword(
                account.getLogin(), account.getPassword()
        );
        if (accountDb.isEmpty()) {
            return "redirect:/loginPage?fail=true";
        }
        HttpSession session = req.getSession();
        session.setAttribute("account", accountDb.get());
        return "redirect:/tasks";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/loginPage";
    }
}
