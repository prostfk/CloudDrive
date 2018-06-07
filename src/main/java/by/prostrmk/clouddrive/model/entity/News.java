package by.prostrmk.clouddrive.model.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "News")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "dateOfCreation")
    private String dateOfCreation;

    @Column(name = "arrayOfLinksToPics")
    private String arrayOfLinksToPics;

    public News() {
    }

    public News(String title, String content, String dateOfCreation, String arrayOfLinksToPics) {
        this.title = title;
        this.content = content;
        this.dateOfCreation = dateOfCreation;
        this.arrayOfLinksToPics = arrayOfLinksToPics;
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

    public String getArrayOfLinksToPics() {
        return arrayOfLinksToPics;
    }

    public void setArrayOfLinksToPics(String arrayOfLinksToPics) {
        this.arrayOfLinksToPics = arrayOfLinksToPics;
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
                Objects.equals(arrayOfLinksToPics, news.arrayOfLinksToPics);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, title, content, dateOfCreation, arrayOfLinksToPics);
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", dateOfCreation='" + dateOfCreation + '\'' +
                ", arrayOfLinksToPics='" + arrayOfLinksToPics + '\'' +
                '}';
    }
}
