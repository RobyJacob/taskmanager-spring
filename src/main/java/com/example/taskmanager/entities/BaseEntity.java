package com.example.taskmanager.entities;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;

@MappedSuperclass
@Getter
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Integer id;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDate createdAt = LocalDate.now();

//    {
//        try {
//            createdAt = new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(LocalDate.now()));
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
