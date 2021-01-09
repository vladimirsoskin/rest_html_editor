package storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.w3c.dom.Text;

import static org.junit.jupiter.api.Assertions.*;

class TextStorageTest {

    static TextStorage textStorage;

    @BeforeEach
    void setUp() {
        textStorage = new TextStorageStringBuilderImpl();
    }

//    @Test
//    void substring() {
//        textStorage.append("123");
//        assertEquals("123", textStorage.getText());
////        assertEquals("2", textStorage.substring(1));
//    }
//
//    @Test
//    void testSubstring() {
//        textStorage.append("123");
//        assertEquals("123", textStorage.getText());
//        assertEquals("23", textStorage.substring(1, 2));
//    }

    @Test
    void length() {
        textStorage.append("123");
        assertEquals(3, textStorage.length());
        textStorage.remove(0, 2);
        assertEquals(0, textStorage.length());
    }

    @Test
    void append() {
        textStorage.append("123");
        assertEquals("123", textStorage.getText());
        textStorage.append("qwe");
        assertEquals("123qwe", textStorage.getText());
    }

    @Test
    void insert() {
        textStorage.append("123");
        assertEquals("123", textStorage.getText());
        textStorage.insert("qwe", 2);
        assertEquals( "12qwe3", textStorage.getText());
    }

    @Test
    void remove() {
        textStorage.append("123");
        assertEquals("123", textStorage.getText());
        textStorage.insert("qwe", 2);
        assertEquals( "12qwe3", textStorage.getText());
        textStorage.remove(2, 4);
        assertEquals("123", textStorage.getText());
    }
}