package hillelee.reflection;

import java.lang.reflect.Method;

public class ProblemSolver {
    public String solve(Object promlem) {
        Class<?> aClass = promlem.getClass();
        Method[] methods = aClass.getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(CorrectAnswer.class)) {
                try {
                    return (String) method.invoke(promlem);
                } catch (Exception e) {
                   throw new RuntimeException(e);
                }
            }
        }
        throw new RuntimeException("There is no CorrectAnswer");
    }

}
