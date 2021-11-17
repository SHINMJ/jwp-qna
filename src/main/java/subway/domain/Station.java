package subway.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "line_id")  //foreign key
    private Line line;

    /**
     * jpa spec에서 권장 지연 로딩과 관련이 있어요. -> 필요시 객체를 가져오는 방식. 지연 로딩할 때 프록시객체를 만들어서 관리를 하는데 그때 필요한게 빈.
     * 빈생성시 빈 생성자가 필요함. 다른곳에서 빈 생성자를 사용하지 않도록 하기 위해 protected 로 설정함. 생성자를 통해서 완전한 객체를 만들도록 하는게 좋음.
     */
    protected Station() {
    }

    public Station(String name) {
        this.name = name;
    }

    public void changeName(String name) {
        this.name = name;
    }

    /**
     * 연관관계 설정
     * 메모리상의 불일치를 맞춰주기 위해.
     *
     * @param line
     */
    public void setLine(Line line) {
        this.line = line;
        line.getStations().add(this);
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

}
