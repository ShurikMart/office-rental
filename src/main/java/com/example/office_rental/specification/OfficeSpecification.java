package com.example.office_rental.specification;

import com.example.office_rental.model.OfficeSpace;
import com.example.office_rental.model.OfficeStatus;

import org.springframework.data.jpa.domain.Specification;

public class OfficeSpecification {

    public static Specification<OfficeSpace> hasStatus(OfficeStatus status) {

        return (root, query, cb) ->
                status == null ? cb.conjunction() : cb.equal(root.get("status"), status);
    }

    public static Specification<OfficeSpace> minPrice(Double minPrice) {

        return (root, query, cb) ->
                minPrice == null ? cb.conjunction() : cb.greaterThanOrEqualTo(root.get("rentalPrice"), minPrice);
    }

    public static Specification<OfficeSpace> maxPrice(Double maxPrice) {

        return (root, query, cb) ->
                maxPrice == null ? cb.conjunction() : cb.lessThanOrEqualTo(root.get("rentalPrice"), maxPrice);
    }

    public static Specification<OfficeSpace> floor(Integer floor) {

        return (root, query, cb) ->
                floor == null ? cb.conjunction() : cb.equal(root.get("floor"), floor);
    }

    public static Specification<OfficeSpace> minArea(Double minArea) {

        return (root, query, cb) ->
                minArea == null ? cb.conjunction() : cb.greaterThanOrEqualTo(root.get("area"), minArea);
    }

    public static Specification<OfficeSpace> maxArea(Double maxArea) {

        return (root, query, cb) ->
                maxArea == null ? cb.conjunction() : cb.lessThanOrEqualTo(root.get("area"), maxArea);
    }

    public static Specification<OfficeSpace> minCapacity(Integer minCapacity) {

        return (root, query, cb) ->
                minCapacity == null ? cb.conjunction() : cb.greaterThanOrEqualTo(root.get("capacity"), minCapacity);
    }

    public static Specification<OfficeSpace> officeType(String officeType) {

        return (root, query, cb) ->
                officeType == null || officeType.isBlank() ? cb.conjunction() : cb.equal(root.get("officeType"), officeType);
    }

    public static Specification<OfficeSpace> hasFurniture(Boolean hasFurniture) {

        return (root, query, cb) ->
                hasFurniture == null ? cb.conjunction() : cb.equal(root.get("hasFurniture"), hasFurniture);
    }
}