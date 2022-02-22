package jandi.server.util;

import org.kohsuke.github.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CommitUtil {

    private GitHub github;

    private static String token;

    @Value("${github.token}")
    private void setToken(String token) {
        CommitUtil.token = token;
    }

    public PagedIterator<GHCommit> getCommits(String userId) {
        if (github == null) {
            try { connectToGithub(token); }
            catch (IOException e) {
                throw new IllegalArgumentException("failed to connect gitHub");
            }
        }

        GHCommitSearchBuilder builder = github.searchCommits()
                .author(userId)
                .sort(GHCommitSearchBuilder.Sort.AUTHOR_DATE);
        PagedSearchIterable<GHCommit> commits = builder.list().withPageSize(7);
        return commits._iterator(1);
    }

    private void connectToGithub(String token) throws IOException {
        github = new GitHubBuilder().withOAuthToken(token).build();
        github.checkApiUrlValidity();
    }
}
