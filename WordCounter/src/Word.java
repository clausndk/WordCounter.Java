import java.util.Locale;

public class Word implements IWord {
    private final String _word;
    private final String _wordLowerCased;
    private int _count;

    public Word(String word) {
        _word = word.trim();
        _wordLowerCased = _word.toLowerCase(Locale.ROOT);
    }

    @Override
    public String toString() {
        return _word;
    }

    @Override
    public int hashCode() {
        return _wordLowerCased.hashCode();
    }

    @Override
    public void incrementCount() {
        _count++;
    }

    @Override
    public int getCount() {
        return _count;
    }

    @Override
    public int compare(IWord other) {
        if (other == null)
            return -1;
        return getKey().compareTo(other.getKey());
    }

    @Override
    public String getLetterIndex() {
        return _wordLowerCased.substring(0, 1);
    }

    public String getKey() {
        return _wordLowerCased;
    }
}
