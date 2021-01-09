package storage;

public interface TextStorage {

    int length();

    void append(String s);

    void insert(String s, Integer position);

    String remove(Integer from, Integer to);

    String getText();
}
