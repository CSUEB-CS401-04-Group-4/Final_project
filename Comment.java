import java.time.LocalDateTime; 

public class Comment {
    private int commentID;
    private Post post;
    private User author;
    private String content;
    private LocalDateTime commentedDate;  // Use LocalDateTime instead of custom DateTime

    public Comment(int commentID, Post post, User author, String content) {
        this.commentID = commentID;
        this.post = post;
        this.author = author;
        this.content = content;
        this.commentedDate = LocalDateTime.now(); // Set to current date and time
    }

    // Getters and Setters
    public LocalDateTime getCommentedDate() {
        return commentedDate;
    }

    public void setCommentedDate(LocalDateTime commentedDate) {
        this.commentedDate = commentedDate;
    }
    
    public String getContent() {
        return content;
    }
    
    public User getAuthor() {
        return author;
    }

}

