package hiber.model;

import javax.persistence.*;

@Entity
@Table(name = "car")
public class Car {

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private User user;

    public Car() {

    }

    public Car(String name, int series, Long userID) {
        this.id = id;
        this.name = name;
        this.series = series;
        this.userID = userID;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "series")
    private int series;

    @Column(name = "user_id")
    private Long userID;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }
}