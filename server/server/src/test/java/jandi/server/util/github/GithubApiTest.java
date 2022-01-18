package jandi.server.util.github;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kohsuke.github.GHCommit;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Iterator;

@SpringBootTest
public class GithubApiTest {

    GithubApi githubApi = new GithubApi();

    @Test
    void connection() {
        String token = "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";
        try {
            githubApi.getCommits(token, "raae7742");
        } catch (Exception e) {
            Assertions.fail("connection Error");
        }
    }
}
