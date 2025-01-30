package com.greenmart.app.domain.entities;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reviews")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Review {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id", nullable = false)
	private User user;
	
	@ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
	
	@Column(nullable = false)
	private int rating;
	
	@Column(columnDefinition = "TEXT")
	private String comment;
	
	@Column(nullable = false)
	private LocalDateTime createdAt;
	
	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
	}

	@Override
	public int hashCode() {
		return Objects.hash(comment, createdAt, id, rating);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Review other = (Review) obj;
		return Objects.equals(comment, other.comment) && Objects.equals(createdAt, other.createdAt)
				&& Objects.equals(id, other.id) && rating == other.rating;
	}
	
}
