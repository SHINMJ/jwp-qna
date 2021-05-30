package qna.repository;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import qna.domain.Answer;
import qna.domain.QuestionTest;
import qna.domain.UserTest;

@DataJpaTest
public class AnswerRepositoryTest {
	private final AnswerRepository answers;
	private final QuestionRepository questions;
	private final UserRepository users;

	@Autowired
	public AnswerRepositoryTest(AnswerRepository answers, QuestionRepository questions, UserRepository users) {
		this.answers = answers;
		this.questions = questions;
		this.users = users;

		users.save(UserTest.JAVAJIGI);
		users.save(UserTest.SANJIGI);
		questions.save(QuestionTest.Q1.writeBy(UserTest.JAVAJIGI));
		questions.save(QuestionTest.Q2.writeBy(UserTest.SANJIGI));
	}

	@Test
	@DisplayName("저장")
	void save() {
		Answer expected = new Answer(UserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
		Answer actual = answers.save(expected);
		assertThat(actual.getId()).isNotNull();
	}

	@Test
	@DisplayName("삭제 대상 조회")
	void findByIdAndDeletedFalse() {
		Answer expected = new Answer(UserTest.SANJIGI, QuestionTest.Q2, "Answers Contents1");
		answers.save(expected);

		Answer actual = answers.findByIdAndDeletedFalse(expected.getId()).orElse(null);
		assertThat(actual).isEqualTo(expected);

		expected.setDeleted(true);
		actual = answers.findByIdAndDeletedFalse(expected.getId()).orElse(null);
		assertThat(actual).isNull();
	}
}