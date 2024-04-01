import java.util.List;
import java.util.Optional;

public interface IWordFilesSourceFactory {
    List<WordFile> GetWordSources();
    Optional<WordFile> GetExcludeFile();
}
