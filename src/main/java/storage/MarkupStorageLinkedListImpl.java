package storage;

import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.LinkedList;

@Service
public class MarkupStorageLinkedListImpl implements MarkupStorage {

    private final LinkedList<Mark> markup = new LinkedList<>(); //we choose linkedlist, because add, remove should be O(1)

    @Override
    public Integer size() {
        return markup.size();
    }

    @Override
    public Mark get(Integer position) {
        return markup.get(position);
    }

    @Override
    public void add(Mark mark) {
        markup.add(mark);
    }

    @Override
    public void add(Integer position, Mark mark) {
        markup.add(position, mark);
    }

    @Override
    public void appendEmpty(Integer size) {
        for (int i = 0; i < size; i++) {
            markup.add(new Mark()); //TODO is there any more elegant way????
        }
    }

    @Override
    public void insert(Mark[] marks, Integer position) {
        for (int i = 0; i < marks.length; i++) {
            markup.add(position + i, marks[i]);
        }
    }

    @Override
    public void insertEmpty(Integer size, Integer position) {
        for (int i = 0; i < size; i++) {
            markup.add(position + i, new Mark());
        }
    }

    @Override
    public Mark[] remove(Integer from, Integer to) {
        Mark[] removed = new Mark[to - from + 1];
        Iterator<Mark> iterator = markup.iterator();
        int i = 0;
        int j = 0;
        while (iterator.hasNext() && j <= to) {
            Mark mark = iterator.next();
            if (j++ >= from) {
                removed[i++] = mark;
                iterator.remove();
            }
        }
        return removed;
    }

    @Override
    public void overwrite(Mark[] marks, Integer from, Integer to) {
        for (int i = 0; i <= (to - from); i++) {
            markup.get(from + i).cloneFrom(marks[i]); //TODO is there more elegant way?
        }
    }

    @Override
    public Mark[] makeBold(Integer from, Integer to) {
        int size = to - from + 1; //TODO should we change according to first bold/not bold???
        Mark[] changed = new Mark[size];
        for (int i = 0; i < size; i++) {
            Mark current = markup.get(from + i);
            changed[i] = new Mark(current);
            current.b = !current.b;
        }
        return changed;
    }

    @Override
    public Mark[] makeItalic(Integer from, Integer to) {
        int size = to - from + 1; //TODO should we change according to first bold/not bold???
        Mark[] changed = new Mark[size];
        for (int i = 0; i < size; i++) {
            Mark current = markup.get(from + i);
            changed[i] = new Mark(current);
            current.i = !current.i;
        }
        return changed;
    }

    @Override
    public Mark[] makeUnderline(Integer from, Integer to) {
        int size = to - from + 1; //TODO should we change according to first bold/not bold???
        Mark[] changed = new Mark[size];
        for (int i = 0; i < size; i++) {
            Mark current = markup.get(from + i);
            changed[i] = new Mark(current);
            current.u = !current.u;
        }
        return changed;
    }

    @Override
    public String toString() {
        return markup.toString();
    }
}
