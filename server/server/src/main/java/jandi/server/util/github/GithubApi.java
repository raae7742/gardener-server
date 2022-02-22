package jandi.server.util.github;

import org.kohsuke.github.*;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

public class GithubApi {
    GitHub github;

    @Value("${github.token}")
    String token;

    public PagedIterator<GHCommit> getCommits(String userId) {
        try { connectToGithub(token); }
        catch (IOException e) {
            throw new IllegalArgumentException("failed to connect gitHub");
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
