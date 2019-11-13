package com.example.recomendador.models;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name="SeriesAnimadas")
public class Series  implements Serializable {
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

   @Column(name="title")
    private String title;

   @Column(name="description")
    private String description;

   @Column(name = "type")
    private String[] type;

   @Column(name = "year")
    private Integer year;

   @Column(name = "productType")
    private String productType;
   @Column(name="image")
   private String image;

   @Column(name = "status")
    private Boolean status;

   public Series(){
   }
   public Series(String title, String description,String[] type,Integer year,String productType,String image,Boolean status){
       this.title=title;
       this.description=description;
       this.type=type;
       this.year=year;
       this.image=image;
       this.productType=productType;
       this.status=status;
   }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public Boolean getStatus() {
        return status;
    }

    public Integer getYear() {
        return year;
    }

    public String getDescription() {
        return description;
   }

    public String getProductType() {
        return productType;
    }

    public String getTitle() {
        return title;
    }

    public String[] getType() {
        return type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String[] type) {
        this.type = type;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return (title+ " , "+id+" , "+image);
    }
    @Override
    public boolean equals(Object o){
        if(o==null || o.getClass()!=getClass()) return false;
        final Series series=(Series) o;
        return (series.getId().equals(this.id));
    }
    @Override
    public int hashCode(){
       return this.id;
    }
}
