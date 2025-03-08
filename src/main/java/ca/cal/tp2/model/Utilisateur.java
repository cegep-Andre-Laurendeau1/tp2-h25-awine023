package ca.cal.tp2.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "typeUtilisateur", discriminatorType = DiscriminatorType.STRING)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public abstract class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;

    private String name;
    private String email;
    private String phoneNumber;

    @Column(name = "typeUtilisateur", nullable = false, updatable = false, insertable = false)
    private String typeUtilisateur;


    public Utilisateur(String name, String email, String phoneNumber, String typeUtilisateur) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.typeUtilisateur = typeUtilisateur;
    }

    public void login() {
        System.out.println(name + " s'est connecté.");
    }
}
