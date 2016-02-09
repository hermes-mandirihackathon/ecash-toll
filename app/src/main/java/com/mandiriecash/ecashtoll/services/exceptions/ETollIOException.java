package com.mandiriecash.ecashtoll.services.exceptions;

import java.io.IOException;

public class ETollIOException extends IOException {
    public ETollIOException(IOException e){
        super(e);
    }
}
