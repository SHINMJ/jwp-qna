package subway.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class LineTest {

    @Autowired
    private LineRepository lines;

    @Autowired StationRepository stations;

    @Test
    public void saveWithLine(){
        final Station station = new Station("잠실역");
        /**
         * 연관된 모든 엔티티들은 기본적으로 영속상태에 있어야 함.
         */
        station.setLine(lines.save(new Line("2호선")));
        Station save = stations.save(station);
        /**
         * 일종의 commit. 쿼리를 보기 위함.
         */
        stations.flush();
        assertThat(save).isSameAs(station);

    }

    @Test
    public void updateWithLine() {
        Station stationsByName = stations.findByName("교대역");
        stationsByName.setLine(lines.save(new Line("2호선")));
        /**
         * 라인을 제거하고 싶은 경우.
         * line 테이블에서 3호선을 지우는 것은 아니고 foreign key가 null 이 되는 것.
         */
//        stationsByName.setLine(null);
        stations.flush();
    }

    @Test
    public void findByName() {
        final Line line = lines.findByName("3호선");
        assertThat(line.getStations()).hasSize(1);
    }

    @Test
    public void save() {
        final Line line = new Line("2호선");
        /**
         * 연관관계 주인인 객체에 알려주어야 함.
         */
        line.addStations(stations.save(new Station("잠실역")));
        lines.save(line);
        lines.flush();
    }

}
