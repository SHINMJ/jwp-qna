package subway.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Line {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    /**
     * 연관관계의 주인이 누구인지 알려준다. mappedBy가 없는 쪽인 연관관계 주인.
     * Station 객체의 line이라는 변수에 연관관계가 정의 되어 있다라고 알려주는 것!!
     * 양방향에서 mappedBy 속성이 없는 경우 foreign key를 누가 관리하는지 알 수 없어 매핑테이블이 생성된다.
     */
    @OneToMany(mappedBy = "line")
    private List<Station> stations = new ArrayList<>();

    protected Line(){
    }

    public Line(String name) {
        this.name = name;
    }

    public List<Station> getStations() {
        return this.stations;
    }

    /**
     * 연관관계 편의 메서드
     * 무한루프에 빠지지 않도록 적절한 조치가 필요하다.
     *
     * @param station
     */
    public void addStations(Station station) {
        this.stations.add(station);
        /**
         * 연관관계 주인인 객체에 알려주어야 함.
         */
        station.setLine(this);
    }

}
