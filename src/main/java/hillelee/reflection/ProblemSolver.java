package hillelee.reflection;

import lombok.SneakyThrows;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class ProblemSolver {
    public String solve(Object problem) {
        Class<?> aClass = problem.getClass();
        Method[] methods = aClass.getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(CorrectAnswer.class)) {
                try {
                    return (String) method.invoke(problem);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        throw new RuntimeException("There is no CorrectAnswer");
    }

    //    @SneakyThrows(RuntimeException.class) // won't work
//    @SneakyThrows({IllegalAccessException.class, InvocationTargetException.class})
//    @SneakyThrows(ReflectiveOperationException.class) // first parent of two upper classes
    @SneakyThrows
    public String solve2(Object problem) throws InvocationTargetException, IllegalAccessException {
        Class<?> aClass = problem.getClass();
        Method[] methods = aClass.getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(CorrectAnswer.class)) {
                return (String) method.invoke(problem);
            }
        }
        throw new RuntimeException("There is no CorrectAnswer");
    }

    /*  // we can use this annotation with methods and constructors
    @SneakyThrows
    public ProblemSolver() {
    }*/
}
