package vlasov.eugene.sql2oTutorial.service;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import vlasov.eugene.sql2oTutorial.dao.FetchingData;
import vlasov.eugene.sql2oTutorial.dao.InsertUpdateData;
import static vlasov.eugene.sql2oTutorial.utils.Sql2oRunner.getInstance;

public class TransactionService {
    private Sql2o sql2o;

    private FetchingData fetchingData = new FetchingData();
    private InsertUpdateData insertUpdateData = new InsertUpdateData();

    public TransactionService() {
        sql2o = getInstance();
    }

    public void startTransaction(){
        try (Connection conn = sql2o.beginTransaction()){
                ///... do some
            if (doSome()){
                conn.commit();
            } else {
                conn.rollback();
            }
            // If you don't explicitly call commit() or rollback() on the Connection object,
            // the transaction will automatically be rolled back when exiting the try-with-resources block.
        }
    }

    public boolean doSome(){
        return true;
    }
}
