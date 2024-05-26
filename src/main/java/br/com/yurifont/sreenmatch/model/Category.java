package br.com.yurifont.sreenmatch.model;

public enum Category {
    ACTION("Action"),
    COMEDY("Comedy"),
    ROMANCE("Romance"),
    DRAMA("Drama"),
    CRIME("Crime"),
    THRILLER("Thriller"),
    HORROR("Horror");

    private String omdbCategory;

    Category(String omdbCategory){
        this.omdbCategory = omdbCategory;
    }

    public static Category fromString(String text) {
        for (Category category : Category.values()) {
            if (category.omdbCategory.equalsIgnoreCase(text))
                return category;
        }
        throw new IllegalArgumentException("No categories found for this series!!!");
    }
}
