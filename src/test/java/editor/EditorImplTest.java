package editor;

import history.HistoryHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import storage.Mark;
import storage.MarkupStorage;
import storage.TextStorage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EditorImplTest {
    @Mock
    TextStorage text;

    @Mock
    MarkupStorage markup;

    @Mock
    HistoryHandler history;

    @InjectMocks
    EditorImpl editor;


    @BeforeEach
    void setUp() {

    }

    @Test
    void add() {
        String s = "123";
        when(text.getText()).thenReturn(s);
        when(text.length()).thenReturn(s.length());
        when(markup.get(any())).thenReturn(new Mark());
        when(markup.size()).thenReturn(3);
        assertEquals(s, editor.add(s));
        verify(text, times(1)).getText();
        verify(markup, times(s.length())).get(any());
        verify(markup, times(2)).size();
    }

    @Test
    void remove() {
        //TODO
    }

    @Test
    void italic() {
        //TODO
    }

    @Test
    void bold() {
        //TODO
    }

    @Test
    void underline() {
        //TODO
    }

    @Test
    void redo() {
        //TODO
    }

    @Test
    void undo() {
        //TODO
    }
}