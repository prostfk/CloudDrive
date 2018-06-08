package by.prostrmk.clouddrive.model.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "News")
public class News implements IEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "dateOfCreation")
    private String dateOfCreation;

    @Column(name = "pathToPic")
    private String pathToPic;

    public News() {
    }

    public News(String title, String content, String dateOfCreation, String pathToPic) {
        this.title = title;
        this.content = content;
        this.dateOfCreation = dateOfCreation;
        this.pathToPic = pathToPic;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(String dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public String getPathToPic() {
        return pathToPic;
    }

    public void setPathToPic(String pathToPic) {
        this.pathToPic = pathToPic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News news = (News) o;
        return Objects.equals(id, news.id) &&
                Objects.equals(title, news.title) &&
                Objects.equals(content, news.content) &&
                Objects.equals(dateOfCreation, news.dateOfCreation) &&
                Objects.equals(pathToPic, news.pathToPic);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, title, content, dateOfCreation, pathToPic);
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", dateOfCreation='" + dateOfCreation + '\'' +
                ", pathToPic='" + pathToPic + '\'' +
                '}';
    }
}
