
package extractkubrik;

/**
 *
 * @author Jenia
 */
import com.itextpdf.text.DocumentException;
import java.io.IOException;
import java.sql.SQLException;



public class ExtractKubrik {
    public static void main(String[] args) throws IOException, DocumentException, SQLException {
        MultimediaExtractor extractor = new MultimediaExtractor();
        extractor.extractAttachments("/home/reshet/Downloads/db2_2_2010.pdf");
    }
}
