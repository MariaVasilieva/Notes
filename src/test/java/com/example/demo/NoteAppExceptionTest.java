package com.example.demo;



import com.example.demo.exception.NoteAppException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NoteAppExceptionTest {

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private NoteAppException noteAppException;

    @Test
    void testGetLocalizedMessage_WithArgs() {
        // Given
        String message = "User not found";
        Object[] args = new Object[] {"username"};
        noteAppException = new NoteAppException(message, args);
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        resourceBundleMessageSource.setBasename("messages");
        when(messageSource.getMessage(message, args, Locale.US)).thenReturn("User 'username' not found");

        // When
        String localizedMessage = noteAppException.getLocalizedMessage(messageSource, Locale.US);

        // Then
        assertEquals("User 'username' not found", localizedMessage);
        verify(messageSource).getMessage(message, args, Locale.US);
    }

    @Test
    void testGetLocalizedMessage_WithoutArgs() {
        // Given
        String message = "User not found";
        noteAppException = new NoteAppException(message);
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        resourceBundleMessageSource.setBasename("messages");
        when(messageSource.getMessage(message, null, Locale.US)).thenReturn("User not found");

        // When
        String localizedMessage = noteAppException.getLocalizedMessage(messageSource, Locale.US);

        // Then
        assertEquals("User not found", localizedMessage);
        verify(messageSource).getMessage(message, null, Locale.US);
    }

    @Test
    void testGetArgs() {
        // Given
        String message = "User not found";
        Object[] args = new Object[] {"username"};
        noteAppException = new NoteAppException(message, args);

        // When
        Object[] argsReturned = noteAppException.getArgs();

        // Then
        assertNotNull(argsReturned);
        assertEquals(args, argsReturned);
    }
}