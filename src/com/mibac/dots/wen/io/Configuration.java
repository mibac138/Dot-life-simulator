package com.mibac.dots.wen.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Configuration {
    public static final String EXTENSION = ".dc";
    private static final Pattern FORMAT_ENCODER =
            Pattern.compile("(?<key>[a-zA-Z\\.]*)=(?<val>[^;]*);");
    private static final String FORMAT = "$key=$val;$newline";
    private static Map<String, Integer> GROUP_SORT_ORDER = new HashMap<>();
    private Comparator<Entry<String, Object>> keyComparator = (e1, e2) -> {
        String s1 = e1.getKey();
        String s2 = e2.getKey();
        int d1 = s1.indexOf('.');
        int d2 = s2.indexOf('.');
        String g1 = s1.substring(0, d1 == -1 ? s1.length() : d1).trim().toLowerCase();
        String g2 = s2.substring(0, d2 == -1 ? s2.length() : d2).trim().toLowerCase();
        if (g1.equals(g2))
            return 0;
        else {
            Integer gso1 = GROUP_SORT_ORDER.get(g1);
            Integer gso2 = GROUP_SORT_ORDER.get(g2);
            if (gso1 == null)
                return -1;
            else if (gso2 == null)
                return 1;
            else
                return GROUP_SORT_ORDER.get(g1).compareTo(GROUP_SORT_ORDER.get(g2));
        }
    };
    private Path path;
    private File file;
    private String content;
    private Map<String, Object> values;

    public Configuration(File file, Map<String, Object> defaults) throws IOException {
        int i = 0;
        GROUP_SORT_ORDER.put("world", i++);
        GROUP_SORT_ORDER.put("debug", i++);
        this.values = new LinkedHashMap<>();
        file.createNewFile();
        setFile(file);

        if (defaults != null) {
            Map<String, Object> __values = values;
            values = new LinkedHashMap<>(defaults);
            setAll(__values);
            if (!__values.equals(values))
                save();
        }
    }

    public Configuration(File file) throws IOException {
        this(file, null);
    }

    private void sortValues() {
        List<Entry<String, Object>> list = new LinkedList<Entry<String, Object>>(values.entrySet());

        Collections.sort(list, keyComparator);

        for (Entry<String, Object> e : list)
            values.put(e.getKey(), e.getValue());
    }

    public void generateContent() {
        sortValues();
        String n = System.getProperty("line.separator");
        String content = "";
        for (String key : values.keySet())
            content += FORMAT.replace("$key", key).replace("$val", values.get(key).toString())
                    .replace("$newline", n);

        this.content = content;
    }

    public void reload() throws IOException {
        String content = "";

        for (String s : Files.readAllLines(path))
            content += " " + s;

        if (content.trim().isEmpty()) {
            this.content = "";
            return;
        }

        values.clear();
        content = content.substring(1);
        Matcher m = FORMAT_ENCODER.matcher(content);

        while (m.find())
            values.put(m.group("key"), m.group("val"));

        this.content = content;
    }

    public void save() throws IOException {
        save(file);
    }

    public void save(File f) throws IOException {
        f = verifyExtension(f);
        generateContent();
        Files.write(Paths.get(f.toURI()), content.getBytes());
    }

    public Comparator<Entry<String, Object>> getComparator() {
        return keyComparator;
    }

    public void setComparator(Comparator<Entry<String, Object>> keyComparator) {
        this.keyComparator = keyComparator;
    }

    public String getContent() {
        return content;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) throws IOException {
        this.path = verifyExtension(path);
        this.file = this.path.toFile();
        reload();
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) throws IOException {
        this.file = verifyExtension(file);
        this.path = Paths.get(this.file.toURI());
        reload();
    }

    public void setAll(Map<String, Object> vals) {
        for (String s : vals.keySet())
            set(s, vals.get(s));
    }

    public void set(String key, Object val) {
        values.put(key, val);
    }

    public Map<String, Object> getValues() {
        return new HashMap<String, Object>(values);
    }

    public Object getObject(String key) {
        return values.get(key);
    }

    public String getString(String key) {
        Object o = values.get(key);
        return o == null ? null : o.toString();
    }

    public long getLong(String key) {
        Object o = values.get(key);
        return o == null ? 0L : Long.parseLong(o.toString());
    }

    public double getDouble(String key) {
        Object o = values.get(key);
        return o == null ? 0d : Double.parseDouble(o.toString());
    }

    public float getFloat(String key) {
        Object o = values.get(key);
        return o == null ? 0f : Float.parseFloat(o.toString());
    }

    public int getInt(String key) {
        Object o = values.get(key);
        return o == null ? 0 : Integer.parseInt(o.toString());
    }

    public short getShort(String key) {
        Object o = values.get(key);
        return o == null ? 0 : Short.parseShort(o.toString());
    }

    public byte getByte(String key) {
        Object o = values.get(key);
        return o == null ? 0 : Byte.parseByte(o.toString());
    }

    public boolean getBoolean(String key) {
        Object o = values.get(key);
        return o == null ? false : Boolean.parseBoolean(o.toString()) || o.toString().equals("1");
    }

    private File verifyExtension(File f) {
        if (!f.toString().endsWith(EXTENSION))
            f = new File(file + EXTENSION);
        return f;
    }

    private Path verifyExtension(Path p) {
        if (!p.endsWith(EXTENSION))
            p = Paths.get(p.toUri() + EXTENSION);
        return p;
    }
}
