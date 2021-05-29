package com.woowahan.woowahanbatchservice;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.util.Objects;

@Immutable
@Entity
@Table(
        name = "user"
)
public class User {

    @Id
    @Column(name = "email_id")
    private String emailId;

    @Column(name = "hide_yn", columnDefinition = "varchar(1) default 'N'")
    private boolean hidden;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "ranking")
    private int rank;

    @Column(name = "score")
    private int score;

    public User() {
    }

    public User(
            String emailId,
            boolean hidden,
            String name,
            String password,
            int rank,
            int score
    ) {
        this.emailId = emailId;
        this.hidden = hidden;
        this.name = name;
        this.password = password;
        this.rank = rank;
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return emailId.equals(user.emailId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emailId);
    }

    public String getEmailId() {
        return emailId;
    }

    public boolean isHidden() {
        return hidden;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public int getRank() {
        return rank;
    }

    public int getScore() {
        return score;
    }
}