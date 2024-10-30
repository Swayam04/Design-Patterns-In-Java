package creational.singleton;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class IMDatabase {
    private final Map<String, String> mainStore;

    private static final class InstanceHolder {
        private static final IMDatabase instance = new IMDatabase();
    }

    private IMDatabase() {
        mainStore = new ConcurrentHashMap<>();
    }

    public static IMDatabase getInstance() {
        return InstanceHolder.instance;
    }

    public void set(String key, String value) {
        mainStore.put(key, value);
    }

    public String get(String key) {
        return mainStore.get(key);
    }
}
