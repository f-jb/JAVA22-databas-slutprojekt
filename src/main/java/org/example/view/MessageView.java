package org.example.view;

import org.example.controller.UserInput;

public class MessageView {
    public static void error(String error) {
        System.out.println("ERROR");
        System.out.println(error);
        UserInput.getEnter();
    }

    public static void information(String error) {
        System.out.println("INFORMATION");
        System.out.println(error);
        UserInput.getEnter();
    }
}
