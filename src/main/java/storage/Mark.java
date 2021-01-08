package storage;

import java.util.Objects;

public class Mark {

    boolean b = false;
    boolean i = false;
    boolean u = false;

    public Mark(Mark copyFrom) {
        this.b = copyFrom.b;
        this.i = copyFrom.i;
        this.u = copyFrom.u;
    }

    public Mark() {
    }

    public void cloneFrom(Mark copyFrom) {
        this.b = copyFrom.b;
        this.i = copyFrom.i;
        this.u = copyFrom.u;
    }

    public String getOpenTag() {
        StringBuilder res = new StringBuilder();
        if (b) res.append("<b>");
        if (i) res.append("<i>");
        if (u) res.append("<u>");
        return res.toString();
    }

    public String getCloseTag() {
        StringBuilder res = new StringBuilder();
        if (u) res.append("</u>");
        if (i) res.append("</i>");
        if (b) res.append("</b>");
        return res.toString();
    }

    @Override
    public String toString() {
        return "Mark{" +
                "b=" + b +
                ", i=" + i +
                ", u=" + u +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mark mark = (Mark) o;
        return b == mark.b && i == mark.i && u == mark.u;
    }
}
