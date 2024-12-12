package onetomany;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import onetomany.Users.*;

public class Tests {

	@InjectMocks
	UserController ucon;

	@Mock
	UserRepository repo;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getUserByIdTest() {
		when(repo.findById(999)).thenReturn(new User("Bob", "password"));

		User u1 = ucon.getUserByID(1);

		assertEquals("Bob", u1.getUsername());
		assertEquals("password", u1.getPassword());
	}

}
