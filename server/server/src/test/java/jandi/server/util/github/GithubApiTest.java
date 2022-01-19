package jandi.server.util.github;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GithubApiTest {

    GithubApi githubApi = new GithubApi();

    @Test
    void connection() {
        try {
            githubApi.getCommits("raae7742");
        } catch (Exception e) {
            Assertions.fail("connection Error");
        }
    }
}
