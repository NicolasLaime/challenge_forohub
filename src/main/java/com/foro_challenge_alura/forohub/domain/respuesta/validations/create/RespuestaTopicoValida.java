import com.foro_challenge_alura.forohub.domain.respuesta.dto.CrearRespuestaDTO;
import com.foro_challenge_alura.forohub.domain.respuesta.validations.create.ValidarRespuestaCreada;
import com.foro_challenge_alura.forohub.domain.topico.Estado;
import com.foro_challenge_alura.forohub.domain.topico.repository.TopicoRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RespuestaTopicoValida implements ValidarRespuestaCreada {

    @Autowired
    private TopicoRepository repository;

    @Override
    public void validate(CrearRespuestaDTO data) {
        // Verificar si el tópico existe
        var topicoExiste = repository.existsById(data.topicoId());
        if (!topicoExiste) {
            throw new ValidationException("Este tópico no existe.");
        }

        // Obtener el tópico y verificar si está abierto
        var topicoAbierto = repository.findById(data.topicoId())
                .orElseThrow(() -> new ValidationException("El tópico proporcionado no existe."));

        if (topicoAbierto.getEstado() != Estado.OPEN) {
            throw new ValidationException("Este tópico no está abierto para agregar respuestas.");
        }
    }
}
