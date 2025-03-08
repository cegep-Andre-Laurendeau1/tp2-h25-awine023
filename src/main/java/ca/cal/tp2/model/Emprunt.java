package ca.cal.tp2.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class Emprunt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long borrowID;

    private Date dateEmprunt;
    private String status;

    @ManyToOne
    private Emprunteur emprunteur;

    @OneToMany(mappedBy = "emprunt", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmpruntDetail> empruntDetails = new ArrayList<>();

}
