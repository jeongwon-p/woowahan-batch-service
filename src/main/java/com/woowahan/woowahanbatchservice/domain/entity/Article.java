package com.woowahan.woowahanbatchservice.domain.entity;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Immutable
@Entity
@Table(
        name = "article"
)
public class Article {

    @Id
    @Column(name = "article_id", length = 36)
    private String id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "none"), name = "user_id", nullable = false, insertable = false, updatable = false)
    private User user;

    // field
    @Column(name = "board_id", length = 36)
    private String boardId;

    @Lob
    @Column(name = "content")
    private String contents;

    @Column(name = "crt_tm", length = 45)
    private LocalDateTime createDateTime;

    @Column(name = "hide_yn", columnDefinition = "varchar(1) default 'N'")
    private boolean hidden;

    @Column(name = "chg_tm", length = 45)
    private LocalDateTime modifyDateTime;

    @Column(name = "title", length = 200)
    private String title;

    @Column(name = "user_id", length = 200)
    private String userId;

    public Article() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getBoardId() {
        return boardId;
    }

    public String getContents() {
        return contents;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public boolean isHidden() {
        return hidden;
    }

    public LocalDateTime getModifyDateTime() {
        return modifyDateTime;
    }

    public String getTitle() {
        return title;
    }

    public String getUserId() {
        return userId;
    }
}
