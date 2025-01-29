package com.greenmart.app.entities;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="products")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Product {
//ProductID, VendorID, ProductName, Category, Description, Price, StockQuantity, ImageURL, DateAdded
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(nullable = false)
	private String name;
	//vendor
	//Category
	
	@Column(nullable = false, columnDefinition = "TEXT")
	private String description;
	
	@Column(nullable = false)
	private double price;
	
	@Column(nullable = false)
	private Long quantity;
	
	@Column(nullable = false)
	private String imageUrl;
	
	@Column(nullable = false)
	private LocalDateTime createAt;
	
	@Column(nullable = false)
	private LocalDateTime restockedAt;

	@Override
	public int hashCode() {
		return Objects.hash(createAt, description, id, imageUrl, name, price, quantity, restockedAt);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return Objects.equals(createAt, other.createAt) && Objects.equals(description, other.description)
				&& Objects.equals(id, other.id) && Objects.equals(imageUrl, other.imageUrl)
				&& Objects.equals(name, other.name)
				&& Double.doubleToLongBits(price) == Double.doubleToLongBits(other.price)
				&& Objects.equals(quantity, other.quantity) && Objects.equals(restockedAt, other.restockedAt);
	}
	
	@PrePersist
	protected void onCreate() {
		this.createAt = LocalDateTime.now();
		this.restockedAt = LocalDateTime.now();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.restockedAt = LocalDateTime.now();
	}
}
