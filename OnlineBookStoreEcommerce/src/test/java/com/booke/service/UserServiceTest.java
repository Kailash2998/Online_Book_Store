package com.booke.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.booke.model.User;
import com.booke.repository.UserRepository;

//@ExtendWith(MockitoExtension.class)

public class UserServiceTest {

	 @Mock
	    private UserRepository userRepository;

	    @InjectMocks
	    private UserServiceImpl userService;

	    @Test
	    public void testSaveUser() {
	        User user = new User();
	        user.setUsername("testuser");
	        user.setPassword("password");

	        when(userRepository.save(user)).thenReturn(user);

	        User savedUser = userService.save(user);

	        assertNotNull(savedUser);
	        assertEquals("testuser", savedUser.getUsername());
	        assertEquals("password", savedUser.getPassword());
	    }

//	    @Test
//	    public void testFindByEmail() {
//	        String email = "test@example.com";
//	        User user = new User();
//	        user.setEmail(email);
//
//	        when(userRepository.findByEmail(email)).thenReturn(user);
//
//	        User foundUser = userService.findByEmail(email);
//
//	        assertNotNull(foundUser);
//	        assertEquals(email, foundUser.getEmail());
//	    }

}
