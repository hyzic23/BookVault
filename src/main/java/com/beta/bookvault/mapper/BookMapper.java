package com.beta.bookvault.mapper;

import com.beta.bookvault.dtos.AuthorSummary;
import com.beta.bookvault.dtos.BookRequest;
import com.beta.bookvault.dtos.BookResponse;
import com.beta.bookvault.dtos.TagSummary;
import com.beta.bookvault.entity.Author;
import com.beta.bookvault.entity.Book;
import com.beta.bookvault.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")  // This generate this mapper as a Spring bean so I can @Autowire it
public interface BookMapper {

    @Mapping(target = "author", source = "author") // Map from a Source field to a target field
    @Mapping(target = "tags", source = "tags")  // Map from a Source field to a target field
    BookResponse toResponse(Book book);

    @Mapping(target = "id", ignore = true)  // Meaning don't map this field - I'll set it manually
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "tags", ignore = true)
    Book toEntity(BookRequest request);

    AuthorSummary toSummary(Author author);
    TagSummary toSummary(Tag tag);
}
