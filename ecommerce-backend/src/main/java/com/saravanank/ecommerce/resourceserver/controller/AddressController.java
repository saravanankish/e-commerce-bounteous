package com.saravanank.ecommerce.resourceserver.controller;

import java.security.Principal;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saravanank.ecommerce.resourceserver.model.Address;
import com.saravanank.ecommerce.resourceserver.service.AddressService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/v1/address")
public class AddressController {

	private static final Logger logger = Logger.getLogger(AddressController.class);

	@Autowired
	private AddressService addressService;

	@GetMapping("/user/{userId}")
	@ApiOperation(value = "Get addresses of user", notes = "All users can use this endpoint")
	public ResponseEntity<List<Address>> getAddressesOfCurrentUser(Principal principal,
			@PathVariable("userId") long userId) {
		logger.info("GET request to /api/v1/address by user " + principal.getName());
		return new ResponseEntity<List<Address>>(addressService.getUserAddresses(userId), HttpStatus.OK);
	}

	@GetMapping("/{addressId}")
	@ApiOperation(value = "Get address by id", notes = "All users can use this endpoint")
	public ResponseEntity<Address> getAddressById(@PathVariable("addressId") long addressId) {
		logger.info("GET request to /api/v1/address/" + addressId);
		return new ResponseEntity<Address>(addressService.getAddressById(addressId), HttpStatus.OK);
	}

	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
	@ApiOperation(value = "Add address", notes = "User with customer access can use this endpoint to add their address")
	public ResponseEntity<Address> addAddress(Principal principal, @RequestBody Address address) {
		logger.info("POST request to /api/v1/address");
		System.out.println(address.getCity());
		System.out.println(address.getArea());
		System.out.println(address.getState());
		System.out.println(address.getDoorNo());
		return new ResponseEntity<Address>(addressService.addAddressByUsername(principal.getName(), address),
				HttpStatus.CREATED);
	}

	@PostMapping("/{userId}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@ApiOperation(value = "Add address to user", notes = "Users with admin access can use this endpoint to add address to a user")
	public ResponseEntity<Address> addAddressToUser(Principal principal, @PathVariable("userId") long userId,
			@RequestBody Address address) {
		logger.info("POST request to /api/v1/address/" + userId);
		return new ResponseEntity<Address>(addressService.addAddress(userId, address), HttpStatus.CREATED);
	}

	@PutMapping("/{addressId}")
	@PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
	@ApiOperation(value = "Update address", notes = "Users with customer access can edit their address")
	public ResponseEntity<Address> updateAddress(Principal principal, @PathVariable("addressId") long addressId,
			@RequestBody Address address) {
		logger.info("PUT request to /api/v1/address/" + addressId);
		return new ResponseEntity<Address>(addressService.updateAddress(addressId, address), HttpStatus.CREATED);
	}

	@DeleteMapping("/{addressId}")
	@PreAuthorize("hasAnyAuthority('ROLE_CUSTOMER', 'ROLE_ADMIN')")
	@ApiOperation(value = "Delete address", notes = "Users with customer or admin access can delete an address by id")
	public ResponseEntity<Void> deleteAddress(Principal principal, @PathVariable("addressId") long addressId) {
		logger.info("DELETE request to /api/v1/address/" + addressId);
		addressService.deleteAddress(addressId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PutMapping("/delivery/{addressId}")
	@PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
	@ApiOperation(value = "Change delivery address", notes = "Users with customer access can change their delivery address")
	public ResponseEntity<Void> changeDeliveryAddress(@PathVariable("addressId") long addressId, Principal principal) {
		addressService.changeDeliveryAddressOfUser(addressId, principal.getName());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
