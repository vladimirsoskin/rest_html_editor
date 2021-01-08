package history;

public interface HistoryHandler {
    Integer increment();

    Integer decrement();

    void addRecords(HistoryRecord redoTextRecord, HistoryRecord undoTextRecord, HistoryRecord redoMarkupRecord, HistoryRecord undoMarkupRecord);

    HistoryRecord getRedoTextRecord(Integer version);

    HistoryRecord getUndoTextRecord(Integer version);

    HistoryRecord getRedoMarkupRecord(Integer version);

    HistoryRecord getUndoMarkupRecord(Integer version);
}
