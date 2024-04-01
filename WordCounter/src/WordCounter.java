import java.util.Arrays;
import java.util.Hashtable;

public class WordCounter {
    private final IWordFilesSourceFactory _wordFileSourcesFactory;

    public WordCounter(IWordFilesSourceFactory _wordFileSourcesFactory) {
        this._wordFileSourcesFactory = _wordFileSourcesFactory;
    }

    public WordCountResult countWords() {
        var wordSources = _wordFileSourcesFactory.GetWordSources();
        var excludeFile = _wordFileSourcesFactory.GetExcludeFile();
        var wordsToSkip = new Hashtable<String, IWord>();
        if (excludeFile.isPresent()) {
            Arrays.stream(excludeFile.get().getContent()
                            .split(" "))
                    .map(Word::new)
                    .forEach(w -> wordsToSkip.put(w.getKey(), w));
        }

        var result = new WordCountResult(wordsToSkip);
        for (var wordSource : wordSources) {
            var words = wordSource.getContent().split(" ");
            for (var word : words) {
                if (word.isBlank() || word.isEmpty())
                    continue;
                var w = new Word(word);
                result.Count(w);
            }
        }

        return result;
    }
}

