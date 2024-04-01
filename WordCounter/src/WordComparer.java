public class WordComparer implements java.util.Comparator<IWord> {
    @Override
    public int compare(IWord o1, IWord o2) {
        return o1.compare(o2);
    }
}
