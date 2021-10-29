package com.ehizman.know.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @NotNull
    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    private String duration;

    private String language;

    @ElementCollection
    @Column(name = "image_urls")
    private List<String> imageUrls;

    @CreationTimestamp
    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @Column(name = "date_published")
    private LocalDateTime datePublished;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private boolean isPublished;

    @ManyToMany
    private List<Student> students;

    @ManyToOne
    private Instructor instructor;

    public Course(String title) {
        this.title = title;
    }
}
