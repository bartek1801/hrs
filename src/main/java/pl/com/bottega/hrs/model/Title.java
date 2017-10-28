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

    @Transient
    private TimeProvider timeProvider;

    public Title() {
    }

    public Title(Integer empNo, String title, TimeProvider timeProvider) {
        this.id = new TitleId(empNo, title, timeProvider);
        this.toDate = Constants.MAX_DATE;
        this.timeProvider = timeProvider;
    }

    public boolean isCurrent() {
        return toDate.isAfter(timeProvider.today());
    }

    public String getTitleName() {
        return id.title;
    }

    public boolean startsToday() {
        return toDate.isEqual(timeProvider.today());
    }

    public void change(String newTitle) {
        id.change(newTitle);
    }

    public void terminate() {
        toDate = timeProvider.today();
    }

    public LocalDate getFromDate() {
        return id.fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    @Embeddable
    public static class TitleId implements Serializable {
        @Column(name = "emp_no")
        private Integer emoNo;

        private String title;

        @Column(name = "from_date")
        private LocalDate fromDate;

        public TitleId() {
        }

        public TitleId(Integer emoNo, String title, TimeProvider timeProvider) {
            this.emoNo = emoNo;
            this.title = title;
            this.fromDate = timeProvider.today();
        }

        public void change(String newTitle) {
            this.title = newTitle;
        }
    }


}
