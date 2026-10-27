package com.profile.svc.shared.mapper;

import com.profile.svc.admin.entities.BookEntity;
import com.profile.svc.shared.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {
    @Mapping(target = "coverImage", ignore = true)
    @Mapping(target = "pdf", ignore = true)
    Book toBook(BookEntity book);
}
