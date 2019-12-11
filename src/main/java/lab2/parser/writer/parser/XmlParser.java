package lab2.parser.writer.parser;

import lab2.parser.writer.Library;
import lab2.parser.writer.Reader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.Optional;

public class XmlParser extends Reader<Library> {

    public XmlParser(String path){
        super(path);
    }


    @Override

    public Optional<Library> parse(){
        try {
            return this.deserializeXml(path);
        } catch (JAXBException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private Optional<Library> deserializeXml(String path) throws  JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Library.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Library company = (Library) unmarshaller.unmarshal(new File(path));
        return Optional.of(company);

    }
}
