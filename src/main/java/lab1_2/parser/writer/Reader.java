package lab1_2.parser.writer;

import java.util.Optional;

public abstract class Reader<T> {

    protected final String path;

    protected Reader(String path){
        this.path = path;
    }

    public abstract Optional<T> parse();
}
