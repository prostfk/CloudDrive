package by.prostrmk.clouddrive.model.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Files")
public class UploadedFile {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "serverPath")
    public String serverPath;

    @Column(name = "clientPath")
    public String clientPath;


    public UploadedFile() {
    }

    public UploadedFile(String username) {
        this.username = username;
    }

    public UploadedFile(String username, String serverPath, String clientPath) {
        this.username = username;
        this.serverPath = serverPath;
        this.clientPath = clientPath;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getServerPath() {
        return serverPath;
    }

    public void setServerPath(String serverPath) {
        this.serverPath = serverPath;
    }

    public String getClientPath() {
        return clientPath;
    }

    public void setClientPath(String clientPath) {
        this.clientPath = clientPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UploadedFile that = (UploadedFile) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(username, that.username) &&
                Objects.equals(serverPath, that.serverPath) &&
                Objects.equals(clientPath, that.clientPath);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, username, serverPath, clientPath);
    }

    @Override
    public String toString() {
        return "UploadedFile{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", serverPath='" + serverPath + '\'' +
                ", clientPath='" + clientPath + '\'' +
                '}';
    }
}
