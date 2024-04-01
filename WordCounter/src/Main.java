import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello world!");
        System.out.println("Welcome to a Word counter.");
        System.out.println("A source directory must be supplied, where '.txt' files must be located. These test files");
        System.out.println("must include minimum 200 words of content, which this program will count. Good luck.");

        if (args == null || args.length == 0) {
            System.out.println("No source directory supplied, exiting...");
            return;
        }

        var sourceDirectory = Paths.get(args[0]);
        if (!Files.isDirectory(sourceDirectory)) {
            System.out.println(STR."Source directory '\{sourceDirectory}' does not exist, exiting...");
            return;
        }
        System.out.println(STR."Source directory supplied: '\{sourceDirectory}'.");

        var outputFolder = Paths.get(sourceDirectory.toString(), "Output");
        if (Files.isDirectory(outputFolder)) {
            System.out.println("Cleaning output folder...");
            deleteDirectoryRecursively(outputFolder);
            System.out.println("Output folder cleaned.");
        }
        Files.createDirectory(outputFolder);
        System.out.println("Outputfolder created.");
        System.out.println();

        System.out.println("Counting words...");

        var wordFiles = listFilesUsingFilesList(sourceDirectory);
        var wordFileSourcesFactory = new WordFilesSourceFactory(wordFiles);
        var wordCounter = new WordCounter(wordFileSourcesFactory);
        var wordCountResult = wordCounter.countWords();
        System.out.println("Word count is:");
        for (var word : wordCountResult.getWords()) {
            System.out.println(STR."  '\{word}': \{word.getCount()}");
        }
        System.out.println(STR."Total words: \{wordCountResult.getTotalWords()}");
        System.out.println(STR."Total words skipped: \{wordCountResult.getTotalWordsSkipped()}");

        var excludedCountResultFile = Paths.get(outputFolder.toString(), "ExcludedCount.txt");
        System.out.println(STR."Writing number of excluded words to file: \{excludedCountResultFile}");
        Files.writeString(excludedCountResultFile, STR."Excluded word count: \{wordCountResult.getTotalWordsSkipped()}");

        System.out.println();
        System.out.println("Writing letter index files to output folder...");
        for (Map.Entry<String, List<IWord>> letterIndexWord : wordCountResult.getLetterIndexWords().entrySet())
        {
            var letterIndexWordFile = Paths.get(outputFolder.toString(), STR."File_\{letterIndexWord.getKey()}.txt");
            var letterIndexWordContent = String.join("\r\n", letterIndexWord.getValue().stream().map(w -> STR."\{w} \{w.getCount()}").toList());
            Files.writeString(letterIndexWordFile, letterIndexWordContent);
            System.out.println(STR."  Wrote file \{letterIndexWordFile}");
        }
        System.out.println("Done writing letter files.");

        System.out.println();
        System.out.println("Have a nice day!");
    }

    private static void deleteDirectoryRecursively(Path folder) throws IOException {
        Files.walk(folder, FileVisitOption.FOLLOW_LINKS)
                .sorted(Comparator.reverseOrder())
                .forEach(path -> {
                    try {
                        Files.delete(path);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    public static List<File> listFilesUsingFilesList(Path dir) throws IOException {
        return Arrays.stream(dir.toFile().listFiles())
                .filter(f -> !f.isDirectory() && f.getName().endsWith("txt"))
                .toList();
    }
}