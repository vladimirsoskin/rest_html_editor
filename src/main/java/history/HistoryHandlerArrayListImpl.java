package history;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@Qualifier("arrayList")
@Primary
public class HistoryHandlerArrayListImpl implements HistoryHandler {

    private final ArrayList<HistoryRecord> undoTextHistory = new ArrayList<>();
    private final ArrayList<HistoryRecord> redoTextHistory = new ArrayList<>();
    private final ArrayList<HistoryRecord> undoMarkupHistory = new ArrayList<>();
    private final ArrayList<HistoryRecord> redoMarkupHistory = new ArrayList<>();

    private Integer historySize = 0;
    private Integer currentVersion = -1;

    @Override
    public Integer increment() {
        int maxHistory = historySize - 1;
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
    public void addRecords(HistoryRecord redoTextRecord, HistoryRecord undoTextRecord, HistoryRecord redoMarkupRecord, HistoryRecord undoMarkupRecord) {
        clearOldRecords();
        historySize++;
        if (historySize <= redoTextHistory.size()) {
            redoTextHistory.set(historySize - 1, redoTextRecord);
            undoTextHistory.set(historySize - 1, undoTextRecord);
            redoMarkupHistory.set(historySize - 1, redoMarkupRecord);
            undoMarkupHistory.set(historySize - 1, undoMarkupRecord);
        } else {
            redoTextHistory.add(redoTextRecord);
            undoTextHistory.add(undoTextRecord);
            redoMarkupHistory.add(redoMarkupRecord);
            undoMarkupHistory.add(undoMarkupRecord);
        }
        increment();
    }

    private void clearOldRecords() {
        historySize = currentVersion + 1;
    }

    @Override
    public HistoryRecord getRedoTextRecord(Integer version) {
        if(version > historySize) throw new ArrayIndexOutOfBoundsException("there are less versions in history than " + version);
        return redoTextHistory.get(version);
    }

    @Override
    public HistoryRecord getUndoTextRecord(Integer version) {
        if(version > historySize) throw new ArrayIndexOutOfBoundsException("there are less versions in history than " + version);
        return undoTextHistory.get(version);
    }

    @Override
    public HistoryRecord getRedoMarkupRecord(Integer version) {
        if(version > historySize) throw new ArrayIndexOutOfBoundsException("there are less versions in history than " + version);
        return redoMarkupHistory.get(version);
    }

    @Override
    public HistoryRecord getUndoMarkupRecord(Integer version) {
        if(version > historySize) throw new ArrayIndexOutOfBoundsException("there are less versions in history than " + version);
        return undoMarkupHistory.get(version);
    }
}
