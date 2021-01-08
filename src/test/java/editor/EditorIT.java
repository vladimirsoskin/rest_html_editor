package editor;

import history.HistoryHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import storage.MarkupStorage;
import storage.TextStorage;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EditorImpl.class)
@EnableAutoConfiguration
@ComponentScan({"editor", "history", "storage"})
public class EditorIT {
    @Autowired
    private Editor editor;

    @Test
    public void contextLoads(){
        System.out.println("ok");
    }

    @Test
    @DirtiesContext
    public void add(){
        String s = "123";
        String res = editor.add(s);
        assertEquals(s, res);
    }

    @Test
    @DirtiesContext
    public void addPlace(){
        editor.add("123");
        String res = editor.add("zxc", 2);
        assertEquals("12zxc3", res);
    }

    @Test
    @DirtiesContext
    public void remove(){
        editor.add("123");
        String res = editor.add("zxc", 2);
        assertEquals("12zxc3", res);
        res = editor.remove(2, 4);
        assertEquals("123", res);
        res = editor.add("zxc", 2);
        assertEquals("12zxc3", res);
        res = editor.undo();
        assertEquals("123", res);
        res = editor.redo();
        assertEquals("12zxc3", res);
    }



}
