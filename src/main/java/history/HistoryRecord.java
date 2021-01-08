package history;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HistoryRecord {

    private final Object invokedObject;
    private final String methodName;
    private final Object[] args;

    public boolean invokeRecord() {
        if (methodName == null) return true;

        Class<?>[] classes = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            classes[i] = args[i].getClass();
        }

        try {
            Method method = invokedObject.getClass().getDeclaredMethod(methodName, classes);
            method.setAccessible(true);
            method.invoke(invokedObject, args);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public HistoryRecord(Object invokedObject, String methodName, Object[] args) {
        this.invokedObject = invokedObject;
        this.methodName = methodName;
        this.args = args;
    }
}
