package fr.cotedazur.univ.polytech.startingpoint.takenoko.searching;

import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.hexagonebox.enumBoxProperties.Color;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.hexagonebox.enumBoxProperties.Special;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class RetrieveBoxIdWithParametersTest {

    private static RetrieveBoxIdWithParameters retrieveBoxIdWithParameters;

    @BeforeAll
    @Order(1)
    public static void setUpGeneral() {
        retrieveBoxIdWithParameters = new RetrieveBoxIdWithParameters();
        retrieveBoxIdWithParameters.setBoxColor(1009901,Color.VERT);
        retrieveBoxIdWithParameters.setBoxIsIrrigated(1009901,true);
        retrieveBoxIdWithParameters.setBoxHeight(1009901,3);
        retrieveBoxIdWithParameters.setBoxSpeciality(1009901,Special.CLASSIQUE);

        retrieveBoxIdWithParameters.setBoxColor(1009802,Color.JAUNE);
        retrieveBoxIdWithParameters.setBoxIsIrrigated(1009802,true);
        retrieveBoxIdWithParameters.setBoxHeight(1009802,1);
        retrieveBoxIdWithParameters.setBoxSpeciality(1009802,Special.CLASSIQUE);

        retrieveBoxIdWithParameters.setBoxColor(1000199,Color.VERT);
        retrieveBoxIdWithParameters.setBoxIsIrrigated(1000199,false);
        retrieveBoxIdWithParameters.setBoxHeight(1000199,4);
        retrieveBoxIdWithParameters.setBoxSpeciality(1000199,Special.ENGRAIS);

        retrieveBoxIdWithParameters.setBoxColor(1000298,Color.ROUGE);
        retrieveBoxIdWithParameters.setBoxIsIrrigated(1000298,false);
        retrieveBoxIdWithParameters.setBoxHeight(1000298,3);
        retrieveBoxIdWithParameters.setBoxSpeciality(1000298,Special.ENGRAIS);

        retrieveBoxIdWithParameters.setBoxColor(1000397,Color.VERT);
        retrieveBoxIdWithParameters.setBoxIsIrrigated(1000397,true);
        retrieveBoxIdWithParameters.setBoxHeight(1000397,2);
        retrieveBoxIdWithParameters.setBoxSpeciality(1000397,Special.CLASSIQUE);

        retrieveBoxIdWithParameters.setBoxColor(1009703,Color.JAUNE);
        retrieveBoxIdWithParameters.setBoxIsIrrigated(1009703,true);
        retrieveBoxIdWithParameters.setBoxHeight(1009703,4);
        retrieveBoxIdWithParameters.setBoxSpeciality(1009703,Special.PROTEGER);
    }

    private static Stream<Arguments> provideSetBoxHeight_AndDelete(){
        return Stream.of(
                Arguments.of(1009901, 3,0),
                Arguments.of(1009802, 1,0)
        );
    }

    private static Stream<Arguments> provideSetBoxColor(){
        return Stream.of(
                Arguments.of(1009901, Color.VERT,0,3),
                Arguments.of(1009802, Color.JAUNE,0,2),
                Arguments.of(1000199, Color.VERT,1,3)
        );
    }

    private static Stream<Arguments> provideSetBoxIrrigated(){
        return Stream.of(
                Arguments.of(1009901, true,0,4),
                Arguments.of(1009802, true,1,4),
                Arguments.of(1000199, false,0,2)
        );
    }

    private static Stream<Arguments> provideSetBoxSpeciality(){
        return Stream.of(
                Arguments.of(1009901, Special.CLASSIQUE,0,3),
                Arguments.of(1009802, Special.CLASSIQUE,1,3),
                Arguments.of(1000199, Special.ENGRAIS,0,2)
        );
    }

    private static Stream<Arguments> provideGetAllIdThatCompleteCondition(){
        return Stream.of(
                Arguments.of(new ArrayList<>(Arrays.asList(1009901, 1009802, 1000397, 1009703, 1000199, 1000298)), 6,
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty()),

                Arguments.of(new ArrayList<>(Arrays.asList(1000298)), 1,
                        Optional.of(new ArrayList<>(Arrays.asList(Color.ROUGE))),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty()),
                Arguments.of(new ArrayList<>(Arrays.asList(1000199, 1000298)), 2,
                        Optional.empty(),
                        Optional.of(new ArrayList<>(Arrays.asList(false))),
                        Optional.empty(),
                        Optional.empty()),
                Arguments.of(new ArrayList<>(Arrays.asList(1009802)), 1,
                        Optional.empty(),
                        Optional.empty(),
                        Optional.of(new ArrayList<>(Arrays.asList(1))),
                        Optional.empty()),
                Arguments.of(new ArrayList<>(Arrays.asList(1009703)), 1,
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.of(new ArrayList<>(Arrays.asList(Special.PROTEGER)))),

                Arguments.of(new ArrayList<>(Arrays.asList(1009901,1000397)), 2,
                        Optional.of(new ArrayList<>(Arrays.asList(Color.VERT))),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.of(new ArrayList<>(Arrays.asList(Special.CLASSIQUE)))),
                Arguments.of(new ArrayList<>(Arrays.asList(1009901)), 1,
                        Optional.empty(),
                        Optional.of(new ArrayList<>(Arrays.asList(true))),
                        Optional.of(new ArrayList<>(Arrays.asList(3))),
                        Optional.empty()),
                Arguments.of(new ArrayList<>(Arrays.asList(1000199)), 1,
                        Optional.of(new ArrayList<>(Arrays.asList(Color.VERT))),
                        Optional.empty(),
                        Optional.of(new ArrayList<>(Arrays.asList(4))),
                        Optional.empty()),
                Arguments.of(new ArrayList<>(Arrays.asList(1000199,1000298)), 2,
                        Optional.empty(),
                        Optional.of(new ArrayList<>(Arrays.asList(false,true))),
                        Optional.empty(),
                        Optional.of(new ArrayList<>(Arrays.asList(Special.ENGRAIS)))),

                Arguments.of(new ArrayList<>(Arrays.asList(1009901,1000397)), 2,
                        Optional.of(new ArrayList<>(Arrays.asList(Color.VERT))),
                        Optional.of(new ArrayList<>(Arrays.asList(true))),
                        Optional.of(new ArrayList<>(Arrays.asList(1,2,3))),
                        Optional.empty()),
                Arguments.of(new ArrayList<>(Arrays.asList(1000397, 1009802, 1009703)), 3,
                        Optional.of(new ArrayList<>(Arrays.asList(Color.VERT, Color.ROUGE, Color.JAUNE))),
                        Optional.empty(),
                        Optional.of(new ArrayList<>(Arrays.asList(1,2,4))),
                        Optional.of(new ArrayList<>(Arrays.asList(Special.CLASSIQUE,Special.PROTEGER)))),

                Arguments.of(new ArrayList<>(Arrays.asList(1009901,1000397)), 2,
                        Optional.of(new ArrayList<>(Arrays.asList(Color.VERT))),
                        Optional.of(new ArrayList<>(Arrays.asList(true,false))),
                        Optional.of(new ArrayList<>(Arrays.asList(1,2,3))),
                        Optional.of(new ArrayList<>(Arrays.asList(Special.CLASSIQUE)))),

                Arguments.of(new ArrayList<>(Arrays.asList()), 0,
                        Optional.of(new ArrayList<>(Arrays.asList(Color.ROUGE))),
                        Optional.of(new ArrayList<>(Arrays.asList(false))),
                        Optional.of(new ArrayList<>(Arrays.asList(1))),
                        Optional.of(new ArrayList<>(Arrays.asList(Special.ENGRAIS)))));
    }


    @ParameterizedTest
    @MethodSource("provideSetBoxColor")
    void testSetBoxColor(int id, Color color, int indice, int size) {
        assertEquals(id,retrieveBoxIdWithParameters.getAllIdThatCompleteCondition(
                Optional.of(new ArrayList<>(Arrays.asList(color))),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()).get(indice));
        assertEquals(size,retrieveBoxIdWithParameters.getAllIdThatCompleteCondition(
                Optional.of(new ArrayList<>(Arrays.asList(color))),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()).size());
    }

    @ParameterizedTest
    @MethodSource("provideSetBoxIrrigated")
    void testSetBoxIrrigated(int id, boolean isIrrigated, int indice, int size) {
        assertEquals(id,retrieveBoxIdWithParameters.getAllIdThatCompleteCondition(
                Optional.empty(),
                Optional.of(new ArrayList<>(Arrays.asList(isIrrigated))),
                Optional.empty(),
                Optional.empty()).get(indice));
        assertEquals(size,retrieveBoxIdWithParameters.getAllIdThatCompleteCondition(
                Optional.empty(),
                Optional.of(new ArrayList<>(Arrays.asList(isIrrigated))),
                Optional.empty(),
                Optional.empty()).size());
    }

    @ParameterizedTest
    @MethodSource("provideSetBoxHeight_AndDelete")
    void testSetBoxHeight(int id, int newHeight, int oldHeight) {
        assertEquals(id,retrieveBoxIdWithParameters.getAllIdThatCompleteCondition(
                Optional.empty(),
                Optional.empty(),
                Optional.of(new ArrayList<>(Arrays.asList(newHeight))),
                Optional.empty()).get(0));
    }

    @ParameterizedTest
    @MethodSource("provideSetBoxHeight_AndDelete")
    void testSetBoxHeightDelete(int id, int newHeight, int oldHeight) {
        assertTrue(retrieveBoxIdWithParameters.getAllIdThatCompleteCondition(
                Optional.empty(),
                Optional.empty(),
                Optional.of(new ArrayList<>(Arrays.asList(oldHeight))),
                Optional.empty()).isEmpty());
    }

    @ParameterizedTest
    @MethodSource("provideSetBoxSpeciality")
    void testSetBoxSpeciality(int id, Special speciality, int indice, int size) {
        assertEquals(id,retrieveBoxIdWithParameters.getAllIdThatCompleteCondition(
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.of(new ArrayList<>(Arrays.asList(speciality)))).get(indice));
        assertEquals(size,retrieveBoxIdWithParameters.getAllIdThatCompleteCondition(
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.of(new ArrayList<>(Arrays.asList(speciality)))).size());
    }

    @ParameterizedTest
    @MethodSource("provideGetAllIdThatCompleteCondition")
    void testGetAllIdThatCompleteCondition(ArrayList<Integer> listIdReturned,
                                           int size,
                                           Optional<ArrayList<Color>> color,
                                           Optional<ArrayList<Boolean>> isIrrigated,
                                           Optional<ArrayList<Integer>> height,
                                           Optional<ArrayList<Special>> speciality) {
        System.out.println(retrieveBoxIdWithParameters.getBoxColor());
        assertEquals(listIdReturned,retrieveBoxIdWithParameters.getAllIdThatCompleteCondition(
                color,
                isIrrigated,
                height,
                speciality));
        assertEquals(size,retrieveBoxIdWithParameters.getAllIdThatCompleteCondition(
                color,
                isIrrigated,
                height,
                speciality).size());
    }
}