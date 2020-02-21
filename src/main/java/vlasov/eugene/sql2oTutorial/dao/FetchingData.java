package vlasov.eugene.sql2oTutorial.dao;

import org.sql2o.Connection;
import org.sql2o.ResultSetIterable;
import vlasov.eugene.sql2oTutorial.model.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FetchingData {

    //getting rows from db and parse it to java-object
    public List<Task> getAllTasks(Connection connection) {
        String sql = "SELECT id, description, duedate FROM tasks";

        return connection.createQuery(sql).executeAndFetch(Task.class);

    }

    //getting rows from db and parse it to java-object
    public List<Task> getTasksBetweenDates(Connection connection, Date fromDate, Date toDate) {
        String sql = "SELECT id, description, duedate FROM tasks WHERE duedate >= :fromDate AND duedate < :toDate";

        return connection.createQuery(sql)
                .addParameter("fromDate", fromDate)
                .addParameter("toDate", toDate)
                .executeAndFetch(Task.class);
    }


    //getting data from db and return it as primitive or obj-wrapper value
    public Integer getStudentCount(Connection connection) {
        String sql = "SELECT count(id) FROM students";

        return connection.createQuery(sql).executeScalar(Integer.class);
    }


    //getting data from db and return it as List<primitive or obj-wrapper>
    public List<Integer> getStudentIdList(Connection connection) {
        String sql = "SELECT id FROM students";

        return connection.createQuery(sql).executeScalarList(Integer.class);
    }

    //lazy-fetching data
    public void readAndFlushAllTasks(Connection connection) {
        String sql = "SELECT id, description, duedate " +
                "FROM tasks";

        final int BATCH_SIZE = 1000;

        List<Task> batch = new ArrayList<Task>(BATCH_SIZE);

        try (ResultSetIterable<Task> tasks = connection.createQuery(sql).executeAndFetchLazy(Task.class)) {
            for (Task task : tasks) {
                if (batch.size() == BATCH_SIZE) {
                    // here is where you flush your batch to file
                    batch.clear();
                }
                batch.add(task);
            }

            if (!batch.isEmpty()) {
                // also flush to your file the last items
                batch.clear();
            }
        }
    }
}
