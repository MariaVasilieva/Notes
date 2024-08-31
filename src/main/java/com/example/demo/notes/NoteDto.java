package com.example.demo.notes;

import com.example.demo.notes.model.AccessType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoteDto {
    private long id;
    private String title;
    private String content;
    private AccessType accessType;
}
