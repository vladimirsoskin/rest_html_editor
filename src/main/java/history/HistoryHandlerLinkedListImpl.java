package history;

import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
public class HistoryHandlerLinkedListImpl implements HistoryHandler {

    private final LinkedList<HistoryRecord> undoTextHistory = new LinkedList<>();
    private final LinkedList<history.HistoryRecord> redoTextHistory = new LinkedList<>();
    private final LinkedList<history.HistoryRecord> undoMarkupHistory = new LinkedList<>();
    private final LinkedList<history.HistoryRecord> redoMarkupHistory = new LinkedList<>();

    private Integer currentVersion = -1;

    @Override
    public Integer increment() {
        int maxHistory = undoTextHistory.size() - 1;
        if (currentVersion == -1 && maxHistory == -1) {
            return null;
        } else if (currentVersion < maxHistory) {
            return ++currentVersion;
        } else { //out of history size - usually on trying to redo when history ends
            return null;
        }
    }

    @Override
    public Integer decrement() {
        if (currentVersion > -1) {
            return currentVersion--;
        } else {
            return null;
        }
    }

    @Override
    public void addRecords(HistoryRecord redoTextRecord, HistoryRecord undoTextRecord, HistoryRecord redoMarkupRecord, HistoryRecord undoMarkupRecord){
        clearOldRecords();
        redoTextHistory.add(redoTextRecord);
        undoTextHistory.add(undoTextRecord);
        redoMarkupHistory.add(redoMarkupRecord);
        undoMarkupHistory.add(undoMarkupRecord);
        increment();
    }

    private void clearOldRecords() {
        if (currentVersion == -1) return;

        int maxSize = currentVersion + 1;

        while (undoTextHistory.size() > maxSize) {
            undoTextHistory.removeLast();
        }
        while (redoTextHistory.size() > maxSize) {
            redoTextHistory.removeLast();
        }
        while (undoMarkupHistory.size() > maxSize) {
            undoMarkupHistory.removeLast();
        }
        while (redoMarkupHistory.size() > maxSize) {
            redoMarkupHistory.removeLast();
        }
    }

    @Override
    public HistoryRecord getRedoTextRecord(Integer version) {
        return redoTextHistory.get(version);
    }

    @Override
    public HistoryRecord getUndoTextRecord(Integer version) {
        return undoTextHistory.get(version);
    }

    @Override
    public HistoryRecord getRedoMarkupRecord(Integer version) {
        return redoMarkupHistory.get(version);
    }

    @Override
    public HistoryRecord getUndoMarkupRecord(Integer version) {
        return undoMarkupHistory.get(version);
    }
}
