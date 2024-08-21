package demo.test.jeann.exercise.usecase.finduserbyemail;

import demo.test.jeann.exercise.usecases.finduserbyemail.models.FindUserByEmailRequest;
import demo.test.jeann.exercise.usecases.finduserbyemail.models.FindUserByEmailResponse;
import demo.test.jeann.exercise.utilities.formats.LoadStubs;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class FindUserByEmailStubs {
  public FindUserByEmailStubs() {}

  public static FindUserByEmailResponse getFindUserByEmailResponse() throws IOException {
    return new ObjectMapper()
        .readValue(
            LoadStubs.getStubs("find-user-by-email-response.json"),
            new TypeReference<FindUserByEmailResponse>() {});
  }

  public static FindUserByEmailRequest getFindUserByEmailRequest() throws IOException {
    return new ObjectMapper()
        .readValue(
            LoadStubs.getStubs("find-user-by-email-request.json"),
            new TypeReference<FindUserByEmailRequest>() {});
  }
}
