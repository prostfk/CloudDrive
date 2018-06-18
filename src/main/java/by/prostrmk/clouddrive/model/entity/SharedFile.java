package by.prostrmk.clouddrive.model.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "SharedFiles")
public class SharedFile implements IEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "path")
    private String path;

    @Column(name = "username")
    private String username;

    public SharedFile() {
    }

    public SharedFile(String username, String path) {
        this.path = path;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SharedFile that = (SharedFile) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(path, that.path) &&
                Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, path, username);
    }

    @Override
    public String toString() {
        return "SharedFile{" +
                "id=" + id +
                ", path='" + path + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
