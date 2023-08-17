package Driver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DriverInfo {

    private static Map<String, List<String>> map = new HashMap<>();

    public synchronized static void setDriverInfo(String key, List<String> value) {
        map.put(key, value);
    }

    public synchronized static void removeDriverInfo(String key) {
        map.remove(key);
    }

    public synchronized static Map<String, List<String>> getDriverInfo() {
        return map;
    }

    public synchronized static void clearDriverInfo() {
        map.clear();
    }
}
