package com.amar.blog.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Where(clause = "obsolete = 0")
public class Post implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="title", nullable = false)
    private String title;

    @Column(name="author", nullable = false)
    private String author;

    @Column(name="content", nullable = false)
    private String content;

    @Column(name="obsolete")
    private Integer obsolete;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Comment> comments;

    //wrongly I added fetch = FetchType.EAGER, above, and faced issue that when i was calling delete method on comment, it was not deleting the comment.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
    }

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "image")
    private String image; // #ALTER TABLE blog.post MODIFY image LONGBLOB;

    @Column(name = "code_snippet")
    private String codeSnippet;


}
