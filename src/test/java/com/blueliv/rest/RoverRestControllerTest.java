package com.blueliv.rest;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.blueliv.RoverApplication;
import com.blueliv.controller.MarsRoverController;
import com.blueliv.controller.PlateauResponse;
import com.blueliv.controller.command.CommandFactoryProvider;
import com.blueliv.controller.command.CompositeCommand;
import com.blueliv.controller.command.exception.CommandFormatException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoverApplication.class)
@WebAppConfiguration
public class RoverRestControllerTest {

	private static final String EMPTY_BODY = "";

	private static final String ERROR_MESSAGE = EMPTY_BODY;

	private static final String ROVER_ENDPOINT = "/rover";

	private static final String PLAIN_UTF_8_MEDIATYPE = MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8";

	private static final String REQUEST_BODY_OK = "5 5\n1 3 N\nMMRLR";
	private static final String REQUEST_BODY_WRONG_NUMBER_OF_LINES = "3 3\n1 3 N\nMMRLR\n1 4 N";
	private static final String REQUEST_BODY_WRONG_COMMAND_FORMAT = "3 3\n1 3N\nMMRLR";
	private static final String OK_RESPONSE = "1 5 E";

	private MockMvc mockMvc;

	@InjectMocks
	RoverRestController roverRestController;

	@Mock
	MarsRoverController marsRoverControllerMock;

	@Mock
	CommandFactoryProvider commandFactoryProviderMock;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(roverRestController).build();
	}

	@Test
	public void postCommandsOK() throws Exception {
		PlateauResponse response = new PlateauResponse(OK_RESPONSE);
		CompositeCommand command = new CompositeCommand();

		when(commandFactoryProviderMock.parseCommand(same(CompositeCommand.class), eq(REQUEST_BODY_OK)))
				.thenReturn(command);
		when(marsRoverControllerMock.processCommands(any(CompositeCommand.class))).thenReturn(response);

		mockMvc.perform(post(ROVER_ENDPOINT).contentType(PLAIN_UTF_8_MEDIATYPE).content(REQUEST_BODY_OK))
				.andExpect(status().isOk())
				.andExpect(content().contentType(PLAIN_UTF_8_MEDIATYPE))
				.andExpect(content().string(OK_RESPONSE));

		verify(commandFactoryProviderMock, times(1)).parseCommand(same(CompositeCommand.class), eq(REQUEST_BODY_OK));
		verify(marsRoverControllerMock, times(1)).processCommands(same(command));
		verifyNoMoreInteractions(marsRoverControllerMock);

	}

	@Test
	public void postCommandsEmptyBodyKO() throws Exception {

		mockMvc.perform(post(ROVER_ENDPOINT).contentType(MediaType.TEXT_PLAIN).content(EMPTY_BODY))
				.andExpect(status().isBadRequest());

		verifyZeroInteractions(commandFactoryProviderMock);
		verifyZeroInteractions(marsRoverControllerMock);

	}

	@Test
	public void postCommandsWrongNumberOfLinesKO() throws Exception {
		when(commandFactoryProviderMock.parseCommand(same(CompositeCommand.class), any(String.class)))
				.thenThrow(new CommandFormatException(ERROR_MESSAGE));

		mockMvc.perform(
				post(ROVER_ENDPOINT).contentType(MediaType.TEXT_PLAIN).content(REQUEST_BODY_WRONG_NUMBER_OF_LINES))
				.andExpect(status().isBadRequest())
				.andExpect(content().string(ERROR_MESSAGE));

		verify(commandFactoryProviderMock, times(1)).parseCommand(same(CompositeCommand.class),
				eq(REQUEST_BODY_WRONG_NUMBER_OF_LINES));
		verifyZeroInteractions(marsRoverControllerMock);
	}

	@Test
	public void postCommandsWrongCommandFormatKO() throws Exception {
		when(commandFactoryProviderMock.parseCommand(same(CompositeCommand.class), any(String.class)))
				.thenThrow(new CommandFormatException(ERROR_MESSAGE));

		mockMvc.perform(
				post(ROVER_ENDPOINT).contentType(MediaType.TEXT_PLAIN).content(REQUEST_BODY_WRONG_COMMAND_FORMAT))
				.andExpect(status().isBadRequest())
				.andExpect(content().string(ERROR_MESSAGE));

		verify(commandFactoryProviderMock, times(1)).parseCommand(same(CompositeCommand.class),
				eq(REQUEST_BODY_WRONG_COMMAND_FORMAT));
		verifyZeroInteractions(marsRoverControllerMock);
	}

}
