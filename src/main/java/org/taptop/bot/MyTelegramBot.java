package org.taptop.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyTelegramBot extends TelegramLongPollingBot {

    @Value("${telegram.bot.username}")
    private String botUsername;

    @Value("${telegram.bot.token}")
    private String botToken;

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            String text = message.getText();

            // Check if user has sent the /start command
            if ("/start".equals(text)) {
                sendMenu(message.getChatId().toString());
            } else {
                sendResponse(message.getChatId().toString(), "You said: " + text);
            }
        }
    }

    // Helper method to send a response message
    public void sendResponse(String chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);

        try {
            execute(message); // Call execute to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    // Method to send the custom menu with "Play Game" and "Check Score" options
    public void sendMenu(String chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Choose an option:");

        // Create a keyboard markup for the menu
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        // Create a row for the keyboard with buttons
        KeyboardRow row = new KeyboardRow();
        row.add(new KeyboardButton("Play Game"));
        row.add(new KeyboardButton("Check Score"));

        // Add the row to the keyboard
        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);

        // Set the keyboard markup to the message
        message.setReplyMarkup(keyboardMarkup);

        try {
            execute(message); // Send the message with the keyboard
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}