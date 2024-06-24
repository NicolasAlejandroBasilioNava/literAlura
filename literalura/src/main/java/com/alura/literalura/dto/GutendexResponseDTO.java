package com.alura.literalura.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class GutendexResponseDTO {
    private int count;
    private String next;
    private String previous;
    private List<BookDTO> results;

    // Getters and setters
    @Getter
    @Setter
    public static class BookDTO {
        private int id;
        private String title;
        private List<AuthorDTO> authors;
        private List<TranslatorDTO> translators;
        private List<String> subjects;
        private List<String> bookshelves;
        private List<String> languages;
        private boolean copyright;

        @JsonProperty("media_type")
        private String mediaType;

        private Map<String, String> formats;

        @JsonProperty("download_count")
        private int downloadCount;

        // Getters and setters
    }
    @Getter
    @Setter
    public static class AuthorDTO {
        @JsonProperty("name")
        private String name;

        @JsonProperty("birth_year")
        private Integer birthYear;

        @JsonProperty("death_year")
        private Integer deathYear;

        // Getters and setters
    }

    public static class TranslatorDTO {
        @JsonProperty("name")
        private String name;

        @JsonProperty("birth_year")
        private Integer birthYear;

        @JsonProperty("death_year")
        private Integer deathYear;

        // Getters and setters
    }

    public static class FormatsDTO {
        @JsonProperty("text/html")
        private String textHtml;

        @JsonProperty("application/epub+zip")
        private String applicationEpubZip;

        @JsonProperty("application/x-mobipocket-ebook")
        private String applicationXMobipocketEbook;

        @JsonProperty("application/rdf+xml")
        private String applicationRdfXml;

        @JsonProperty("image/jpeg")
        private String imageJpeg;

        @JsonProperty("text/plain; charset=us-ascii")
        private String textPlainCharsetUsAscii;

        @JsonProperty("application/octet-stream")
        private String applicationOctetStream;

    }
}
