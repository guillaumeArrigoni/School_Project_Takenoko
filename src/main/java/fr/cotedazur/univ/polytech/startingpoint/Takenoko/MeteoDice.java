package fr.cotedazur.univ.polytech.startingpoint.Takenoko;

public class MeteoDice {
    private final int NBSIDE = 1;
    public enum Meteo {
        VENT(1),
        PLUIE(2),
        ORAGE(3),
        SOLEIL(4),
        NUAGES(5),
        HASARD(6),

        NO_METEO(7);
        private final int id;
        Meteo(int id) {
            this.id = id;
        }
    }
    public MeteoDice(){
    }

    public Meteo roll() {
        int i =  (int) (Math.random() * NBSIDE) + 1;
        return switch (i) {
            case 1 -> Meteo.VENT;
            case 2 -> Meteo.PLUIE;
            case 3 -> Meteo.ORAGE;
            case 4 -> Meteo.SOLEIL;
            case 5 -> Meteo.NUAGES;
            case 6 -> Meteo.HASARD;
            default -> null;
        };
    }
}
