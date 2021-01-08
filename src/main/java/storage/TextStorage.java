package storage;

public interface TextStorage {
    String substring(Integer from, Integer to);

    String substring(Integer position);

    int length();

    void append(String s);

    void insert(String s, Integer position);

    String remove(Integer from, Integer to);

    String getText();
}
