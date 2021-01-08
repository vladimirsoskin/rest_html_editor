package storage;

public interface MarkupStorage {
    Integer size();

    Mark get(Integer position);

    void add(Mark mark);

    void add(Integer position, Mark mark);

    void appendEmpty(Integer size);

    void insert(Mark[] marks, Integer position);

    void insertEmpty(Integer size, Integer position);

    Mark[] remove(Integer from, Integer to);

    void overwrite(Mark[] marks, Integer from, Integer to);

    Mark[] makeBold(Integer from, Integer to);

    Mark[] makeItalic(Integer from, Integer to);

    Mark[] makeUnderline(Integer from, Integer to);
}
