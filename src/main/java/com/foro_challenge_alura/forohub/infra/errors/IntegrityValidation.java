package com.foro_challenge_alura.forohub.infra.errors;

import java.io.Serial;

public class IntegrityValidation extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;
    public IntegrityValidation(String s){
      super(s);
    }


}



