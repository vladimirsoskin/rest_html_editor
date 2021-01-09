package controller;

import editor.EditorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class EditorControllerTest {

    @Mock
    EditorImpl editor;

    @InjectMocks
    EditorController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    void add() {
        String s = "123";
        when(editor.add(s)).thenReturn(s);
        try {
            mockMvc
                    .perform(MockMvcRequestBuilders
                        .post("/add")
                        .param("string", s))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType("text/html;charset=UTF-8"))
                    .andExpect(content().string(s));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    void remove() {
    }

    @Test
    void italic() {
    }

    @Test
    void bold() {
    }

    @Test
    void underline() {
    }

    @Test
    void redo() {
    }

    @Test
    void undo() {
    }
}