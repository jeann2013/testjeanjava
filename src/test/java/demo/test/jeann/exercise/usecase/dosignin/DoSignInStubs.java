package demo.test.jeann.exercise.usecase.dosignin;

import demo.test.jeann.exercise.usecases.dosignin.models.DoSignInRequest;
import demo.test.jeann.exercise.usecases.dosignin.models.DoSignInResponse;
import demo.test.jeann.exercise.utilities.formats.LoadStubs;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class DoSignInStubs {
  public DoSignInStubs() {}

  public static DoSignInRequest getDoSignInRequest() throws IOException {
    return new ObjectMapper()
        .readValue(
            LoadStubs.getStubs("sign-in-request.json"), new TypeReference<DoSignInRequest>() {});
  }

  public static DoSignInResponse getDoSignInResponse() throws IOException {
    return new ObjectMapper()
        .readValue(
            LoadStubs.getStubs("sign-in-response.json"), new TypeReference<DoSignInResponse>() {});
  }
}
