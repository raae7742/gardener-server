package jandi.server.model.attendance;

import jandi.server.model.Timestamped;
import jandi.server.model.member.Member;
import jandi.server.util.BooleanToYNConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Attendance extends Timestamped implements Comparable<Attendance>{

    @Id
    @Column(name = "attendance_id", length = 20)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

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

    public Attendance(Member member, LocalDate date) {
        this.date = date;
        this.member = member;
        this.is_checked = false;
        this.commit = false;
        this.til = false;
    }

    public void setCommitOn() {
        this.commit = true;
        this.is_checked = true;
    }

    public void setTilOn() {
        this.til = true;
        if (this.commit) this.is_checked = true;
    }

    @Override
    public int compareTo(Attendance o) {
        return this.date.compareTo(o.date);
    }
}
