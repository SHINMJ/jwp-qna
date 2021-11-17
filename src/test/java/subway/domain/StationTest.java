package subway.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * jpa의 모든 기능은 트랜잭션 안에서 사용해야 함.
 */
@DataJpaTest
public class StationTest {

    @Autowired
    private StationRepository stations;

    @Test
    public void name() {
        Station station = new Station("잠실역");
        Station save = stations.save(station);

        assertThat(save.getId()).isNotNull();
        assertThat(save.getName()).isEqualTo(station.getName());
    }

    @Test
    public void findByName() {

        final Station save = stations.save(new Station("잠실역"));
        final Station station = stations.findByName("잠실역");

        
        assertThat(save).isSameAs(station);

    }

    @Test
    public void findById() {
        /**
         * 영속성 컨텍스트(쓰기 지연 저장소와 1차 캐시)에 저장됨
         * but. 식별자 생성전략에 따라 다름. -> auto increment와 같은 자동생성인 경우 
         * DB 에서 읽어와야 하므로 바로 DB에 저장함.
         * 
         * id를 지정하는 경우 create가 아니라 merge 로 동작하기 때문에
         * select 쿼리를 먼저 해서 확인함.
         */
        final Station save = stations.save(new Station("잠실역"));
        /**
         * 1차 캐시에 저장된 객체를 가져옴. DB에 쿼리하지 않음.
         */
        final Station station = stations.findById(save.getId()).get();
        assertThat(save).isSameAs(station);

    }
    
    @Test
    public void update() {
        final Station save = stations.save(new Station("잠실역"));

        /**
         * 영속성 컨텍스트에 저장.
         * commit 시 db 에 반영/
         */
        save.changeName("몽촌토성역");

        /**
         * DB에 접근하는 쿼리임.(1차 캐시는 id 기반)
         * 이전에 진행되었던 영속성 컨텍스트에 있던 것들을 DB에 먼저 반영하고
         * 해당 쿼리 동작
         */
        final Station station = stations.findByName("몽촌토성역");

        assertThat(station).isNotNull();

    }

}
