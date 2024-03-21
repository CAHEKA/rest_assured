import PojoClass.UserCred;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import java.util.UUID;


public class Spec {

    public  RequestSpecification requestNewUser(){
        UserCred cred = new UserCred(UUID.randomUUID().toString(),"12345");
        BaseTest.userRegister(cred);
        return new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer " + 
                        BaseTest.getUserToken(cred))
                .build();
    }

    public RequestSpecification requestOldUser(){
        return new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer " + 
                        BaseTest.getUserToken(new UserCred("Qwerty", "12345")))
                .build();
    }

    public  RequestSpecification requestUserWithoutToken(){
        return new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer")
                .build();
    }

    public RequestSpecification requestUserWithWrongToken(){
        return new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer " +
                        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJmcmVzaCI6ZmFsc2UsImlhdCI6MTcxMTAyMTI0MiwianRpIjoiNDEyZjhmY2YtM2E1Yy00OWI2LWJjM2ItMWQ5ZjZjZjQzZGY2IiwidHlwZSI6ImFjY2VzcyIsInN1YiI6IjBhMGY4MzJkLT")
                .build();
    }
    
}
