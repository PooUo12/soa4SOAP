import com.example.springboot.POJO.HireWorkerDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.net.ssl.SSLContext;

public class HrTest {

    ObjectMapper mapper = new ObjectMapper();


    @BeforeAll
    static void init(){
        try {
            SSLContext sslcontext = SSLContexts.custom()
                    .loadTrustMaterial(null, new TrustSelfSignedStrategy())
                    .build();

            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            CloseableHttpClient httpclient = HttpClients.custom()
                    .setSSLSocketFactory(sslsf)
                    .build();
            Unirest.setHttpClient(httpclient);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private HttpResponse<String> sendFireRequest(String url, Object worker_id) throws UnirestException {
        return Unirest.patch(url + worker_id)
                .header("Content-Type","application/json")
                .asString();
    }

    private HttpResponse<String> sendHireRequest(String url, String salary, String start_date, HireWorkerDTO worker) throws UnirestException, JsonProcessingException {
        return Unirest.post(url + salary + "/" + start_date)
                .header("Content-Type","application/json")
                .body(mapper.writeValueAsString(worker))
                .asString();
    }


    @Test
    public void testFire() throws UnirestException {
            int worker_id_1 = -1;
            int worker_id_2 = 2;
            int worker_id_3 = 406;
            int worker_id_4 = 1000;
            int worker_id_5 = 407;
            String worker_id_6 = null;
            String worker_id_7 = "Smth went wrong";
            String url = "https://127.0.0.1:18990/api/hr/fire/";
            String http_url = "http://127.0.0.1:18990/api/hr/fire/";

            var test_1 = sendFireRequest(url, worker_id_1);
            Assertions.assertEquals("Worker doesn't exist", test_1.getBody());
            Assertions.assertEquals(422, test_1.getStatus());

            var test_2 = sendFireRequest(url, worker_id_2);
            Assertions.assertEquals("Worker doesn't exist", test_2.getBody());
            Assertions.assertEquals(422, test_2.getStatus());

            var test_4 = sendFireRequest(url, worker_id_4);
            Assertions.assertEquals("Worker doesn't exist", test_4.getBody());
            Assertions.assertEquals(422, test_4.getStatus());

            var test_6 = sendFireRequest(url, worker_id_6);
            Assertions.assertEquals("Id should be integer", test_6.getBody());
            Assertions.assertEquals(422, test_6.getStatus());

            var test_7 = sendFireRequest(url, worker_id_7);
            Assertions.assertEquals("Id should be integer", test_7.getBody());
            Assertions.assertEquals(422, test_7.getStatus());

            var test_8 = sendFireRequest(http_url, worker_id_3);
            Assertions.assertTrue(test_8.getBody().contains("This combination of host and port requires TLS"));
            Assertions.assertEquals(400, test_8.getStatus());

            var test_3_1 = sendFireRequest(url, worker_id_3);
            Assertions.assertEquals("Worker was fired :(", test_3_1.getBody());
            Assertions.assertEquals(200, test_3_1.getStatus());


            var test_3_2 = sendFireRequest(url, worker_id_3);
            Assertions.assertEquals("Worker is already fired", test_3_2.getBody());
            Assertions.assertEquals(422, test_3_2.getStatus());

            var test_5 = sendFireRequest(url, worker_id_5);
            Assertions.assertEquals("Worker is already fired", test_5.getBody());
            Assertions.assertEquals(422, test_5.getStatus());

    }


    private HireWorkerDTO createDTO(String worker) throws JsonProcessingException {
        return mapper.readValue(worker, HireWorkerDTO.class);

    }

    @Test
    public void testHire() throws UnirestException, JsonProcessingException {

        String url = "https://127.0.0.1:18990/api/hr/hire/";
        String http_url = "http://127.0.0.1:18990/api/hr/hire/";

        String good_answer = """
                    "creationDate": "2024-12-09",
                    "salary": 100,
                    "startDate": "2024-12-01T17:55:50.317Z[UTC]",
                    "endDate": null,
                    "status": "HIRED",
                """;

        String worker_dto_1 = """
                {
                  "birthday": "2024-12-01T17:55:50.317Z",
                  "name": "Vitaliy",
                  "eyeColor": "GReen",
                  "hairColor": "BLACK",
                  "nationality": "francE",
                  "location": {
                    "x": 11,
                    "y": 11,
                    "z": 11.11,
                    "name": "string"
                  },
                  "coordinates": {
                    "x" : -110,
                    "y": 10
                  }
                }
                """;
        int salary_1 = 100;
        String salary_2 = "Ooops smth went wrong";
        String salary_3 = null;

        String date_1 = "2024-12-01T17:55:50.317Z";
        String date_2 = "2024-";
        String date_3 = null;

        HireWorkerDTO worker_2 = null;
        HireWorkerDTO worker_1 = createDTO(worker_dto_1);
        HireWorkerDTO worker_3 = createDTO(worker_dto_1);
        worker_3.setNationality("Russia");


        var test_1 = sendHireRequest(url, String.valueOf(salary_1), date_1, worker_1);
        Assertions.assertTrue(test_1.getBody().contains(good_answer.replace("\n", "").replace(" ", "")));
        Assertions.assertEquals(200, test_1.getStatus());

        var test_2 = sendHireRequest(http_url, String.valueOf(salary_1), date_1, worker_1);
        Assertions.assertTrue(test_2.getBody().contains("This combination of host and port requires TLS"));
        Assertions.assertEquals(400, test_2.getStatus());

        var test_3 = sendHireRequest(url, salary_2, date_1, worker_1);
        Assertions.assertEquals("Salary should be integer", test_3.getBody());
        Assertions.assertEquals(422, test_3.getStatus());

        var test_4 = sendHireRequest(url, salary_3, date_1, worker_1);
        Assertions.assertEquals("Salary should be integer", test_4.getBody());
        Assertions.assertEquals(422, test_4.getStatus());

        var test_5 = sendHireRequest(url, String.valueOf(salary_1), date_2, worker_1);
        Assertions.assertEquals("[\"Illegal Start-date format. It should be yyyy-MM-dd'T'HH:mm:ss.S'Z'\"]", test_5.getBody());
        Assertions.assertEquals(422, test_5.getStatus());

        var test_6 = sendHireRequest(url, String.valueOf(salary_1), date_3, worker_1);
        Assertions.assertEquals("[\"Illegal Start-date format. It should be yyyy-MM-dd'T'HH:mm:ss.S'Z'\"]", test_6.getBody());
        Assertions.assertEquals(422, test_6.getStatus());

        var test_7 = sendHireRequest(url, String.valueOf(salary_1), date_1, worker_2);
        Assertions.assertTrue(test_7.getBody().contains("Bad Request"));
        Assertions.assertEquals(400, test_7.getStatus());

        var test_8 = sendHireRequest(url, String.valueOf(salary_1), date_1, worker_3);
        Assertions.assertEquals("[\"Illegal nationality type\"]", test_8.getBody());
        Assertions.assertEquals(422, test_8.getStatus());

    }

}
