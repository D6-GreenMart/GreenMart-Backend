package com.greenmart.app.domain.entities;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import com.greenmart.app.domain.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Order {
	//OrderID, UserID, OrderDate, TotalAmount, OrderStatus, ShippingAddress
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id", nullable = false)
	private User user;
	
	@Column(nullable = false)
	private LocalDateTime createdAt;
	
	private double amount;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	
	private String address;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItem> orderItems = new HashSet<>();
	
	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, amount, createdAt, id, status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		return Objects.equals(address, other.address)
				&& Double.doubleToLongBits(amount) == Double.doubleToLongBits(other.amount)
				&& Objects.equals(createdAt, other.createdAt) && Objects.equals(id, other.id) && status == other.status;
	}
	
	
}
