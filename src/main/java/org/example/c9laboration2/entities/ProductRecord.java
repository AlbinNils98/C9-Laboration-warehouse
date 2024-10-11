package org.example.c9laboration2.entities;

import java.time.LocalDate;

public record ProductRecord(
    String id,
    String name,
    Category category,
    int rating,
    LocalDate creationDate,
    LocalDate lastModified) {
}

