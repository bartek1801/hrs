package pl.com.bottega.hrs.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "titles")
public class Title {

    @EmbeddedId
    private TitleId id;

    @Column(name = "to_date")
    private LocalDate toDate;

    public LocalDate getToDate() {
        return toDate;
    }

    public TitleId getId() {
        return id;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }


    @Embeddable
    public static class TitleId implements Serializable{
    @Column(name = "emp_no")
    private Integer emoNo;

    private String title;

    @Column(name = "from_date")
    private LocalDate fromDate;

        public TitleId(Integer emoNo, String title, LocalDate fromDate) {
            this.emoNo = emoNo;
            this.title = title;
            this.fromDate = fromDate;
        }

        public String getTitle() {
            return title;
        }
    }

    public Title(){}

    public Title(TitleId id, LocalDate toDate) {
        this.id = id;
        this.toDate = toDate;
    }
}
