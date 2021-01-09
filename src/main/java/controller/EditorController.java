package controller;

import editor.Editor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EditorController {

    @Autowired
    Editor editor;

    @PostMapping(value = "/add", produces="text/html;charset=UTF-8")
    public String add(@RequestParam(name="string") String s,
                      @RequestParam(name="position", required = false) Integer position)
    {
        if(position == null){
            return editor.add(s);
        } else {
            return editor.add(s, position);
        }
    }

    @PostMapping(value = "/remove", produces="text/html;charset=UTF-8")
    public String remove(@RequestParam(name="from") Integer from,
                         @RequestParam(name="to")Integer to)
    {
        return editor.remove(from, to);
    }

    @PostMapping(value = "/italic", produces="text/html;charset=UTF-8")
    public String italic(@RequestParam(name="from") Integer from,
                         @RequestParam(name="to") Integer to)
    {
        return editor.italic(from, to);
    }

    @PostMapping(value = "/bold", produces="text/html;charset=UTF-8")
    public String bold(@RequestParam(name="from") Integer from,
                       @RequestParam(name="to") Integer to)
    {
        return editor.bold(from, to);
    }

    @PostMapping(value = "/underline", produces="text/html;charset=UTF-8")
    public String underline(@RequestParam(name="from") Integer from,
                            @RequestParam(name="to") Integer to)
    {
        return editor.underline(from, to);
    }

    @PostMapping(value = "/redo", produces="text/html;charset=UTF-8")
    public String redo(){
        return editor.redo();
    }

    @PostMapping(value = "/undo", produces="text/html;charset=UTF-8")
    public String undo(){
        return editor.undo();
    }


}
