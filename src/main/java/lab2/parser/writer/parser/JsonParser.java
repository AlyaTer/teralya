package lab2.parser.writer.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lab5Test.Library;
import lab2.parser.writer.adapter.LocaleDateAdapterJson;
import lab2.parser.writer.Reader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

public class JsonParser extends Reader<Library> {

    public JsonParser(String path){
        super(path);
    }

    @Override
    public Optional<Library> parse(){
        try {
            return this.deserializeJson(path);
        }
        catch (IOException e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private Optional<Library> deserializeJson(String path) throws IOException{
        File file = new File(path);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocaleDateAdapterJson()).create();
        return Optional.of((gson.fromJson(new FileReader(file),Library.class)));
    }
}
