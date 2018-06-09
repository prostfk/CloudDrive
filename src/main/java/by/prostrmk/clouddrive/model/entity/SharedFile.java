package by.prostrmk.clouddrive.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "SharedFiles")
public class SharedFile implements IEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "files")
    private String files;

    @Column(name = "username")
    private String username;

    public SharedFile() {
    }

    public SharedFile(String username, String files) {
        this.files = files;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
