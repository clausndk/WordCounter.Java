import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class WordFilesSourceFactory implements IWordFilesSourceFactory {
    private final String ExcludeFileName = "exclude.txt";
    private final List<File> _wordFiles;

    public WordFilesSourceFactory(List<File> wordFiles) {
        _wordFiles = wordFiles;
    }

    @Override
    public List<WordFile> GetWordSources() {
        return _wordFiles.stream()
                .filter(f -> !f.getName().equalsIgnoreCase(ExcludeFileName))
                .map(f -> {
                    try {
                        return new WordFile(f);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
    }

    @Override
    public Optional<WordFile> GetExcludeFile() {
        return _wordFiles.stream()
                .filter(f -> f.getName().equalsIgnoreCase(ExcludeFileName))
                .map(f -> {
                    try {
                        return new WordFile(f);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .findFirst();
    }
}
