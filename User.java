import java.util.ArrayList;
import java.util.List;

public class User {
    private int userID;
    private String username;
    private String email;
    private String password;
    private List<User> followers;
    private List<User> following;
    private List<Post> posts;
    private List<Group> groups;
    private List<Event> events;

    public User(int userID, String username, String email, String password) {
        this.userID = userID;
        this.username = username;
        this.email = email;
        this.password = password;
        this.followers = new ArrayList<>();
        this.following = new ArrayList<>();
        this.posts = new ArrayList<>();
        this.groups = new ArrayList<>();
        this.events = new ArrayList<>();
    }

    public void createPost(String content) {
        Post post = new Post(posts.size() + 1, content, this);
        posts.add(post);
    }

    public void commentOnPost(Post post, String content) {
        Comment comment = new Comment(post.getComments().size() + 1, post, this, content);
        post.addComment(comment);
    }

    public void likePost(Post post) {
        if (!post.getLikes().contains(this)) {
            post.getLikes().add(this);
        }
    }

    public void followUser(User user) {
        if (!following.contains(user)) {
            following.add(user);
            user.getFollowers().add(this);
        }
    }

    public void unfollowUser(User user) {
        if (following.contains(user)) {
            following.remove(user);
            user.getFollowers().remove(this);
        }
    }

    public void joinGroup(Group group) {
        if (!groups.contains(group)) {
            groups.add(group);
            group.addMember(this);
        }
    }

    public void leaveGroup(Group group) {
        if (groups.contains(group)) {
            groups.remove(group);
            group.removeMember(this);
        }
    }

    // Getters and setters
    public List<User> getFollowers() {
        return followers;
    }

    public List<User> getFollowing() {
        return following;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public List<Group> getGroups() {
        return new ArrayList<>(groups); // Return a copy of the groups list
    }

    public List<Event> getEvents() {
        return events;
    }
    
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}