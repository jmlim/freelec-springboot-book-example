package io.jmlim.freelec.springboot.domain.posts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//@Slf4j
@SpringBootTest
class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @AfterEach
    void cleanUp() {
        postsRepository.deleteAll();
    }

    @Test
    @DisplayName("게시글 저장 불러오기")
    public void load() {
        //given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        //when
        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("hackerljm@gmail.com")
                .build());

        List<Posts> all = postsRepository.findAll();

        //then
        Posts posts = all.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    @DisplayName("Base time entity 등록")
    public void addBaseTimeEntity() {
        // given
        LocalDateTime now = LocalDateTime.of(2019, 12, 26, 0, 0, 0);
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);
        //log.info(">>>>>>>>>>>>>>>>>>> createDate={}, modifiedDate={}", posts.getCreatedDate(), posts.getModifiedDate());

        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }
}