package com.example.repository.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PhoneDao {
    @Id
    private String id;
    private String userId;
    private long number;
    private int cityCode;
    private String countryCode;
}
