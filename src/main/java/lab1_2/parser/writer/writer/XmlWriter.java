package lab1_2.parser.writer.writer;

import lab1_2.parser.writer.Library;
import lab1_2.parser.writer.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.Optional;

public class XmlWriter extends Writer<Library> {

    public XmlWriter(String path){
        super(path);
    }

    @Override
    public Optional<Library> write(Library library){
        try {
            return this.serializeXml(library);
        }catch (JAXBException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<Library> serializeXml(Library library) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Library.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
        marshaller.marshal(library, new File(path));
        return Optional.of(library);
    }
}
