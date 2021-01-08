package editor;

import history.HistoryHandler;
import history.HistoryRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import storage.Mark;
import storage.MarkupStorage;
import storage.TextStorage;

@Service
public class EditorImpl implements Editor {

    @Autowired
    private TextStorage text;

    @Autowired
    private MarkupStorage markup;

    @Autowired
    private HistoryHandler history;

    private String generateHTML(String message) {
        StringBuilder res = new StringBuilder();
        Mark prevMark = new Mark();
        for (int i = 0; i < markup.size(); i++) {
            //close tag if markup changes
            Mark mark = markup.get(i);
            if(!prevMark.equals(mark)){
                res.append(mark.getCloseTag());
                res.append(mark.getOpenTag());
                prevMark = mark;
            }

            //add symbol itself. change new line to HTML tag
            String symbol = text.substring(i);
            if (symbol.equals("\r")) {
                res.append("<br>");
            } else {
                res.append(symbol);
            }
        }
        //anyway close latest markup tag
        res.append(prevMark.getCloseTag());

        if(message.equals("ok")){
            return res.toString();
        } else {
            return message + "<br>" +
                        "--------result text (" + text.length() + ")--------<br>" +
                        res.toString();
        }

    }

    public String add(String s) {
        text.append(s);
        Integer removeFrom = text.length() - s.length();
        Integer removeTo = text.length() - 1;
        HistoryRecord redoText = new HistoryRecord(text, "append", new Object[]{s});
        HistoryRecord undoText = new HistoryRecord(text, "remove", new Object[]{removeFrom, removeTo});

        markup.appendEmpty(s.length());
        HistoryRecord redoMarkup = new HistoryRecord(markup, "appendEmpty", new Object[]{s.length()});
        HistoryRecord undoMarkup = new HistoryRecord(markup, "remove", new Object[]{removeFrom, removeTo});

        history.addRecords(redoText, undoText, redoMarkup, undoMarkup);

        return generateHTML("ok");
    }

    public String add(String s, Integer position) {
        if (position < 0 || position >= text.length()) return generateHTML("Error. incorrect parameter position " + position);

        text.insert(s, position);
        Integer removeFrom = position;
        Integer removeTo = position + s.length() - 1;
        HistoryRecord redoText = new HistoryRecord(text, "insert", new Object[]{s, position});
        HistoryRecord undoText = new HistoryRecord(text, "remove", new Object[]{removeFrom, removeTo});

        markup.insertEmpty(s.length(), position);
        HistoryRecord redoMarkup = new HistoryRecord(markup, "insertEmpty", new Object[]{s.length(), position});
        HistoryRecord undoMarkup = new HistoryRecord(markup, "remove", new Object[]{removeFrom, removeTo});

        history.addRecords(redoText, undoText, redoMarkup, undoMarkup);

        return generateHTML("ok");
    }

    public String remove(Integer fromPosition, Integer toPosition) {
        if (fromPosition < 0 || fromPosition >= text.length() || fromPosition > toPosition)
            return generateHTML("Error. incorrect fromPosition parameter " + fromPosition);
        if (toPosition >= text.length())
            return generateHTML("Error. incorrect toPosition parameter " + toPosition);

        String removedText = text.remove(fromPosition, toPosition);
        HistoryRecord redoText = new HistoryRecord(text, "remove", new Object[]{fromPosition, toPosition});
        HistoryRecord undoText = new HistoryRecord(text, "insert", new Object[]{removedText, fromPosition});

        Mark[] removedMarkup = markup.remove(fromPosition, toPosition);
        HistoryRecord redoMarkup = new HistoryRecord(markup, "remove", new Object[]{fromPosition, toPosition});
        HistoryRecord undoMarkup = new HistoryRecord(markup, "insert", new Object[]{removedMarkup, fromPosition});

        history.addRecords(redoText, undoText, redoMarkup, undoMarkup);

        return generateHTML("ok");
    }

    public String italic(Integer fromPosition, Integer toPosition) {
        if (fromPosition < 0 || fromPosition >= text.length() || fromPosition > toPosition)
            return generateHTML("Error. incorrect fromPosition parameter " + fromPosition);
        if (toPosition >= text.length())
            return generateHTML("Error. incorrect toPosition parameter " + toPosition);

        Mark[] removedMarkup = markup.makeItalic(fromPosition, toPosition);
        HistoryRecord redoMarkup = new HistoryRecord(markup, "makeItalic", new Object[]{fromPosition, toPosition});
        HistoryRecord undoMarkup = new HistoryRecord(markup, "overwrite", new Object[]{removedMarkup, fromPosition, toPosition});

        history.addRecords(null, null, redoMarkup, undoMarkup);

        return generateHTML("ok");
    }

    public String bold(Integer fromPosition, Integer toPosition) {
        if (fromPosition < 0 || fromPosition >= text.length() || fromPosition > toPosition)
            return generateHTML("Error. incorrect fromPosition parameter " + fromPosition);
        if (toPosition >= text.length())
            return generateHTML("Error. incorrect toPosition parameter " + toPosition);

        Mark[] removedMarkup = markup.makeBold(fromPosition, toPosition);
        HistoryRecord redoMarkup = new HistoryRecord(markup, "makeBold", new Object[]{fromPosition, toPosition});
        HistoryRecord undoMarkup = new HistoryRecord(markup, "overwrite", new Object[]{removedMarkup, fromPosition, toPosition});

        history.addRecords(null, null, redoMarkup, undoMarkup);

        return generateHTML("ok");
    }

    public String underline(Integer fromPosition, Integer toPosition) {
        if (fromPosition < 0 || fromPosition >= text.length() || fromPosition > toPosition)
            return generateHTML("Error. incorrect fromPosition parameter " + fromPosition);
        if (toPosition >= text.length())
            return generateHTML("Error. incorrect toPosition parameter " + toPosition);

        Mark[] removedMarkup = markup.makeUnderline(fromPosition, toPosition);
        HistoryRecord redoMarkup = new HistoryRecord(markup, "makeUnderline", new Object[]{fromPosition, toPosition});
        HistoryRecord undoMarkup = new HistoryRecord(markup, "overwrite", new Object[]{removedMarkup, fromPosition, toPosition});

        history.addRecords(null, null, redoMarkup, undoMarkup);

        return generateHTML("ok");
    }

    public String undo() {
        Integer versionToInvoke = history.decrement();
        if (versionToInvoke == null)
            return generateHTML("Error. No more undo history");

        HistoryRecord textRecord = history.getUndoTextRecord(versionToInvoke);
        if (textRecord != null) {
            boolean res = textRecord.invokeRecord();
            if (!res)
                return "Error on server side with undo text history. Contact developer";
        }

        HistoryRecord markupRecord = history.getUndoMarkupRecord(versionToInvoke);
        if (markupRecord != null) {
            boolean res = markupRecord.invokeRecord();
            if (!res)
                return "Error on server side with undo markup history. Contact developer";
        }

        return generateHTML("ok");
    }

    public String redo() {
        Integer versionToInvoke = history.increment();
        if (versionToInvoke == null)
            return generateHTML("Error. No more redo history");

        HistoryRecord textRecord = history.getRedoTextRecord(versionToInvoke);
        if (textRecord != null) {
            boolean res = textRecord.invokeRecord();
            if (!res)
                return "Error on server side with redo text history. Contact developer";
        }

        HistoryRecord markupRecord = history.getUndoMarkupRecord(versionToInvoke);
        if (markupRecord != null) {
            boolean res = history.getRedoMarkupRecord(versionToInvoke).invokeRecord();
            if (!res)
                return "Error on server side with redo markup history. Contact developer";
        }

        return generateHTML("ok");
    }
}
