import java.util.List;
import org.sql2o.*;

public class Specialty {
  private int id;
  private String type;

  public Specialty(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }

  public int getId() {
    return id;
  }

  @Override
  public boolean equals(Object otherSpecialty) {
    if (!(otherSpecialty instanceof Specialty)) {
      return false;
    } else {
      Specialty newSpecialty = (Specialty) otherSpecialty;
      return this.getType().equals(newSpecialty.getType()) && this.getId() == newSpecialty.getId();
    }
  }

  public static List<Specialty> all() {
    String sql = "SELECT id, type FROM specialties";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Specialty.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO specialties (type) VALUES (:type)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("type", this.type)
        .executeUpdate()
        .getKey();
    }
  }

  public static Specialty find (int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM specialties WHERE id=:id";
      Specialty specialty = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Specialty.class);
      return specialty;
    }
  }

}
