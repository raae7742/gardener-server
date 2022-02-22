package jandi.server.util;

import jandi.server.util.CommitUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CommitUtilTest {

    CommitUtil commitUtil = new CommitUtil();

    @Test
    void connection() {
        try {
            commitUtil.getCommits("raae7742");
        } catch (Exception e) {
            Assertions.fail("connection Error");
        }
    }
}
