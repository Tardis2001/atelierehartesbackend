package com.renata.atelierehartesbackend.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "category")
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "category_name")
    private String categoryName;

    @NonNull
    private String categoryDescription;

    @NonNull
    private String imageurl;

    
	@OneToMany(mappedBy = "category", fetch = FetchType.LAZY,
			cascade = CascadeType.ALL)
	Set<Product> products;

    public Category(Integer id, String categoryName, String categoryDescription, String imageurl) {
        this.id = id;
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
        this.imageurl = imageurl;
    }

    @Override
    public String toString() {
        return "Category [id=" + id + ", categoryName=" + categoryName + ", categoryDescription=" + categoryDescription
                + ", imageurl=" + imageurl + ", products=" + products + "]";
    }
    
}
