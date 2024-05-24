package readability;

public class TextAssessor {

    // these brackets are based on the ARI score; however assignment requirements require separating
    // the assignment has me assigning a score two marks higher than matches the chart....idk
    final String[] AGE_BRACKETS = {"5", "6", "7", "8", "9", "10", "11", "12", "13",
    "14", "15", "16", "17", "18", "19", "20", "21", "22"};

    // this header is contained in every test and must not be factored
    final String HEADER = "The text is:";

    // full string without the pointless header
    String textHeaderRemoved;

    // this variable is used for sentence count
    int punctuationCount;

    // this variable is either 1 or zero; 1 if there is no punctuation at the end.
    int periodCheck = 0;

    // these are all the variables we care about creating to calculate the correct scores.
    double ARI;

    double fKincaid;

    double gobbledyGook;

    double colemanLiau;

    double characterLength;

    double wordCount;

    int syllables;

    int polySyllables;

    // my getters and setters in a bad order
    public double getSentences() {
        return ((double) getPunctuationCount() + (double) periodCheck);
    }

    public double getfKincaid() {
        return fKincaid;
    }

    public void setfKincaid(double fKincaid) {
        this.fKincaid = fKincaid;
    }

    public double getGobbledyGook() {
        return gobbledyGook;
    }

    public void setGobbledyGook(double gobbledyGook) {
        this.gobbledyGook = gobbledyGook;
    }

    public double getColemanLiau() {
        return colemanLiau;
    }

    public void setColemanLiau(double colemanLiau) {
        this.colemanLiau = colemanLiau;
    }

    public int getPolySyllables() {
        return polySyllables;
    }

    public int getSyllables() {
        return syllables;
    }

    public int getPunctuationCount() {
        return punctuationCount;
    }

    public void setPunctuationCount(int punctuationCount) {
        this.punctuationCount = punctuationCount;
    }

    public double getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    public double getCharacterLength() {
        return characterLength;
    }

    public void setCharacterLength(int characterLength) {
        this.characterLength = characterLength;
    }

    public double getARI() {
        return ARI;
    }

    public void setARI(double ARI) {
        this.ARI = ARI;
    }

    // the meat, where we analyze all the text
    void buildModel(String input) {

        // This line of code strips the phrase "The text is:" from the front of file
        textHeaderRemoved = input.replace(HEADER, "").trim();

        // With the header removed, we can count characters minus space, tabs and new lines
        setCharacterLength(textHeaderRemoved.replaceAll("[\\s\\t\\n]", "").length());

        // analyzes text and separates into array split by spaces
        setWordCount(textHeaderRemoved.split(" ").length);

        // analyzes punctuation marks in the text
        setPunctuationCount(textHeaderRemoved.replaceAll("[^.!?]+", " ")
                .trim().split(" ").length);

        // this if statement add 1 to the total punctuation if punctuation is left out on the end
        if (!String.valueOf(input.toCharArray()[input.length() - 1]).matches("[.!?]")) {
            periodCheck = 1;
        }

        countSyllables(textHeaderRemoved);

        setARI(4.71 * (getCharacterLength() / getWordCount()) + 0.5 * (getWordCount() / getSentences()) - 21.43);

        setfKincaid(.39 * (getWordCount() / getSentences()) + 11.8 * (getSyllables() / getWordCount()) - 15.59);

        setGobbledyGook(1.043 * Math.sqrt(polySyllables * (30 / getSentences())) + 3.1291);

        setColemanLiau(.0588 * (getCharacterLength() / getWordCount() * 100) - .296 *
                (getSentences() / getWordCount() * 100) - 15.8);

    }

    // macro-level score average
    String analyzeScore(double dbl) {
        return AGE_BRACKETS[(int) Math.round(dbl) + 1];
    }

    // this calculates an average based on all 4 scores
    String averageScores () {
        double d = ((
        Double.parseDouble(analyzeScore(getARI())) +
        Double.parseDouble(analyzeScore(getGobbledyGook())) +
        Double.parseDouble(analyzeScore(getfKincaid())) +
        Double.parseDouble(analyzeScore(getColemanLiau()))) / 4);
        return "%.2f".formatted(d);
    }

    // count the syllables (almost) in each word and assigns to syllables
    void countSyllables (String input) {
        // this strips all punctuation and divides each word into array; we only want the words
        String[] eachWord = input.replaceAll("[:;\",.!?]+", "").split(" ");

        // using this variable to count the number of syllables in each word
        int count;

        // pulls each word out of eachWord array
        for (String i : eachWord) {
            // always start a new count at zero since we will add new count to total syllabus
            count = 0;

            // counts vowels unless there are two in a row
            // does not look at last character
            for (int j = 0; j < i.length() - 1; j++) {
                if (String.valueOf(i.charAt(j)).matches("[aeiouyAEIOUY]")) {
                    if (!String.valueOf(i.charAt(j + 1)).matches("[aeiouyAEIOUY]")) {
                        count++;
                    }
                }
            }
            // as long as last character is not 'e,' we count it
            if (String.valueOf(i.charAt(i.length() - 1)).matches("[aiouyAIOUY]")) {
                count++;
            }
            // if we didn't count anything (I.E. "the"), then we will need to assign a syllabus of 1
            if (count == 0) {
                count = 1;
            }
            if (count > 2) {
                polySyllables += 1;
            }
            syllables += count;
        }
    }

    @Override
    public String toString() {
        return """
                Words: %.0f
                Sentences: %s
                Characters: %.0f
                Syllables: %d
                Polysyllables: %d
                """.formatted(getWordCount(), getPunctuationCount() + periodCheck, getCharacterLength(),
         getSyllables(), getPolySyllables());
    }

    // nice tree that allows the user switch score they want to see or all
    void scores(String input) {
        switch (input) {
            case "ARI" -> System.out.printf("Automated Readability Index: %.2f (about %s-year-olds).",
                    getARI(), analyzeScore(getARI()));
            case "FK" -> System.out.printf("Flesch–Kincaid readability tests: %.2f (about %s-year-olds).",
                    getfKincaid(), analyzeScore(getfKincaid()));
            case "SMOG" -> System.out.printf("Simple Measure of Gobbledygook: %.2f (about %s-year-olds).",
                    getGobbledyGook(), analyzeScore(getGobbledyGook()));
            case "CL" -> System.out.printf("Coleman–Liau index: %.2f (about %s-year-olds).",
                    getColemanLiau(), analyzeScore(getColemanLiau()));
            case "all" -> System.out.printf("""
                            
                            Automated Readability Index: %.2f (about %s-year-olds).
                            Flesch–Kincaid readability tests: %.2f (about %s-year-olds).
                            Simple Measure of Gobbledygook: %.2f (about %s-year-olds).
                            Coleman–Liau index: %.2f (about %s-year-olds).
                            
                            """,
                    getARI(), analyzeScore(getARI()),
                    getfKincaid(), analyzeScore(getfKincaid()),
                    getGobbledyGook(), analyzeScore(getGobbledyGook()),
                    getColemanLiau(), analyzeScore(getColemanLiau()));
        }
        // always prints the average
        System.out.printf("This text should be understood in average by %s-year-olds.", averageScores());
    }
}
