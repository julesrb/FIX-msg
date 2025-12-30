package com.github.julesrb.fixme.springbootdb.repository;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data // generates getters, setters, toString, equals, hashCode
@NoArgsConstructor // generates a no-args constructor (needed by JPA)
@AllArgsConstructor
@Entity
public class TransactionEntity {

    @Id
    private int id;
}
