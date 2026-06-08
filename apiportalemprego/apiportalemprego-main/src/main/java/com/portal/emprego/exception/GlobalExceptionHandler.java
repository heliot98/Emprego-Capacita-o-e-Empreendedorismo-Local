package com.portal.emprego.exception;

import com.portal.emprego.dto.ErroRespostaDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Captura erros de validação (Campos em branco, nulos, inválidos) -> Retorna 400 Bad Request
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroRespostaDTO> tratarValidacao(MethodArgumentNotValidException ex) {
        List<String> erros = new ArrayList<>();
        
        for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
            erros.add(fe.getField() + ": " + fe.getDefaultMessage());
        }

        ErroRespostaDTO erroResposta = new ErroRespostaDTO(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Dados inválidos na requisição",
                erros
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroResposta);
    }

    // Captura erros quando um recurso não é encontrado (Vaga/Curso inexistente) -> Retorna 404 Not Found
    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroRespostaDTO> tratarNaoEncontrado(RecursoNaoEncontradoException ex) {
        ErroRespostaDTO erroResposta = new ErroRespostaDTO(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Recurso não encontrado",
                List.of(ex.getMessage())
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroResposta);
    }
    // Captura erro de email já cadastrado (tentativa de register com email duplicado) -> Retorna 409 Conflict
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErroRespostaDTO> tratarArgumentoInvalido(IllegalArgumentException ex) {
        ErroRespostaDTO erroResposta = new ErroRespostaDTO(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "Conflito de dados",
                List.of(ex.getMessage())
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(erroResposta);
    }

    // Captura erro de credenciais incorretas no login (email ou senha errados) -> Retorna 401 Unauthorized
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErroRespostaDTO> tratarCredenciaisInvalidas(BadCredentialsException ex) {
        ErroRespostaDTO erroResposta = new ErroRespostaDTO(
                LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                "Credenciais invalidas",
                List.of("Email ou senha incorretos.")
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erroResposta);
    }
}
