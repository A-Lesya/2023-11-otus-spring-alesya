package ru.otus.hw.rest.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.rest.dto.AuthorDto;
import ru.otus.hw.services.AuthorService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping("/api/author")
    public List<AuthorDto> getAllAuthors() {
        return authorService.findAll()
                .stream()
                .map(AuthorDto::new)
                .toList();
    }
}
