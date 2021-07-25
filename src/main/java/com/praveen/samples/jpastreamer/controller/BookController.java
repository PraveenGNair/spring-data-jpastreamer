package com.praveen.samples.jpastreamer.controller;

import java.util.stream.Collectors;
import java.util.stream.LongStream;
import com.praveen.samples.jpastreamer.entities.Book;
import com.praveen.samples.jpastreamer.entities.Book$;
import com.speedment.jpastreamer.application.JPAStreamer;
import com.speedment.jpastreamer.projection.Projection;
import com.speedment.jpastreamer.streamconfiguration.StreamConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Book Controller having some sample endpoints to test JPAstreamer.
 * @author Praveen.Nair
 */
@RequestMapping("/api")
@RestController
public class BookController {

    private final JPAStreamer jpaStreamer;

    public BookController(JPAStreamer jpaStreamer) {
        this.jpaStreamer = jpaStreamer;
    }

    @GetMapping("/books")
    public ResponseEntity<?> findAllBooks() {
        return ResponseEntity.ok(
                jpaStreamer.stream(Book.class)
                .sorted(Book$.id.reversed())
                .collect(Collectors.toList()));
    }

    @GetMapping("/books/ids")
    public ResponseEntity<?> findBookIds() {
        final LongStream ids = jpaStreamer.stream(Book.class)
                .mapToLong(Book$.id.asLong());
        return ResponseEntity.ok(ids);
    }

    @GetMapping("/books/{title}")
    public ResponseEntity<?> findBookByTitle(@PathVariable String title) {
        return ResponseEntity.ok(
                jpaStreamer.stream(Book.class)
                .filter(Book$.title.contains(title))
                .collect(Collectors.toList()));
    }


    @GetMapping("/books/sc/{title}")
    public ResponseEntity<?> findBookByTitleUsingStreamConfig(@PathVariable String title) {
        StreamConfiguration<Book> sc = StreamConfiguration
                .of(Book.class)
                .selecting(Projection.select(Book$.id, Book$.title));
        return ResponseEntity.ok(
                jpaStreamer.stream(sc)
                .filter(Book$.id.greaterThan(2L))
                .collect(Collectors.toList()));
    }


    @GetMapping("/author/{authorLastName}")
    public ResponseEntity<?> findBookByAuthorLastName(@PathVariable String authorLastName) {
        return ResponseEntity.ok(jpaStreamer.stream(Book.class)
                .filter(k-> k.getAuthor().getLastName().contains(authorLastName))
                .collect(Collectors.toList()));
    }


}
