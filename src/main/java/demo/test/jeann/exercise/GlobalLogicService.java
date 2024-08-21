package demo.test.jeann.exercise;

import demo.test.jeann.exercise.usecases.dosignin.models.DoSignInResponse;
import demo.test.jeann.exercise.usecases.getusers.models.GetUsersResponse;
import demo.test.jeann.exercise.usecases.dosignin.models.DoSignInRequest;
import demo.test.jeann.exercise.usecases.finduserbyemail.models.FindUserByEmailRequest;
import demo.test.jeann.exercise.usecases.finduserbyemail.models.FindUserByEmailResponse;

public interface GlobalLogicService {

  DoSignInResponse doSignIn(DoSignInRequest doSignInRequest);

  GetUsersResponse getUsers();

  FindUserByEmailResponse findUserByEmail(FindUserByEmailRequest findUserByEmailRequest);
}
