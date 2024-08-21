package demo.test.jeann.exercise.usecase.getusers;

import demo.test.jeann.exercise.GlobalLogicProtectedController;
import demo.test.jeann.exercise.GlobalLogicServiceFacade;
import demo.test.jeann.exercise.data.GlobalLogicRepository;
import demo.test.jeann.exercise.data.dtos.User;
import demo.test.jeann.exercise.usecases.dosignin.DoSignInUseCase;
import demo.test.jeann.exercise.usecases.finduserbyemail.FindUserByEmailUseCase;
import demo.test.jeann.exercise.usecases.getusers.GetUsersUseCase;
import demo.test.jeann.exercise.usecases.getusers.models.GetUsersResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static demo.test.jeann.exercise.usecase.UserStubs.getUser;
import static demo.test.jeann.exercise.usecase.getusers.GetUsersStubs.getGetUsersResponse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class GetUsersTest {

  @Mock private DoSignInUseCase doSignInUseCase;
  @Mock private FindUserByEmailUseCase findUserByEmailUseCase;
  @Mock private GlobalLogicRepository globalLogicRepository;

  private GlobalLogicProtectedController globalLogicProtectedController;

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(16);
  }

  @Before
  public void setup() throws IOException {

    final GetUsersUseCase getUsersUseCase = new GetUsersUseCase(globalLogicRepository);

    final GlobalLogicServiceFacade globalLogicServiceFacade =
        new GlobalLogicServiceFacade(doSignInUseCase, getUsersUseCase, findUserByEmailUseCase);

    globalLogicProtectedController = new GlobalLogicProtectedController(globalLogicServiceFacade);

    final List<User> listUser = new ArrayList<>();
    listUser.add(
        new User(
            getUser().getId(),
            getUser().getName(),
            getUser().getEmail(),
            getUser().getPassword(),
            getUser().getPhones(),
            getUser().getCreated(),
            getUser().getModified(),
            getUser().getLastLogin(),
            getUser().getToken(),
            getUser().getRoles(),
            getUser().isActive()));

    when(globalLogicRepository.findAll()).thenReturn(listUser);
  }

  @Test
  public void itShouldDoGetUsersWhenApiSuccess() throws IOException {
    final GetUsersResponse getUsersResponse = globalLogicProtectedController.getUsers();

    assertNotNull(getGetUsersResponse());
    assertNotNull(getUsersResponse);
    assertEquals(getUsersResponse, getGetUsersResponse());
  }
}
