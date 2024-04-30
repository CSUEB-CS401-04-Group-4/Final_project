import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Event {
    private int eventID;
    private String title;
    private String description;
    private LocalDateTime eventDate;  // Change DateTime to LocalDateTime
    private String location;
    private List<User> participants;

    public Event(int eventID, String title, String description, LocalDateTime eventDate, String location) {
        this.eventID = eventID;
        this.title = title;
        this.description = description;
        this.eventDate = eventDate;
        this.location = location;
        this.participants = new ArrayList<>();
    }

    public void addParticipant(User user) {
        if (!participants.contains(user)) {
            participants.add(user);
        }
    }

    public void removeParticipant(User user) {
        participants.remove(user);
    }
    
    public String getTitle() {
        return title;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }
    
    public String getLocation() {
        return location;
    }
    
}
