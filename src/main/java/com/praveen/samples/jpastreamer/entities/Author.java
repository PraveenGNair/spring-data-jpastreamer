package com.praveen.samples.jpastreamer.entities;

import javax.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Author entity.
 * @author Praveen.Nair
 */
@Data
@Entity(name = "author")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Author {

    @Id
    @GeneratedValue
    private Long id;

    private String firstName;

    @Column(unique = true)
    private String lastName;

    @OneToMany
    private List<Book> books;

}
