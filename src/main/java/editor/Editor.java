package editor;

public interface Editor {

    public String add(String s);

    public String add(String s, Integer position);

    public String remove(Integer fromPosition, Integer toPosition);

    public String italic(Integer fromPosition, Integer toPosition);

    public String bold(Integer fromPosition, Integer toPosition);

    public String underline(Integer fromPosition, Integer toPosition);

    public String redo();

    public String undo();
}
