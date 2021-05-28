package qna.domain.wrap;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

import static java.lang.String.format;

@Embeddable
public class UserId {
    private static final int MAXIMUM_LENGTH = 20;

    @Column(length = MAXIMUM_LENGTH, nullable = false, unique = true)
    private String userId;

    protected UserId() {
    }

    public UserId(String userId) {
        validate(userId);

        this.userId = userId;
    }

    private void validate(String userId) {
        if (Objects.isNull(userId)) {
            throw new IllegalArgumentException("유저ID는 null일 수 없다.");
        }
        if (userId.length() > MAXIMUM_LENGTH) {
            throw new IllegalArgumentException(format("유저ID는 %d자를 넘길 수 없습니다.", MAXIMUM_LENGTH));
        }
    }

    public String toString() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserId userId1 = (UserId) o;
        return Objects.equals(userId, userId1.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}