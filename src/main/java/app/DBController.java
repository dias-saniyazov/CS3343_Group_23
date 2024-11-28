package main.java.app;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.FileReader;
import java.io.FileWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import main.java.user.*;
import main.java.object.*;


public class DBController {
    private final String MEMBERS = "data/members.json";
    private final String MENU = "data/menu.json";
    private final String ORDERS = "data/orders.json";
    private List<Member> members;
    private List<Order> orders;
    private List<MenuItem> menu;

    private static DBController instance = null;

    public static DBController getInstance() {
        if (instance == null) {
            instance = new DBController();
        }
        return instance;
    }
    private DBController() {
        members = loadMembers();
        orders = loadOrders();
        menu = loadMenu();
        if (members == null) {
            members = new ArrayList<>();
        }
        if (orders == null) {
            orders = new ArrayList<>();
        }   
        if (menu == null) {
            menu = new ArrayList<>();
        }   
        Member.setCounter(members.size());
        Order.setCounter(orders.size());
        MenuItem.setCounter(menu.size());
    }

    public List<Member> loadMembers() {
        try (FileReader reader = new FileReader(MEMBERS)) {
            Type type = new TypeToken<ArrayList<Member>>() {}.getType();
            return getGson().fromJson(reader, type);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
    
    public List<Order> loadOrders() {
        try (FileReader reader = new FileReader(ORDERS)) {
            Type type = new TypeToken<ArrayList<Order>>() {}.getType();
            return getGson().fromJson(reader, type);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public List<MenuItem> loadMenu() {
        try (FileReader reader = new FileReader(MENU)) {
            Type type = new TypeToken<ArrayList<MenuItem>>() {}.getType();
            return getGson().fromJson(reader, type);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public void deleteOrders() {
        orders = new ArrayList<>();
        saveOrders();
    }

    public boolean checkIfMemberExist(String username) {
        return members.stream().anyMatch(m -> m.getUsername().equals(username));
    }

    private void saveMembers() {
        try (FileWriter writer = new FileWriter(MEMBERS)) {
            getGson().toJson(members, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void saveMenu() {
        Gson gson = new GsonBuilder()
            .addSerializationExclusionStrategy(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return "observers".equals(f.getName());
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        })
        .create();
        try (FileWriter writer = new FileWriter(MENU)) {
            gson.toJson(menu, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveOrders() {
        try (FileWriter writer = new FileWriter(ORDERS)) {
            getGson().toJson(orders, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Gson getGson() {
        return new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
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

    // public boolean loginMember(String username, String password) {
    //     for (Member member : members) {
    //         System.out.println(member.getUsername());
    //         System.out.println(member.getPassword());
    //         if (member.getUsername().equals(username) && member.getPassword().equals(password)) {
    //             return true;
    //         }
    //     }
    //     return false;
    // }

    public boolean createTransaction(Order order) {
        if(orders == null) {
            orders = new ArrayList<>();
        }
        orders.add(order);
        saveOrders();
        saveMembers();
        return true;
    }
    public List<MenuItem> viewMenu() {
        return menu;
    }

    public List<Order> viewOrders() {
        return orders;
    }

    public List<Member> viewMembers(){
        return members;
    }

    public boolean addNewMenuItem(String name, String description, float price, List<String> tags) {
        for (MenuItem menuItem : menu) {
            if (menuItem.getName().equals(name)) {
                return false; // name already exists
            }
        }
        MenuItem newItem = new MenuItem(name, description, price, tags);
        menu.add(newItem);
        for (Member member : members) {
            if (!member.getMemberState().equals(MembershipState.ADMIN)) {
                newItem.addObserver(member);
            }
        }
        newItem.notifyObservers();
        saveMembers();
        saveMenu();
        return true;
    }

    public boolean addNewMenuItem(MenuItem item) {
        for (MenuItem menuItem : menu) {
            if (menuItem.getName().equals(item.getName())) {
                return false; // name already exists
            }
        }
        menu.add(item);
        saveMenu();
        return true;
    }

    public Member validateMemberCredentials(String username, String password) {
        for (Member member : members) {
            if (member.getUsername().equals(username) && member.getPassword().equals(password)) {
                member.loadOrders();
                return member;
            }
        }
        return null;
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

    public boolean changeBalance(Member member, float amount) {
        for (Member m : members) {
            if (m.getUsername().equals(member.getUsername())) {
                m.topUpBalance(amount);
                saveMembers();
                return true;
            }
        }
        return false; // User not found
    }

    public float findPrice(String itemName) {
        for (MenuItem item : menu) {
            if (item.getName().equals(itemName)) {
                return item.getPrice();
            }
        }
        return 0;
    }
    

    public class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
        private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        @Override
        public void write(JsonWriter out, LocalDateTime value) throws IOException {
            out.value(value.format(formatter));
        }

        @Override
        public LocalDateTime read(JsonReader in) throws IOException {
            return LocalDateTime.parse(in.nextString(), formatter);
        }
    }
    
}
