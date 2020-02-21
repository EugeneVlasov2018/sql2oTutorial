package vlasov.eugene.sql2oTutorial.dao;

import org.sql2o.Connection;
import org.sql2o.Query;

public class BatchingQuery {

    //If you need to run an UPDATE, INSERT or DELETE query multiple times with different parameters,
    // you will get a huge performance gain by running them in a batch.
    // This is by some databases also known as bulk updates or bulk inserts.
    // Sql2o supports only homogeneous batches, that is batches with only one query,
    // but with multiple sets of parameters. This is due to limitations in jdbc.
    //
    //In the example below, 100 rows are inserted into a table in the database in one batch.
    public void insertABunchOfRows(Connection connection) {
        final String sql = "INSERT INTO SomeTable(id, value) VALUES (:id, :value)";

        Query query = connection.createQuery(sql);

        for (int i = 0; i < 100; i++) {
            query.addParameter("id", i).addParameter("value", "foo" + i)
                    .addToBatch();
        }
        query.executeBatch(); // executes entire batch
    }
}
