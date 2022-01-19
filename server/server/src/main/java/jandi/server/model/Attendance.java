package jandi.server.model;

import jandi.server.util.BooleanToYNConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Attendance extends Timestamped {

    @Id
    @Column(name = "attendance_id", length = 20)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Column(name = "date", unique = true)
    private LocalDate date;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "is_checked", length = 1)
    private boolean is_checked;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "commit", length = 1)
    private boolean commit;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "til", length = 1)
    private boolean til;

    public Attendance(User user, LocalDate date) {
        this.date = date;
        this.is_checked = false;
        this.commit = false;
        this.til = false;
        this.user = user;
    }

    public void setCommitOn() {
        this.commit = true;
        this.is_checked = true;
    }

    public void setTilOn() {
        this.til = true;
        if (this.commit) this.is_checked = true;
    }
}
