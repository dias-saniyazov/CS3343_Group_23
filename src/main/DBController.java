import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

class DBController {
    private final String DB_FILE = "data/db.json";

    boolean checkAvailability(MenuItem selectedVehicle) {
        return true;
    }

    boolean checkMemberExist(String username) {
        List<Member> members = readMembers();
        return members.stream().anyMatch(m -> m.getUsername().equals(username));
    }

    boolean createMember(Member member) {
        List<Member> members = readMembers();
        members.add(member);
        return writeMembers(members);
    }

    boolean createTransaction(Payment payment) {
        // Simulate transaction creation
        return true;
    }

    ArrayList<MenuItem> getAvailableDisplayCount() {
        return readMenu();
    }

    boolean addNewMenuItem(MenuItem item) {
        List<MenuItem> menuItems = readMenu();
        menuItems.add(item);
        return writeMenu(menuItems);
    }

    boolean removeNewMenuItem(MenuItem vehicle) {
        List<MenuItem> menuItems = readMenu();
        menuItems.remove(vehicle);
        return writeMenu(menuItems);
    }

    String validateMemberCredentials(String username, String password) {
        List<Member> members = readMembers();
        return members.stream().anyMatch(m -> m.getUsername().equals(username) && m.getPassword().equals(password)) 
                ? "valid" : "invalid";
    }

    private List<Member> readMembers() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Read the existing JSON file into a Map
            Map<String, Object> jsonMap = mapper.readValue(
                new File(DB_FILE),
                TypeFactory.defaultInstance().constructMapType(Map.class, String.class, Object.class)
            );
    
            // Extract the "members" array from the Map
            Object membersObject = jsonMap.get("members");
            if (membersObject instanceof List) {
                return mapper.convertValue(membersObject, TypeFactory.defaultInstance().constructCollectionType(List.class, Member.class));
            } else {
                return new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private boolean writeMembers(List<Member> members) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Read the existing JSON file into a Map
            Map<String, Object> jsonMap = mapper.readValue(
                new File(DB_FILE),
                TypeFactory.defaultInstance().constructMapType(Map.class, String.class, Object.class)
            );
    
            // Update the "members" array in the Map
            jsonMap.put("members", members);
    
            // Write the updated Map back to the JSON file
            mapper.writeValue(new File(DB_FILE), jsonMap);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private ArrayList<MenuItem> readMenu() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Read the existing JSON file into a Map
            Map<String, Object> jsonMap = mapper.readValue(
                new File(DB_FILE),
                TypeFactory.defaultInstance().constructMapType(Map.class, String.class, Object.class)
            );
    
            // Extract the "menu" array from the Map
            Object menuObject = jsonMap.get("menu");
            if (menuObject instanceof ArrayList) {
                return mapper.convertValue(menuObject, TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, MenuItem.class));
            } else {
                return new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    private boolean writeMenu(List<MenuItem> menuItems) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Read the existing JSON file into a Map
            Map<String, Object> jsonMap = mapper.readValue(
                new File(DB_FILE),
                TypeFactory.defaultInstance().constructMapType(Map.class, String.class, Object.class)
            );
    
            // Update the "menu" array in the Map
            jsonMap.put("menu", menuItems);
    
            // Write the updated Map back to the JSON file
            mapper.writeValue(new File(DB_FILE), jsonMap);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
