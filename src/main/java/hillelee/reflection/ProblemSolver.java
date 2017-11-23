package hillelee.reflection;

import lombok.SneakyThrows;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Stream;


public class ProblemSolver {

 /*   //without Stream
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
    }*/
   /* // WithStreamsButWithoutflatMap
    @SneakyThrows
    public String solve(Object problem) {
        Class<?> aClass = problem.getClass();
         return Arrays.stream(aClass.getMethods())
                .filter(method -> method.isAnnotationPresent(CorrectAnswer.class))
                .map(method -> (String) method.invoke(problem))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("There is no CorrectAnswer Annotation"));
    }*/

    //WithStreamsAndflatMap
//    @SneakyThrows
    public String solve(Object problem) {
        return   Stream.of(problem)
                .map(Object::getClass)
                .flatMap(clazz -> Arrays.stream(clazz.getMethods()))
                .filter(method -> method.isAnnotationPresent(CorrectAnswer.class))
                .map(invokeOn(problem))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("There is no CorrectAnswer Annotation"));
    }

    private Function<Method, String > invokeOn(Object object) {
        return method -> {
            try {
                return (String) method.invoke(object);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

//    @SneakyThrows(RuntimeException.class) // won't work
//    @SneakyThrows({IllegalAccessException.class, InvocationTargetException.class})
//    @SneakyThrows(ReflectiveOperationException.class) // first parent of two upper classes
    @SneakyThrows
    public String solve2(Object problem) throws InvocationTargetException, IllegalAccessException {
        Class<?> aClass = problem.getClass();
        Method[] methods = aClass.getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent( CorrectAnswer.class)) {
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
