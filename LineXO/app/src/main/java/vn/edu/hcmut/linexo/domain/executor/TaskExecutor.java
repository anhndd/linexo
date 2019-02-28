package vn.edu.hcmut.linexo.domain.executor;

import android.support.annotation.NonNull;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TaskExecutor implements Executor {

    private final int CORE_POOL_SIZE    = 3;
    private final int MAX_POOL_SIZE     = 5;
    private final int KEEP_ALIVE_TIME   = 10;

    /**
     * Used to contain task. Allow a limited number of jobs
     * to be executed at the same time
     */
    private ThreadPoolExecutor threadPoolExecutor;

    /**
     * Create a new {@Code TaskExecutor} with a {@code ThreadPoolExecutor}.
     */
    private TaskExecutor() {
        threadPoolExecutor = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>()
        );
    }

    /**
     * An Instance of this class.
     */
    private static TaskExecutor taskExecutor;

    /**
     * This method is responsible for providing an unique instance of this class.
     * @return an instance of this class
     */
    public static TaskExecutor getInstance() {
        if (taskExecutor == null) {
            taskExecutor = new TaskExecutor();
        }
        return taskExecutor;
    }

    /**
     * Executes the given {@code command}. The command is executed
     * in a pooled thread.
     * @param command the runnable task.
     */
    @Override
    public void execute(@NonNull Runnable command) {
        threadPoolExecutor.execute(command);
    }
}
