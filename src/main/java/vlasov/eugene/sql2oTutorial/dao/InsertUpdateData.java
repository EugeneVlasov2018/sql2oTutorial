package vlasov.eugene.sql2oTutorial.dao;

import org.sql2o.Connection;
import vlasov.eugene.sql2oTutorial.model.SomeModel;

public class InsertUpdateData {

    public void updateData(Connection connection, Integer id, String value) {
        String updateSql = "update myTable set value = :valParam where id = :idParam";

        connection.createQuery(updateSql)
                .addParameter("valParam", value)
                .addParameter("idParam", id)
                .executeUpdate();

    }

    public void insertData(Connection connection, Integer id, String value) {
        String insertSql = "insert into myTable(id, value) values (:idParam, :valParam)";

        connection.createQuery(insertSql)
                .addParameter("idParam", id)
                .addParameter("valParam", value)
                .executeUpdate();

    }

    //Autoincremented values and identity columns
    // assuming a table called MYTABLE with two colums.
    // - id integer primary key autoincrement, and
    // - value varchar(10)
    public void insertInAtoincrementedTable(Connection connection, String value) {
        String sql = "insert into MYTABLE ( value ) values ( :val )";

        Integer insertedId = (Integer) connection.createQuery(sql, true)
                .addParameter("val", value)
                .executeUpdate()
                .getKey();//To fetch the actual inserted value, call the getKey() method after executing the statement.
    }

    //If you need to add many parameters from a POJO class, you can use the Query.bind(Object) method.
    public void bindMethod(Connection connection, SomeModel model) {
        String sql = "insert into MYTABLE(col1, col2, col3, col4) values (:prop1, :prop2, :prop3, :prop4)";
        connection.createQuery(sql).bind(model).executeUpdate();
    }
}
