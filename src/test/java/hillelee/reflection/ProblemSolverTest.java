package hillelee.reflection;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

public class ProblemSolverTest {

    @Test
    public void solvePuzzle() throws Exception {
        String result = new ProblemSolver().solve(new Puzzle());

        Assert.assertThat(result, Matchers.is("Correct answer"));
    }

    @Test
    public void solveDecryption() throws Exception {
        String result = new ProblemSolver().solve(new MessageDecryptor());

        Assert.assertThat(result, Matchers.is("Correct answer"));
    }

    @Test
    public void solvePuzzle2() throws Exception {
        String result = new ProblemSolver().solve(new Puzzle());

        Assert.assertThat(result, Matchers.is("Correct answer"));
    }

    @Test
    public void solveDecryption2() throws Exception {
        String result = new ProblemSolver().solve(new MessageDecryptor());

        Assert.assertThat(result, Matchers.is("Correct answer"));
    }
}