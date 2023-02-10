package fr.cotedazur.univ.polytech.startingpoint.takenoko;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.logger.LogInfoStats;

import java.io.*;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CSVHandler {
    FileSystem fs = FileSystems.getDefault();
    Path path = fs.getPath("./stats/", "stats.csv");
    File file = new File(path.toString());
    File tempfile = new File("temp_" + file.getName());

    public List<String[]> readFile() throws IOException, CsvException {

        int lineCount = 0;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            while (bufferedReader.readLine() != null) {
                lineCount++;
            }
        } catch(IOException e){
            e.printStackTrace();
        }

        if (lineCount > 0) {
            CSVReader reader = new CSVReader(new FileReader(file));
            List<String[]> lines = reader.readAll();
            lines.remove(0);
            reader.close();
            return lines;
        }

        return Collections.emptyList();
    }

    public void writeNewData(ArrayList<String[]> newData, int numberOfPlayer, int numberOfGame, LogInfoStats logInfoStats) throws IOException, CsvException {
        CSVWriter writer = new CSVWriter(new FileWriter(tempfile, true));

        String[] currentWinPercentage;
        String[] currentMeanScore;
        String[] currentGamePlayed;

        String[] header = new String[] {"", "BotDFS", "BotRB", "BotRandom1", "BotRandom2"};
        String[] firstLine = new String[5];
        String[] secondLine = new String[5];
        String[] thirdLine = new String[5];

        firstLine[0] = "Winrate";
        secondLine[0] = "Score moyen";
        thirdLine[0] = "Nombre de partie";

        List<String[]> lines = readFile();

        if (!lines.isEmpty()) {
            for (int i = 0; i < lines.size(); i++) {
                ArrayList<String> tempLine = new ArrayList<>(Arrays.asList(lines.get(i)));
                tempLine.remove(0);
                lines.set(i, tempLine.toArray(new String[0]));
            }

            currentWinPercentage = lines.get(0);
            currentMeanScore = lines.get(1);
            currentGamePlayed = lines.get(2);

            for (int i = 1; i < numberOfPlayer+1; i++) {
                firstLine[i] = String.valueOf((Double.parseDouble((newData.get(0))[i-1])*numberOfGame + Double.parseDouble(currentWinPercentage[i-1])*Double.parseDouble(currentGamePlayed[i-1]))/(numberOfGame + Double.parseDouble(currentGamePlayed[i-1])));
                secondLine[i] = String.valueOf((Double.parseDouble((newData.get(1))[i-1])*numberOfGame + Double.parseDouble(currentMeanScore[i-1])*Double.parseDouble(currentGamePlayed[i-1]))/(numberOfGame + Double.parseDouble(currentGamePlayed[i-1])));
                thirdLine[i] = String.valueOf((Double.parseDouble(currentGamePlayed[i-1])+numberOfGame));
            }
        }
        else {
            for (int i = 1; i < numberOfPlayer+1; i++) {
                firstLine[i] = (newData.get(0))[i-1];
                secondLine[i] = (newData.get(1))[i-1];
                thirdLine[i] = String.valueOf(numberOfGame);
            }
        }
        writer.writeNext(header);
        writer.writeNext(firstLine);
        writer.writeNext(secondLine);
        writer.writeNext(thirdLine);
        writer.close();
        if (!file.delete()) logInfoStats.addLog("Erreur de suppression du fichier");
        if (!tempfile.renameTo(file)) logInfoStats.addLog("Erreur rename du fichier");
    }

}
