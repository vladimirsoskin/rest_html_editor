package editor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EditorImpl.class)
@EnableAutoConfiguration
@ComponentScan({"editor", "history", "storage"})
public class EditorImplIT {
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
