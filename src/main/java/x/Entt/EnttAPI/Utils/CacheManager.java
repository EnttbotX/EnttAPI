package x.Entt.EnttAPI.Utils;

import org.yaml.snakeyaml.Yaml;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;

public class CacheManager {

    private static class CacheEntry {
        Object value;
        boolean persistent;

        CacheEntry(Object value, boolean persistent) {
            this.value = value;
            this.persistent = persistent;
        }
    }

    private final Map<String, CacheEntry> cache = new HashMap<>();
    private final File cacheFile;
    private final String encryptionKey = "EnTTaPi2009LPP4E";
    private final Yaml yaml = new Yaml();

    public CacheManager(File dataFolder) {
        if (!dataFolder.exists()) dataFolder.mkdirs();
        this.cacheFile = new File(dataFolder, "._cache");
    }

    public void set(String key, Object value, boolean persistent) {
        cache.put(key, new CacheEntry(value, persistent));
    }

    public <T> T get(String key, Class<T> type) {
        CacheEntry entry = cache.get(key);
        if (entry == null) return null;
        return type.isInstance(entry.value) ? type.cast(entry.value) : null;
    }

    public boolean contains(String key) {
        return cache.containsKey(key);
    }

    public void remove(String key) {
        cache.remove(key);
    }

    public void clearVolatile() {
        cache.entrySet().removeIf(e -> !e.getValue().persistent);
    }

    public void clearAll() {
        cache.clear();
    }

    public void save() {
        try {
            Map<String, Object> persistentData = new HashMap<>();
            for (Map.Entry<String, CacheEntry> entry : cache.entrySet()) {
                if (entry.getValue().persistent) {
                    Map<String, Object> node = new HashMap<>();
                    node.put("value", entry.getValue().value);
                    node.put("persistent", true);
                    persistentData.put(entry.getKey(), node);
                }
            }

            String yamlString = yaml.dump(persistentData).replace(": ", ":");
            String encrypted = encrypt(yamlString);

            try (FileWriter writer = new FileWriter(cacheFile)) {
                writer.write(encrypted);
            }
        } catch (Exception ignored) {}
    }

    @SuppressWarnings("unchecked")
    public void load() {
        if (!cacheFile.exists()) return;
        try {
            StringBuilder sb = new StringBuilder();
            try (BufferedReader r = new BufferedReader(new FileReader(cacheFile))) {
                String line;
                while ((line = r.readLine()) != null) sb.append(line);
            }
            String decrypted = decrypt(sb.toString());
            Map<String, Map<String, Object>> loaded = yaml.load(decrypted);

            if (loaded != null) {
                cache.clear();
                for (Map.Entry<String, Map<String, Object>> e : loaded.entrySet()) {
                    cache.put(e.getKey(), new CacheEntry(e.getValue().get("value"), true));
                }
            }
        } catch (Exception ignored) {}
    }

    private String encrypt(String data) throws Exception {
        Key aesKey = new SecretKeySpec(encryptionKey.getBytes(StandardCharsets.UTF_8), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, aesKey);
        return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes(StandardCharsets.UTF_8)));
    }

    private String decrypt(String encrypted) throws Exception {
        Key aesKey = new SecretKeySpec(encryptionKey.getBytes(StandardCharsets.UTF_8), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, aesKey);
        return new String(cipher.doFinal(Base64.getDecoder().decode(encrypted)), StandardCharsets.UTF_8);
    }
}