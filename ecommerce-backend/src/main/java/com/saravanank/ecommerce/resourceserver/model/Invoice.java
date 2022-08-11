package com.saravanank.ecommerce.resourceserver.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "invoice")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long invoiceId;
	
	@OneToOne(cascade =  CascadeType.ALL)
	@JoinColumn(name ="invoice_of_user")
	private User user;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "invoice_of_order")
	private Order order;
	
	private float totalAmountReceivable;
	private float totalAmountReceived;
	private float amountPending;
	
	private float totalAmountReturnable;
	private float totalAmountReturned;
	private float pendingReturns;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name = "invoice_id")
	private List<Transactions> transactions;
}
