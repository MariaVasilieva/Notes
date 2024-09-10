package com.example.demo.note.model;

import com.example.demo.user.User;
import jakarta.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Note {
    @Id

    @Column(length = 100, nullable = false)
    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)

