package br.com.yurifont.sreenmatch.model;

public enum Category {
    ACTION,
    COMEDY,
    ROMANCE,
    DRAMA,
    CRIME,
    THRILLER,
    HORROR;

    private String omdbCategory;

    public static Category fromString(String text) {
        for (Category category : Category.values()) {
            if (category.omdbCategory.equalsIgnoreCase(text))
                return category;
        }
        throw new IllegalArgumentException("No categories found for this series!!!");
    }
}
