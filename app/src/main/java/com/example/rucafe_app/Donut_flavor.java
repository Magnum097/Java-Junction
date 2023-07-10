package com.example.rucafe_app;

/**
 * Enum class to list available flavor for a donut
 * All different types of Donuts have same flavor
 * @author Minseok Park, Amogh Sarangdhar
 */
public enum Donut_flavor
{
    PLAIN, GLAZE, CHOCOLATE, STRAWBERRY, BOSTON, APPLE_FRITTER, BANANA, POWDER_SUGAR, MAPLE, ECLAIR, SCARLET_SPECIAL, BUSCH_GOOSE;


    /**
     * Returns flavor of the donut in a "plain" text string.
     * @return flavor in plain text
     */
    public String toPlainString()
    {
        switch(this)
        {
            case GLAZE:
                return "Glaze";
            case PLAIN:
                return "Plain";
            case CHOCOLATE:
                return "Chocolate";
            case STRAWBERRY:
                return "Strawberry";
            case BOSTON:
                return "Boston";
            case APPLE_FRITTER:
                return "Apple Fritter";
            case BANANA:
                return "Banana";
            case POWDER_SUGAR:
                return "Powder sugar";
            case MAPLE:
                return "Maple";
            case ECLAIR:
                return "Eclair";
            case SCARLET_SPECIAL:
                return "Scarlet Special";
            case BUSCH_GOOSE:
                return "Busch Goose";
            default:
                return null;
        }
    }

}
