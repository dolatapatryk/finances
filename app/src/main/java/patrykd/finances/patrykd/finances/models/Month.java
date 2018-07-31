package patrykd.finances.patrykd.finances.models;

public enum Month {
    JANUARY("Styczeń"),
    FEBRUARY("Luty"),
    MARCH("Marzec"),
    APRIL("Kwiecień"),
    MAY("Maj"),
    JUNE("Czerwiec"),
    JULY("Lipiec"),
    AUGUST("Sierpień"),
    SEPTEMBER("Wrzesień"),
    OCTOBER("Październik"),
    NOVEMBER("Listopad"),
    DECEMBER("Grudzień");

    private final String name;

    Month(String name){
        this.name = name;
    }

    public String getName(){return name;}

}
