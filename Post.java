import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class Post {
    private int postID;
    private String content;
    private LocalDateTime postedDate;
    private User author;
    private List<Comment> comments;
    private List<User> likes;
    private List<Share> shares;

    public Post(int postID, String content, User author) {
        this.postID = postID;
        this.content = content;
        this.postedDate = LocalDateTime.now();
        this.author = author;
        this.comments = new ArrayList<>();
        this.likes = new ArrayList<>();
        this.shares = new ArrayList<>();
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public List<Comment> getComments() {
        return comments;
    }

    public String getContent() {
        return content;
    }

    public List<User> getLikes() {
        return likes;
    }
    
    public int getPostID() {
        return postID;
    }

    public User getAuthor() {
        return author;
    }
    
    public LocalDateTime getPostedDate() {
        return postedDate;
    }
}