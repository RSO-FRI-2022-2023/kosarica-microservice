package si.fri.rso.zddt.kosarica.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "kosarica")
@NamedQueries(value =
        {
                //vrni vse kosarice
                @NamedQuery(name = "Kosarica.getAll",
                        query = "SELECT k FROM Kosarica k"),
                //vrni kosarico glede na id
                @NamedQuery(name = "Kosarica.getById",
                        query = "SELECT k FROM Kosarica k where k.id = :kosaricaId"),
                //vrni kosarico glede na user_id
                @NamedQuery(name = "Kosarica.getByUserId",
                        query = "SELECT k FROM Kosarica k where k.uporabnik.id = :userId")
        })
public class Kosarica implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kosarica_id")
    private Integer id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Izdelek> izdelki;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Uporabnik uporabnik;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Izdelek> getIzdelki() {
        return izdelki;
    }

    public void setIzdelki(List<Izdelek> izdelki) {
        this.izdelki = izdelki;
    }

    public Uporabnik getUporabnik() {
        return uporabnik;
    }

    public void setUporabnik(Uporabnik uporabnik) {
        this.uporabnik = uporabnik;
    }
}
