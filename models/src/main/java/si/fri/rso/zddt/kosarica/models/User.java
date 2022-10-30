package si.fri.rso.zddt.kosarica.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user")
@NamedQueries(value =
        {
                //vrni vse uporabnike
                @NamedQuery(name = "User.getAll",
                        query = "SELECT u FROM User u"),
                //vrni uporabnika glede na id
                @NamedQuery(name = "User.getById",
                        query = "SELECT u FROM User u WHERE u.id = :userId")
        })
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    private String firstname;
    private String lastname;

    public Integer getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
