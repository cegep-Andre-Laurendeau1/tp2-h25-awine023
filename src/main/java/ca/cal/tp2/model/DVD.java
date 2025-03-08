package ca.cal.tp2.model;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString(callSuper = true)
public class DVD extends Document {
    private String director;
    private int duree;
    private String rating;
}
