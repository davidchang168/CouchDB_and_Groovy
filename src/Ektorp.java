import org.codehaus.jackson.JsonNode;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.DocumentNotFoundException;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.ektorp.support.CouchDbDocument;

import java.util.HashMap;
import java.util.Map;

// Need to create a document first before the call "Sofa sofa = db.get(Sofa.class, "ektorp");"
// curl --user root:veryfiner -X PUT http://davidchang.couchone.com:5984/testdb/ektorp -d '{ "color": "red" }'

public class Ektorp {
    public static void main(String[] args) {
        HttpClient httpClient = new StdHttpClient.Builder()
                .host("davidchang.couchone.com")
                .port(5984)
                .username("root")
                .password("veryfiner")
                .build();

        CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);
        CouchDbConnector db = new StdCouchDbConnector("testdb", dbInstance);    // database = testdb

        db.createDatabaseIfNotExists();

        Sofa sofa;   // document = ektorp

        try {
            sofa = db.get(Sofa.class, "ektorp");
        } catch (DocumentNotFoundException e) {
            // **** working with object ****
            //sofa = new Sofa();
            //sofa.setId("ektorp");   // set document name
            //db.create(sofa);    // create document with the key 'ekoorp'

            // http://www.ektorp.org/reference_documentation.html
            // **** document mapped as Map ****
            Map<String, Object> document = new HashMap<String, Object>();
            document.put("_id", "ektorp");
            document.put("color", "green");
            db.create(document);

            System.out.println("New document 'ektorp' created.");
            return;
        }

        if ("red".equals(sofa.getColor())) {
            sofa.setColor("green");
            System.out.println("color set to green.");
        } else {
            sofa.setColor("red");
            System.out.println("color set to red.");
        }
        db.update(sofa);
    }
}

class Sofa extends CouchDbDocument {

    private String color;

    Sofa() {
        color = "green";
    }

    public void setColor(String s) {
        color = s;
    }

    public String getColor() {
        return color;
    }
}
