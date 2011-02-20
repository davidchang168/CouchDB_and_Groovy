import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.annotate.JsonClass;
import org.codehaus.jackson.node.ObjectNode;
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

public class Ektorp2 {
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

        try {
            JsonNode doc = db.get(JsonNode.class, "ektorp");
            JsonNode color = doc.findPath("color");
            String c = color.getTextValue();

            System.out.println("Found color = " + c);
        } catch (DocumentNotFoundException e) {
            System.out.println("Document not found.");
        }
    }
}

class Sofa2 extends CouchDbDocument {

    private String color;

    public void setColor(String s) {
        color = s;
    }

    public String getColor() {
        return color;
    }
}
