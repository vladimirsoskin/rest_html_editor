package storage;

import org.springframework.stereotype.Service;

@Service
public class TextStorageStringBuilderImpl implements TextStorage {

    private final StringBuilder text = new StringBuilder();

    @Override
    public String substring(Integer from, Integer to) {
        return text.substring(from, to + 1);
    }

    @Override
    public String substring(Integer position){
        return text.substring(position, position + 1);
    }

    @Override
    public int length() {
        return text.length();
    }

    @Override
    public void append(String s) {
        text.append(s);
    }

    @Override
    public void insert(String s, Integer position) {
        text.insert(position, s);
    }

    @Override
    public String remove(Integer from, Integer to) {
        String removed = text.substring(from, to + 1);
        text.delete(from, to + 1);
        return removed;
    }

    @Override
    public String getText() {
        return text.toString();
    }
}
