package demo.test.jeann.exercise;

import demo.test.jeann.exercise.usecases.getusers.GetUsersUseCase;
import demo.test.jeann.exercise.usecases.getusers.models.GetUsersResponse;
import demo.test.jeann.exercise.usecases.dosignin.DoSignInUseCase;
import demo.test.jeann.exercise.usecases.dosignin.models.DoSignInRequest;
import demo.test.jeann.exercise.usecases.dosignin.models.DoSignInResponse;
import demo.test.jeann.exercise.usecases.finduserbyemail.FindUserByEmailUseCase;
import demo.test.jeann.exercise.usecases.finduserbyemail.models.FindUserByEmailRequest;
import demo.test.jeann.exercise.usecases.finduserbyemail.models.FindUserByEmailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GlobalLogicServiceFacade implements GlobalLogicService {

  // region field
  private final DoSignInUseCase doSignInUseCase;
  private final GetUsersUseCase getUsersUseCase;
  private final FindUserByEmailUseCase findUserByEmailUseCase;
  // endregion

  @Autowired
  public GlobalLogicServiceFacade(
      final DoSignInUseCase doSignInUseCase,
      final GetUsersUseCase getUsersUseCase,
      final FindUserByEmailUseCase findUserByEmailUseCase) {
    this.doSignInUseCase = doSignInUseCase;
    this.getUsersUseCase = getUsersUseCase;
    this.findUserByEmailUseCase = findUserByEmailUseCase;
  }

  @Override
  public DoSignInResponse doSignIn(final DoSignInRequest doSignInRequest) {
    // Redirect to doSignInUseCase
    return doSignInUseCase.doSignIn(doSignInRequest);
  }

  @Override
  public GetUsersResponse getUsers() {
    // Redirect to doGetUsers
    return getUsersUseCase.getUsers();
  }

  @Override
  public FindUserByEmailResponse findUserByEmail(FindUserByEmailRequest findUserByEmailRequest) {
    // Redirect to findUserByEmail
    return findUserByEmailUseCase.findUserByEmail(findUserByEmailRequest);
  }
}
