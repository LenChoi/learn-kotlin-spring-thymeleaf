package com.example.blog

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/login")
class LoginController(private val repository: UserRepository) {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @GetMapping
    fun form(model: Model): String {
        logger.info("form()...")
        model.addAttribute("user", User())
        return "login"
    }

    @PostMapping
    fun login(user: User, model: Model): String {
        logger.info("login($user)")

        val optional = repository.findByEmail(user.email)
        if (optional.isEmpty) {
            val messageError = "Empty user"
            logger.error(messageError)
            model.addAttribute("messageError", messageError)
            return "login"
        }
        val userDatabase = optional.get()
        if (user.password != userDatabase.password) {
            val messageError = "password invalid"
            logger.error(messageError)
            model.addAttribute("messageError", messageError)
            return "login"
        }

        logger.info("Login success")
        return "redirect:/"
    }
}