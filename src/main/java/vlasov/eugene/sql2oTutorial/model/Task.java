package vlasov.eugene.sql2oTutorial.model;

import lombok.Data;

import java.util.Date;

@Data
public class Task {
    private Long id;
    private String description;
    private Date dueDate;
}
