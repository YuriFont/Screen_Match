package br.com.yurifont.sreenmatch.model;

public enum Category {
    ACTION("Action", "Ação"),
    COMEDY("Comedy", "Comédia"),
    ROMANCE("Romance", "Romance"),
    DRAMA("Drama", "Drama"),
    CRIME("Crime", "Crime"),
    THRILLER("Thriller", "Suspense"),
    HORROR("Horror", "Terror");

    private String omdbCategory;
    private String categoryPtBr;

    Category(String omdbCategory, String categoryPtBr){

        this.omdbCategory = omdbCategory;
        this.categoryPtBr = categoryPtBr;
    }

    public static Category fromString(String text) {
        for (Category category : Category.values()) {
            if (category.omdbCategory.equalsIgnoreCase(text))
                return category;
        }
        throw new IllegalArgumentException("No categories found for this series!!!");
    }

    public static Category fromStringPtBr(String text) {
        for (Category category : Category.values()) {
            if (category.categoryPtBr.equalsIgnoreCase(text))
                return category;
        }
        throw new IllegalArgumentException("No categories found for this series!!!");
    }
}
