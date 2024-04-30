import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//public class Group {
//    private int groupID;
//    private String name;
//    private String description;
//    private List<User> members;
//    private List<Event> events;
//
//    public Group(int groupID, String name, String description) {
//        this.groupID = groupID;
//        this.name = name;
//        this.description = description;
//        this.members = new ArrayList<>();
//        this.events = new ArrayList<>();
//    }
//
//    public void addMember(User user) {
//        if (!members.contains(user)) {
//            members.add(user);
//        }
//    }
//
//    public void removeMember(User user) {
//        members.remove(user);
//    }
//
//    public List<Event> getEvents() {
//        return events;
//    }
//
//    public Event createEvent(String title, String desc, LocalDateTime date, String location) {
//        Event event = new Event(events.size() + 1, title, desc, date, location);
//        events.add(event);
//        return event;
//    }
//}


public class Group {
    private int groupID;
    private String name;
    private String description;
    private List<User> members;
    private List<Event> events;
    private List<Post> posts;  // List to hold posts specific to the group.

    public Group(int groupID, String name, String description) {
        this.groupID = groupID;
        this.name = name;
        this.description = description;
        this.members = new ArrayList<>();
        this.posts = new ArrayList<>();
        this.events = new ArrayList<>();
    }

    public int getGroupID() {
        return groupID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<User> getMembers() {
        return members;
    }

    public void addMember(User user) {
        if (!members.contains(user)) {
            members.add(user);
        }
    }
    
    public List<Post> getPosts() {
        return posts;  // Ensure this returns the list of posts in the group
    }

    // Method to handle posting within the group
    public void createGroupPost(User user, String content) {
        Post newPost = new Post(posts.size() + 1, content, user);
        posts.add(newPost);
    }
    
    public void removeMember(User user) {
        members.remove(user);
    }

    public List<Event> getEvents() {
        return events;
    }

    public void createEvent(String title, LocalDateTime dateTime, String location, String description) {
        Event newEvent = new Event(events.size() + 1, title, description, dateTime, location);
        events.add(newEvent);
    }
    
}
