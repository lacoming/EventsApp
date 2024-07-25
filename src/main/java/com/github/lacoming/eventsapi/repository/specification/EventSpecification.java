package com.github.lacoming.eventsapi.repository.specification;

import com.github.lacoming.eventsapi.entity.Category;
import com.github.lacoming.eventsapi.entity.Event;
import com.github.lacoming.eventsapi.model.EventFilterModel;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.Set;

public interface EventSpecification {

    static Specification<Event> withFilter(EventFilterModel filterModel){
        return Specification.where(
                isEquals("id", filterModel.getId())
                        .and(isEquals("name", filterModel.getName()))
                        .and(isEquals("location", "city", filterModel.getCity()))
                        .and(isEquals("location", "street", filterModel.getStreet()))
                        .and(isEquals("organization", "name", filterModel.getOrganizationName()))
                        .and(inCategories(filterModel.getCategoryIds()))
                        .and(isEquals(filterModel.getStartTime(), "startTime"))
                        .and(isEquals(filterModel.getEndTime(), "endTime"))
        );
    }

    private static <T> Specification<Event> isEquals(String filedName, T object){
        return (root, query, criteriaBuilder) -> {
            if (object == null){
                return null;
            }

            return criteriaBuilder.equal(root.get(filedName), object);
        };
    }

    private static <T> Specification<Event> isEquals(LocalDate date, String filedName){
        return (root, query, criteriaBuilder) -> {
            if (date == null){
                return null;
            }

            return criteriaBuilder.equal(
                    criteriaBuilder.function("date", LocalDate.class, root.get(filedName)),
                    date
            );

        };
    }

    private static Specification<Event> isEquals(String rootName, String filedName, String value){
        return (root, query, criteriaBuilder) -> {
            if (value == null){
                return null;
            }

            return criteriaBuilder.equal(root.get(rootName).get(filedName), value);
        };
    }

    private static <T> Specification<Event> inCategories(Set<Long> categoryIds){
        return (root, query, criteriaBuilder) -> {
            if (CollectionUtils.isEmpty(categoryIds)){
                return null;
            }

            Join<Event, Category> categoryJoin = root.join("categories", JoinType.INNER);
            Predicate[] predicates = categoryIds.stream()
                    .map(categoryId -> criteriaBuilder.equal(categoryJoin.get("id"), categoryIds))
                    .toArray(Predicate[]::new);

            return criteriaBuilder.or(predicates);
        };
    }

}
