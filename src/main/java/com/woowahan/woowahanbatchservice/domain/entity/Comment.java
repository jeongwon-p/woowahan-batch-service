package com.woowahan.woowahanbatchservice.domain.entity;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Immutable
@Entity
@Table(
        name = "comment"
)
public class Comment {

    @Id
    @Column(name = "comment_id", length = 36)
    private String id;

    //relation
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "none"), name = "article_id", nullable = false, insertable = false, updatable = false)
    private Article article;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "none"), name = "user_id", nullable = false, insertable = false, updatable = false)
    private User user;

    //field
    @Column(name = "article_id", length = 36)
    private String articleId;

    @Column(name = "content", length = 2000)
    private String content;

    @Column(name = "crt_tm", length = 45)
    private LocalDateTime createDateTime;

    @Column(name = "hide_yn", columnDefinition = "varchar(1) default 'N'")
    private boolean hidden;

    @Column(name = "chg_tm", length = 45)
    private LocalDateTime modifyDateTime;

    @Column(name = "user_id", length = 200)
    private String userId;

    public Comment() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return id.equals(comment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getId() {
        return id;
    }

    public Article getArticle() {
        return article;
    }

    public String getArticleId() {
        return articleId;
    }

    public String getContent() {
        return content;
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

    public String getUserId() {
        return userId;
    }
}
