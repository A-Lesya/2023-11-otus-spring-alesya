package ru.otus.hw.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BookPagesController {
    @GetMapping("/")
    public String searchBookPage() {
        return "search_book";
    }

    @GetMapping("/add-book")
    public String addBookPage() {
        return "add_or_edit_book";
    }

    @GetMapping("/edit-book/{id}")
    public String editBookPage(@PathVariable("id") long id) {
        return "add_or_edit_book";
    }

    @GetMapping("/view-book/{id}")
    public String viewBookPage(@PathVariable("id") long id) {
        return "view_book";
    }
}
