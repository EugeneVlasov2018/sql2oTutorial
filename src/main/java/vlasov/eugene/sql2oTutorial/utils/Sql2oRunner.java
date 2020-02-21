package vlasov.eugene.sql2oTutorial.utils;

import org.sql2o.Sql2o;

public class Sql2oRunner {
    private static Sql2o sql2o;

    static {sql2o = new Sql2o("someUrl","someUser","somePassword");}

    public static Sql2o getInstance(){
        return sql2o;
    }
}
