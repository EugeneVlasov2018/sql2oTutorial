package vlasov.eugene.sql2oTutorial.dao;

import org.sql2o.Connection;
import org.sql2o.ResultSetIterable;
import org.sql2o.Sql2o;
import vlasov.eugene.sql2oTutorial.pojo.Task;
import vlasov.eugene.sql2oTutorial.utils.Sql2oRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FetchingData {
    private Sql2o sql2o = Sql2oRunner.getInstance();

    //getting rows from db and parse it to java-object
    public List<Task> getAllTasks(){
        String sql =
                "SELECT id, description, duedate " +
                        "FROM tasks";
        try(Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Task.class);
        }
    }

    //getting rows from db and parse it to java-object
    public List<Task> getTasksBetweenDates(Date fromDate, Date toDate){
        String sql =
                "SELECT id, description, duedate " +
                        "FROM tasks " +
                        "WHERE duedate >= :fromDate AND duedate < :toDate";

        try(Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("fromDate", fromDate)
                    .addParameter("toDate", toDate)
                    .executeAndFetch(Task.class);
        }
    }


    //getting data from db and return it as primitive or obj-wrapper value
    public Integer getStudentCount(){
        String sql = "SELECT count(id) FROM students";

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeScalar(Integer.class);
        }
    }


    //getting data from db and return it as List<primitive or obj-wrapper>
    public List<Integer> getStudentIdList(){
        String sql = "SELECT id FROM students";

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeScalarList(Integer.class);
        }
    }

    //lazy-fetching data
    public void readAndFlushAllTasks() {
        String sql = "SELECT id, description, duedate " +
                "FROM tasks";

        final int BATCH_SIZE = 1000;

        List<Task> batch = new ArrayList<Task>(BATCH_SIZE);

        try (Connection con = sql2o.open()) {
            try (ResultSetIterable<Task> tasks = con.createQuery(sql).executeAndFetchLazy(Task.class)) {
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
}
