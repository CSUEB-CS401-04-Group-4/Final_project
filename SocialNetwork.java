	import java.util.Scanner;
	import java.util.HashMap;
	import java.util.Map;
	import java.time.format.DateTimeFormatter;
	import java.time.LocalDateTime;
	import java.util.Date;
	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	
	public class SocialNetwork {

	    private static final Map<String, User> users = new HashMap<>();
	    private static final Map<Integer, Group> groups = new HashMap<>();
	    private static final Scanner scanner = new Scanner(System.in);
	    
	    public static void main(String[] args) {
	        seedData();

	        System.out.print("Enter your email to login: ");
	        String email = scanner.nextLine();
	        User loggedInUser = users.get(email);

	        if (loggedInUser == null) {
	            System.out.println("User not found. Exiting...");
	            return;
	        }

	        System.out.println("Logged in as: " + loggedInUser.getUsername());
	        
	        while (true) {
	        	System.out.println("\nChoose an option:");
	            System.out.println("1. Post a message");
	            System.out.println("2. View posts");
	            System.out.println("3. Comment on a post");
	            System.out.println("4. Like a post");
	            System.out.println("5. Create or join a group");
	            System.out.println("6. Post in a group");
	            System.out.println("7. View posts in a group");
	            System.out.println("8. Manage group events");
	            System.out.println("9. Enter Library Management System");
	            System.out.println("10. Exit");

	            System.out.print("Enter choice: ");
	            int choice = scanner.nextInt();
	            scanner.nextLine();
	            
	            
	            switch (choice) {
	                case 1:
	                    createPost(loggedInUser);
	                    break;
	                case 2:
	                    displayPosts();
	                    break;
	                case 3:
	                    commentOnPost(loggedInUser);
	                    break;
	                case 4:
	                    likePost(loggedInUser);
	                    break;
	                case 5:
	                    manageGroups(loggedInUser);
	                    break;
	                case 6:
	                    postInGroup(loggedInUser);
	                    break;
	                case 7:
	                    viewGroupPosts(loggedInUser);
	                    break;
	                case 8:
	                    manageGroupEvents(loggedInUser);
	                    break;
	                case 9:
	                    libraryManagementSystem();
	                    break;
	                case 10:
	                    System.out.println("Goodbye!");
	                    return;
	                default:
	                    System.out.println("Invalid choice. Please choose again.");
	            }
	        }

	    }
	
	    private static void createPost(User user) {
	        System.out.print("Enter your message: ");
	        String content = scanner.nextLine();
	        user.createPost(content);
	        System.out.println("Posted successfully!");
	    }
	
	    private static void displayPosts() {
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	        System.out.println("All Posts:");
	        users.values().forEach(user -> user.getPosts().forEach(post -> {
	            // Include the formatted date and time of the post creation
	            String formattedDate = post.getPostedDate().format(formatter);
	            System.out.println(post.getPostID() + ": " + post.getContent() + 
	                               " by " + post.getAuthor().getUsername() +
	                               " | Likes: " + post.getLikes().size() +
	                               " | Posted on: " + formattedDate);
	
	            // Display each comment under the post
	            post.getComments().forEach(comment -> {
	                System.out.println("  Comment by " + comment.getAuthor().getUsername() + 
	                                   ": " + comment.getContent());
	            });
	        }));
	    }
	
	
	
	    private static void commentOnPost(User user) {
	        displayPosts();
	        System.out.print("Enter the post ID to comment on: ");
	        int postID = scanner.nextInt();
	        scanner.nextLine(); // consume newline left-over
	        System.out.print("Enter your comment: ");
	        String content = scanner.nextLine();
	        Post post = findPostById(postID);
	        if (post != null) {
	            user.commentOnPost(post, content);
	            System.out.println("Comment added!");
	        } else {
	            System.out.println("Post not found.");
	        }
	    }
	
	    private static void likePost(User user) {
	        displayPosts();
	        System.out.print("Enter the post ID to like: ");
	        int postID = scanner.nextInt();
	        scanner.nextLine(); // consume newline left-over
	        Post post = findPostById(postID);
	        if (post != null) {
	            user.likePost(post);
	            System.out.println("Post liked!");
	        } else {
	            System.out.println("Post not found.");
	        }
	    }
	
	    private static Post findPostById(int postID) {
	        for (User user : users.values()) {
	            for (Post post : user.getPosts()) {
	                if (post.getPostID() == postID) {
	                    return post;
	                }
	            }
	        }
	        return null;
	    }
	    
	    
	    private static void manageGroups(User user) {
	        System.out.println("1. Create a new group");
	        System.out.println("2. Join an existing group");
	        int choice = scanner.nextInt();
	        scanner.nextLine();  // consume newline left-over

	        if (choice == 1) {
	            System.out.print("Enter group name: ");
	            String groupName = scanner.nextLine();
	            System.out.print("Enter group description: ");
	            String groupDesc = scanner.nextLine();
	            Group newGroup = new Group(groups.size() + 1, groupName, groupDesc);
	            groups.put(newGroup.getGroupID(), newGroup);
	            user.joinGroup(newGroup);  // Add group to user's list and handle membership
	            System.out.println("Group created and joined successfully!");
	        } else if (choice == 2) {
	            System.out.println("Available groups:");
	            groups.values().forEach(g -> System.out.println(g.getGroupID() + ": " + g.getName()));
	            System.out.print("Enter group ID to join: ");
	            int groupId = scanner.nextInt();
	            scanner.nextLine();  // consume newline left-over
	            Group group = groups.get(groupId);
	            if (group != null && !user.getGroups().contains(group)) {
	                user.joinGroup(group);  // Add group to user's list and handle membership
	                System.out.println("Joined group: " + group.getName());
	            } else {
	                System.out.println("Group not found or already a member.");
	            }
	        }
	    }


	    private static void postInGroup(User user) {
	        System.out.println("Your groups:");
	        if (user.getGroups().isEmpty()) {
	            System.out.println("You are not a member of any groups. Join or create one first.");
	            return;
	        }

	        user.getGroups().forEach(g -> System.out.println(g.getGroupID() + ": " + g.getName()));
	        System.out.print("Select a group ID to post in: ");
	        int groupId = scanner.nextInt();
	        scanner.nextLine();  // consume newline left-over
	        System.out.print("Enter your message: ");
	        String content = scanner.nextLine();

	        Group group = groups.get(groupId);
	        if (group != null && user.getGroups().contains(group)) {
	            group.createGroupPost(user, content);
	            System.out.println("Posted in group successfully!");
	        } else {
	            System.out.println("Group not found or you are not a member.");
	        }
	    }

	    
	    private static void viewGroupPosts(User user) {
	        System.out.println("Your groups:");
	        if (user.getGroups().isEmpty()) {
	            System.out.println("You are not a member of any groups. Join or create one first.");
	            return;
	        }

	        user.getGroups().forEach(g -> System.out.println(g.getGroupID() + ": " + g.getName()));
	        System.out.print("Select a group ID to view posts: ");
	        int groupId = scanner.nextInt();
	        scanner.nextLine();  // consume newline left-over

	        Group group = groups.get(groupId);
	        if (group != null && user.getGroups().contains(group)) {
	            if (group.getPosts().isEmpty()) {
	                System.out.println("No posts available in this group.");
	            } else {
	                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	                group.getPosts().forEach(post -> {
	                    String formattedDate = post.getPostedDate().format(formatter);
	                    System.out.println("Post ID " + post.getPostID() + ": " + post.getContent() +
	                                       " by " + post.getAuthor().getUsername() +
	                                       " | Likes: " + post.getLikes().size() +
	                                       " | Posted on: " + formattedDate);
	                });
	            }
	        } else {
	            System.out.println("Group not found or you are not a member.");
	        }
	    }
	    
	    
	    private static void manageGroupEvents(User user) {
	        System.out.println("Your groups:");
	        user.getGroups().forEach(g -> System.out.println(g.getGroupID() + ": " + g.getName()));

	        System.out.print("Select a group ID to manage events: ");
	        int groupId = scanner.nextInt();
	        scanner.nextLine();
	        Group group = groups.get(groupId);

	        if (group != null && user.getGroups().contains(group)) {
	            System.out.println("1. Create an event");
	            System.out.println("2. View events");
	            int eventChoice = scanner.nextInt();
	            scanner.nextLine();

	            if (eventChoice == 1) {
	                System.out.print("Enter event title: ");
	                String title = scanner.nextLine();
	                System.out.print("Enter event description: ");
	                String description = scanner.nextLine();
	                System.out.print("Enter event date (YYYY-MM-DD): ");
	                String date = scanner.nextLine();
	                System.out.print("Enter event time (HH:MM): ");
	                String time = scanner.nextLine();
	                System.out.print("Enter event location: ");
	                String location = scanner.nextLine();

	                LocalDateTime dateTime = LocalDateTime.parse(date + "T" + time);
	                group.createEvent(title, dateTime, location, description);
	                System.out.println("Event created successfully!");
	            } else if (eventChoice == 2) {
	                if (group.getEvents().isEmpty()) {
	                    System.out.println("No events in this group.");
	                } else {
	                    group.getEvents().forEach(e -> System.out.println("Event: " + e.getTitle() + 
	                        " at " + e.getLocation() + " on " + e.getEventDate()));
	                }
	            }
	        } else {
	            System.out.println("Group not found or you are not a member.");
	        }
	    }
	    
	    
	    private static void libraryManagementSystem() {
	    	DbConnection.connect();
	        Library library = new Library(); // Assuming library data should be persistent across calls, move this initialization to a broader scope.
	        
	        while (true) {
	            System.out.println("\nLibrary Management:");
	            System.out.println("1. Add Book");
	            System.out.println("2. Remove Book");
	            System.out.println("3. Register Patron");
	            System.out.println("4. Remove Patron");
	            System.out.println("5. Checkout Book");
	            System.out.println("6. Return Book");
	            System.out.println("7. Exit Library System");

	            System.out.print("Choose your action: ");
	            int option = scanner.nextInt();
	            scanner.nextLine(); // consume newline left-over

	            switch (option) {
	                case 1:
	                    addBook(library);
	                    break;
	                case 2:
	                    removeBook(library);
	                    break;
	                case 3:
	                    registerPatron(library);
	                    break;
	                case 4:
	                    removePatron(library);
	                    break;
	                case 5:
	                    checkoutBook(library);
	                    break;
	                case 6:
	                    returnBook(library);
	                    break;
	                case 7:
	                    return; // Exit library system
	                default:
	                    System.out.println("Invalid option. Please choose again.");
	            }
	        }
	    }

	    
	    
	    private static void addBook(Library library) {
	        System.out.print("Enter ISBN: ");
	        String ISBN = scanner.nextLine();
	        System.out.print("Enter Title: ");
	        String title = scanner.nextLine();
	        System.out.print("Enter Author: ");
	        String author = scanner.nextLine();
	        System.out.print("Enter Genre: ");
	        String genre = scanner.nextLine();
	        
	        insertBook(ISBN, title, author, genre);  // Insert into database

	        Book newBook = new Book(ISBN, title, author, genre);
	        library.addBook(newBook);
	        
	    }
	    
	    private static void insertBook(String ISBN, String title, String author, String genre) {
	        Connection con = DbConnection.connect();
	        PreparedStatement ps = null;
	        try {
	            String sql = "INSERT INTO Books(ISBN, title, author, genre) VALUES(?,?,?,?)";
	            ps = con.prepareStatement(sql);
	            ps.setString(1, ISBN);
	            ps.setString(2, title);
	            ps.setString(3, author);
	            ps.setString(4, genre);
	            ps.execute();
	            System.out.println("Book added successfully to the database.");
	        } catch (SQLException e) {
	            System.out.println(e.toString());
	        } finally {
	            try {
	                if (ps != null) ps.close();
	                con.close();
	            } catch (SQLException e) {
	                System.out.println(e.toString());
	            }
	        }
	    }
	    

	    private static void removeBook(Library library) {
	        System.out.print("Enter ISBN of the book to remove: ");
	        String ISBN = scanner.nextLine();
	        Book book = library.getBooks().stream()
	                            .filter(b -> b.getISBN().equals(ISBN))
	                            .findFirst()
	                            .orElse(null);

	        deleteBook(ISBN);
	        if (book != null) {
	            library.removeBook(book);
	            System.out.println("Book removed successfully.");
	        } else {
	            System.out.println("Book not found.");
	        }
	    }
	    
	    private static void deleteBook(String ISBN) {
	        Connection con = DbConnection.connect();
	        PreparedStatement ps = null;
	        try {
	            String sql = "DELETE FROM books WHERE ISBN = ?";
	            ps = con.prepareStatement(sql);
	            ps.setString(1, ISBN);
	            int affectedRows = ps.executeUpdate();
	            if (affectedRows > 0) {
	                System.out.println("Book has been deleted!");
	            } else {
	                System.out.println("No book found with the specified ISBN.");
	            }
	        } catch (SQLException e) {
	            System.out.println("Error deleting book: " + e.getMessage());
	        } finally {
	            try {
	                if (ps != null) ps.close();
	                con.close();
	            } catch (SQLException e) {
	                System.out.println("Error closing resources: " + e.getMessage());
	            }
	        }
	    }

	    
	    private static void registerPatron(Library library) {
	        System.out.print("Enter Patron ID: ");
	        int id = scanner.nextInt();
	        scanner.nextLine();  // consume newline left-over
	        System.out.print("Enter Patron Name: ");
	        String name = scanner.nextLine();
	        System.out.print("Enter Contact Info: ");
	        String contactInfo = scanner.nextLine();

	        Patron newPatron = new Patron(id, name, contactInfo);
	        library.registerPatron(newPatron);
	        insertPatron(id, name, contactInfo);
	    }

	    private static void insertPatron(int id, String name, String contactInfo) {
	        Connection con = DbConnection.connect();
	        PreparedStatement ps = null;
	        try {
	            String sql = "INSERT INTO Patron(id, name, contactInfo) VALUES(?,?,?)";
	            ps = con.prepareStatement(sql);
	            ps.setInt(1, id);
	            ps.setString(2, name);
	            ps.setString(3, contactInfo);
	            ps.execute();
	            System.out.println("Patron added successfully to the database.");
	        } catch (SQLException e) {
	            System.out.println(e.toString());
	        } finally {
	            try {
	                if (ps != null) ps.close();
	                con.close();
	            } catch (SQLException e) {
	                System.out.println(e.toString());
	            }
	        }
	    }


	    private static void removePatron(Library library) {
	        System.out.print("Enter Patron ID to remove: ");
	        int id = scanner.nextInt();
	        scanner.nextLine();  // consume newline left-over

	        Patron patron = library.getPatrons().stream()
	                               .filter(p -> p.getID() == id)
	                               .findFirst()
	                               .orElse(null);
	        
	        deletePatron(id);
	        if (patron != null) {
	            library.removePatron(patron);
	            System.out.println("Patron removed successfully.");
	        } else {
	            System.out.println("Patron not found.");
	        }
	    }
	    
	    private static void deletePatron(int id) {
	        Connection con = DbConnection.connect();
	        PreparedStatement ps = null;
	        try {
	            String sql = "DELETE FROM Patron WHERE id = ?";
	            ps = con.prepareStatement(sql);
	            ps.setInt(1, id);
	            int affectedRows = ps.executeUpdate();
	            if (affectedRows > 0) {
	                System.out.println("Patron has been deleted!");
	            } else {
	                System.out.println("No patron found with the specified ID.");
	            }
	        } catch (SQLException e) {
	            System.out.println("Error deleting patron: " + e.getMessage());
	        } finally {
	            try {
	                if (ps != null) ps.close();
	                con.close();
	            } catch (SQLException e) {
	                System.out.println("Error closing resources: " + e.getMessage());
	            }
	        }
	    }
	    

	    private static void checkoutBook(Library library) {
	        System.out.print("Enter Book ISBN: ");
	        String ISBN = scanner.nextLine();
	        System.out.print("Enter Patron ID: ");
	        int patronId = scanner.nextInt();
	        scanner.nextLine();  // consume newline left-over

	        Book book = library.getBooks().stream()
	                           .filter(b -> b.getISBN().equals(ISBN) && b.isAvailable())
	                           .findFirst()
	                           .orElse(null);
	        Patron patron = library.getPatrons().stream()
	                               .filter(p -> p.getID() == patronId)
	                               .findFirst()
	                               .orElse(null);

	        if (book == null) {
	            System.out.println("Book not found or not available.");
	            return;
	        }

	        if (patron == null) {
	            System.out.println("Patron not found.");
	            return;
	        }

	        library.checkoutBook(book, patron, new Date());
	    }

	    private static void returnBook(Library library) {
	        System.out.print("Enter Book ISBN to return: ");
	        String ISBN = scanner.nextLine();

	        Book book = library.getBooks().stream()
	                           .filter(b -> b.getISBN().equals(ISBN))
	                           .findFirst()
	                           .orElse(null);

	        if (book != null && !book.isAvailable()) {
	            library.returnBook(book, new Date());
	        } else {
	            System.out.println("Book not found or already returned.");
	        }
	    }




	    
	
	    private static void seedData() {
	        // Creating users
	        User jane = new User(1, "JaneDoe", "jane@example.com", "password123");
	        User john = new User(2, "JohnDoe", "john@example.com", "password123");
	        User alice = new User(3, "AliceWonder", "alice@example.com", "password456");
	        User bob = new User(4, "BobBuilder", "bob@example.com", "password789");

	        users.put(jane.getEmail(), jane);
	        users.put(john.getEmail(), john);
	        users.put(alice.getEmail(), alice);
	        users.put(bob.getEmail(), bob);

	        // Creating groups
	        Group bookClub = new Group(groups.size() + 1, "Book Club", "A group for book lovers");
	        Group HarryPotter = new Group(groups.size() + 1, "Harry Potter Club", "A group for Harry Potter enthusiasts");

	        groups.put(bookClub.getGroupID(), bookClub);
	        groups.put(HarryPotter.getGroupID(), HarryPotter);

	        // Adding users to groups
	        bookClub.addMember(jane);
	        bookClub.addMember(john);
	        HarryPotter.addMember(alice);
	        HarryPotter.addMember(bob);

	        // Creating posts by Jane and John
	        jane.createPost("Hello, world! This is my first post.");
	        john.createPost("It's a great day to read something new!");

	        // Alice comments on Jane's first post
	        Post janePost = jane.getPosts().get(0);
	        alice.commentOnPost(janePost, "Nice post, Jane!");

	        // Bob likes John's post
	        Post johnPost = john.getPosts().get(0);
	        bob.likePost(johnPost);


	       

	        System.out.println("Data seeded successfully.");
	    }

	}
