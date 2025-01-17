package jp.co.feeps.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tags")
public class Tags {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer tag_id;
    
    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<Recipe> recipes;
    

	public Integer getTag_id() {
		return tag_id;
	}
	
	public void setTag_id(Integer tag_id) {
		this.tag_id = tag_id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
    
    

    // getters and setters
}
