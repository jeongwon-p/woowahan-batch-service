package com.woowahan.woowahanbatchservice.domain.entity;

import com.woowahan.woowahanbatchservice.common.BooleanToYnConverter;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(
        name = "user"
)
public class User {

    @Id
    @Column(name = "email_id")
    private String emailId;

    @Column(name = "hide_yn", columnDefinition = "varchar(1) default 'N'")
    @Convert(converter = BooleanToYnConverter.class)
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

    private User(
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

    public User updateScore(long score){
        return new User(
                this.emailId,
                this.hidden,
                this.name,
                this.password,
                this.rank,
                Math.toIntExact(score)
        );
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