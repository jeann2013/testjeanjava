package demo.test.jeann.exercise.usecase.finduserbyemail;

import demo.test.jeann.exercise.GlobalLogicProtectedController;
import demo.test.jeann.exercise.GlobalLogicServiceFacade;
import demo.test.jeann.exercise.data.GlobalLogicRepository;
import demo.test.jeann.exercise.data.dtos.User;
import demo.test.jeann.exercise.usecases.dosignin.DoSignInUseCase;
import demo.test.jeann.exercise.usecases.finduserbyemail.FindUserByEmailUseCase;
import demo.test.jeann.exercise.usecases.finduserbyemail.models.FindUserByEmailResponse;
import demo.test.jeann.exercise.usecases.getusers.GetUsersUseCase;
import demo.test.jeann.exercise.utilities.exceptions.ExceptionHandlerResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

import static demo.test.jeann.exercise.usecase.UserStubs.getUser;
import static demo.test.jeann.exercise.usecase.finduserbyemail.FindUserByEmailStubs.getFindUserByEmailRequest;
import static demo.test.jeann.exercise.usecase.finduserbyemail.FindUserByEmailStubs.getFindUserByEmailResponse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class FindUserByEmailTest {

  @Mock private DoSignInUseCase doSignInUseCase;
  @Mock private GetUsersUseCase getUsersUseCase;
  @Mock private GlobalLogicRepository globalLogicRepository;

  private GlobalLogicProtectedController globalLogicProtectedController;

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(16);
  }

  @Before
  public void setup() throws IOException {

    final FindUserByEmailUseCase findUserByEmailUseCase =
        new FindUserByEmailUseCase(globalLogicRepository);

    final GlobalLogicServiceFacade globalLogicServiceFacade =
        new GlobalLogicServiceFacade(doSignInUseCase, getUsersUseCase, findUserByEmailUseCase);

    globalLogicProtectedController = new GlobalLogicProtectedController(globalLogicServiceFacade);

    when(globalLogicRepository.findByEmail(any(String.class)))
        .thenReturn(
            new User(
                getUser().getId(),
                getUser().getCreated(),
                getUser().getModified(),
                getUser().getLastLogin(),
                getUser().getToken(),
                getUser().isActive()));
  }

  @Test
  public void itShouldFindUserByEmailWhenApiSuccess() throws IOException {
    assertNotNull(getFindUserByEmailRequest());

    final FindUserByEmailResponse findUserByEmailResponse =
        globalLogicProtectedController.findUserByEmail(getFindUserByEmailRequest());

    assertNotNull(getFindUserByEmailResponse());
    assertNotNull(findUserByEmailResponse);
    assertEquals(findUserByEmailResponse, getFindUserByEmailResponse());
  }

  @Test
  public void itShouldFindUserByEmailWhenApiFailure() throws IOException {
    assertNotNull(getFindUserByEmailRequest());
    try {
      globalLogicProtectedController.findUserByEmail(null);
    } catch (NullPointerException ex) {
      assertEquals(new ExceptionHandlerResponse<>(null).getMessage(), ex.getMessage());
    }
  }
}
