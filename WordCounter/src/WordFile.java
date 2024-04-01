import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class WordFile implements IWordFile {
    private final String _content;
    private final File _file;

    public WordFile(File file) throws IOException {
        _file = file;
        _content = Files.readString(file.toPath())
                .replaceAll("\r\n", " ")
                .replaceAll(",", "")
                .replaceAll("\\.", "");
    }

    @Override
    public String getContent() {
        return _content;
    }

    @Override
    public String getFilename() {
        return _file.getName();
    }

    @Override
    public String getPathAndFilename() {
        return _file.getAbsolutePath();
    }
}
