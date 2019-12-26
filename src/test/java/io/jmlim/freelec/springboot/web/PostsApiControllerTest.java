package io.jmlim.freelec.springboot.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jmlim.freelec.springboot.domain.posts.Posts;
import io.jmlim.freelec.springboot.domain.posts.PostsRepository;
import io.jmlim.freelec.springboot.web.dto.PostsSaveRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @AfterEach
    public void tearDown() {
        postsRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER") // 인증된 모의 사용자를 만듬, role 에 권한 추가 가능
    @DisplayName("Posts 등록")
    void save() throws Exception {
        //given
        String title = "타이틀";
        String content = "내용요요요용";

        PostsSaveRequestDto build = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("hackerljm@gmail.com")
                .build();
        String url = "http://localhost:" + port + "/api/v1/posts";


        //when
        //ResponseEntity<Long> postResponseEntity = restTemplate.postForEntity(url, build, Long.class);
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(build)))
                .andExpect(status().isOk());

        //then
        //assertThat(postResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        //assertThat(postResponseEntity.getBody()).isGreaterThan(0L);//.isEqualTo(1L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @WithMockUser(roles = "USER") // 인증된 모의 사용자를 만듬, role 에 권한 추가 가능
    @DisplayName("Posts 수정")
    void update() throws Exception {
        //given
        Posts savedPost = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author").build());

        Long updateId = savedPost.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent).build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;

        //HttpEntity<PostsSaveRequestDto> requestEntity = new HttpEntity<>(requestDto);
        //when
        //ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());


        //then
        //assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        // assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();

        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
    }
}