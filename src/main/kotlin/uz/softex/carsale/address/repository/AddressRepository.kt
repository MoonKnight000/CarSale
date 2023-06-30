package uz.softex.carsale.address.repository

import org.springframework.data.jpa.repository.JpaRepository
import uz.softex.carsale.address.entity.Address

interface AddressRepository :JpaRepository<Address,Int>