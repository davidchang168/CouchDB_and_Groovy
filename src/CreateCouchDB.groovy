import groovyx.net.http.RESTClient
import static groovyx.net.http.ContentType.JSON

@Grab(group = 'org.codehaus.groovy.modules.http-builder', module = 'http-builder', version = '[0.5.0,0.5.1)')
def getRESTClient(local = false) {
    RESTClient client;

    if (!local) {
        client = new RESTClient("http://davidchang.couchone.com:5498/")
    } else {
        client = new RESTClient("http://davidchang.couchone.com:5984/")
    }

    def authHash = "root:veryfiner".getBytes('iso-8859-1').encodeBase64()
    client.headers.Authorization = "Basic $authHash"

    return client;
}

def client = getRESTClient(true)

try {
    client.get(path: "parking_tickets")
    println "Deleting DB parking_tickets ..."
    client.delete(path: "parking_tickets")
} catch (Exception e) {
    println "DB parking_tickets does not exist ..."
}

println "Creating new DB parking_tickets ..."
def response = client.put(path: "parking_tickets", requestContentType: JSON, contentType: JSON)
assert response.data.ok: "response from server wasn't ok"

