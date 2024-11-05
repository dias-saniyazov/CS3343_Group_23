import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.FileReader;
import java.io.FileWriter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;


class DBController {
    private final String MEMBERS = "data/members.json";
    private final String MENU = "data/menu.json";
    private final String ORDERS = "data/orders.json";
    private List<Member> members;
    private List<MenuItem> orders;
    private List<MenuItem> menu;

    public DBController() {
        members = loadMembers();
        orders = loadOrders();
        menu = loadMenu();
    }

    private List<Member> loadMembers() {
        try (FileReader reader = new FileReader(MEMBERS)) {
            Type type = new TypeToken<ArrayList<Member>>() {}.getType();
            return new Gson().fromJson(reader, type);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
    
    private List<MenuItem> loadOrders() {
        try (FileReader reader = new FileReader(ORDERS)) {
            Type type = new TypeToken<ArrayList<MenuItem>>() {}.getType();
            return new Gson().fromJson(reader, type);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private List<MenuItem> loadMenu() {
        try (FileReader reader = new FileReader(MENU)) {
            Type type = new TypeToken<ArrayList<MenuItem>>() {}.getType();
            return new Gson().fromJson(reader, type);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    boolean checkIfMemberExist(String username) {
        return members.stream().anyMatch(m -> m.getUsername().equals(username));
    }

    private void saveMembers() {
        try (FileWriter writer = new FileWriter(MEMBERS)) {
            new Gson().toJson(members, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveMenu() {
        try (FileWriter writer = new FileWriter(MENU)) {
            new Gson().toJson(menu, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveOrders() {
        try (FileWriter writer = new FileWriter(ORDERS)) {
            new Gson().toJson(orders, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean createMember(String username, String password) {
        for (Member member : members) {
            if (member.getUsername().equals(username)) {
                return false; // Username already exists
            }
        }
        members.add(new Member(username, password));
        saveMembers();
        return true;
    }

    public boolean loginMember(String username, String password) {
        for (Member member : members) {
            System.out.println(member.getUsername());
            System.out.println(member.getPassword());
            if (member.getUsername().equals(username) && member.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public boolean createTransaction(Payment payment) {
        // Simulate transaction creation
        return true;
    }

    List<MenuItem> viewMenu() {
        return menu;
    }

    boolean addNewMenuItem(String name, String description, float price, List<Tag> tags) {
        for (MenuItem menuItem : menu) {
            if (menuItem.getName().equals(name)) {
                return false; // Username already exists
            }
        }
        menu.add(new MenuItem(name, description, price, tags));
        saveMenu();
        return true;
    }

    boolean addNewMenuItem(MenuItem item) {
        for (MenuItem menuItem : menu) {
            if (menuItem.getName().equals(item.getName())) {
                return false; // Username already exists
            }
        }
        menu.add(item);
        saveMenu();
        return true;
    }

    String validateMemberCredentials(String username, String password) {
        return members.stream().anyMatch(m -> m.getUsername().equals(username) && m.getPassword().equals(password)) 
                ? "valid" : "invalid";
    }

    public boolean upgradeMember(Member member) {
        for (Member m : members) {
            if (m.getMemberId() == member.getMemberId()) {
                member.setRole("Premium");
                saveMembers();
                return true;
            }
        }
        return false; // User not found
    }

    public boolean downgradeMember(Member member) {
        for (Member m : members) {
            if (m.getMemberId() == member.getMemberId()) {
                member.setRole("Standard");
                saveMembers();
                return true;
            }
        }
        return false; // User not found
    }
}
