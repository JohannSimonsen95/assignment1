
package bombastic.game;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CollisionDetection {

    private final ExecutorService executor =
        Executors.newFixedThreadPool(2);

    public boolean[] checkBoth(
        Callable<Boolean> player1Check,
        Callable<Boolean> player2Check
    ) {
        ArrayList<Future<Boolean>> results = new ArrayList<>();

        results.add(executor.submit(player1Check));
        results.add(executor.submit(player2Check));

        boolean[] collisions = new boolean[2];

        try {
            collisions[0] = results.get(0).get();
            collisions[1] = results.get(1).get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        return collisions;
    }

    public void shutdown() {
        executor.shutdown();
    }
}
