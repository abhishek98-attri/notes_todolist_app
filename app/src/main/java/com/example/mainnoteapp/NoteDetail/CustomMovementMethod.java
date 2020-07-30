package com.example.mainnoteapp.NoteDetail;

import android.text.method.ArrowKeyMovementMethod;
import android.text.method.MovementMethod;

class CustomeMovementMethod extends ArrowKeyMovementMethod {

    private static CustomeMovementMethod sInstance;

    public static MovementMethod getInstance() {
        if (sInstance == null) {
            sInstance = new CustomeMovementMethod ();
        }
        return (MovementMethod) sInstance;
    }
}

