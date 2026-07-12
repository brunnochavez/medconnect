package com.bruno.medconnectcenter.exceptions;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<StandardErrorDTO> handleIllegalArgumentException(IllegalArgumentException e, HttpServletRequest request){

        StandardErrorDTO error = new StandardErrorDTO(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                "BAD REQUEST",
                e.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(400).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request){

        List<FieldMessage> listErrors = new ArrayList<>();
        for(FieldError erro : e.getBindingResult().getFieldErrors()){
                listErrors.add(new FieldMessage(erro.getField(), erro.getDefaultMessage()));
        }

        ValidationErrorDTO error = new ValidationErrorDTO(
                Instant.now(),
                422,
                "Erro de validação",
                "Os dados  enviados são inválidos!",
                request.getRequestURI(),
                listErrors
        );

        return ResponseEntity.status(422).body(error);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardErrorDTO> handleEntityNotFoundException(EntityNotFoundException e, HttpServletRequest request){

        StandardErrorDTO error = new StandardErrorDTO(
                Instant.now(),
                HttpStatus.NOT_FOUND.value(),
                "Recurso não encontrado",
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(404).body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardErrorDTO> dataIntegrityViolation(DataIntegrityViolationException e, HttpServletRequest request){

        StandardErrorDTO error = new StandardErrorDTO(
                Instant.now(),
                HttpStatus.CONFLICT.value(),
                "Conflito de dados",
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(409).body(error);
    }

    @ExceptionHandler(OptimisticLockingFailureException.class)
    public ResponseEntity<StandardErrorDTO> optimisticLockingFailure(OptimisticLockingFailureException e, HttpServletRequest request){

        StandardErrorDTO error = new StandardErrorDTO(
                Instant.now(),
                HttpStatus.CONFLICT.value(),
                "Conflito de atualização",
                "Esta consulta foi modificada por outro usuário. Atualize a página e tente novamente",
                request.getRequestURI()
        );

        return ResponseEntity.status(409).body(error);
    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public ResponseEntity<StandardErrorDTO> invalidDataAccessApiUsage(InvalidDataAccessApiUsageException e, HttpServletRequest request){

        StandardErrorDTO error = new StandardErrorDTO(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Parâmetro de busca inválido",
                "Verifique o parâmetro de ordenação (sort) enviado — deve ser um campo existente, no formato 'campo,asc' ou 'campo,desc'.",
                request.getRequestURI()
        );

        return ResponseEntity.status(400).body(error);
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<StandardErrorDTO> dateTimeParse(DateTimeParseException e, HttpServletRequest request){

        StandardErrorDTO error = new StandardErrorDTO(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Formato de data é inválido.",
                "Por favor, envie no padrão Ano-Mês-Dia (AAAA-MM-DD).",
                request.getRequestURI()
        );

        return ResponseEntity.status(400).body(error);
    }
}
