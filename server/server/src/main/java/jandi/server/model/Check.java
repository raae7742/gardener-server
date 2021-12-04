package jandi.server.model;

import jandi.server.util.RandomGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Check extends Timestamped {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = RandomGenerator.generatorName)
    @GenericGenerator(name = RandomGenerator.generatorName, strategy = "jandi.server.util.RandomGenerator")
    private String id;

    @OneToOne
    @Column(name = "participant_id")
    private String participant_id;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "is_checked")
    private boolean is_checked;

    @Column(name = "commit")
    private boolean commit;

    @Column(name = "til")
    private boolean til;

    public void setIs_checkedOn() {
        this.is_checked = true;
    }

    public void setCommitOn() {
        this.commit = true;
    }

    public void setTilOn() {
        this.til = true;
    }
}
