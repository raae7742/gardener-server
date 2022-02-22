package jandi.server.util.github;

import org.kohsuke.github.*;

import java.io.IOException;

public class GithubApi {
    GitHub github;
    String token = "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";

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
