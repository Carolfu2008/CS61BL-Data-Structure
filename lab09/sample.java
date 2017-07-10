  public interface thingThatKnowsWords {
        public String lookUpDefinition(String wordToLookUp);
    }


public class Computer implements thingThatKnowsWords {
        ...
        public String lookUpDefinition(String wordToLookUp) {
            // Do some stuff and returns a defintion found on Google
        }
        ...
    }

public class Dictionary implements thingThatKnowsWords {
        ...
        public String lookUpDefinition(String wordToLookUp) {
            // Flip through some pages and return a definition
        }
        ...
    }

public class ConfusedStudent {
        ...
        static String whatDoesSyzygyMean (thingThatKnowsWords wordKnower){
            return wordKnower.lookUpDefinition("syzygy");
        }
        ...

        public void iwannaknowwhatamean () {
            thingThatKnowsWords computer = new Computer();
            whatDoesSyzygyMean(computer);
        }
    }
