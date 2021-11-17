package subway.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class MemberTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    FavoriteRepository favoriteRepository;

    @Test
    public void save() {
        final Member member = new Member("shinmj");
        member.addFavorite(favoriteRepository.save(new Favorite()));

        Member save = memberRepository.save(member);
        memberRepository.flush();
    }

}
