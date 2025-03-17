package com.motorcycleparts.ecommerce.services;

import com.motorcycleparts.ecommerce.dto.AddressDTO;
import com.motorcycleparts.ecommerce.models.Address;
import com.motorcycleparts.ecommerce.models.User;
import com.motorcycleparts.ecommerce.repositories.AddressRepository;
import com.motorcycleparts.ecommerce.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<AddressDTO> getAddressesByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        List<Address> addresses = addressRepository.findByUser(user);
        return addresses.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public AddressDTO getAddressById(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));
        return convertToDTO(address);
    }

    public AddressDTO getDefaultAddress(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Address defaultAddress = addressRepository.findDefaultAddress(user)
                .orElseThrow(() -> new RuntimeException("Default address not found"));
        
        return convertToDTO(defaultAddress);
    }

    @Transactional
    public AddressDTO createAddress(AddressDTO addressDTO) {
        User user = userRepository.findById(addressDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Address address = convertToEntity(addressDTO);
        address.setUser(user);
        
        // If this is the first address or set as default, make it the default
        if (addressDTO.isDefault() || addressRepository.findByUser(user).isEmpty()) {
            // Reset any existing default address
            if (addressDTO.isDefault()) {
                addressRepository.findDefaultAddress(user).ifPresent(defaultAddress -> {
                    defaultAddress.setDefault(false);
                    addressRepository.save(defaultAddress);
                });
            }
            address.setDefault(true);
        }
        
        Address savedAddress = addressRepository.save(address);
        return convertToDTO(savedAddress);
    }

    @Transactional
    public AddressDTO updateAddress(Long id, AddressDTO addressDTO) {
        Address existingAddress = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));
        
        existingAddress.setStreetAddress(addressDTO.getStreetAddress());
        existingAddress.setCity(addressDTO.getCity());
        existingAddress.setState(addressDTO.getState());
        existingAddress.setPostalCode(addressDTO.getPostalCode());
        existingAddress.setCountry(addressDTO.getCountry());
        
        // Handle default address changes
        if (addressDTO.isDefault() && !existingAddress.isDefault()) {
            User user = existingAddress.getUser();
            
            // Reset any existing default address
            addressRepository.findDefaultAddress(user).ifPresent(defaultAddress -> {
                defaultAddress.setDefault(false);
                addressRepository.save(defaultAddress);
            });
            
            existingAddress.setDefault(true);
        }
        
        Address updatedAddress = addressRepository.save(existingAddress);
        return convertToDTO(updatedAddress);
    }

    @Transactional
    public void deleteAddress(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));
        
        // If this is the default address, find another address to make default
        if (address.isDefault()) {
            User user = address.getUser();
            List<Address> userAddresses = addressRepository.findByUser(user);
            
            if (userAddresses.size() > 1) {
                // Find another address to make default
                Address newDefault = userAddresses.stream()
                        .filter(a -> !a.getId().equals(id))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("No other address found"));
                
                newDefault.setDefault(true);
                addressRepository.save(newDefault);
            }
        }
        
        addressRepository.delete(address);
    }

    private AddressDTO convertToDTO(Address address) {
        AddressDTO addressDTO = modelMapper.map(address, AddressDTO.class);
        addressDTO.setUserId(address.getUser().getId());
        return addressDTO;
    }

    private Address convertToEntity(AddressDTO addressDTO) {
        return modelMapper.map(addressDTO, Address.class);
    }
} 