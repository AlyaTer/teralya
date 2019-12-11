package lab2.parser.writer.writer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lab2.parser.writer.Library;
import lab2.parser.writer.adapter.LocaleDateAdapterJson;
import lab2.parser.writer.Writer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

public class JsonWriter extends Writer<Library> {

    public JsonWriter(String path){
        super(path);
    }

    @Override
    public Optional<Library> write(Library library){
        try {
            return this.serializeJson(library);
        }
        catch (IOException e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<Library> serializeJson(Library library) throws IOException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocaleDateAdapterJson()).create();

        File file = new File(path);
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(gson.toJson(library));
        fileWriter.close();
        return Optional.of(library);
    }
}
