import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

public class WordCountResult {
    private final Hashtable<String, IWord> _words;
    private final Hashtable<String, IWord> _wordsToSkip;
    private int _totalWords;
    private int _totalWordsSkipped;

    public WordCountResult(Hashtable<String, IWord> wordsToSkip) {

        _wordsToSkip = wordsToSkip;
        _words = new Hashtable<>();
    }

    public void Count(Word word) {

        var existingSkippableWordToSkip = _wordsToSkip.getOrDefault(word.getKey(), null);
        if (existingSkippableWordToSkip != null) {
            existingSkippableWordToSkip.incrementCount();
            _totalWordsSkipped++;
            return;
        }

        var existingWord = _words.getOrDefault(word.getKey(), null);
        if (existingWord != null) {
            existingWord.incrementCount();
        } else {
            _words.put(word.getKey(), word);
        }
        _totalWords++;
    }

    public Collection<IWord> getWords() {
        return _words.values()
                .stream()
                .sorted(new WordComparer())
                .toList();
    }

    public int getTotalWords() {
        return _totalWords;
    }

    public int getTotalWordsSkipped() {
        return _totalWordsSkipped;
    }

    public Map<String, List<IWord>> getLetterIndexWords() {
        return _words.values().stream()
                .sorted(new WordComparer())
                .collect(Collectors.groupingBy(IWord::getLetterIndex));
    }
}
